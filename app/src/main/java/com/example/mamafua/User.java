package com.example.mamafua;


public class User {

    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataPhone;
    private String dataImage;
    private String key;

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

    public User(String dataTitle, String dataPhone, String dataDesc, String dataLang, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataLang = dataLang;
        this.dataImage = dataImage;
        this.dataPhone = dataPhone;
    }
    public User(){

    }


}