package com.slavikart.hw_10.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shopping_lists.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_LISTS = "lists";
    public static final String TABLE_TYPES = "types";
    public static final String TABLE_PRODUCTS = "products";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LISTS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "date INTEGER NOT NULL," +
                "description TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TYPES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "label TEXT NOT NULL," +
                "rule TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "count REAL NOT NULL," +
                "list_id INTEGER NOT NULL," +
                "checked INTEGER NOT NULL," +
                "count_type INTEGER NOT NULL," +
                "FOREIGN KEY(list_id) REFERENCES " + TABLE_LISTS + "(_id)," +
                "FOREIGN KEY(count_type) REFERENCES " + TABLE_TYPES + "(_id))");

        insertInitialTypes(db);
    }

    private void insertInitialTypes(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_TYPES + " (label, rule) VALUES ('шт', 'int')");
        db.execSQL("INSERT INTO " + TABLE_TYPES + " (label, rule) VALUES ('кг', 'float')");
        db.execSQL("INSERT INTO " + TABLE_TYPES + " (label, rule) VALUES ('л', 'float')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTS);
        onCreate(db);
    }
} 