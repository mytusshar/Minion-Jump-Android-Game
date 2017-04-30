package com.example.mytusshar.minionjump.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.mytusshar.minionjump.otherviews.OptionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mytusshar on 8/17/2016.
 */
public class DataBaseOperation  {

    private static final String TABLE_NAME = "myGame_TABLE";
    private SQLOpenHelper sqlOpenHelper;
    SharedPreferences sharedPreferences;
    String difficultyString;
////////////////////////////////////////////////////////////////////////////////////////////////////
    public DataBaseOperation(Context context){
        this.sqlOpenHelper = new SQLOpenHelper(context);
        sharedPreferences = context.getSharedPreferences(OptionView.PREFS_DIFF, 0);
        difficultyString = sharedPreferences.getString(OptionView.PREFS_DIFF, "Tush");
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void SaveHeight(long height, String date){
        SQLiteDatabase database_read = sqlOpenHelper.getReadableDatabase();
        Cursor cursor = database_read.query(TABLE_NAME, null,  null,  null, null,  null, "highid desc");
        int height_col = cursor.getColumnIndex("height");
        int diff_col = cursor.getColumnIndex("difficulty");
        long temp_h = 0;
        for (cursor.moveToFirst() ; !(cursor.isAfterLast()) ; cursor.moveToNext()){
            if(cursor.getString(diff_col).equalsIgnoreCase(difficultyString)){
                temp_h =  Long.parseLong(cursor.getString(height_col));
            }
        }
        if(GetNum() > 0){
            cursor.moveToFirst();
            if(height > temp_h)
                SaveToDataBase(height, date);
        }
        else {
            SaveToDataBase(height, date);
        }
        cursor.close();
        database_read.close();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void SaveToDataBase(long height, String date) {
        SQLiteDatabase database_write = sqlOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("height", ""+height);
        values.put("difficulty", difficultyString);
        database_write.insert(TABLE_NAME, "highid", values);
        database_write.close();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<MyString> GetScoreList(){
        List<MyString> scoreList = new ArrayList<MyString>();
        SQLiteDatabase database_read = sqlOpenHelper.getReadableDatabase();
        Cursor cursor = database_read.query(TABLE_NAME, null,  null,  null, null,  null, "highid desc");
        int height_col = cursor.getColumnIndex("height");
        int date_col = cursor.getColumnIndex("date");
        int diff_col = cursor.getColumnIndex("difficulty");
        int num = 0;
        if(GetNum() > 0){
            for (cursor.moveToFirst() ; !(cursor.isAfterLast()) ; cursor.moveToNext()){
                num++;
                scoreList.add(new MyString(""+num+"."+cursor.getString(date_col)+"     "+cursor.getString(height_col), cursor.getString(diff_col)));
            }
        }
        return scoreList;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public long GetNum(){
        SQLiteDatabase database_read = sqlOpenHelper.getReadableDatabase();
        Cursor cursor = database_read.query(TABLE_NAME, null,  null,  null, null,  null, "highid desc");
        int num = 0;
        for (cursor.moveToFirst() ; !(cursor.isAfterLast()) ; cursor.moveToNext()){
            num ++;
        }
        return num;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
