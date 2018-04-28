package com.example.root.advicely.fragment.home;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyActivity;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.Home;
import com.example.root.advicely.fragment.libraryBooks.LibraryBookDataModel;
import com.example.root.advicely.fragment.libraryBooks.LibraryBookMyAdapter;
import com.example.root.advicely.fragment.offlineDownloads.Download_File;

import java.util.ArrayList;

/**
 * Created by root on 1/25/18.
 */

public class HomeEbookViewAll extends MyActivity {
    private RecyclerView homeEbookViewAllrecyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    public static ArrayList<LibraryBookDataModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ebook_view_all);

        loadAdvertisement();

        Toolbar toolbar = (Toolbar) findViewById(R.id.homeebooktoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.viewall_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshContent();
            }
        });

        homeEbookViewAllrecyclerView = (RecyclerView)findViewById(R.id.home_ebook_view_all);
        homeEbookViewAllrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LibraryBookMyAdapter(HomeEbookViewAll.list, new LibraryBookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(list.get(position).getBookLink().toString()), "application/pdf");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.d("l", "l");
                }
                Toast toast = Toast.makeText(getApplicationContext(), "view online", Toast.LENGTH_SHORT);
                toast.show();
            }
        }, new LibraryBookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                showMyAdd();
                new Download_File(getApplication(),list.get(position).getBookLink(), list.get(position).getBookName());
                Toast toast = Toast.makeText(getApplicationContext(), "save Offline", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        homeEbookViewAllrecyclerView.setLayoutManager(layoutManager);
        homeEbookViewAllrecyclerView.setAdapter(adapter);
        homeEbookViewAllrecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void refereshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                FireStore.loadNotification();
            }
        },2000);
        adapter.notifyDataSetChanged();
        mySwipeRefreshLayout.setRefreshing(false);
    }
}
