package com.example.mathijs.mathijsparmentierpset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mathijs on 27/11/2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {

    private static RestoDatabase instance;

    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE resto (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, amount INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS resto");
        onCreate(db);
    }

    public static RestoDatabase getInstance(Context c) {
        if (instance == null)
            instance = new RestoDatabase(c, "todos", null, 1);
        return instance;
    }

    public void addItem(String name, int price) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM resto WHERE name = " + name, null);
        if (cursor.getCount() < 1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("price", price);
            contentValues.put("amount", 1);
            db.insert("resto", null, contentValues);
        } else {
            db.execSQL("UPDATE resto SET amount = amount + 1 WHERE name = " + name, null);
        }
    }

    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS resto");
        onCreate(db);
    }

}
