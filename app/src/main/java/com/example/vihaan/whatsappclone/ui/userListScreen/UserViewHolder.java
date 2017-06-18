package com.example.vihaan.whatsappclone.ui.userListScreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vihaan.whatsappclone.R;
import com.example.vihaan.whatsappclone.ui.models.User;

/**
 * Created by vihaan on 18/06/17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView userIV;
    public TextView usernameTV;
    public TextView statusTV;

    public UserViewHolder(View itemView) {
        super(itemView);

        userIV = (ImageView) itemView.findViewById(R.id.userIV);
        usernameTV = (TextView) itemView.findViewById(R.id.userNameTV);
        statusTV = (TextView) itemView.findViewById(R.id.statusTV);
    }

    public void bindToUser(User user, View.OnClickListener starClickListener) {

    }
}
