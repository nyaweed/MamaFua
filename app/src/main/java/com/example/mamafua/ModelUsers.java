package com.example.mamafua;

public class ModelUsers {
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    String name;
    String email;
    String image;

    public ModelUsers() {
    }

    String onlineStatus;
    String typingTo;

    public String getName() {
        return name;
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