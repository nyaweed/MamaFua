package com.example.mamafua;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VendorsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Vendors> vendorsArrayList;
    VendorsAdapter myAdapter;
    String[] name;
    int[] image;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        recyclerView = findViewById(R.id.vendor_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        vendorsArrayList = new ArrayList<>();
        myAdapter = new VendorsAdapter(this, vendorsArrayList);
        recyclerView.setAdapter(myAdapter);

        name = new String[]{
                "Mama Tony",
                "Ngarisha Services",
                "Carpet Masters",
                "Car Wash Masters",
                "Laundry Provider"
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
            Vendors vend = new Vendors(name[i], image[i]);
            vendorsArrayList.add(vend);
        }
        myAdapter.notifyDataSetChanged();
    }

}