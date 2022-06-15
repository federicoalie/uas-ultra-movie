package com.gdz.ultramovie.recyclerviewadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.genre;

import java.util.ArrayList;

public class genreRecyclerViewAdapter extends RecyclerView.Adapter<genreRecyclerViewAdapter.myViewHolder> {

    private static final String TAG = "genreRecyclerViewAdapter";
    private final onGenreClickListener GenreClickListener;
    private final ArrayList<genre> genres;

    public genreRecyclerViewAdapter(ArrayList<genre> genreArrayList, onGenreClickListener onGenreClickListener) {
        this.genres = genreArrayList;
        this.GenreClickListener = onGenreClickListener;
        Log.d(TAG, "genreRecyclerViewAdapterSize: " + genreArrayList.size());

    }


    @NonNull
    @Override
    public genreRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_genre, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull genreRecyclerViewAdapter.myViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.id.setText(genres.get(position).getId());
        holder.namaGenre.setText(genres.get(position).getGenre());
        holder.bind(genres.get(position), this.GenreClickListener);

    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final TextView id, namaGenre;
        genre genre;
        onGenreClickListener onGenreClickListener;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtIdGenre);
            namaGenre = itemView.findViewById(R.id.txtGenre);
            itemView.setOnLongClickListener(this);
        }

        public void bind(genre genre, onGenreClickListener onGenreClickListener){
            this.genre = genre;
            this.onGenreClickListener = onGenreClickListener;
        }

        @Override
        public boolean onLongClick(View view) {
            onGenreClickListener.onGenreLongClick(genre, this.getLayoutPosition());
            return true;
        }
    }

    public interface onGenreClickListener{
        void onGenreLongClick(genre genre, int position);
    }
}
