package com.xx.invoker.dailynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by invoker on 2017/3/28.
 */

public class MyHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Collection";
    public static final int DB_VERSION = 2;

    public MyHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists collections(id int primary key,username varchar,title varchar,url varchar)");
        db.execSQL("create table if not exists user(id int primary key,username varchar, password varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            db.execSQL("drop table collections");
            onCreate(db);
        }
    }
}
