package com.example.root.advicely.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.root.advicely.R;

import java.util.ArrayList;

public class LibraryMyAdapter extends RecyclerView.Adapter<LibraryMyAdapter.LibraryViewHolder> {
    public ArrayList<LibraryDataModel> list;
    Context context;
    private RecyclerViewClickListener recyclerClickListener;

    public LibraryMyAdapter(ArrayList<LibraryDataModel> Data,RecyclerViewClickListener rv) {
        list = Data;
        recyclerClickListener = rv;
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_recycler_item,parent,false);
        LibraryViewHolder holder = new LibraryViewHolder(view,recyclerClickListener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final LibraryViewHolder holder, int position) {

        //holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
        holder.mTitle.setText(list.get(position).getTitle());
        holder.mDescription.setText(list.get(position).getDescription());


        Glide.with(context)
                .load(list.get(position).getImage())
                .into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class LibraryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImage;
        public TextView mTitle;
        public TextView mDescription;
        private RecyclerViewClickListener recyclerViewClickListener;

        public LibraryViewHolder(View itemView , RecyclerViewClickListener rv) {
            super(itemView);

            mTitle = (TextView)itemView.findViewById(R.id.tv_title);
            mDescription = (TextView)itemView.findViewById(R.id.tv_description);
            mImage = (ImageView)itemView.findViewById(R.id.iv_image);

            recyclerViewClickListener = rv;

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view , getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}
