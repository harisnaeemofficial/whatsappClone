package com.example.vihaan.whatsappclone;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by vihaan on 23/06/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init()
    {

        initFirebase();
    }

    private void initFirebase()
    {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
