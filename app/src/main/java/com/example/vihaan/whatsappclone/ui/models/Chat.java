
package com.example.vihaan.whatsappclone.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat implements Parcelable {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("message")
    @Expose
    private ChatListMessage chatListMessage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatListMessage getChatListMessage() {
        return chatListMessage;
    }

    public void setChatListMessage(ChatListMessage chatListMessage) {
        this.chatListMessage = chatListMessage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.chatListMessage, flags);
    }

    public Chat() {
    }

    protected Chat(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.chatListMessage = in.readParcelable(ChatListMessage.class.getClassLoader());
    }

    public static final Parcelable.Creator<Chat> CREATOR = new Parcelable.Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel source) {
            return new Chat(source);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}
