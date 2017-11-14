package com.zzz.studentsystem.phone_call;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz.studentsystem.MainActivity;

/**
 * Created by Ares on 2017/11/14.
 */

public class PhoneCall extends AppCompatActivity {
    private TextView phone_num;
    private Context context;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public PhoneCall(TextView phone_num, Context context){
        this.phone_num=phone_num;
        this.context=context;
    }

    private void callPhone(){
        String phoneNum = phone_num.getText().toString().trim();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://"+phoneNum));
        context.startActivity(intent);
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }else {
            callPhone();
        }
    }

}
