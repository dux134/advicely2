package com.example.root.advicely.DatabaseHelper_and_Adds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.root.advicely.utils.AppVariable.db;

/**
 * Created by root on 12/25/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    private String branch;
    private String year;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static String loadCourse() {
        return null;
    }

    public boolean insertBranch (String branch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("branch", branch);
        db.insert("course", null, contentValues);
        return true;
    }

    public boolean insertYear (String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("year", year);
        db.insert("course", null, contentValues);
        return true;
    }


    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void getDataFromDatabase()
    {
//        try
//        {
//            Cursor cursor = null;
//            db.OpenDatabase();
//            cursor = db.retrieveRecords();
//            if (cursor.getCount() != 0)
//            {
//                if (cursor.moveToFirst())
//                {
//                    do
//                    {
//                        titleArrayList.add(cursor.getString(cursor.getColumnIndex("title")));
//                        bodyArrayList.add(cursor.getString(cursor.getColumnIndex("body")));
//                    } while (cursor.moveToNext());
//                }
//                db.closeDatabase();
//            }
//            cursor.close();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
    }
}
