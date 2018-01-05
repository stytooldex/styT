package nico.styTool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import dump.t.addpr0;

/**
 * Created by 26526 on 2017/12/16.
 */

public class AppsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断收到的是什么广播
        String action = intent.getAction();
        //获取安装更新卸载的是什么应用
        Uri uri = intent.getData();
        if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
            if ("com.chaquanbao".equals(uri)) {
                Toast.makeText(context, "SaveListener", Toast.LENGTH_SHORT).show();
                //uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                //Ringtone rt = RingtoneManager.getRingtone(context, uri);
                // rt.play();
            } else {

                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                addpr0 feedback = new addpr0();
                feedback.setContent(tm.getDeviceId());
                feedback.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {

                        } else {

                        }
                    }
                });

            }
            // Toast.makeText(context, uri + "被安装了", Toast.LENGTH_SHORT).show();

        }

    }

}