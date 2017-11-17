package com.zzz.studentsystem.note;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzz.studentsystem.R;
import com.zzz.studentsystem.database.StudentDao;

/**
 * Created by Ares on 2017/11/15.
 */

public class NoteEd extends Activity {
    private TextView tv_date;
    private EditText et_content;
    private Button btn_ok;
    private Button btn_cancel;
    private StudentDao dao;
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

        dao = new StudentDao(this);
        System.out.println("数据库中："+dao.queryNote(studentid));
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

                    updateNote(et_content.getText().toString());

                finish();
            }
        });
    }



    private void updateNote(String note){
        dao.updateNote(studentid,note);
    }
    private void getNote(){
        String text = dao.queryNote(studentid);
        et_content.setText(text);
    }
}

