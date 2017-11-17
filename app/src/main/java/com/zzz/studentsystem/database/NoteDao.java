package com.zzz.studentsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ares on 2017/11/14.
 */

public class NoteDao {

    public StudentDBOpenhelper helper;

    public NoteDao(Context context){
    helper = new StudentDBOpenhelper(context);
    }

    public boolean add(String studentid, String note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentid",studentid);
        values.put("note",note);
        long row = db.insert("note",null,values);
        db.close();
        return row == -1 ? false:true;
    }

    public int update(String studentid, String note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentid",studentid);
        values.put("note",note);
        int row = db.update("note",values,"studentid=?",new String[]{note});
        db.close();
        return row;
    }
    public String query(String studentid){
        SQLiteDatabase db = helper.getReadableDatabase();
        String note = null;
        Cursor cursor = db.query("note", new String[]{"note"},"studentid=?",new String[]{studentid},null,null,null);
        if (cursor.moveToNext()){
            note=cursor.getString(0);
        }
        db.close();
        return note;
    }
}
