package com.example.vihaan.whatsappclone.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.Database;
import com.example.vihaan.whatsappclone.ui.homescreen.MainActivity;
import com.example.vihaan.whatsappclone.ui.models.Message;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import static android.R.attr.id;

/*
 * Created by Mahmoud on 3/13/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        //todo: handle notification
        Log.d("data message", remoteMessage.getData().toString());

        final String data = remoteMessage.getData().toString();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(MessagingService.this.getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                Message message = new Gson().fromJson(remoteMessage.getData().toString(), Message.class);
                onMessageReceived(message);
            }
        });

    }

    private void onMessageReceived(final Message message)
    {
        String senderUid = message.getSenderUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(Database.NODE_USERS).child(senderUid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null)
                {
                    User user = dataSnapshot.getValue(User.class);
                    showNotification(MessagingService.this.getApplicationContext(), user, message);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showNotification(Context context, User user, Message message)
    {

        User sendingUser;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//        String title = context.getString(titleResId);
        String title = user.getName();
//        String text = context.getString(textResId);
        String text = message.getData();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.notification)
                .setSmallIcon(R.drawable.ic_action_message)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setTicker(title)
                .setContentIntent(contentIntent);
        notificationManager.notify(id, builder.build());
    }

}
