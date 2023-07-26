package com.example.mamafua;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    AdapterUsers adapterUsers;
    List<ModelUsers> usersList;

    FirebaseAuth firebaseAuth;




    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_home, container, false)

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton notify = rootView.findViewById(R.id.notifyButton);
        Button buttonMoveToActivity = rootView.findViewById(R.id.services_btn);
        Button moveToVendors = rootView.findViewById(R.id.vendor_btn);

        recyclerView = rootView.findViewById(R.id.recyclerview_recommended);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        usersList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        getAllUsers();

        notify.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NotifyActivity.class);
            startActivity(intent);

        });
        buttonMoveToActivity.setOnClickListener(v -> {
            // Create an Intent to start the new activity
            Intent intent = new Intent(getActivity(), ServicesActivity.class);
            startActivity(intent);
        });

        moveToVendors.setOnClickListener(v -> {
            // Create an Intent to start the new activity
            Intent intent = new Intent(getActivity(), VendorsActivity.class);
            startActivity(intent);
        });



        return rootView;
    }

    private void getAllUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelUsers modelUsers = dataSnapshot1.getValue(ModelUsers.class);

                    if (modelUsers.getUid() != null && !modelUsers.getUid().equals(firebaseUser.getUid())) {
                        usersList.add(modelUsers);
                    }

                   //here
                    adapterUsers = new AdapterUsers(getActivity(), usersList);
                    recyclerView.setAdapter(adapterUsers);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}