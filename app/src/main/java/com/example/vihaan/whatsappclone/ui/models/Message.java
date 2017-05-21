
package com.example.vihaan.whatsappclone.ui.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

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

}
