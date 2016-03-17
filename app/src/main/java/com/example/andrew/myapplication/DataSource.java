package com.example.andrew.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] destinationAllColumns = { MySQLiteHelper.COLUMN_DESTINATION_ID, MySQLiteHelper.COLUMN_DESTINATION };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createDestination(String destination) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DESTINATION, destination);
        long insertId = database.insert(MySQLiteHelper.TABLE_DESTINATIONS, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    public void deleteDestination(Destination destination) {
        if (!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_DESTINATIONS, MySQLiteHelper.COLUMN_DESTINATION_ID + " =?", new String[]{Long.toString(destination.getId())});

        if (database.isOpen())
            close();
    }

    public void updateDestination(Destination destination) {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_DESTINATION, destination.getDestination());
        database.update(MySQLiteHelper.TABLE_DESTINATIONS, args, MySQLiteHelper.COLUMN_DESTINATION_ID + "=?", new String[]{Long.toString(destination.getId())});
        if (database.isOpen())
            close();
    }

    public List<Destination> getAllDestinations() {
        if (!database.isOpen())
            open();

        List<Destination> destinations = new ArrayList<Destination>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DESTINATIONS, destinationAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Destination destination = cursorToDestination(cursor);
            destinations.add(destination);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return destinations;
    }

    public Destination getDestination(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DESTINATIONS, destinationAllColumns, MySQLiteHelper.COLUMN_DESTINATION_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        Destination destination = cursorToDestination(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return destination;
    }

    private Destination cursorToDestination(Cursor cursor) {
        try {
            Destination destination = new Destination();
            destination.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_DESTINATION_ID)));
            destination.setDestination(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_DESTINATION)));
            return destination;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
