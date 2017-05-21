package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.models.Chat;

import java.util.List;

/**
 * Created by vihaan on 21/05/17.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {


    private List<Chat> mChats;

    public ChatsAdapter(List<Chat> chats) {
        mChats = chats;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public ChatViewHolder(View v) {
            super(v);
        }
    }

}
