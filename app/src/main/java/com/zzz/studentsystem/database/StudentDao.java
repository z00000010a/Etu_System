package com.zzz.studentsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zzz.studentsystem.domain.StudentInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ares on 2017/11/10.
 */

public class StudentDao {
    private StudentDBOpenhelper helper;

    public StudentDao(Context context){
        helper = new StudentDBOpenhelper(context);
    }

    /**
     * 添加数据到数据库
     * @param Studentid
     * @param name
     * @param phone
     * @return
     */
    public boolean add(String Studentid, String name, String phone){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentid",Studentid);
        values.put("name",name);
        values.put("phone",phone);
        long row = db.insert("info",null,values);
        db.close();
        return row == -1?false : true;
    }
    public boolean add(StudentInfo info){
        return add(String.valueOf(info.getId()),info.getName(),info.getPhone());
    }

    /**
     * 通过查找学生Id来删除学生
     * @param Studentid
     * @return
     */
    public boolean delete(String Studentid){
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete("info","studentid=?",new String[]{Studentid});
        db.close();
        return count<=0?false : true;
    }

    /**
     * 得到某一行的具体信息
     * @param position
     * @return
     */
    public Map<String, String> getStudentInfo(int position){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", new String[]{"studentid","name","phone"},null,null,null,null,null);
        cursor.moveToPosition(position);
        String studentid = cursor.getString(0);
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
        cursor.close();
        db.close();
        HashMap<String,String> result = new HashMap<>();
        result.put("studentid",studentid);
        result.put("name",name);
        result.put("phone",phone);
        return result;
    }

    public int getTotalCount(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info",null,null,null,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * 删除所有数据
     */
    public void deleteALL(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            Cursor cursor = db.query("info",new String[]{"studentid"},null,null,null,null,null);
            while (cursor.moveToNext()){
                String studentid=cursor.getString(0);
                db.delete("info","studentid=?",new String[]{studentid});
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            db.endTransaction();
            db.close();
        }
    }
}
