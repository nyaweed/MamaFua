package com.example.mamafua;

import android.app.Dialog;
import android.content.Context;
import android.location.GnssAntennaInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewDialog extends Dialog {
    private Context context;
    private DatabaseReference reviewRef;
    private AlertDialog dialog;
    private FirebaseAuth auth;
    private ReviewsAdapter reviewsAdapter;
    private TextView reviewBar;

    private Button btnSubmit;
    private String vendorId;

    public ReviewDialog(Context context, String vendorId) {
        super(context);
        this.context = context;
        this.vendorId = vendorId;
        reviewRef = FirebaseDatabase.getInstance().getReference("Reviews");
        auth = FirebaseAuth.getInstance();
        //reviewsAdapter = new ReviewsAdapter(reviewsList);

    }

    public void setData(String vendorId){
        this.vendorId = vendorId;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_review, null);

        reviewBar = view.findViewById(R.id.reviews);
        btnSubmit = view.findViewById(R.id.btn_submit);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        // Set onClickListener for the Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review = reviewBar.getText().toString();

                // Check if the review text is not empty
                if (!TextUtils.isEmpty(review)) {
                    // Save the review to the Firebase Realtime Database
                    saveReview(review);

                    // Dismiss the dialog
                    dialog.dismiss();
                } else {
                    // Show a toast or dialog indicating that the review cannot be empty
                    Toast.makeText(dialog.getContext(), "Please enter your review", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });
    }

    // Method to handle saving the rating to Firestore
    private void saveReview(String review) {
        String userId = auth.getUid(); // Replace this with the actual user ID

        // Create a new node for the user's rating in the ratings node
        DatabaseReference userReviewsRef = reviewRef.child(vendorId).child(userId);
        String reviewId = userReviewsRef.push().getKey();


        // Save the rating value to the database
        userReviewsRef.child(reviewId).setValue(review)
                .addOnSuccessListener(aVoid -> {
                    // Rating saved successfully
                    // You can show a success message here if you want
                    Toast.makeText(dialog.getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error when rating could not be saved
                        // You can show an error message here if you want
                    }
                });

    }

    // Method to show reviews for a specific vendor
    private void showReviews(String vendorId, ReviewDataListener listener) {
        DatabaseReference vendorReviewsRef = reviewRef.child(vendorId);

        vendorReviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Review> reviewsList = new ArrayList<>();

                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    // Get each review and display them
                    String review = reviewSnapshot.getValue(String.class);

                   // Review review = reviewSnapshot.getValue(Review.class);
                    // Show the review using a TextView, RecyclerView, or any other UI element
                    // ...
                    if (review != null) {
                        //reviewsList.add(review);
                    }
                }
                listener.onReviewDataReceived(reviewsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });
    }
    public interface ReviewDataListener {
        void onReviewDataReceived(List<Review> reviewsList);
    }



}
