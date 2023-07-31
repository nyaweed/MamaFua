package com.example.mamafua;

public class ModelUsers {
    String name;
    String email;
    String image;

    public ModelUsers() {
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    String onlineStatus;
    String typingTo;

    public ModelUsers(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    String fcmToken;

    public String getName() {
        return name;
    }
    public void setOnlineStatus(){
        this.onlineStatus = onlineStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }

    public String getEmail() {
        return email;
    }

    public ModelUsers(String name, String email, String image, String onlineStatus, String typingTo, String uid) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.onlineStatus = onlineStatus;
        this.typingTo = typingTo;
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    String uid;
}
