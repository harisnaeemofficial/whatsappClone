package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vihaan on 21/05/17.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public ChatViewHolder(View v) {
            super(v);
        }
    }

}
