package com.example.vihaan.whatsappclone.ui.homescreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.models.Chat;
import com.example.vihaan.whatsappclone.ui.models.ChatListMessage;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vihaan on 21/05/17.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {


    private List<Chat> mChats;
    private Context mContext;

    public ChatsAdapter(Context context, List<Chat> chats) {
        mContext = context;
        mChats = chats;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        Chat chat = mChats.get(position);
        User user = chat.getUser();
        ChatListMessage chatListMessage = chat.getChatListMessage();

        if(!TextUtils.isEmpty(user.getProfilePicUrl()))
        {
            Picasso.with(holder.userIV.getContext()).load(user.getProfilePicUrl()).into(holder.userIV);
        }
        holder.nameTV.setText(user.getName());
        holder.messageTV.setText(chatListMessage.getLastMessage());
        holder.lastMessageTimeTV.setText(chatListMessage.getLastMessageTime());

        if(TextUtils.isEmpty(chatListMessage.getUnreadMessageCount()))
        {
            holder.unreadMessageCountTV.setVisibility(View.GONE);
        }
        else
        {
            holder.unreadMessageCountTV.setVisibility(View.VISIBLE);
            holder.unreadMessageCountTV.setText(chat.getChatListMessage().getUnreadMessageCount());
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout chatRL;
        CircleImageView userIV;
        TextView nameTV;
        TextView messageTV;
        TextView lastMessageTimeTV;
        TextView unreadMessageCountTV;

        public ChatViewHolder(View v){
            super(v);
            chatRL = (RelativeLayout) v.findViewById(R.id.chatRL);
            userIV = (CircleImageView) v.findViewById(R.id.userIV);
            nameTV = (TextView) v.findViewById(R.id.nameTV);
            messageTV = (TextView) v.findViewById(R.id.messageTV);
            lastMessageTimeTV = (TextView) v.findViewById(R.id.lastMessageTimeTV);
            unreadMessageCountTV= (TextView) v.findViewById(R.id.unreadMessageCountTV);
            chatRL.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
//                Chat chat = mChats.get(position);
//                Intent intent = new Intent(mContext, ChatActivity.class);
//                intent.putExtra(ChatActivity.EXTRAS_CHAT,chat);
//                mContext.startActivity(intent);
            }
        }
    }


}
