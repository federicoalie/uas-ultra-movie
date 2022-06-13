package com.gdz.ultramovie.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdz.ultramovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class movieRecyclerViewAdapter extends RecyclerView.Adapter<movieRecyclerViewAdapter.myViewHolder> {

    private final ArrayList<movie> movies;

    public movieRecyclerViewAdapter(ArrayList<movie> movieArrayList){
        this.movies = movieArrayList;
    }

    @NonNull
    @Override
    public movieRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieRecyclerViewAdapter.myViewHolder holder, int position) {
        String img = movies.get(position).getMovie_image();
        holder.judul.setText(movies.get(position).getNamaMovie());
        holder.tahun.setText(movies.get(position).getTahunMovie());
        Picasso.get().load(img).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher_round).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        private final TextView judul, tahun;
        private final ImageView image;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txtJudul);
            tahun = itemView.findViewById(R.id.txtTahun);
            image = itemView.findViewById(R.id.imagePoster);
        }
    }
}
