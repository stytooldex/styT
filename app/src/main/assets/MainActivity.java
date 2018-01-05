package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="horizontal">

                <ProgressBar
                    android:id="@+id/pb_store"
                    style="?android:attr/progressBarStyleHorizontal"
                    android:layout_width="0dp"
                    android:layout_height="11sp"
                    android:layout_weight="1"
                    android:max="100"
                    android:minHeight="15dp"
                    android:progress="50"
                    android:progressDrawable="@drawable/custom_progressbar" />

                <TextView
                    android:id="@+id/showSd_tv"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="10086"
                    android:textSize="11sp" />
            </LinearLayout>
 */

/**
 * <LinearLayout
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:padding="16dp">
 * <p>
 * <EditText
 * android:layout_width="match_parent"
 * android:layout_height="28dp"
 * android:background="@drawable/bg"
 * android:drawableLeft="@drawable/ic_search_grey_800_24dp"
 * android:drawablePadding="4dp"
 * android:gravity="center_vertical"
 * android:imeOptions="actionSearch"
 * android:inputType="text"
 * android:maxLines="1"
 * android:paddingLeft="10dp"
 * android:text="13800138000"
 * android:textColor="#e6000000"
 * android:textCursorDrawable="@null"
 * android:textSize="12sp" />
 * </LinearLayout>
 * <activity
 * android:name=".jni_string"
 * android:label="查看当前Activity" />
 * <p>
 * <p>
 * <activity android:name=".WelcomeActivity" />
 * <p>
 * <p>
 * <activity android:name=".LxwBlogActivity" />
 * <p>
 * <p>
 * <activity android:name=".io_styTool" />
 * <p>
 * <p>
 * <activity android:name=".WupHexUtil" />
 * <p>
 * <p>
 * <activity android:name=".a" />
 * <p>
 * <p>
 * <activity
 * android:name=".lua_web"
 * android:configChanges="orientation|screenSize|keyboardHidden" />
 * <p>
 * <p>
 * <activity android:name=".KILL" />
 * <p>
 * <p>
 * <activity android:name=".LoginF" />
 * <p>
 * <p>
 * <activity
 * android:name=".ChatActivity"
 * android:windowSoftInputMode="adjustResize" />
 * <activity android:name=".CreateActivity" />
 * <p>
 * private void openFile(File f) {
 * final MyUser bmobUser = BmobUser.getCurrentUser(SimpleActivity.this, MyUser.class);
 * if (bmobUser != null) {
 * MyUser newUser = new MyUser();
 * newUser.setAddress("激活");
 * newUser.update(SimpleActivity.this, bmobUser.getObjectId(), new UpdateListener() {
 *
 * @Override public void onSuccess() {
 * }
 * @Override public void onFailure(int code, String msg) {
 * Toast.makeText(SimpleActivity.this, "认证失败，请重试（" + code + "）", Toast.LENGTH_SHORT).show();
 * }
 * });
 * } else {
 * //toast("本地用户为null,请登录。");
 * <p>
 * }
 * FK__ feedback = new FK__();
 * feedback.setContent((String) BmobUser.getObjectByKey(this, "objectId"));
 * feedback.save(SimpleActivity.this, new SaveListener() {
 * @Override public void onFailure(int p1, String p2) {
 * <p>
 * }
 * @Override public void onSuccess() {
 * <p>
 * Toast.makeText(SimpleActivity.this, "已关闭弹屏", Toast.LENGTH_SHORT).show();
 * final SharedPreferences setting = SimpleActivity.this.getSharedPreferences("Viewpa__m", 0);
 * setting.edit().putBoolean("FIRST", false).commit();
 * <p>
 * }
 * <p>
 * });
 * Intent intent
 * <p>
 * <uses-permission android:name="android.permission.SET_WALLPAPER" />设置桌面壁纸
 * <uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />
 * <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />修改声音设置
 * 允许程序在手机屏幕关闭后后台进程仍然运行
 * <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />禁用键盘锁
 * <uses-permission android:name="android.permission.GET_ACCOUNTS" />访问GMail账户列表
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />读写存储卡
 * <uses-permission android:name="android.permission.READ_LOGS" />允许程序读取系统底层日志
 * <uses-permission android:name="android.permission.GET_TASKS" />允许程序获取当前或最近运行的应用
 * <p>
 * <PreferenceCategory android:title="全局设置">
 * <CheckBoxPreference
 * android:defaultValue="true"
 * android:key="if_c_1"
 * android:title="暗色主题"
 * android:visibility="gone" />
 * </PreferenceCategory>
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_night_mode_system:
                //    setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                //    setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                //      setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                //       setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");
        adapter.addFragment(new CheeseListFragment(), "Category 2");
        adapter.addFragment(new CheeseListFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
