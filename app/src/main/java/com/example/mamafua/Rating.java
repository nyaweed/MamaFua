package com.example.mamafua;

import java.util.HashMap;
import java.util.Map;

public class Rating {
    private String userId;
    private String vendorId;
    private int rating;

    public Rating(String vendorId) {
        this.vendorId = vendorId;
    }

    public Rating() {
    }

    public Rating(String userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public Rating(String userId, String vendorId, int rating) {

    }

    public String getUserId() {
        return userId;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("vendorId", vendorId);
        result.put("rating", rating);
        return result;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

