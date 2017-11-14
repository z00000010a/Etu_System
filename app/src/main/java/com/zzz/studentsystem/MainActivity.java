package com.zzz.studentsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz.studentsystem.database.StudentDao;
import com.zzz.studentsystem.domain.StudentInfo;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText et_id;
    private EditText et_name;
    private EditText et_phone;
    private ListView lv;
    private StudentDao dao;

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
        dao=new StudentDao(this);
        lv.setAdapter(new MyAdapter());

    }
    public void addStudent(View view){
        String id = et_id.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        if (TextUtils.isEmpty(id)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)){
            Toast.makeText(MainActivity.this,"数据不能为空",Toast.LENGTH_SHORT).show();
            return;
        }else{
            StudentInfo info = new StudentInfo();
            info.setId(Integer.valueOf(id));
            info.setPhone(phone);
            info.setName(name);
            boolean result = dao.add(info);
            if (result){
                Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.item,null);
            TextView tv_item_id = (TextView) view.findViewById(R.id.tv_item_id);
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            TextView tv_item_phone = (TextView) view.findViewById(R.id.tv_item_phone);
            ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            final Map<String,String> map = dao.getStudentInfo(position);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = dao.delete(map.get("studentid"));
                    if (result){
                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                    lv.setAdapter(new MyAdapter());
                }
            });

            tv_item_id.setText(String.valueOf(map.get("studentid")));
            tv_item_name.setText(map.get("name"));
            tv_item_phone.setText(map.get("phone"));


            return view;
        }
        @Override
        public int getCount() {
            return dao.getTotalCount();
        }

        @Override
        public Map<String, String> getItem(int position) {
            return dao.getStudentInfo(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
