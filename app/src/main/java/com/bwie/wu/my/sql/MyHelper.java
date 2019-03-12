package com.bwie.wu.my.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Auther: 武丙涛
 * @Date: 2019/2/28 11:20:12
 * @Description:
 */
public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super (context,"bw.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
