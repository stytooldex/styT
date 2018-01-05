package dump.z;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import nico.styTool.R;

/**
 * Created by 26526 on 2017/12/23.
 */

public class __BaseActivity extends AppCompatActivity {

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            SharedPreferencesUtil.putData("SDK_INT", "4");
        } else {
            //StatusBarUtil.setColor(this, getColorPrimary(), 0);
        }    */

    }

    public Context getContext() {
        return this;
    }

}