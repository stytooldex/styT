package dump.b;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dump.f.xp;
import dump.f.xp_;
import dump.k.i_a;
import dump_dex.Software.DownloadService;
import nico.styTool.MyUser;
import nico.styTool.R;
import nico.styTool.app_th;

public class Main4Activity extends dump.z.BaseActivity {
    private TextView tvStart;
    private TextView tvNotice;


    android.support.v7.widget.ListViewCompat listView;
    FeedbackAdapter adapter;
    TextView emptyView;
    static String msg;
    private Toolbar toolbar;
    private MyUser myUser = null;

    private List<TextView> views = new LinkedList<>();//所有的视图
    private int timeC = 100;//变色时间间隔
    private int lightPosition = 0;//当前亮灯位置,从0开始
    private int runCount = 10;//需要转多少圈
    private int lunckyPosition = 4;//中奖的幸运位置,从0开始

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bkili, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_el) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "妮哩是一款覆盖功能齐全的工具软件。也可以说是一款集成了许多实用功能的工具箱，例如提取APK、root工具、王者荣耀是也修改、百度云直链提取、网易云音乐启动背景替换、身份证信息查询、快递查询、QQ极速红包等功能\n支付微信支付宝扫码收付款的功能快捷使用\n\n还可以快速领红包现金...\n https://www.coolapk.com/apk/nico.styTool");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isWxInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.chaquanbao")) {
                    return true;
                }
            }
        }

        return false;
    }

    private DownloadService.DownBinder mDownloadService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadService = ((DownloadService.DownBinder) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
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
                        nico.styTool.ToastUtil.show(Main4Activity.this, "版本不一致，请更新", Toast.LENGTH_SHORT);
                        finish();
                    }
                } else {

                }
            }
        });
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4main);
        final SharedPreferences i = getSharedPreferences("pHello500", 0);
        Boolean o0 = i.getBoolean("FIRST", true);
        if (o0) {//第一次截图
            /*
            AppCompatDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("其他规则")
                    .setMessage("邀请一个人可以得*+0.10现金{0.25现金上限25人<其他现金无上限}\n推荐 一个人截一图")
                    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            i.edit().putBoolean("FIRST", false).apply();

                        }
                    }).setCancelable(false).create();
            alertDialog.show();*/
        } else {

        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitleTextAppearance(this, R.style.ts);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
        // finish();
        xft();
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        myUser = BmobUser.getCurrentUser(MyUser.class);
        init();
        listView = (android.support.v7.widget.ListViewCompat) findViewById(R.id.lv_feedback);

        emptyView = new TextView(this);
        emptyView.setText("");
        emptyView.setGravity(Gravity.CENTER);
        //emptyView.setTextSize(15); //设置字体大小
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addContentView(emptyView, params);
        listView.setEmptyView(emptyView);

        BmobQuery<xp> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<xp>() {

            @Override
            public void done(List<xp> object, BmobException e) {
                if (e == null) {
                    adapter = new FeedbackAdapter(Main4Activity.this, object);
                    listView.setAdapter(adapter);
                } else {
                    emptyView.setText((CharSequence) object);
                }
            }

        });

    }

    private void sendMessage(String message) {
        BmobPushManager bmobPush = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        query.addWhereEqualTo("isDeveloper", true);
        bmobPush.setQuery(query);
        bmobPush.pushMessage(message);

        saveFeedbackMsg(message);
    }

    private void saveFeedbackMsg(String msg) {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String imei = "" + telephonyManager.getDeviceId();
        //String last6chars = imei.remove(0,imei.length-6);
        int n = 4;
        final String b = imei.substring(imei.length() - n, imei.length());
        //final int count = Integer.parseInt(b) + 36;
        xp feedback = new xp();
        feedback.setContent(b + "; " + msg);
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {

                } else {
                }
            }
        });
    }

    private void init() {
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        TextView tv5 = (TextView) findViewById(R.id.tv5);
        TextView tv6 = (TextView) findViewById(R.id.tv6);
        TextView tv7 = (TextView) findViewById(R.id.tv7);
        TextView tv8 = (TextView) findViewById(R.id.tv8);


        final TextView t5 = (TextView) findViewById(R.id.t5);
        final TextView t5_ = (TextView) findViewById(R.id.t6);

        tvStart = (TextView) findViewById(R.id.tvStart);
        tvNotice = (TextView) findViewById(R.id.tv_notice);
        views.add(tv1);
        views.add(tv2);
        views.add(tv3);
        views.add(tv4);
        views.add(tv5);
        views.add(tv6);
        views.add(tv7);
        views.add(tv8);

        try {
            tvStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean tmp = fileIsExists(Environment.getExternalStorageDirectory().getPath() + "/oOOOO0O");
                    if (tmp) {
                        Toast.makeText(Main4Activity.this, "一人仅限一个", Toast.LENGTH_SHORT).show();
                        final String packname = getPackageName();
                        try {
                            PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
                            Signature[] signs = packageInfo.signatures;
                            Signature sign = signs[0];
                            int code = sign.hashCode();
                            if (code != 312960342) {
                                views.add(t5);
                                views.add(t5_);
                            } else {
                                final TextView t5_ = (TextView) findViewById(R.id.t7);
                                t5_.setText("一人仅限一个 " + " 不能存在重复抽奖");
                                //
                            }
                        } catch (PackageManager.NameNotFoundException ignored) {
                        }
                        //System.out.println("exist");
                    } else {

                        boolean sFirstRun = isWxInstall(Main4Activity.this);
                        if (sFirstRun) {
                            final SharedPreferences i = getSharedPreferences("epo", 0);
                            Boolean o0 = i.getBoolean("FIRST", true);
                            if (o0) {//第一次
                                final String packname = getPackageName();
                                try {
                                    PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
                                    Signature[] signs = packageInfo.signatures;
                                    Signature sign = signs[0];
                                    int code = sign.hashCode();
                                    if (code != 312960342) {
                                        views.add(t5);
                                        views.add(t5_);
                                    } else {
                                        Toast.makeText(Main4Activity.this, "抽奖中", Toast.LENGTH_SHORT).show();
                                        final TextView t5_ = (TextView) findViewById(R.id.t7);
                                        t5_.setText("FIRST");
                                        //
                                    }
                                } catch (PackageManager.NameNotFoundException ignored) {
                                }
                                tvStart.setClickable(false);
                                tvStart.setEnabled(false);
                                tvNotice.setText("");
                                runCount = 10;
                                timeC = 100;
                                views.get(lunckyPosition).setBackgroundColor(Color.TRANSPARENT);
                                lunckyPosition = randomNum(0, 7);
                                new TimeCount(timeC * 9, timeC).start();
                            } else {
                                Toast.makeText(Main4Activity.this, "调皮", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(Main4Activity.this, "安装后才允许抽奖", Toast.LENGTH_SHORT).show();
                            LayoutInflater infl = LayoutInflater.from(Main4Activity.this);
                            View vie = infl.inflate(R.layout.g_m, null);
                            android.support.design.widget.FloatingActionButton mBtnDownload = vie.findViewById(R.id.appbili_g);
                            mBtnDownload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = "http://hdyhq.yicuba.com/tongji/59e5c9601061d22f40000478.apk";
                                    mDownloadService.startDownload(url);
                                }
                            });
                            AlertDialog.Builder bu = new AlertDialog.Builder(Main4Activity.this);
                            bu.setView(vie).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                        }


                        //System.out.println("no exist");

                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成随机数
     */
    private int randomNum(int minNum, int maxNum) {
        Random random = new Random();
        return random.nextInt(maxNum) % (maxNum - minNum + 1) + minNum;
    }

    private class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            lightPosition = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            // Log.i(">>>", "---" + lightPosition);
            if (runCount > 0) {
                if (lightPosition > 0) {
                    views.get(lightPosition - 1).setBackgroundColor(Color.TRANSPARENT);
                }
                if (lightPosition < 8) {
                    views.get(lightPosition).setBackgroundColor(Color.TRANSPARENT);//
                }

            } else if (runCount == 0) {

                if (lightPosition <= lunckyPosition) {
                    if (lightPosition > 0) {
                        views.get(lightPosition - 1).setBackgroundColor(Color.TRANSPARENT);
                    }
                    if (lightPosition < 8) {
                        views.get(lightPosition).setBackgroundColor(Color.RED);//
                    }
                }
            }

            lightPosition++;
        }

        //
        @Override
        public void onFinish() {
            //Log.i(">>>", "onFinish==" + runCount);
            //如果不是最后一圈，需要还原最后一块的颜色
            TextView tvLast = views.get(7);
            if (runCount != 0) {
                tvLast.setBackgroundColor(Color.TRANSPARENT);
                //最后几转速度变慢
                if (runCount < 3) timeC += 200;
                new TimeCount(timeC * 9, timeC).start();
                runCount--;
            }
            //如果是最后一圈且计时也已经结束
            if (runCount == 0 && lightPosition == 8) {
                /*
                final SharedPreferences i = getSharedPreferences("HT", 0);
                Boolean o0 = i.getBoolean("FIRST", true);
                if (o0) {//第一次
                    i.edit().putBoolean("FIRST", false).apply();
                    myUser.setid(views.get(lunckyPosition).getText().toString() + "");
                } else {

                }
                myUser.setAddress("激活v");
                myUser.update(Main4Activity.this, myUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {

                        if ("激活妮哩帐号".equals(views.get(lunckyPosition).getText().toString() + "")) {
                            myUser.setEmailVerified(true);
                            myUser.update(Main4Activity.this, myUser.getObjectId(), new UpdateListener() {

                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub
                                    //testGetCurrentUser();
                                    Toast.makeText(Main4Activity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                    tvNotice.setText("激活成功");

                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                }
                            });
                        } else {
                           // tvNotice.setText("恭喜你抽中: " + views.get(lunckyPosition).getText().toString() + "\n" + Html.fromHtml("只需提供妮哩个人资料截图)领奖方式QQ<font color='#e87400'>2652649464</font>推荐朋友额外+0.30*收益"));

                        }
                        // TODO Auto-generated method stub
                        //testGetCurrentUser();
                        //Toast.makeText(Main4Activity.this, "激活成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        //toast("更新用户信息失败:" + msg);
                        if (code == 202) {
                            Toast.makeText(Main4Activity.this, "帐号异常，请重新注册", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Main4Activity.this, "异常" + code, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                */
                if ("激活妮哩帐号".equals(views.get(lunckyPosition).getText().toString() + "")) {
                    MyUser myUserw = BmobUser.getCurrentUser(MyUser.class);
                    if (myUserw != null) {
                        myUser.setEmailVerified(true);
                        myUser.update(myUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(Main4Activity.this, "激活成功", Toast.LENGTH_SHORT).show();
                                    tvNotice.setText("激活成功");
                                } else {

                                }
                            }
                        });
                    } else {
                        Intent inte = new Intent(Main4Activity.this, app_th.class);
                        startActivity(inte);
                        Toast.makeText(Main4Activity.this, "激活需要登录帐号", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    xp_ feedback = new xp_();
                    feedback.setContent(tm.getDeviceId() + "&");
                    feedback.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, final BmobException Code) {
                            if (Code == null) {
                                final SharedPreferences i = getSharedPreferences("epo", 0);
                                Boolean o0 = i.getBoolean("FIRST", true);
                                if (o0) {//第一次
                                    i.edit().putBoolean("FIRST", false).apply();
                                } else {
                                    final String packname = getPackageName();
                                    try {
                                        PackageInfo packageInfo = getPackageManager().getPackageInfo(packname, PackageManager.GET_SIGNATURES);
                                        Signature[] signs = packageInfo.signatures;
                                        Signature sign = signs[0];
                                        int code = sign.hashCode();
                                        if (code != 312960342) {
                                            Toast.makeText(Main4Activity.this, "抽奖中", Toast.LENGTH_SHORT).show();
                                        } else {
                                            final TextView t5_ = (TextView) findViewById(R.id.t7);
                                            t5_.setText("一人仅限一个 " + " 不能存在重复抽奖");
                                        }
                                    } catch (PackageManager.NameNotFoundException ignored) {
                                    }
                                }
                                nico.FileUtils.createIfNotExist(Main4Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "oOOOO0O");
                                nico.FileUtils.createIfNotExist(Main4Activity.this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "调皮");

                                if ("谢谢参与".equals(views.get(lunckyPosition).getText().toString() + "")) {
                                    RelativeLayout tvStart = (RelativeLayout) findViewById(R.id.t6jj);
                                    tvStart.setVisibility(View.GONE);
                                } else {
                                    toolbar.setSubtitle(views.get(lunckyPosition).getText().toString());
                                    tvNotice.setText("抽中: " + views.get(lunckyPosition).getText().toString() + "\n" + "只需提供当前截图 领奖方式QQ:2652649464 推荐朋友额外+0.*收益");
                                }
                                //  tvNotice.setText("恭喜你抽中: " + views.get(lunckyPosition).getText().toString() + "\n" + Html.fromHtml("只需提供当前截图)领奖方式QQ<font color='#e87400'>2652649464</font>推荐朋友额外+0.30*收益"));

                            } else {
                                if (Code.getErrorCode() == 401) {
                                    Toast.makeText(Main4Activity.this, "不能存在重复抽奖", Toast.LENGTH_SHORT).show();
                                } else if (Code.getErrorCode() == 9016) {
                                    Toast.makeText(Main4Activity.this, "无网络连接，请检查您的手机网络.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Main4Activity.this, "异常" + Code.getErrorCode(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                    CheckBox cb = (CheckBox) findViewById(R.id.lxwusileCheckBox2);
                    cb.setChecked(true);//选中
                }

                tvStart.setClickable(true);
                tvStart.setEnabled(true);//
                String content = views.get(lunckyPosition).getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    if (content.equals(msg)) {
                        // Toast.makeText(this, "nit", Toast.LENGTH_SHORT).show();
                    } else {
                        msg = content;
                        // 发送反馈信息
                        sendMessage(content);
                        //Toast.makeText(this, "yef", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Toast.makeText(this, "?", Toast.LENGTH_SHORT).show();
                }

                if (lunckyPosition != views.size())
                    tvLast.setBackgroundColor(Color.TRANSPARENT);

            }

        }
    }


    private static class FeedbackAdapter extends BaseAdapter {

        List<xp> fbs;
        LayoutInflater inflater;

        FeedbackAdapter(Context context, List<xp> feedbacks) {
            this.fbs = feedbacks;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fbs.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return fbs.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = inflater.inflate(R.layout.fragment_main, null);
                holder.content = convertView.findViewById(R.id.tv_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            xp feedback = fbs.get(position);

            holder.content.setText(feedback.getContent());

            return convertView;
        }

        static class ViewHolder {
            TextView content;
        }

    }

}

