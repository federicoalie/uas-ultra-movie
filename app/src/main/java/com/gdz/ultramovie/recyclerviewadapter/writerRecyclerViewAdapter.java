package com.gdz.ultramovie.recyclerviewadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdz.ultramovie.R;
import com.gdz.ultramovie.model.writer;

import java.util.ArrayList;

public class writerRecyclerViewAdapter extends RecyclerView.Adapter<writerRecyclerViewAdapter.myViewHolder> {

    private static final String TAG = "writerRecyclerViewAdapt";
    private final ArrayList<writer> writers;
    private final onWriterClickListener writerClickListener;

    public writerRecyclerViewAdapter(ArrayList<writer> writerArrayList, onWriterClickListener onWriterClickListener) {
        this.writerClickListener = onWriterClickListener;
        this.writers = writerArrayList;
        Log.d(TAG, "writerRecyclerViewAdapterSize: " + writerArrayList.size());
    }


    @NonNull
    @Override
    public writerRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_writer, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull writerRecyclerViewAdapter.myViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called ");
        holder.idWriter.setText(writers.get(position).getId());
        holder.namaWriter.setText(writers.get(position).getWriter());
        holder.emailWriter.setText(writers.get(position).getEmail());
        holder.telpWriter.setText(writers.get(position).getNotel());

        holder.bind(writers.get(position), this.writerClickListener);
    }

    @Override
    public int getItemCount() {
        return writers.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView idWriter, namaWriter, emailWriter, telpWriter;
        onWriterClickListener onWriterClickListener;
        writer writer;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            idWriter = itemView.findViewById(R.id.txtIdWriter);
            namaWriter = itemView.findViewById(R.id.txtWritter);
            emailWriter = itemView.findViewById(R.id.txtEmail);
            telpWriter = itemView.findViewById(R.id.txtNoTel);

            itemView.setOnLongClickListener(this);
        }

        public void bind(writer writer, onWriterClickListener onWriterClickListener){
            this.writer = writer;
            this.onWriterClickListener = onWriterClickListener;
        }

        @Override
        public boolean onLongClick(View view) {
            onWriterClickListener.onWriterLongClick(writer, this.getLayoutPosition());
            return true;
        }
    }

    public interface onWriterClickListener{
        void onWriterLongClick(writer writer, int position);
    }
}
