package com.zzz.studentsystem.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzz.studentsystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ares on 2017/11/17.
 */

public class Login extends Activity{
    private EditText nom;
    private EditText pwd;
    private Button login;
    private String u_nom;
    private String u_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.login);
        nom=findViewById(R.id.nom);
        pwd=findViewById(R.id.pwd);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_nom=nom.getText().toString().trim();
                u_pwd=pwd.getText().toString().trim();
                if (md5(u_nom,u_pwd).equals(getPwd())){
                    finish();
                }else {
                    Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private String getPwd(){
        InputStream is = Login.this.getClassLoader().getResourceAsStream("assets/" + "Password.txt");
        int lenght = 0;
        String result = null;
        try {
            lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String md5(String nom,String pwd){
        System.out.println(new Md5().getPassWordMD5(nom,pwd));
        return new Md5().getPassWordMD5(nom,pwd);
    }

}
