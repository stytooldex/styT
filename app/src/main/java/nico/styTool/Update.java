package nico.styTool;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import dump.k.i_a;

public class Update extends dump.z.BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles = {"｡ﾟ(ﾟ∩´﹏`∩ﾟ)ﾟ｡"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_ig);

        //Toolbar部分
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("一个记事");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        xft();
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager();

    }

    private void ti() {
        String urlStr = "https://styTool.top/usd.txt";
        //long a = System.currentTimeMillis();
        try {
        /*
         * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
	     * <uses-permission android:name="android.permission.INTERNET" ></uses>
	     */
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60 * 1000);
            conn.setReadTimeout(60 * 1000);
            // 取得inputStream，并进行读取
            InputStream input = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);

            }

            //int newVersion = Integer.parseInt(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void xft() {

        BmobQuery<i_a> query = new BmobQuery<>();
        query.getObject("03bf357e85", new QueryListener<i_a>() {

            @Override
            public void done(i_a movie, BmobException e) {
                if (e == null) {
                    String s = movie.getContent();
                    String sr = nico.styTool.Constant.a_mi + "\n" + nico.styTool.Constant.a_miui;
                    if (s.equals(sr)) {

                    } else {
                        nico.styTool.ToastUtil.show(Update.this, "版本不一致，请更新", Toast.LENGTH_SHORT);
                        finish();
                    }
                } else {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lxw_user_profile1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_1) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewPager() {
        final String packname = getPackageName();
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int code = sign.hashCode();
            if (code != 312960342) {
                ti();
            } else {
                //
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        fragments = new ArrayList<Fragment>();
        viewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
    }

    FragmentStatePagerAdapter viewPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
