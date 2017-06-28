package com.example.vihaan.whatsappclone.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.Database;
import com.example.vihaan.whatsappclone.ui.chatscreen.ChatActivity;
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
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.R.attr.value;

/*
 * Created by Mahmoud on 3/13/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        //todo: handle notification
        Log.d("data message", remoteMessage.getData().toString());


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {

                    Gson gson = new Gson();
                    String data = remoteMessage.getData().toString();

                    Toast.makeText(MessagingService.this.getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    JsonElement jsonElement  = gson.toJsonTree(remoteMessage.getData());
                    Message message = gson.fromJson(jsonElement, Message.class);
                    onMessageReceived(message);
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }
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

    private final int NOTIFICATION_ID = 237;

    private void showNotification(final Context context, final User user, final Message message)
    {


        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

                String title = user.getName();
                String text = message.getData();

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra(ChatActivity.EXTRAS_USER, user);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                /*


                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.notification)
//                        .setSmallIcon(R.drawable.ic_action_message)
                        .setSmallIcon(R.drawable.ic_action_message)
                        .setLargeIcon(getCircleBitmap(bitmap))
                        .setContentTitle(title)
                        .setContentText(text)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setTicker(title)
                        .setContentIntent(contentIntent);
                notificationManager.notify(NOTIFICATION_ID, builder.build());

                */


                Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
                NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder1 = new Notification.Builder(context);
//                builder1.setContentTitle("Lanes");
                builder1.setContentTitle(user.getName());
//                builder1.setContentText("Notification from Lanes"+value);
                builder1.setContentText(message.getData());
                builder1.setSmallIcon(R.drawable.notifybar);
                builder1.setLargeIcon(bitmap);
                builder1.setContentIntent(contentIntent);
                builder1.setAutoCancel(true);
                inboxStyle.setBigContentTitle("Enter Content Text");
                inboxStyle.addLine("hi events "+value);
                builder1.setStyle(inboxStyle);
                nManager.notify("App Name",NOTIFICATION_ID,builder1.build());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };



        Picasso.with(this).load(user.getProfilePicUrl()).into(target);

    }



    private void showMultipleMessageNotification()
    {


    }



    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
