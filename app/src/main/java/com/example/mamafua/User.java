package com.example.mamafua;


public class User {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataPhone;
    private String dataImage;
    private String key;

    private String image;

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;
    private String userId;


    public User(String dataTitle, String name, String onlineStatus, String typingTo) {
        this.dataTitle = dataTitle;
        this.name = name;
        this.onlineStatus = onlineStatus;
        this.typingTo = typingTo;
    }

    String onlineStatus;

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }

    String typingTo;

    public String getUserId() {
        return userId;
    }




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLang() {
        return dataLang;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getDataPhone(){
        return dataPhone;
    }

    public User(String dataTitle, String dataPhone, String dataDesc, String dataLang, String dataImage, String userId) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.dataPhone = dataPhone;
        this.userId = userId;
    }
    public User(){

    }



}