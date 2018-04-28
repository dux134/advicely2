package com.example.root.advicely.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.advicely.BottomNavigation;
import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyFragment;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.libraryBooks.LibraryBook;
import com.example.root.advicely.fragment.libraryBooks.LoadingScreen;
import com.example.root.advicely.utils.AppVariable;
import com.example.root.advicely.utils.CheckNetworkConnection;

import java.util.ArrayList;

public class Library extends MyFragment {

    public static ArrayList<LibraryDataModel> listitems = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    public static String subject =null;
    private TextView showCurrentCourse;
    SwipeRefreshLayout mySwipeRefreshLayout;

    public Library() {

    }

    public static Library newInstance() {
        return new Library();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);

        showCurrentCourse = (TextView)view.findViewById(R.id.library_current_course_text_view);
        mySwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.library_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshContent();
            }
        });

        AppVariable.selectCourse = AppVariable.prefManager.getEnrolledCourse();
        AppVariable.selectYear = AppVariable.prefManager.getEnrolledYear();

        String str = String.valueOf("You Are Currently Enrolled in :"+AppVariable.selectCourse.toUpperCase().charAt(0)
                +"."+AppVariable.selectCourse.substring(1)
                +" "+AppVariable.selectYear+" Year");
        showCurrentCourse.setText(str);
        loadAdvertisement();

            recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new LibraryMyAdapter(listitems, new LibraryMyAdapter.RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {

                    if(CheckNetworkConnection.isConnectionAvailable(getActivity())) {

                        showMyAdd();
                        subject = listitems.get(position).getTitle().toLowerCase();
                        startActivity(new Intent(getActivity(), LoadingScreen.class));

                    } else {
                        Toast.makeText(getActivity(),"Unable to connect to internet, please check your network connection",Toast.LENGTH_SHORT).show();

                    }

                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void refereshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FireStore.loadLibrary();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        },5000);
        mAdapter.notifyDataSetChanged();
//        recyclerView.invalidateViews();
//        recyclerView.refreshDrawableState();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}