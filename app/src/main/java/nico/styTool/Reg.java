package nico.styTool;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;

/**
 * 注册验证码计时服务
 * @author talentClass
 */
public class Reg extends Service {
    public static final String IN_RUNNING = "nico.styTool.IN_RUNNING";
    public static final String END_RUNNING = "nico.styTool.END_RUNNING";
    private static Handler mHandler;
    private static CountDownTimer mCodeTimer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

		// 第一个参数是总时间， 第二个参数是间隔
        mCodeTimer = new CountDownTimer(129000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
				// 广播剩余时间
                broadcastUpdate(IN_RUNNING, millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
				// 广播倒计时结束
                broadcastUpdate(END_RUNNING);
                // 停止服务
                stopSelf();
            }
        };
		// 开始倒计时
        mCodeTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }

	// 发送广播
    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

	// 发送带有数据的广播
    private void broadcastUpdate(final String action, String time) {
        final Intent intent = new Intent(action);
        intent.putExtra("time", time);
        sendBroadcast(intent);
    }


}
