package com.example.root.advicely;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.root.advicely.fragment.Home;
import com.example.root.advicely.fragment.Library;
import com.example.root.advicely.fragment.Notification;
import com.example.root.advicely.fragment.OfflineDownload;

/**
 * Created by root on 12/24/17.
 */

public class BottomNavigation extends AppCompatActivity {

    public static Fragment selectFragment;
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            selectFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    selectFragment = new Home();
                    break;

                case R.id.navigation_notifications:
                    selectFragment = new Notification();
                    break;


                case R.id.navigation_library:
                    selectFragment = new Library();
                    break;

                case R.id.navigation_offline_download:
                    selectFragment = new OfflineDownload();
                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectFragment);
            transaction.commit();
            return true;
        }
    };
}
