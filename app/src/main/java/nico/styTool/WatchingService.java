package nico.styTool;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Timer;

public class WatchingService extends Service {
	Handler mHanlder = new Handler();
	ActivityManager mActivityManager;
	String serviceName = null;
	private Timer timer;
	private NotificationManager mNotificationManager;

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new timer(this), 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@TargetApi(14)
	public void onTaskRemoved(Intent intent) {
		Intent intent2 = new Intent(getApplicationContext(), getClass());
		intent2.setPackage(getPackageName());
		((AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE)).set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 500,
				PendingIntent.getService(getApplicationContext(), 1, intent2,PendingIntent.FLAG_ONE_SHOT));
		super.onTaskRemoved(intent);
	}
}
