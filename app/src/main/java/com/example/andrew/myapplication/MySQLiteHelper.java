package com.example.andrew.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "thetraveler.db";
    private static final int DATABASE_VERSION = 1;

    // Assignments
    public static final String TABLE_DESTINATIONS = "destinations";
    public static final String COLUMN_DESTINATION_ID = "destination_id";
    public static final String COLUMN_DESTINATION = "destination";

    // Creating the table
    private static final String DATABASE_CREATE_ASSIGNMENTS =
        "CREATE TABLE " + TABLE_DESTINATIONS +
            "(" +
            COLUMN_DESTINATION_ID + " integer primary key autoincrement, " +
            COLUMN_DESTINATION + " text not null" +
            ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the sql to create the table assignments
        db.execSQL(DATABASE_CREATE_ASSIGNMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESTINATIONS);
        onCreate(db);
    }
}
