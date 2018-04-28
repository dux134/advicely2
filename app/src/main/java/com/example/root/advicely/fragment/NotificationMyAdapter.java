package com.example.root.advicely.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.advicely.R;

import java.util.ArrayList;

public class NotificationMyAdapter  extends RecyclerView.Adapter<NotificationMyAdapter.NotificationViewHolder> {
    public ArrayList<NotificationDataModel> list;
    Context context;
    RecyclerViewClickListener recyclerViewClickListener;

    public NotificationMyAdapter(ArrayList<NotificationDataModel> Data , RecyclerViewClickListener mRv) {
        list = Data;
        recyclerViewClickListener = mRv;
    }

    @Override
    public NotificationMyAdapter.NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_recycle_item,parent,false);
        NotificationMyAdapter.NotificationViewHolder holder = new NotificationMyAdapter.NotificationViewHolder(view , recyclerViewClickListener);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position){
        holder.mTitle.setText(list.get(position).getNotificationTitle());
        holder.mLink.setText(list.get(position).getLink());

        if(list.get(position).getType().equalsIgnoreCase("pdf") ) {
            holder.mImage.setImageResource(R.mipmap.pdfimage);
        }
        else if(list.get(position).getType().equalsIgnoreCase("www")) {
            holder.mImage.setImageResource(R.mipmap.wwwimage);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView mImage;
        public TextView mTitle;
        public TextView mLink;
        private RecyclerViewClickListener mListener;

        public NotificationViewHolder(View itemView,RecyclerViewClickListener rv) {
            super(itemView);

            mTitle = (TextView)itemView.findViewById(R.id.notification_title);
            mLink = (TextView)itemView.findViewById(R.id.notification_link);
            mImage = (ImageView)itemView.findViewById(R.id.notification_image);

            mListener = rv;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}