package com.zzz.studentsystem;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zzz.studentsystem.domain.StudentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    private EditText et_id;
    private EditText et_name;
    private EditText et_phone;
    private ListView lv;
    private ArrayList<StudentInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    //初始化界面
    private void initView() {
        setContentView(R.layout.activity_main);
        et_id = (EditText)findViewById(R.id.et_id);
        et_name = (EditText)findViewById(R.id.et_name);
        et_phone = (EditText)findViewById(R.id.et_phone);
        lv = (ListView) findViewById(R.id.lv);

        list = new ArrayList<>();

        lv.setAdapter(new MyAdapter());

        for (int i = 0; i <10; i++) {
            StudentInfo info = new StudentInfo();
             info.setId(i);
            info.setName("Student"+i);
            info.setPhone("1234567"+i);
            list.add(info);
        }

    }
    public void addStudent(View view){

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.item,null);
            TextView tv_item_id = (TextView) view.findViewById(R.id.tv_item_id);
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            TextView tv_item_phone = (TextView) view.findViewById(R.id.tv_item_phone);

            ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    lv.setAdapter(new MyAdapter());
                }
            });

            tv_item_id.setText(String.valueOf(getItem(position).getId()));
            tv_item_name.setText(getItem(position).getName());
            tv_item_phone.setText(getItem(position).getPhone());


            return view;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public StudentInfo getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
