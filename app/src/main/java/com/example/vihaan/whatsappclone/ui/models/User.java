
package com.example.vihaan.whatsappclone.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@IgnoreExtraProperties
public class User implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("profile_pic_url")
    @Expose
    private String profilePicUrl = "";
    @SerializedName("last_seen")
    @Expose
    private String lastSeen = "";
    @SerializedName("is_typing")
    @Expose
    private Boolean isTyping = false;
    @SerializedName("status")
    @Expose
    private String status = "";

    @SerializedName("uid")
    @Expose
    private String uid = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Boolean getIsTyping() {
        return isTyping;
    }

    public void setIsTyping(Boolean isTyping) {
        this.isTyping = isTyping;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.profilePicUrl);
        dest.writeString(this.lastSeen);
        dest.writeValue(this.isTyping);
        dest.writeString(this.status);
        dest.writeString(this.uid);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.profilePicUrl = in.readString();
        this.lastSeen = in.readString();
        this.isTyping = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.status = in.readString();
        this.uid= in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
