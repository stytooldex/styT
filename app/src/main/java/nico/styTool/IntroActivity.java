package nico.styTool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import dump_dex.data.SpanUtils;
import dump_dex.service.ws_Main3Activity;

/**
 * Created by 26526 on 2017/12/25.
 */

public class IntroActivity extends AppCompatActivity {
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public void clearActionBarShadow() {
        if (Build.VERSION.SDK_INT >= 21) {
            ActionBar supportActionBar = ((AppCompatActivity) this).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setElevation(0);
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.BlueTheme0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first2);
        android.support.design.widget.FloatingActionButton btn_publish = (android.support.design.widget.FloatingActionButton) findViewById(R.id.lxw_id_pu);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(IntroActivity.this, ws_Main3Activity.class);
                startActivity(b);
                finish();
                final SharedPreferences i0 = getSharedPreferences("Hellki40", 0);
                i0.edit().putBoolean("FIRST", false).apply();
            }
        });
        setTitle(SpanUtils.getClickableSpan(getApplicationContext()));
        clearActionBarShadow();
        StatusBarUtil.setColor(this, getColorPrimary(), 0);

    }

}