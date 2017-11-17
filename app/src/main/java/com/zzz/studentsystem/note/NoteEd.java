package com.zzz.studentsystem.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzz.studentsystem.MainActivity;
import com.zzz.studentsystem.R;
import com.zzz.studentsystem.database.NoteDao;

/**
 * Created by Ares on 2017/11/15.
 */

public class NoteEd extends Activity {
    private TextView tv_date;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private NoteDao dao;
    private String studentid;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    }

    private void initView() {
        setContentView(R.layout.note);
        tv_date = (TextView) findViewById(R.id.tv_date);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        Bundle bundle = getIntent().getExtras();
        studentid = bundle.getString("num");
        dao = new NoteDao(this);
        System.out.println("数据库中："+dao.query(studentid));
        getNote();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkExist()){
                    System.out.println("更新字段内容："+et_content.getText().toString());
                    updateNote(et_content.getText().toString());
                }else {
                    createNote(et_content.getText().toString());
                }
                finish();
                System.out.println("数据库中："+dao.query(studentid));
            }
        });
    }

    private boolean checkExist(){
        System.out.println("检测是否存在");
        return dao.query(studentid) == null ? false : true;
    }
    private void createNote(String note){
        System.out.println("创建新的字段: "+note);
        dao.add(studentid,note);
        System.out.println("数据库中："+dao.query(studentid));
    }
    private void updateNote(String note){
        System.out.println("更新字段");
        System.out.println("方法内更新字段内容："+note);
        dao.update(studentid,note);
    }
    private void getNote(){
        System.out.println("获取字段");
        System.out.println("数据库中："+dao.query(studentid));
        String text = dao.query(studentid);
        et_content.setText(text);
        System.out.println("字段内容："+text);
    }
}

