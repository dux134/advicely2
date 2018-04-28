package com.example.root.advicely.fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.R;
import com.example.root.advicely.utils.AppVariable;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class OfflineDownload extends Fragment {
    public static ArrayList<String> filename= new ArrayList<String>();//contains list of all files ending with
    private static ListView listView;
    private static ArrayAdapter adapter;
    private static File listFile[];
    static SwipeRefreshLayout mySwipeRefreshLayout;



    public OfflineDownload() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_offline_download__downloaded, container, false);

        mySwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.OfflineDownload_swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refereshContent();
            }
        });

        listView = (ListView)root.findViewById(R.id.downloaded_listview);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,filename);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                File pdfFile = new File(Environment.getExternalStorageDirectory(), AppVariable.folder_main+"/"+filename.get(i)+".pdf");//File path
                if (pdfFile.exists()) //Checking for the file is exist or not
                {
                    //Uri path = Uri.fromFile(pdfFile);
                    Uri path = FileProvider.getUriForFile(getActivity(), getContext().getApplicationContext().getPackageName()+".provider", pdfFile);
                    Toast.makeText(getActivity(), "Your clicked at  "+pdfFile, Toast.LENGTH_LONG).show();
                    Intent objIntent = new Intent(Intent.ACTION_VIEW);
                    objIntent.setDataAndType(path, "application/pdf");
//                    objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(objIntent);
                    } catch (ActivityNotFoundException e) {
                        // No application to view, ask to download one
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("No Application Found");
                        builder.setMessage("Download one from Android Market?");
                        builder.setPositiveButton("Yes, Please",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                                        marketIntent
                                                .setData(Uri
                                                        .parse("market://details?id=com.adobe.reader"));
                                        startActivity(marketIntent);
                                    }
                                });
                        builder.setNegativeButton("No, Thanks", null);
                        builder.create().show();
                    }                } else {

                    Toast.makeText(getActivity(), "The file not exists! ", Toast.LENGTH_SHORT).show();

                }


            }
        });

        return root;

    }

    public static void refereshContent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadDir();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        },5000);

        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
//        mySwipeRefreshLayout.setEnabled(false);

    }

    public static void refereshFromOutside() {
        loadDir();
    }

    public static void loadDir() {
        File dir= new File(android.os.Environment.getExternalStorageDirectory(), AppVariable.folder_main);
        filename.clear();
        listFile = dir.listFiles();

        Log.d("advisely log",""+listFile.toString());


        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                String fileName = listFile[i].getName();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

                if (extension.equals("pdf")) {

                    filename.add(fileName.substring(0,fileName.indexOf(".")));

                }
            }
        }
    }

}