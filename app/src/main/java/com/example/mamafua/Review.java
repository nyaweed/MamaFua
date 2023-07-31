package com.example.mamafua;

public class Review {

    private String userId;

    public Review(String reviewText) {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private String reviewText;

    public Review(String userId, String reviewText, float rating) {
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    private float rating;
}
