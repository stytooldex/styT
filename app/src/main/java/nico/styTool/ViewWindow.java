package nico.styTool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class ViewWindow {
    private static LayoutParams mLayoutParams;
    private static WindowManager mWindowManager;
    private static View mView;

    public static void initView(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new LayoutParams(-2, -2, 2005, 24, -3);
        mLayoutParams.gravity = 51;
        mView = LayoutInflater.from(context).inflate(R.layout.window_tasks, null);
    }

    public static void showView(Context context, String str) {
        if (mWindowManager == null) {
            initView(context);
        }
        ((TextView) mView.findViewById(R.id.text)).setText(str);
        try {
            mWindowManager.addView(mView, mLayoutParams);
        } catch (Exception ignored) {
        }
    }

    public static void removeView() {
        try {
            mWindowManager.removeView(mView);
        } catch (Exception ignored) {
        }
    }
}
