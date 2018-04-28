package com.example.root.advicely.fragment.libraryBooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.LibraryDataModel;
import com.example.root.advicely.fragment.LibraryMyAdapter;
import com.example.root.advicely.fragment.NotificationMyAdapter;
import com.example.root.advicely.fragment.libraryBooks.*;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by root on 1/3/18.
 */

public class LibraryBookMyAdapter extends RecyclerView.Adapter<LibraryBookMyAdapter.LibraryBookViewHolder> {
    public ArrayList<LibraryBookDataModel> list;
    private Context context;
    LibraryBookMyAdapter.RecyclerViewClickListener listenerViewOnline,listenerSaveOffline;

    public LibraryBookMyAdapter(ArrayList<LibraryBookDataModel> lv,RecyclerViewClickListener listenerViewOnline1,RecyclerViewClickListener listenerSaveOffline1) {
        list = lv;
        listenerSaveOffline = listenerSaveOffline1;
        listenerViewOnline = listenerViewOnline1;
    }

    @Override
    public LibraryBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_book_recycler_item,parent,false);
        LibraryBookMyAdapter.LibraryBookViewHolder holder = new LibraryBookMyAdapter.LibraryBookViewHolder(view,listenerViewOnline,listenerSaveOffline);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(LibraryBookViewHolder holder, int position) {
        holder.bookDescription.setText(list.get(position).getBookDescription());
        holder.bookTitle.setText(list.get(position).getBookName());

//        holder.bookImageview.setImageResource(R.drawable.side_nav_bar);

        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .into(holder.bookImageview);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LibraryBookViewHolder extends RecyclerView.ViewHolder{

        private TextView bookTitle;
        private TextView bookDescription;
        private ImageView bookImageview;
        private Button viewOnline;
        private Button saveOffline;
        private LibraryBookMyAdapter.RecyclerViewClickListener viewOnlineListener;
        private LibraryBookMyAdapter.RecyclerViewClickListener saveOfflineListener;

        public LibraryBookViewHolder(View itemView,RecyclerViewClickListener viewOnlineListener1,RecyclerViewClickListener saveOfflineListener1) {
            super(itemView);

            bookTitle = (TextView)itemView.findViewById(R.id.librarybook_title);
            bookDescription = (TextView)itemView.findViewById(R.id.librarybook_description);
            bookImageview = (ImageView)itemView.findViewById(R.id.librarybook_image);
            viewOnline = (Button)itemView.findViewById(R.id.library_book_viewOnline);
            saveOffline = (Button)itemView.findViewById(R.id.library_book_saveOffline);
            viewOnlineListener = viewOnlineListener1;
            saveOfflineListener = saveOfflineListener1;

            viewOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewOnlineListener.onClick(view,getAdapterPosition());
                }
            });

            saveOffline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveOfflineListener.onClick(view,getAdapterPosition());
                }
            });
        }
    }
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

}
