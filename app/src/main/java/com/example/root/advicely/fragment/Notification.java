package com.example.root.advicely.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyFragment;
import com.example.root.advicely.R;
import com.example.root.advicely.utils.AppVariable;
import com.example.root.advicely.utils.CheckNetworkConnection;

import java.util.ArrayList;

public class Notification extends MyFragment{

    public static ArrayList<NotificationDataModel> listitems = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    SwipeRefreshLayout mySwipeRefreshLayout;

    public Notification() {

    }

    public static Notification newInstance() {
        return new Notification();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        loadAdvertisement();

        mySwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.notification_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshContent();
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.notification_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NotificationMyAdapter(listitems, new NotificationMyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(CheckNetworkConnection.isConnectionAvailable(getActivity())) {


                    showMyAdd();
                    if (listitems.get(position).getType().equalsIgnoreCase("pdf")) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(listitems.get(position).getLink()), "application/pdf");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.d("l", "l");
                        }

                    } else if (listitems.get(position).getType().equalsIgnoreCase("www")) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(listitems.get(position).getLink().toString()));
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getActivity(),"Unable to connect to internet, please check your network connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    private void refereshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FireStore.loadNotification();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        },5000);
        mAdapter.notifyDataSetChanged();

    }
}

