package nico.styTool;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class app_th extends dump.z.BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles = {"登录", "注册", "找回密码"};

    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
// 未安装手Q或安装的版本不支持
            return false;
        }
    }

    private void qqa() {
        joinQQGroup("YfKb2i5ShlUlith2KClI5GAxEMj5UoA8");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_tool) {
            AlertDialog.Builder builder = new AlertDialog.Builder(app_th.this);
            AlertDialog alertDialog = builder.setMessage("1、遇到数失败，一般都是用了广告插件或者改了hosts（包括广告快走，幸运破解器）\n2、已经开了网络（可能服务器在维护）")
                    .setPositiveButton("加入官方群", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            qqa();
                        }
                    }).create();
            alertDialog.show();
            //AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //  builder.setMessage("1、遇到数失败，一般都是用了广告插件或者改了hosts（包括广告快走，幸运破解器）\n2、已经开了网络（可能服务器在维护）").create().show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_splash);

        //Toolbar部分
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setTitle("(・・)");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mToolbar.setLogo(R.drawable.ic_lau);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager();
        //mToolbar.setTitle("帐号");
//		设置标题

//		设置副标题
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });

		/*	final SampleApplication myVideoView = (SampleApplication) findViewById(R.id.videoView);
         final String videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_video).toString();
		 myVideoView.setVideoPath(videoPath);
		 myVideoView.start();
		 myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

		 @Override
		 public void onPrepared(MediaPlayer mp) {
		 mp.start();
		 mp.setLooping(true);
		 }
		 });

		 myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

		 @Override
		 public void onCompletion(MediaPlayer mp) {
		 myVideoView.setVideoPath(videoPath);
		 myVideoView.start();
		 }
		 });*/
    }

    private void setViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new LoginActivity());
        fragments.add(new RegisterActivity());
        fragments.add(new Animateddex());

        //  fragments.add(new smali_layout_ida());
        viewPager.setAdapter(viewPagerAdapter);
        //viewPager.setOnPageChangeListener(this);

        // mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);设置后Tab标签往左边靠
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }

    FragmentStatePagerAdapter viewPagerAdapter = new FragmentStatePagerAdapter(
            getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }
    };
}
