package com.zzz.studentsystem.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zzz.studentsystem.R;

/**
 * Created by Ares on 2017/11/17.
 */

public class Login extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.login);

    }
}
