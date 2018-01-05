package com.bilibili.magicasakurademo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 26526 on 2017/12/30.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    protected PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceUtils = PreferenceUtils.getInstance(this);
        initTheme();
        super.onCreate(savedInstanceState);
    }

    private void initTheme() {
        MyThemeUtils.Theme theme = MyThemeUtils.getCurrentTheme(this);
        MyThemeUtils.changTheme(this, theme);
    }
}