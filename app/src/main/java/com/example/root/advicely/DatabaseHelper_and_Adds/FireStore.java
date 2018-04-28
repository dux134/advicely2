package com.example.root.advicely.DatabaseHelper_and_Adds;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.root.advicely.SplashScreen;
import com.example.root.advicely.fragment.Library;
import com.example.root.advicely.fragment.LibraryDataModel;
import com.example.root.advicely.fragment.Notification;
import com.example.root.advicely.fragment.NotificationDataModel;
import com.example.root.advicely.fragment.libraryBooks.LibraryBook;
import com.example.root.advicely.fragment.libraryBooks.LibraryBookDataModel;
import com.example.root.advicely.utils.AppVariable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 12/29/17.
 */

public class FireStore {

    private static String mTitle,mDescription;
    private static String mImage_url;
    private static FirebaseFirestore db = AppVariable.db;

    public static void load() {

    }
    public static void loadNotification() {
        Notification.listitems.clear();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("notifications").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                mTitle = change.getDocument().get("title").toString();
                                mDescription = change.getDocument().get("link").toString();
                                mImage_url = change.getDocument().get("link_type").toString();
                                Notification.listitems.add(new NotificationDataModel(mTitle, mDescription, mImage_url));
                                //Log.d(TAG, "New city:" + change.getDocument().getData());
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
    public static void loadLibrary() {
        Library.listitems.clear();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection(String.valueOf(AppVariable.completeCourse))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {


                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                mTitle = String.valueOf(change.getDocument().get("title"));
                                mDescription = String.valueOf(change.getDocument().get("description").toString());
                                mImage_url =String.valueOf(change.getDocument().get("image_url").toString());
                                Library.listitems.add(new LibraryDataModel(mTitle,mDescription,mImage_url));
                                //Log.d(TAG, "New city:" + change.getDocument().getData());
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
    public static void loadEbooks() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection(String.valueOf(AppVariable.completeCourse))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {


                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                mTitle = String.valueOf(change.getDocument().get("title"));
                                mDescription = String.valueOf(change.getDocument().get("description").toString());
                                mImage_url =String.valueOf(change.getDocument().get("image_url").toString());
                                Library.listitems.add(new LibraryDataModel(mTitle,mDescription,mImage_url));
                                //Log.d(TAG, "New city:" + change.getDocument().getData());
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
    public static void loadLibraryBook(String subject) {
        LibraryBook.libraryBookListitems.clear();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        db.collection(AppVariable.completeCourse).document(subject).collection("books")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                String bookTitle = change.getDocument().get("title").toString();
                                String bookLink = change.getDocument().get("link").toString();
                                String bookDescription = change.getDocument().get("description").toString();
                                String bookImageUrl = change.getDocument().get("image_url").toString();


                                LibraryBook.libraryBookListitems.add(new LibraryBookDataModel(bookTitle,bookDescription,bookLink,bookImageUrl));



                                Log.d("k", "" + Library.listitems.size());

                                Library.listitems.size();

                            }
                            LibraryBook.libraryBookListitems.add(new LibraryBookDataModel("1","1.1","https://www.planetebook.com/ebooks/1984.pdf",""));
                            LibraryBook.libraryBookListitems.add(new LibraryBookDataModel("2","1.1","https://www.planetebook.com/ebooks/1984.pdf",""));
                            LibraryBook.libraryBookListitems.add(new LibraryBookDataModel("3","1.1","https://www.planetebook.com/ebooks/1984.pdf",""));
                            LibraryBook.libraryBookListitems.add(new LibraryBookDataModel("4","1.1","https://www.planetebook.com/ebooks/1984.pdf",""));

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                    }
                });
    }
}
