package nico.styTool;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import dump_dex.service.ws_Main3Activity;

public class CheeseDetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       /*
        boolean tmp = fileIsExists(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/QWallet/2113075983/Wooden");
        if (tmp) {
            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.One.WoodenLetter");
            startActivity(LaunchIntent);
            finish();
        } else {
            //System.out.println("no exist");
        }   */
        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.appbili);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(CheeseDetailActivity.this, ws_Main3Activity.class);
                startActivity(b);
                finish();
                final SharedPreferences i0 = getSharedPreferences("Hellki0", 0);
                i0.edit().putBoolean("FIRST", false).apply();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}