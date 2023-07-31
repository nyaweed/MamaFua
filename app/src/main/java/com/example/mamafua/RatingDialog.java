package com.example.mamafua;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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

import java.util.Map;

public class RatingDialog extends Dialog {
    private Context context;
    private DatabaseReference ratingRef;
    private AlertDialog dialog;
    private FirebaseAuth auth;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private String vendorId;
    private String userId;

    public RatingDialog(Context context, String vendorId) {
        super(context);
        this.context = context;
        this.vendorId = vendorId;
        ratingRef =  FirebaseDatabase.getInstance().getReference("Ratings");
        auth = FirebaseAuth.getInstance();
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_rating, null);

        ratingBar = view.findViewById(R.id.ratingBar);
        btnSubmit = view.findViewById(R.id.btn_submit);
        String userId = auth.getUid();

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        // Set onClickListener for the Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = (int) ratingBar.getRating();

                saveRating(rating, vendorId);

                dialog.dismiss();
            }
        });
    }

    // Method to handle saving the rating to Firestore
    private void saveRating(int rating, String vendorId) {
        String userId = auth.getUid(); // Replace this with the actual user ID

        // Create a new node for the user's rating in the ratings node
        DatabaseReference userRatingRef = ratingRef.push();
        Rating review = new Rating(userId, vendorId, rating);
        //Map<String, Object> reviewValues = review.toMap();
      /*  userRatingRef.child(vendorId).child(userId).setValue(reviewValues).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(dialog.getContext(), "Submitted", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(dialog.getContext(), "failed", Toast.LENGTH_SHORT).show();


            }
        }); */



        // Save the rating value to the database
        userRatingRef.setValue(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Rating saved successfully
                        // You can show a success message here if you want
                        Toast.makeText(dialog.getContext(), "Submitted", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error when rating could not be saved
                        // You can show an error message here if you want
                    }
                });

    }

 /*   private void saveRating(int rating, String vendorId, String userId) {
        //String userId = auth.getUid(); // Replace this with the actual user ID

        // Get a reference to the "ratings" node in Firebase
        DatabaseReference ratingsRef = ratingRef;

        // Create a new node for the user's rating in the ratings node
        DatabaseReference userRatingRef = ratingsRef.child(vendorId);

        // Create a Rating object
        Rating review = new Rating(userId, vendorId, rating);

        // Save the rating data to Firebase
        userRatingRef.setValue(review).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(dialog.getContext(), "Submitted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(dialog.getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    private void calculateAverageRating(String userId) {
        DatabaseReference userRatingsRef = ratingRef.child(userId);
        userRatingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalRatings = 0;
                int numRatings = 0;

                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    int ratingValue = ratingSnapshot.getValue(Integer.class);
                    totalRatings += ratingValue;
                    numRatings++;
                }

                // Calculate the average rating
                double averageRating = totalRatings / (double) numRatings;


                // Update the display with the average rating
                // For example, you can set the average rating to a TextView
                // avgRatingTextView.setText(String.format("%.1f", averageRating));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });
    }



}
