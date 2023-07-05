package com.example.mamafua;

public class User {

    private String name;
    private String email;
    private String profilePictureUrl;

    // Default constructor (required for Firebase)
    public User() {
    }

    public User(String name, String email, String profilePictureUrl) {
        this.name = name;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Object getProfilePictureUrl() {
        return profilePictureUrl;
    }

    // Getter and setter methods
    // ...
}
