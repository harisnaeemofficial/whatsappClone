package com.example.vihaan.whatsappclone.ui.homescreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.apiclient.ApiClient;
import com.example.vihaan.whatsappclone.apiclient.ApiInterface;
import com.example.vihaan.whatsappclone.ui.models.Chat;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vihaan on 20/05/17.
 */

public class ChatsFragment extends Fragment {

    public static final String EXTRA_TAB_NAME = "tab_name";
    private String mTabName;

    public static ChatsFragment newInstance(String tabName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TAB_NAME, tabName);
        ChatsFragment fragment = new ChatsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        mTabName = getArguments().getString(EXTRA_TAB_NAME);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        getChats();
    }

    private void initViews()
    {
        initRecyclerView();

    }

    private RecyclerView mRecyclerView;
    private ChatsAdapter mAdapter;

    private void initRecyclerView()
    {
        mRecyclerView= (RecyclerView) getView().findViewById(R.id.chatsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getChats()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Chat>> call = apiService.getChats();
        call.enqueue(new Callback<ArrayList<Chat>>() {
            @Override
            public void onResponse(Call<ArrayList<Chat>> call, Response<ArrayList<Chat>> response) {

                Log.d("response:", response.body().toString());
                Log.d("json response:", new Gson().toJson(response.body()));
                mAdapter = new ChatsAdapter(getActivity(),response.body());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Chat>> call, Throwable t) {

            }
        });

    }
}
