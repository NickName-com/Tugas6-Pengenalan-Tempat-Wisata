package com.k.tempatwisata;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class ListviewAdapter extends RecyclerView.Adapter<ListviewAdapter.MyViewHolder> implements View.OnClickListener{

    private ArrayList<ModelList> array;
    private Context context;
    private String type;

    @Override
    public void onClick(View v) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, lokasi;
        ImageView image;
        CardView cardView;
        MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            nama = view.findViewById(R.id.nama);
            lokasi = view.findViewById(R.id.lokasi);
            cardView = view.findViewById(R.id.cardview);
        }
    }

    public ListviewAdapter(Context context, ArrayList<ModelList> array, String type){
        this.array = array;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (type.equals("horizontal")){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_vertical, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelList item = array.get(position);
        holder.nama.setText(item.getName());
        holder.lokasi.setText(item.getLokasi());

        Glide.with(context)
                .load(item.getImage())
                .centerCrop()
                .into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(context, DetailActivity.class);
                move.putExtra("image", item.getImage());
                move.putExtra("nama", item.getName());
                move.putExtra("lokasi", item.getLokasi());
                move.putExtra("bintang", item.getBintang());
                move.putExtra("deskripsi", item.getDeskripsi());
                context.startActivity(move);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
}
