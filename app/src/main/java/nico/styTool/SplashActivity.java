package nico.styTool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.Toast;

import dump.z.BaseActivity;
import dump_dex.service.ws_Main3Activity;
import site.gemus.openingstartanimation.OpeningStartAnimation;
import site.gemus.openingstartanimation.RotationDrawStrategy;


public class SplashActivity extends BaseActivity {


    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//       //太占资源，需要销毁资源
//        shimmer.cancel();
    }


    // private final static Long TIME = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.s_splash);
        OpeningStartAnimation openingStartAnimation3 = new OpeningStartAnimation.Builder(this)
                .setDrawStategy(new RotationDrawStrategy())
                .setColorOfAppIcon(getColorPrimary())
                .setColorOfAppName(getColorPrimary())
                .setAppName("花泽榨菜")
                .setAppIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .setColorOfAppStatement(Color.parseColor("#00000000"))
                .create();
        openingStartAnimation3.show(this);
        //android.support.v7.widget.AppCompatImageView iv = (android.support.v7.widget.AppCompatImageView) findViewById(R.id.s_spla);
        //StatusBarUtil.setTransparentForImageView(this, iv);

         /*
         iv.setBackgroundColor(getColorPrimary());
        SharedPreferences setting = SplashActivity.this.getSharedPreferences("play_", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            setting.edit().putBoolean("FIRST", false).apply();
        } else {

        }*/
        new Handler().postDelayed(new Runnable() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT <= 17) {
                    Toast.makeText(SplashActivity.this, "抱歉!不支持4.3以下系统", Toast.LENGTH_SHORT).show();
                } else {
                    final SharedPreferences i0 = getSharedPreferences("Hellki40", 0);
                    Boolean o00 = i0.getBoolean("FIRST", true);
                    if (o00) {
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intt = new Intent(SplashActivity.this, ws_Main3Activity.class);
                        startActivity(intt);
                        finish();
                    }

                }

            }
        }, 1500L);
        try {
            StatusBarUtil.setColor(this, Color.parseColor("#00000000"), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
