package com.example.root.advicely.fragment.libraryBooks;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyActivity;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.Library;
import com.example.root.advicely.fragment.offlineDownloads.Download_File;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryBook extends MyActivity {


    private RecyclerView LibraryBookrecyclerView;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    public static ArrayList<LibraryBookDataModel> libraryBookListitems = new ArrayList<LibraryBookDataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_book);
//        ButterKnife.bind(this);

        loadAdvertisement();

        Toolbar toolbar = (Toolbar) findViewById(R.id.librarybooktoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mySwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.library_book_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshContent();
            }
        });

        LibraryBookrecyclerView = (RecyclerView) findViewById(R.id.librarybookrecyclerview);
        LibraryBookrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LibraryBookMyAdapter(libraryBookListitems, new LibraryBookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(libraryBookListitems.get(position).getBookLink().toString()), "application/pdf");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
                Toast toast = Toast.makeText(getApplicationContext(), "view online", Toast.LENGTH_SHORT);
                toast.show();
            }
        }, new LibraryBookMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                showMyAdd();
                new Download_File(getApplication(), libraryBookListitems.get(position).getBookLink(), libraryBookListitems.get(position).getBookName());
                Toast toast = Toast.makeText(getApplicationContext(), "downloading...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        LibraryBookrecyclerView.setLayoutManager(layoutManager);
        LibraryBookrecyclerView.setAdapter(adapter);
        LibraryBookrecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        libraryBookListitems.clear();
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
                FireStore.loadLibraryBook(Library.subject);

            }
        },5000);
        adapter.notifyDataSetChanged();
        mySwipeRefreshLayout.setRefreshing(false);

    }
}
