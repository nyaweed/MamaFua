package com.example.mamafua;

//import static com.example.mamafua.TestActivity.PhoneNumberUtils.openCallDialer;

import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {

    private FirebaseAuth auth;


    TextView detailDesc, detailTitle, detailLang, detailPhone;
    ImageView detailImage;

    ImageButton callVendor, textVendor;
    //FloatingActionButton deleteButton, editButton;
    String key = "";
    private Button rateUs, review;
    String uid, name;
    String imageUrl = "";
    private RatingBar ratingBar;
    private AlertDialog dialog;
    ReviewsAdapter reviewsAdapter;
    RecyclerView recyclerView;

    private DatabaseReference ratingRef, userIdRef;

    private RatingDialog ratingDialog;
    private ReviewDialog reviewDialog;
    DatabaseReference reviewRef;
    private List<Review> reviewList = new ArrayList<>();
    private String vendorsId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        reviewRef = FirebaseDatabase.getInstance().getReference("Reviews");
        auth = FirebaseAuth.getInstance();





        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailPhone = findViewById(R.id.phone_view);
        //deleteButton = findViewById(R.id.deleteButton);
        textVendor = findViewById(R.id.button_chat);
        callVendor = findViewById(R.id.button_phone);
        detailLang = findViewById(R.id.detailLocation);
        rateUs = findViewById(R.id.rate_Us);
        review = findViewById(R.id.review_Us);
        auth = FirebaseAuth.getInstance();
        // Set up the RecyclerView and display the reviws
        RecyclerView recyclerView = findViewById(R.id.r_View);
        reviewsAdapter = new ReviewsAdapter(new ArrayList<>());

        //ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewsAdapter);
        //reviewsAdapter = new ReviewsAdapter(new ArrayList<>());



        ratingBar =findViewById(R.id.rating);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailPhone.setText(bundle.getString("Contact"));
            name = bundle.getString("Title");

            detailLang.setText(bundle.getString("Location"));
            key = bundle.getString("Key");
            uid = bundle.getString("userId");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);


        }
        ratingRef = FirebaseDatabase.getInstance().getReference("Ratings");
        userIdRef = FirebaseDatabase.getInstance().getReference("ApprovedVendors");


        rateUs.setOnClickListener(view -> openDialog(vendorsId));
        String vendorId = getIntent().getStringExtra("userId");


        review.setOnClickListener(view -> openReview(vendorId));

        //String vendorId1 = "9h1mT3kUFCbB7rA2GTCmUCFfjRs1";

        showReviews(vendorId);


        callVendor.setOnClickListener(view -> openCallDialer());

        textVendor.setOnClickListener(v -> {

            Intent intent = new Intent(TestActivity.this, ChatActivity.class);
            intent.putExtra("userId", uid);
            intent.putExtra("Title", name);
            intent.putExtra("Image", imageUrl);


            startActivity(intent);
        });
        ratingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double averageRating = snapshot.child("averageRating").getValue(Double.class);
                if (averageRating != null) {
                    // Update the RatingBar to display the average rating
                    RatingBar ratingBar = findViewById(R.id.ratingBar);
                    ratingBar.setRating(averageRating.floatValue());
                }
            }//

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String userId = "9h1mT3kUFCbB7rA2GTCmUCFfjRs1";

        ratingRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the rating value from the dataSnapshot
                    int actualRating = dataSnapshot.getValue(Integer.class);

                    // Assuming the maximum rating is 5
                    int maxRating = 5;

                    // Calculate the percentage rating
                    float percentageRating = (float) actualRating / maxRating * ratingBar.getNumStars();

                    // Set the calculated percentage rating to the RatingBar
                    ratingBar.setRating(percentageRating);
                } else {
                    // Handle the case when the user does not have a rating
                    // For example, you can set a default rating here
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any database error that might occur during retrieval
            }
        });



        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Update the vendor's rating in the database

            }
        });


    }
    private void openReview(String vendorId) {

        if(Objects.equals(uid, auth.getUid())) {
            Toast.makeText(TestActivity.this, "You can not Rate yourself", Toast.LENGTH_SHORT).show();
        }else {
            ReviewDialog reviewDialog = new ReviewDialog(TestActivity.this, vendorId);


            reviewDialog.setData(vendorId);
            reviewDialog.showDialog();

            //reviewDialog.showReviews(vendorId, this);

        }
    }
    public interface onReviewDataRecieved {
        void onReviewDataReceived(List<Review> reviewsList);
    }

    private void showReviews(String vendorsId) {
        reviewsAdapter = new ReviewsAdapter(new ArrayList<>());
        DatabaseReference vendorReviewsRef = reviewRef.child(vendorsId).child(vendorsId);

        vendorReviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Review> reviewsList = new ArrayList<>();

                for (DataSnapshot vendorSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot userSnapshot : vendorSnapshot.getChildren()) {
                        for (DataSnapshot reviewSnapshot : userSnapshot.getChildren()) {
                            String reviewText = reviewSnapshot.getValue(String.class);
                            if (reviewText != null) {
                                Review review = new Review(reviewText);
                                reviewsList.add(review);
                            }
                        }
                    }
                }

                // Update the RecyclerView with the fetched reviews
                reviewsAdapter.setReviewsList(reviewsList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });
    }


    private void openDialog(String vendorId) {
        if(Objects.equals(uid, auth.getUid())) {
            Toast.makeText(TestActivity.this, "You can not Rate yourself", Toast.LENGTH_SHORT).show();
        }else {
            RatingDialog ratingDialog = new RatingDialog(TestActivity.this, vendorId);

            ratingDialog.showDialog();
        }
    }

    private void openCallDialer() {
        String phoneNumber = detailPhone.getText().toString().trim();
        if (!phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            // Handle case when the phone number is not available
        }
    }

    public void onReviewDataReceived(List<Review> reviewsList) {
        // Set up the RecyclerView and display the reviews
        RecyclerView recyclerView = findViewById(R.id.r_View);
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void updateRating(float rating) {

    }




}

