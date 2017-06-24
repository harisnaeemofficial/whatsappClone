package com.example.vihaan.whatsappclone.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vihaan on 23/06/17.
 */


public class UserChat implements Parcelable {

    @SerializedName("uid")
    @Expose
    private String uid = "";
    @SerializedName("lastMessage")
    @Expose
    private String lastMessage = "";
    @SerializedName("unreadMessageCount")
    @Expose
    private String unreadMessageCount = "";
    @SerializedName("lastMessageTime")
    @Expose
    private String lastMessageTime = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(String unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.lastMessage);
        dest.writeString(this.unreadMessageCount);
        dest.writeString(this.lastMessageTime);
    }

    public UserChat() {
    }

    protected UserChat(Parcel in) {
        this.uid = in.readString();
        this.lastMessage = in.readString();
        this.unreadMessageCount = in.readString();
        this.lastMessageTime = in.readString();
    }

    public static final Creator<UserChat> CREATOR = new Creator<UserChat>() {
        @Override
        public UserChat createFromParcel(Parcel source) {
            return new UserChat(source);
        }

        @Override
        public UserChat[] newArray(int size) {
            return new UserChat[size];
        }
    };
}