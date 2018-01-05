package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by luxin on 15-12-15.
 * http://luxin.gitcafe.io
 */
public class EdiUserProfileActivity extends AppCompatActivity {
    private EditText ediUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_edi_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        ediUsername = (EditText) findViewById(R.id.lxw_edi_user_profile_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edi_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_edi_user_profile) {
            Intent intent = new Intent();
            intent.putExtra("username", ediUsername.getText().toString().trim());
            setResult(UserProfileActivity.RESULT_CODE, intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
