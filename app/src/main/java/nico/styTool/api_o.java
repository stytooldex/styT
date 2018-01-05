package nico.styTool;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dump.b.b.service.FloatWindowService;
import dump.b.b.service.TrafficInfo;

public class api_o extends AppCompatActivity {

    private TextView mTextView;

    Handler mHandler;
    TrafficInfo speed;

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    FloatWindowService service;
    private ServiceConnection conn = new ServiceConnection() {
        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder binder) {
            // Log.i("test","-------------------");
            service = ((FloatWindowService.ServiceBinder) binder).getService();

        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            service = null;
        }

    };

    private void updateProcessInfo() {
        String mText = "";
        mTextView.setText(mText);

        // 获取ActivityManager
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

        // 获取进程信息***************************************************
        List<RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();

        for (RunningAppProcessInfo info : infos) {
            String name = info.processName;
            int uid = info.uid;
            int pid = info.pid;

            mText = mTextView.getText().toString();
            mText += name + "\n\n";
            mTextView.setText(mText + " uid=" + uid + " pid=" + pid);

        }

    }

    //@Override
    // protected void onDestroy() {
    //     super.onDestroy();
    //     speed.stopCalculateNetSpeed();
    //     unbindService(conn);
    // }

    public void ok(View view) {
        Toast.makeText(this, "请直接按到桌面", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(api_o.this, FloatWindowService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        //finish();
        if (dump.v.testThread.checkAlertWindowsPermission(this)) {
            //Toast.makeText(this, "不需要权限的悬浮窗实现", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "需要开启悬浮窗权限", Toast.LENGTH_LONG).show();
        }

    }

    public void no(View view) {
        Intent intent = new Intent(api_o.this, dump.b.b.service.FloatWindowService.class);
        stopService(intent);
    }


    private String getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }

    //	private static final String TAG = "FxService";
    private String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.a_qwp);
        boolean isFirstRun = (boolean) nico.SPUtils.get(this, "if_styTool__", false);
        if (isFirstRun) {
            StatusBarUtil.setColor(this, getColorPrimary());
        } else {

        }
        ActivityManager mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        //  mTextView = (TextView) findViewById(R.id.tv);

        try {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        //mTextView.setText(msg.obj + "kb/s");
                        if (service != null)
                            service.setSpeed(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()) + "\n" + "手机剩余:" + getRomAvailableSize() + "(" + msg.obj + "kb/s)");
                    }
                    // + "SD卡剩余:" + getSDAvailableSize()
                    super.handleMessage(msg);
                }

            };
            speed = new TrafficInfo(this, mHandler, 10035);
            speed.startCalculateNetSpeed();
        } catch (Exception e) {
            e.printStackTrace();
        }


        final SharedPreferences setting = getSharedPreferences("xposed_l", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            /*
             AlertDialog.Builder obuilder = new AlertDialog.Builder(this);
			 AlertDialog alertDialog = obuilder.setMessage("本功能来自我一个想法...\n每次在录【王者荣耀】时候\n防不胜防会出现内存不足\n然后我就1v5...\n【作者ID】：月色媌")
			 .setNegativeButton("露娜", new DialogInterface.OnClickListener() {
			 @Override
			 public void onClick(DialogInterface dialog, int which)
			 {
			 setting.edit().putBoolean("FIRST", false).commit();

			 }
			 }).setCancelable(false)
			 .create();

			 alertDialog.show();
			 */
        } else {

        }
    }
}
