package com.example.vihaan.whatsappclone.ui.chatscreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;

import java.util.List;

/**
 * Created by vihaan on 11/06/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {


    private List<ChatMessage> mChatMessages;
    private Context mContext;

    private static final String MSG_TYPE_TEXT = "text";
    private static final String MSG_TYPE_IMG= "image";

    public ChatAdapter(Context context, List<ChatMessage> chatMessages)
    {
        mContext = context;
        mChatMessages = chatMessages;
    }



    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatViewHolder holder, int position) {

        ChatMessage chatMessage = mChatMessages.get(position);

        if(chatMessage.getType().equalsIgnoreCase(MSG_TYPE_TEXT))
        {
            holder.messageTV.setVisibility(View.VISIBLE);
            holder.messageTV.setText(chatMessage.getMessage());
            holder.chatIV.setVisibility(View.GONE);
        }
        else if (chatMessage.getType().equals(MSG_TYPE_IMG))
        {
            holder.messageTV.setVisibility(View.GONE);
            holder.chatIV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }


    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView messageTV;
        ImageView chatIV;

        public ChatViewHolder(View v){
            super(v);
            messageTV = (TextView) v.findViewById(R.id.chatTV);
            chatIV = (ImageView) v.findViewById(R.id.chatIV);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
            }
        }
    }
}
