package com.example.vihaan.whatsappclone.ui;

/**
 * Created by vihaan on 18/06/17.
 */

public class Util {

    public static String getMessageNode(String sendingUid, String receivingUid)
    {
        String node = "";

        switch (sendingUid.compareTo(receivingUid))
        {
            case 0:

                break;

            case 1:
                //sending is greater than receiving
                node = receivingUid + "-" +sendingUid  ;
                break;

            case -1:
                //sending is lesser than receiving
                node = sendingUid + "-" + receivingUid;
                break;

        }
        return node;
    }
}
