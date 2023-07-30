package com.example.mamafua;

import static android.content.Intent.getIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avatartv;
    TextView nam, email;
    //RecyclerView postrecycle;
    Button fab;
    Button fab1;
    ProgressDialog pd;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // creating a  view to inflate the layout
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(getActivity());
        // getting current user data
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        if(firebaseUser != null){
            String userId = firebaseUser.getUid();
        }
        // Initialising the text view and imageview
        avatartv = view.findViewById(R.id.avatartv);
        nam = view.findViewById(R.id.nametv);
        email = view.findViewById(R.id.emailtv);
        fab1 = view.findViewById(R.id.vendor_status);
        fab = view.findViewById(R.id.fab);
        //pd = new ProgressDialog(getActivity());
        //pd.setCanceledOnTouchOutside(false);
        //showAllUserData();
 /*       Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //DataSnapshot dataSnapshot1 = dataSnapshot.getChildren().iterator().next();
                    //ModelUsers user = dataSnapshot.getValue(ModelUsers.class);
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot.child("name").getValue();
                    String emaill = "" + dataSnapshot.child("email").getValue();
                    String image = "" + dataSnapshot.child("image").getValue();
                    // setting data to our text view
                    nam.setText(name);
                    email.setText(emaill);

                    Glide.with(AccountFragment.this)
                            .load(image)
                            .placeholder(R.drawable.dryclean)
                            .into(avatartv);
                }

                /*

                try {
                    Glide.with(getActivity()).load(image).into(avatartv);
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  */

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial data and again
                // whenever data at this location is updated.
                String emaill = firebaseUser.getEmail();


                // Iterate through the data
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve data for each user
                    String name = "" + userSnapshot.child("name").getValue(String.class);
                    String emails = userSnapshot.child("email").getValue(String.class);
                    String image = userSnapshot.child("image").getValue(String.class);

                    // Do something with the data (e.g., display it in the UI)
                    if(emaill != null && emaill.equals(emails)) {

                        nam.setText(name);

                        email.setText(emails);


                        if(isAdded()) {

                            Glide.with(getContext())
                                    .load(image)
                                    .placeholder(R.drawable.baseline_account_circle_24)
                                    .into(avatartv);
                        }
                    }

                    //Log.d("User Data", "Name: " + name + ", Email: " + email + ", Image: " + image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });



        fab.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditProfile.class)));
        fab1.setOnClickListener(v -> startActivity(new Intent(getActivity(), VendorActivity.class)));

        return view;
    }
/**
    public void showAllUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");


        nam.setText(nameUser);
        email.setText(emailUser);


    private void fetchDataFromFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String dataName = snapshot.getValue(String.class);
                    nam.setText(dataName);

                }else{
                    nam.setText("User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                nam.setText("Error Fetching Data");

            }
        });
    }   **/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
}