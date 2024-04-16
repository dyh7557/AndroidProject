package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "AddressTable";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String MESSAGE = "message";
    public static final String COLOR = "color";
    public static final String TIME = "time";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + TIME + " TEXT PRIMARY KEY,"
                + NAME + " TEXT, "
                + PHONE + " TEXT, "
                + MESSAGE + " TEXT, "
                + COLOR + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
