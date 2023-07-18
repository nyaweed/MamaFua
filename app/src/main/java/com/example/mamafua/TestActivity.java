package com.example.mamafua;

//import static com.example.mamafua.TestActivity.PhoneNumberUtils.openCallDialer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLang, detailPhone;
    ImageView detailImage;

    ImageButton callVendor;
    //FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";
    private RatingBar ratingBar;

    private DatabaseReference ratingRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailPhone = findViewById(R.id.phone_view);
        //deleteButton = findViewById(R.id.deleteButton);
        //editButton = findViewById(R.id.editButton);
        callVendor = findViewById(R.id.button_phone);
        detailLang = findViewById(R.id.detailLocation);

        ratingBar =findViewById(R.id.rating);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailPhone.setText(bundle.getString("Contact"));

            detailLang.setText(bundle.getString("Location"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        ratingRef = FirebaseDatabase.getInstance().getReference("Ratings");


        callVendor.setOnClickListener(view -> openCallDialer());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Update the vendor's rating in the database
                updateRating(rating);
            }
        });
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

    private void updateRating(float rating) {
        // Convert the rating to the desired format if needed
        // For example, you might want to round it to a specific number of decimal places

        // Update the rating in the Firebase Realtime Database
        ratingRef.setValue(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TestActivity.this, "Rating updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TestActivity.this, "Failed to update rating", Toast.LENGTH_SHORT).show();
                    }
                });
    }




}
