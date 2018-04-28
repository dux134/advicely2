package com.example.root.advicely.utils;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by root on 1/16/18.
 */

public class AppVariable {
    public static PrefManager prefManager;
    public static final String folder_main = "Android/data/com.example.root.advicely/files";
    public static String completeCourse = "bsc1";
    public static String selectCourse = "";
    public static String selectYear = "";
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static int addCount = 1;
}
