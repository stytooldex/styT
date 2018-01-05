package nico.Compat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import dump.z.BaseActivity;
import nico.Blanknt;
import nico.styTool.R;

public class CompatActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private String[] titles = {"动态"};

    //CompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compat);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        toolbar.setSubtitleTextAppearance(this, R.style.ts);
        //toolbar.setTitle("nico～nico～ni");
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager();

    }

    private void setViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new Blanknt());
        /*
        boolean isF = (boolean) nico.SPUtils.get(this, "if_ThemeS", true);
        if (isF) {
           // fragments.add(new GirlFragment());
        } else {

        }
        fragments.add(new Compat1());
      //  fragments.add(new Compat2());
     */
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
            //return titles[position];
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }
    };
}