package com.example.mamafua;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServicesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Services> servicesArrayList;
    ServiceAdapter myAdapter;
    String[] name;
    int[] image;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        recyclerView = findViewById(R.id.service_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        servicesArrayList = new ArrayList<>();
        myAdapter = new ServiceAdapter(this, servicesArrayList);
        recyclerView.setAdapter(myAdapter);

        name = new String[]{
                "Dry Cleaning",
                "Folding And Ironing",
                "Carpet Washing",
                "Car Wash",
                "Laundry"
        };
        image =new int[]{
             R.drawable.dryclean,
             R.drawable.ironing,
             R.drawable.carpet,
             R.drawable.carwash,
             R.drawable.laundry
        };
    
    getData();


    
    }

    private void getData() {
        for(int i=0;i<name.length;i++){
            Services serv = new Services(name[i], image[i]);
            servicesArrayList.add(serv);
        }
        myAdapter.notifyDataSetChanged();
    }



}
