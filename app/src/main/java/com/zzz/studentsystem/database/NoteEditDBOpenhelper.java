package com.zzz.studentsystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ares on 2017/11/14.
 */

public class NoteEditDBOpenhelper extends SQLiteOpenHelper {
    public NoteEditDBOpenhelper(Context context) {
        super(context,"noteEdit.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("创建数据库");
        db.execSQL("create table note (_id integer primary key autoincrement , studentid varchar(20) , note varchar(300));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}