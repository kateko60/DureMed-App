package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbConnector extends SQLiteOpenHelper {
    public dbConnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstname TEXT, " +
                "lastname TEXT, " +
                "email TEXT UNIQUE, " +
                "occupation TEXT, " +
                "password TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate table on upgrade (you can enhance this with migrations)
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    // Register a new user
    public void register(String firstname, String lastname, String email, String occupation, String password) {
        ContentValues cv = new ContentValues();
        cv.put("firstname", firstname);
        cv.put("lastname", lastname);
        cv.put("email", email);
        cv.put("occupation", occupation);
        cv.put("password", password);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Users", null, cv);
        db.close();
    }

    // Get occupation for login
    public String getOccupation(String email, String password) {
        String[] args = {email, password};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery(
                "SELECT occupation FROM Users WHERE email = ? AND password = ?", args);

        if (cur.moveToFirst()) {
            return cur.getString(0);
        }
        return null;
    }

    // Check login and return firstname
    public String login(String email, String password) {
        String[] args = {email, password};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery(
                "SELECT firstname FROM Users WHERE email = ? AND password = ?", args);

        if (cur.moveToFirst()) {
            return cur.getString(0); // Return firstname
        }
        return null;
    }
}
