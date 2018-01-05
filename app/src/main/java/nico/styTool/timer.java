package nico.styTool;

import android.app.ActivityManager.RunningTaskInfo;

import java.util.List;
import java.util.TimerTask;

class timer extends TimerTask {
	final WatchingService mWatchingService;

	timer(WatchingService watchingService) {
		this.mWatchingService = watchingService;
	}

	public void run() {
		List runningTasks = mWatchingService.mActivityManager.getRunningTasks(1);
		String serviceName = ((RunningTaskInfo) runningTasks.get(0)).topActivity.getPackageName() + "\n"
				+ ((RunningTaskInfo) runningTasks.get(0)).topActivity.getClassName();
		if (!serviceName.equals(mWatchingService.serviceName)) {
			mWatchingService.serviceName = serviceName;
			if (DefaultSharedPreferences.read(mWatchingService)) {
				mWatchingService.mHanlder.post(new RunView(this));
			}
		}
	}
}
