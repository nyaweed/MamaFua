package com.example.mamafua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VendorsAdapter extends RecyclerView.Adapter<VendorsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Vendors> vendorsArraylist;

    //generate constructor

    public VendorsAdapter(Context context, ArrayList<Vendors> vendorsArraylist) {
        this.context = context;
        this.vendorsArraylist = vendorsArraylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.vendors_list,parent,false);
        return new MyViewHolder(v);
    }

    //@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vendors vendor = vendorsArraylist.get(position);
        holder.s_name.setText(vendor.name);
        holder.s_image.setImageResource(vendor.image);

    }

    @Override
    public int getItemCount() {
        return vendorsArraylist.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView s_name;
        ImageView s_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            s_image = itemView.findViewById(R.id.vendorImage);
            s_name = itemView.findViewById(R.id.vendorName);
        }
    }
}