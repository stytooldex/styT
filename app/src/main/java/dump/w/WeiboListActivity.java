package dump.w;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import nico.styTool.R;

public class WeiboListActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);

        android.support.v7.widget.SwitchCompat switchShop = menu.findItem(R.id.myswitch).getActionView().findViewById(R.id.switchForActionBar);
        switchShop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
                if (isChecked) { //开店申请
                    setTitle("显示个人");
                    //UI.toast(getApplicationContext(), "开店啦");
                } else { //关店申请
                    setTitle("显示全部");

                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_);
    }
}