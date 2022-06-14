package com.gdz.ultramovie.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.movie;
import com.gdz.ultramovie.activity.movieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class movieRecyclerViewAdapter extends RecyclerView.Adapter<movieRecyclerViewAdapter.myViewHolder> {

    private static final String TAG = "movieRecyclerViewAdapter";
    private final ArrayList<movie> movies;
    private final Context context;

    public movieRecyclerViewAdapter(ArrayList<movie> movieArrayList, Context context){
        this.movies = movieArrayList;
        this.context = context;
        Log.d("mainMenuAdmin", "Adapter List size : " + movieArrayList.size() );
    }

    @NonNull
    @Override
    public movieRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieRecyclerViewAdapter.myViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        String img = movies.get(position).getMovie_image();
        holder.judul.setText(movies.get(position).getNamaMovie());
        holder.tahun.setText(movies.get(position).getTahunMovie());
        Picasso.get().load(img).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher_round).into(holder.image);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: pressed " + holder.judul.getText());
                Intent movieDetail = new Intent(context, movieDetailActivity.class);
                movieDetail.putExtra("movie_name", holder.judul.getText());
                context.startActivity(movieDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        private final TextView judul, tahun;
        private final ImageView image;
        RelativeLayout parent;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txtJudul);
            tahun = itemView.findViewById(R.id.txtTahun);
            image = itemView.findViewById(R.id.imagePoster);
            parent = itemView.findViewById(R.id.parent_layout);

        }
    }
}
