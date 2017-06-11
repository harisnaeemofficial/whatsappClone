package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.vihaan.whatsappclone.ui.homescreen.ChatsAdapter;

import java.util.List;

/**
 * Created by vihaan on 11/06/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {


    private List<ChatMessage> mChatMessages;
    private Context mContext;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages)
    {

        mContext = context;
        mChatMessages = chatMessages;
    }



    @Override
    public ChatsAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatsAdapter.ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
