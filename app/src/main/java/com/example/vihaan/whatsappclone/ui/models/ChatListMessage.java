
package com.example.vihaan.whatsappclone.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatListMessage implements Parcelable {

    @SerializedName("last_message")
    @Expose
    private String lastMessage;
    @SerializedName("last_message_time")
    @Expose
    private String lastMessageTime;
    @SerializedName("unread_message_count")
    @Expose
    private String unreadMessageCount;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(String unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lastMessage);
        dest.writeString(this.lastMessageTime);
        dest.writeString(this.unreadMessageCount);
    }

    public ChatListMessage() {
    }

    protected ChatListMessage(Parcel in) {
        this.lastMessage = in.readString();
        this.lastMessageTime = in.readString();
        this.unreadMessageCount = in.readString();
    }

    public static final Parcelable.Creator<ChatListMessage> CREATOR = new Parcelable.Creator<ChatListMessage>() {
        @Override
        public ChatListMessage createFromParcel(Parcel source) {
            return new ChatListMessage(source);
        }

        @Override
        public ChatListMessage[] newArray(int size) {
            return new ChatListMessage[size];
        }
    };
}
