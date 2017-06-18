package com.example.vihaan.whatsappclone.ui.userListScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.chatscreen.ChatFragment;

/**
 * Created by vihaan on 18/06/17.
 */

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initViews();
    }

    private void initViews()
    {

        showFragment();
    }


    private void showFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, ChatFragment.newInstance());
        ft.commit();
    }

}

