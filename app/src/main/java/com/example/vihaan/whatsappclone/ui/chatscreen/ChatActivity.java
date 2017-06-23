package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.models.Chat;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vihaan on 21/05/17.
 */

public class ChatActivity extends AppCompatActivity {

    public static final String EXTRAS_USER= "user";
    public static final String tag = ChatActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        setArguments();
        initView();
    }

    private void initView() {
        initToolbar();
        showFragment();
    }

    private void showFragment()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, ChatFragment.newInstance(getIntent().getExtras()));
        ft.commit();
    }

    private Chat mChat;
    private User mUser;

    private void setArguments() {
        Bundle bundle = getIntent().getExtras();
        mUser = bundle.getParcelable(EXTRAS_USER);
        if(mUser != null)
        {
            setTitle(mUser.getName());
        }
    }


    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(mUser.getName());

        CircleImageView userIV = (CircleImageView) findViewById(R.id.userIV);

        if(!TextUtils.isEmpty(mUser.getProfilePicUrl()))
        {
            Picasso.with(this).load(mUser.getProfilePicUrl()).into(userIV);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Toolbar","Clicked");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, " onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, " onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, " onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, " onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, " onDestroy()");
    }
}
