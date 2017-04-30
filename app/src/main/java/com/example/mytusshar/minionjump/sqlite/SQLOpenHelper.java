package com.example.mytusshar.minionjump.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class SQLOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myGame_TABLE.db";
    private static final int DATABASE_VERSION = 1;

    public SQLOpenHelper(Context context){
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("create table myGame_TABLE (highid integer primary key autoincrement,"
                +"date text,"+"height text,"+"difficulty text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
