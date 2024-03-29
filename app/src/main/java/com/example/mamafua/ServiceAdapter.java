package com.example.mamafua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    Context context;
    ArrayList<Services> servicesArraylist;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    //generate constructor

    public ServiceAdapter(Context context, ArrayList<Services> servicesArraylist) {
        this.context = context;
        this.servicesArraylist = servicesArraylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.services_list,parent,false);
        return new MyViewHolder(v);
    }

    //@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Services service = servicesArraylist.get(position);
        holder.s_name.setText(service.name);
        holder.s_image.setImageResource(service.image);

        holder.itemView.setOnClickListener(v -> {
            //listener.onItemClick(position);
            Intent intent = new Intent(context, VendorsActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return servicesArraylist.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView s_name;
        ImageView s_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            s_image = itemView.findViewById(R.id.imageViewvendor);
            s_name = itemView.findViewById(R.id.Service_name);
            //S_card = itemView.findViewById(R.id.card_view);
        }
    }
}
