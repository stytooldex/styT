package nico.styTool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import dump.z.AppCompatPreferenceActivity;

public class lua extends AppCompatPreferenceActivity {
    /**
     * 自定义布局B
     **/
    Preference preference1 = null;
    Context mContext = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//		setContentView(R.layout.activity_setting);StatusBarUtil.setStatusBarLightMode(this, getResources().getColor(R.color.colorAccent));

        addPreferencesFromResource(R.xml.preferences);
        Toast.makeText(this, "聊天界面不自动发,长按+音量键", Toast.LENGTH_SHORT).show();
        preference1 = findPreference("donate_alipay");
        preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        SharedPreferences sp = preference.getSharedPreferences();
        boolean ON_OFF = sp.getBoolean("auto_send_message", false);
        boolean next_screen = sp.getBoolean("next_screen_checkbox_preference", false);
        String text = sp.getString("auto_send_message_text", "");
        String listtext = sp.getString("auto_send_message_frequency", "");
        return true;
    }
}
