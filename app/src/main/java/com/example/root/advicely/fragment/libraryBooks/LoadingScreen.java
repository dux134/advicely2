package com.example.root.advicely.fragment.libraryBooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.root.advicely.DatabaseHelper_and_Adds.FireStore;
import com.example.root.advicely.R;
import com.example.root.advicely.fragment.Library;
import com.example.root.advicely.utils.AppVariable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

public class LoadingScreen extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        FireStore.loadLibraryBook(Library.subject);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },2000);
        progressDialog.dismiss();
        startActivity(new Intent(this, LibraryBook.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
