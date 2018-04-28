package com.example.root.advicely.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.libraryBooks.LibraryBookDataModel;

import java.util.ArrayList;

/**
 * Created by root on 1/25/18.
 */

public class EbookMyAdapter extends RecyclerView.Adapter<EbookMyAdapter.EbookViewHolder> {
    public ArrayList<LibraryBookDataModel> list;
    Context context;
    EbookMyAdapter.RecyclerViewClickListener recyclerViewClickListener;

    public EbookMyAdapter(ArrayList<LibraryBookDataModel> Data , EbookMyAdapter.RecyclerViewClickListener mRv) {
        list = Data;
        recyclerViewClickListener = mRv;
    }

    @Override
    public EbookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_ebook_recycler_item,parent,false);
        EbookMyAdapter.EbookViewHolder holder = new EbookMyAdapter.EbookViewHolder(view , recyclerViewClickListener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(EbookViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getBookName());
        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .apply(options)
                .into(holder.imageView);
//        holder.imageView.setImageResource(R.drawable.side_nav_bar);
    }

    @Override
    public int getItemCount() {
        int arraySize = list.size();
        if(arraySize >= 10)
            return 10;
        else
            return arraySize;
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView textView;
        private EbookMyAdapter.RecyclerViewClickListener mListener;

        public EbookViewHolder(View itemView ,EbookMyAdapter.RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.ebook_imageView);
            textView = (TextView)itemView.findViewById(R.id.ebook_textView);

            mListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int adapterPosition);
    }
}
