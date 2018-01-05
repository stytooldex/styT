package nico.styTool;

/**
 * Created by lum on 2017/10/31.
 */

import android.annotation.SuppressLint;
import android.content.Context;

import dump.o.SharedPreferencesUtil;

/**
 * Created by kongqw on 2015/11/3.
 */
public class Exce implements Thread.UncaughtExceptionHandler {
    @SuppressLint("StaticFieldLeak")
    private static Exce myCrashHandler;

    private Context mContext;

    private Exce(Context context) {
        mContext = context;
    }

    public static synchronized Exce getInstance(Context context) {
        if (null == myCrashHandler) {
            myCrashHandler = new Exce(context);
        }
        return myCrashHandler;
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        long threadId = thread.getId();
        String message = throwable.getMessage();
        String localizedMessage = throwable.getLocalizedMessage();
        //Log.i("KqwException", "------------------------------------------------------");
        //Log.i("KqwException", "threadId = " + threadId);
        // Log.i("KqwException", "message = " + message);
        // Log.i("KqwException", "localizedMessage = " + localizedMessage);
        // Log.i("KqwException", "------------------------------------------------------");
        throwable.printStackTrace();
        // Log.i("KqwException", "------------------------------------------------------");

        // TODO 下面捕获到异常以后要做的事情，可以重启应用，获取手机信息上传到服务器等
        // Log.i("KqwException", "------------------应用被重启----------------");
        // 重启应用
        SharedPreferencesUtil.putData("msgbug", "threadId = " + threadId + "\nmessage = " + message + "\nlocalizedMessage = " + localizedMessage);
        mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()));
        //干掉当前的程序
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}