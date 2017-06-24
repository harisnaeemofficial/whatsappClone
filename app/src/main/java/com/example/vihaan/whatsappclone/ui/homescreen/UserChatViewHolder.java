package com.example.vihaan.whatsappclone.ui.homescreen;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.Database;
import com.example.vihaan.whatsappclone.ui.models.User;
import com.example.vihaan.whatsappclone.ui.models.UserChat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vihaan on 18/06/17.
 */

public class UserChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    RelativeLayout chatRL;
    CircleImageView userIV;
    TextView nameTV;
    TextView messageTV;
    TextView lastMessageTimeTV;
    TextView unreadMessageCountTV;

    public UserChatViewHolder(View itemView) {
        super(itemView);

        chatRL = (RelativeLayout) itemView.findViewById(R.id.chatRL);
        userIV = (CircleImageView) itemView.findViewById(R.id.userIV);
        nameTV = (TextView) itemView.findViewById(R.id.nameTV);
        messageTV = (TextView) itemView.findViewById(R.id.messageTV);
        lastMessageTimeTV = (TextView) itemView.findViewById(R.id.lastMessageTimeTV);
        unreadMessageCountTV = (TextView) itemView.findViewById(R.id.unreadMessageCountTV);
//        chatRL.setOnClickListener(this);
    }

    private ValueEventListener mValueEventListener;
    private DatabaseReference mUserNode;
    public void bindToUserChat(UserChat userChat, View.OnClickListener starClickListener) {

        String uid = userChat.getUid();

        mUserNode = FirebaseDatabase.getInstance().getReference().child(Database.NODE_USERS).child(uid);

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if (!TextUtils.isEmpty(user.getProfilePicUrl())) {
                    Picasso.with(userIV.getContext()).load(user.getProfilePicUrl()).into(userIV);
                }
                nameTV.setText(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mUserNode.addValueEventListener(mValueEventListener);

        messageTV.setText(userChat.getLastMessage());
        lastMessageTimeTV.setText(userChat.getLastMessageTime());

        if (TextUtils.isEmpty(userChat.getUnreadMessageCount())) {
            unreadMessageCountTV.setVisibility(View.GONE);
        } else {
            unreadMessageCountTV.setVisibility(View.VISIBLE);
            unreadMessageCountTV.setText(userChat.getUnreadMessageCount());
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void onViewDetachedFromWindow()
    {
        if(mValueEventListener != null)
        {
            mUserNode.removeEventListener(mValueEventListener);
        }
    }
}
