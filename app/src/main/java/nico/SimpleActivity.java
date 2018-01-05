package nico;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

import dump.z.BaseActivity;
import nico.styTool.MyUser;
import nico.styTool.R;


public class SimpleActivity extends BaseActivity {//展开全部

    int count = 0;
    private static ProgressBar progBar;

    private Button ediComment;// 广播
    private TextView userName;


    // private void jko() {


    /*final SharedPreferences setting = SimpleActivity.this.getSharedPreferences("Viewpa__m", 0);
    Boolean user_first = setting.getBoolean("FIRST", true);
    if (user_first) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.a_boo, null);
        ediComment = (Button) view.findViewById(R.id.button4);
        progBar = (ProgressBar) view.findViewById(R.id.progressBar1);

        ediComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownloadService();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("厚脸无耻求支援")
                .setView(view)
                .setPositiveButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("领取现金红包", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String[] os = {"妮媌帐号已经激活过", "下载查券宝并且安装使用", "QQ号最后两位（0.**）＋妮媌注册日期（0.日）－领取当天（0.日）＝领取余额】", "活动时间 2017.7.14-2017.8.16", "说明：领取前提需要（个人资料截图和查券宝截图）", "添加我:2652649464"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SimpleActivity.this);
                AlertDialog alert = builder.setTitle("免费领取现金红包条件")
                        .setPositiveButton("推荐朋友免费领取", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "嗨 妮媌多顶集合功能，快速上手\n简：一目了然\n美：十全十美\n http://www.coolapk.com/apk/nico.styTool");
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setItems(os, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false).create();
                alert.show();

            }
        }).setCancelable(false)
                .create().show();
    } else {

    }*/
    private void ake(final String filePath) {
        boolean isFirstRun = (boolean) nico.SPUtils.get(SimpleActivity.this, "if_cp", true);
        if (isFirstRun) {
            final ProgressDialog mProgressDialog = ProgressDialog.show(SimpleActivity.this, null, "初始化内核...");
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//判断当前Tbs播放器是否已经可以使用。

                //if(TbsVideo.canUseTbsPlayer(SimpleActivity.this))
                //{
                //    TbsVideo.openVideo(SimpleActivity.this, "http://192.168.3.108:8080/alert_icon.mp4");
                //  }

                @Override
                public void onViewInitFinished(boolean b) {
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    // Log.d("app", " onViewInitFinished is " + b);
                    if (b) {
                        Intent inten = new Intent(SimpleActivity.this, dump.y.x5_MainActivity.class);
                        inten.putExtra("#", filePath);
                        startActivity(inten);
                        mProgressDialog.dismiss();
                    } else {
                        Toast.makeText(SimpleActivity.this, "初始化内核失败", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }

                }

                @Override
                public void onCoreInitFinished() {

                }
            };
            QbSdk.initX5Environment(getApplicationContext(), cb);
        } else {

        }
    }

    // @Override
    //protected void onResume() {
    //   super.onResume();
    //   xft();
    //  }

    private MyUser myUser = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_list_viewpager);
        // if (Build.VERSION.SDK_INT >= 21) {    //消除actionbar下的阴影线
        // getSupportActionBar().setElevation(0f);
        //   }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //
        //nico.styTool.StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout, 0);
        //
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DateHelper(), "本地服务");
       // adapter.addFragment(new Sample_(), "在线服务");
       // adapter.addFragment(new nico.styTool.smali_layout_shell_Util(), "辅助服务");
        //adapter.addFragment(new Main5Activity(), "Manager");
        // adapter.addFragment(new SampleApplication(), "Category 3");
        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        //ThemeDialog dialog = new ThemeDialog();
        //      dialog.show(context, "theme");
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