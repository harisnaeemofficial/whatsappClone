package com.example.vihaan.whatsappclone.ui.homescreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.chatscreen.ChatActivity;
import com.example.vihaan.whatsappclone.ui.common.adapters.ViewPagerTabAdapter;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.example.vihaan.whatsappclone.ui.userListScreen.UserListActivity;
import com.example.vihaan.whatsappclone.ui.welcomeScreen.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.vihaan.whatsappclone.ui.chatscreen.ChatActivity.tag;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initData();
        initViews();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            User user = bundle.getParcelable(ChatActivity.EXTRAS_USER);
            if(user != null)
            {
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(ChatActivity.EXTRAS_USER, user);
                startActivity(intent);
            }
        }
    }

    private void initViews() {
        initToolbar();
        initNewMessageFloatingButton();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNewMessageFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private ViewPager mViewPager;

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Chats");
        tabNames.add("Status");
        tabNames.add("Calls");
        ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter(getSupportFragmentManager(), getFragments(), tabNames);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(viewPagerTabAdapter);
    }


    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
    }


    private List<Fragment> mFragments;

    private List<Fragment> getFragments() {

        mFragments = new ArrayList<Fragment>();
        mFragments.add(UserChatsFragment.newInstance(""));
        mFragments.add(StatusFragment.newInstance());
        mFragments.add(CallsFragment.newInstance());

        return mFragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}