package com.gdz.ultramovie.recyclerviewadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.movie;
import com.gdz.ultramovie.activity.movieDetailActivity;
import com.gdz.ultramovie.databaseURL;
import com.gdz.ultramovie.mainActivity.mainMenuAdmin;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class movieRecyclerViewAdapter extends RecyclerView.Adapter<movieRecyclerViewAdapter.myViewHolder> {

    private static final String TAG = "movieRecyclerViewAdapter";
    private final onMovieClickListener movieClickListener;
    private final ArrayList<movie> movies;

    public movieRecyclerViewAdapter(ArrayList<movie> movieArrayList, onMovieClickListener onMovieClickListener){
        this.movies = movieArrayList;
        this.movieClickListener = onMovieClickListener;
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
        holder.id.setText(movies.get(position).getIdMovie());
        Picasso.get().load(img).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher_round).into(holder.image);

        holder.bind(movies.get(position), this.movieClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView judul, tahun;
        private final ImageView image;
        TextView id;
        RelativeLayout parent;
        movie movie;
        onMovieClickListener onMovieClickListener;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txtJudul);
            tahun = itemView.findViewById(R.id.txtTahun);
            image = itemView.findViewById(R.id.imagePoster);
            parent = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            id = itemView.findViewById(R.id.IDMovie);
        }


        public void bind(movie movie, onMovieClickListener onMovieClickListener)
        {
            this.movie = movie;
            this.onMovieClickListener = onMovieClickListener;

        }

        @Override
        public void onClick(View view) {
            onMovieClickListener.onMovieClick(movie, this.getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onMovieClickListener.onMovieLongClick(movie, this.getLayoutPosition());
            return true;
        }
    }

    public interface onMovieClickListener{
        void onMovieClick(movie movie, int position);
        void onMovieLongClick(movie movie, int position);

    }
}
