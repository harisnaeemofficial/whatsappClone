package com.example.vihaan.whatsappclone.ui;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

/**
 * Created by vihaan on 18/06/17.
 */

public class Util {

    public static String getMessageNode(String sendingUid, String receivingUid)
    {
        String node = "";

        int result = sendingUid.compareTo(receivingUid);

        if(result == 0)
        {

        }
        else if (result < 0)
        {
            node = sendingUid + "-" + receivingUid;
        }
        else if(result > 0)
        {
            node = receivingUid + "-" +sendingUid  ;
        }

        return node;
    }

    public static void updateToken()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    String token = FirebaseInstanceId.getInstance().getToken();
                    if(!TextUtils.isEmpty(token))
                    {
                        Log.d("updateToken: ", token);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
