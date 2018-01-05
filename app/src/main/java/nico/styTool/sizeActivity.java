package nico.styTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import dump.l.loveviayou;


public class sizeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_dex);
        loveviayou.Modi_WangYi_Mp3_UI_beginning(sizeActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
