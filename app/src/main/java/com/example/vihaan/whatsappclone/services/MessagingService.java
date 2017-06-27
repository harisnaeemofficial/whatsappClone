package com.example.vihaan.whatsappclone.services;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*
 * Created by Mahmoud on 3/13/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //todo: handle notification
        Log.d("data message", remoteMessage.getData().toString());

        final String data = remoteMessage.getData().toString();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(MessagingService.this.getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
