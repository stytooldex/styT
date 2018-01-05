package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

public class WatchingAccessibilityService extends AccessibilityService
{
    private static WatchingAccessibilityService mWatchingAccessibilityService;

    public static WatchingAccessibilityService getInstance()
    {
        return mWatchingAccessibilityService;
    }

    @SuppressLint({"NewApi"})
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent)
    {
        if (DefaultSharedPreferences.read(this))
	{
            ViewWindow.showView(this, accessibilityEvent.getPackageName() + "\n" + accessibilityEvent.getClassName());
        }
    }

    public void onInterrupt()
    {
    }

    protected void onServiceConnected()
    {
        mWatchingAccessibilityService = this;
        if (DefaultSharedPreferences.read(this))
	{
            //NotificationActionReceiver.showNotification(this, false);
        }
        super.onServiceConnected();
    }

    public boolean onUnbind(Intent intent)
    {
        mWatchingAccessibilityService = null;
        ViewWindow.removeView();
        //NotificationActionReceiver.initNotification(this);
        return super.onUnbind(intent);
    }
}
