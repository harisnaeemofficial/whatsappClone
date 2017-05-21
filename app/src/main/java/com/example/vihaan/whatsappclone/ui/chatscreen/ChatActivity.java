package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.models.Chat;

/**
 * Created by vihaan on 21/05/17.
 */

public class ChatActivity extends AppCompatActivity {

    public static final String EXTRAS_CHAT = "chat";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init()
    {
        setArguments();
        initView();
    }

    private void initView()
    {

    }

    private Chat mChat;
    private void setArguments()
    {
        Bundle bundle = getIntent().getExtras();
        mChat = bundle.getParcelable(EXTRAS_CHAT);
        if(mChat != null)
        {
            setTitle(mChat.getUser().getName());
        }
    }









































}
