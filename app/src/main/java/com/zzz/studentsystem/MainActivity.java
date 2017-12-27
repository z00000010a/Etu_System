package com.zzz.studentsystem;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.zzz.studentsystem.login.Login;
import com.zzz.studentsystem.note.NoteEd;
import com.zzz.studentsystem.phone_call.PhoneCall;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText et_id;
    private EditText et_name;
    private EditText et_phone;
    private ListView lv;
    private StudentDao dao;
    private PhoneCall call;

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
        login();

    }
    public void addStudent(View view){
        String id = et_id.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher_studentid = pattern.matcher(phone);
        Matcher matcher_phone = pattern.matcher(phone);
        if (!matcher_phone.matches()||!matcher_studentid.matches()){
            Toast.makeText(MainActivity.this,"Le format d'entrée est incorrect",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(id)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)){
            Toast.makeText(MainActivity.this,"Ne peut pas être vide",Toast.LENGTH_SHORT).show();
            return;
        }else{

            StudentInfo info = new StudentInfo();
            info.setId(Integer.valueOf(id));
            info.setPhone(phone);
            info.setName(name);
            info.setNote(null);

            if (dao.getStudentInfo(id).get("studentid")!=null){
                Toast.makeText(MainActivity.this,"id confilt",Toast.LENGTH_SHORT).show();
            }else if(dao.getStudentInfo(name).get("name")!=null){
                Toast.makeText(MainActivity.this,"name confilt",Toast.LENGTH_SHORT).show();
            }else if (dao.getStudentInfo(phone).get("phone")!=null){
                Toast.makeText(MainActivity.this,"phone confilt",Toast.LENGTH_SHORT).show();
            }else{
            boolean result = dao.add(info);
            if (result){
                Toast.makeText(MainActivity.this,"Ajouté avec succès",Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
                }
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.item,null);
            final TextView tv_item_id = (TextView) view.findViewById(R.id.tv_item_id);
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            final TextView tv_item_phone = (TextView) view.findViewById(R.id.tv_item_phone);
            ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            final Map<String,String> map = dao.getStudentInfo(position);

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = dao.delete(map.get("studentid"));
                    if (result){
                        Toast.makeText(MainActivity.this,"Supprimé avec succès",Toast.LENGTH_SHORT).show();
                    }
                    lv.setAdapter(new MyAdapter());
                }
            });

            tv_item_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call = new PhoneCall(tv_item_phone,MainActivity.this);
                    call.checkPermission();
                }
            });

            tv_item_id.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("num",tv_item_id.getText().toString().trim());
                    myIntent.putExtras(bundle);
                    myIntent.setClass(MainActivity.this,NoteEd.class);
                    MainActivity.this.startActivity(myIntent);
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
    public void login(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,Login.class);
        MainActivity.this.startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
               call.checkPermission();
            }else {
                Toast.makeText(MainActivity.this,"PREMISSIOM refusé",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
