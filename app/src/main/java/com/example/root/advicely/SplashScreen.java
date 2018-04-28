package com.example.root.advicely;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.DatabaseHelper_and_Adds.MyActivity;
import com.example.root.advicely.fragment.OfflineDownload;
import com.example.root.advicely.utils.AppVariable;
import com.example.root.advicely.utils.PrefManager;

import java.io.File;

import static com.example.root.advicely.R.*;
import static com.example.root.advicely.utils.AppVariable.prefManager;


public class SplashScreen extends MyActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);


        AppVariable.completeCourse = prefManager.getCurentlyEnrolledCourse();
        setContentView(layout.activity_splash_screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                FireStore.loadLibrary();
                FireStore.loadNotification();
                OfflineDownload.loadDir();
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prefManager.isFirstTimeLaunch()) {
                    startActivity(new Intent(SplashScreen.this, SelectCourse.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, NavigationDrawer.class));
                    finish();
                }
            }
        },1000);
    }
}

