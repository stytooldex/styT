package nico.styTool;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.text.TextUtils;

import com.fm.openinstall.OpenInstall;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import dump.o.SharedPreferencesUtil;

/**
 * Created by lum on 2017/10/22.
 */
public class HApp extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;


    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xfffb7299:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case 0xffb85671:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case 0x99f0486c:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return -1;
    }

    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }

    /**
     * 判断是否是主进程
     *
     * @return
     */
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService
                (Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BGASwipeBackHelper.init(this, null);
        if (isMainProcess()) {
            OpenInstall.init(this);
            OpenInstall.setDebug(true);
        }

        context = getApplicationContext();
/*
        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// 初始化Bugly
        CrashReport.initCrashReport(context, "34c3176b4a", false, strategy);
 */


        CrashReport.initCrashReport(getApplicationContext(), "34c31", false);
        SharedPreferencesUtil.getInstance(this, "TestBILI");
        Bmob.initialize(this, "", "dio");
        //Bmob.initialize(this, nico.GetPathFromUri4kitkat.jk("～#～～～……～、～_•%～～•～•&•%～&•%•&～&～～～￥～、～#～～～>•#～&～>～、～、～#～#～>～、•&•～•_"));
        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {
                // if (e == null) {
                //Logger.i(bmobInstallation.getObjectId() + "-" + bmobInstallation.getInstallationId());
                // } else {
                // Logger.e(e.getMessage());
                // }
            }
        });
// 启动推送服务
        BmobPush.startWork(this);
        MobSDK.init(this, this.a(), this.b());
        //初始化辅助功能基类
        BaseAccessibilityService.getInstance().init(getApplicationContext());
        SettingConfig.getInstance().init(getApplicationContext());
        //Exce handler = Exce.getInstance(this);
        //Thread.setDefaultUncaughtExceptionHandler(handler);

        // 异常处理，不需要处理时注释掉这两句即可！
        //CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        //crashHandler.init(getApplicationContext());
    }
}