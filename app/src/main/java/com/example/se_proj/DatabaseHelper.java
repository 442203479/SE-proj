package com.example.se_proj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DishDive.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTablePosts = "CREATE TABLE " + TABLE_POSTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_USER_ID + " TEXT , " +
                COLUMN_TIMESTAMP + " INTEGER" +
                ")";
        db.execSQL(createTablePosts);

        String createTableUsers = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTableUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTablePosts = "DROP TABLE IF EXISTS " + TABLE_POSTS;
        db.execSQL(dropTablePosts);

        String dropTableUsers = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableUsers);

        onCreate(db);
    }

    public List<userPosts> getPosts() {
        List<userPosts> returnList = new ArrayList<>();
        // get data from database
        String queryString = "SELECT * FROM " + TABLE_POSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // loop through cursor results
            do {
                int id = cursor.getInt(0);
                String img_url = cursor.getString(1);
                String desc = cursor.getString(2);
                String UserID = cursor.getString(3);
                String timestamp = cursor.getString(4);
                userPosts myPosts = new userPosts(UserID, img_url, desc, timestamp, id);
                returnList.add(myPosts);
            } while (cursor.moveToNext());
        } else {
            // nothing happens. no one is added.
        }
        // close
        cursor.close();
        db.close();
        return returnList;
    }

    public List<userPosts> getPosts(String username) {
        List<userPosts> returnList = new ArrayList<>();
        // get data from database
        String queryString = "SELECT * FROM " + TABLE_POSTS +
                " WHERE " + COLUMN_USER_ID + "='" + username.replace("'", "''") + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // loop through cursor results
            do {
                int id = cursor.getInt(0);
                String img_url = cursor.getString(1);
                String desc = cursor.getString(2);
                String UserID = cursor.getString(3);
                String timestamp = cursor.getString(4);
                userPosts myPosts = new userPosts(UserID, img_url, desc, timestamp, id);
                returnList.add(myPosts);
            } while (cursor.moveToNext());
        } else {
            // nothing happens. no one is added.
        }
        // close
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean insertUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }
}