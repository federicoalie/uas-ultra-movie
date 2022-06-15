package com.gdz.ultramovie.recyclerviewadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class movieRecyclerViewAdapterMember extends RecyclerView.Adapter<movieRecyclerViewAdapterMember.myViewHolder> {

    private static final String TAG = "movieRecyclerViewAdapte";
    private final onMovieMemberClickListener movieMemberClickListener;
    private final ArrayList<movie> movies;

    public movieRecyclerViewAdapterMember(ArrayList<movie> movieArrayList, onMovieMemberClickListener onMovieMemberClickListener) {
        this.movieMemberClickListener = onMovieMemberClickListener;
        this.movies = movieArrayList;
        Log.d(TAG, "Adapter size " + movies.size());
    }

    @NonNull
    @Override
    public movieRecyclerViewAdapterMember.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_member, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieRecyclerViewAdapterMember.myViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        String img = movies.get(position).getMovie_image();
        holder.judul.setText(movies.get(position).getNamaMovie());
        holder.tahun.setText(movies.get(position).getTahunMovie());

        Picasso.get().load(img).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher_round).into(holder.image);

        holder.bind(movies.get(position), this.movieMemberClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView judul, tahun;
        ImageView image;
        movie movie;
        onMovieMemberClickListener onMovieMemberClickListener;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.txtJudulMember);
            tahun = itemView.findViewById(R.id.txtTahunMember);
            image = itemView.findViewById(R.id.imagePosterMember);

            itemView.setOnClickListener(this);

        }

        public void bind(movie movie, onMovieMemberClickListener onMovieMemberClickListener) {
            this.movie = movie;
            this.onMovieMemberClickListener = onMovieMemberClickListener;
        }

        @Override
        public void onClick(View view) {
            onMovieMemberClickListener.onMemberClick(movie, this.getLayoutPosition());
        }
    }

    public interface onMovieMemberClickListener{
        void onMemberClick(movie movie, int position);
    }
}
