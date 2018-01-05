package dump.b.b.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import nico.styTool.R;

public class FloatWindowService extends Service {

    // 定义浮动窗口布局
    public LinearLayout mFloatLayout;
    public WindowManager.LayoutParams wmParams;
    // 创建浮动窗口设置布局参数的对象
    public WindowManager mWindowManager;
    public AppCompatTextView mFloatView;

    private ServiceBinder binder = new ServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onTrimMemory(int level) {
        //Log.i("test", " onTrimMemory...");

    }

    private void createFloatView() {
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) getApplication().getSystemService(android.app.Application.WINDOW_SERVICE);
        wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT;// 设置window
        // type为TYPE_SYSTEM_ALERT
        wmParams.format = PixelFormat.RGBA_8888;// 设置图片格式，效果为背景透明
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;// 默认位置：左上角
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.x = (WidgetUtils.getScreenWidth(getApplicationContext()) - wmParams.width) / 2;// 设置x、y初始值，相对于gravity
        wmParams.y = 10;
        // 获取浮动窗口视图所在布局
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        mWindowManager.addView(mFloatLayout, wmParams);// 添加mFloatLayout
        mFloatView = mFloatLayout.findViewById(R.id.float_id);
        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 设置监听浮动窗口的触摸移动
        mFloatView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth() / 2;
                // Log.i(TAG, "RawX" + event.getRawX());
                // Log.i(TAG, "X" + event.getX());
                wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight() / 2 - 25;// 减25为状态栏的高度
                // Log.i(TAG, "RawY" + event.getRawY());
                // Log.i(TAG, "Y" + event.getY());
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);// 刷新
                return false; // 此处必须返回false，否则OnClickListener获取不到监听
            }
        });
        mFloatView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });
    }

    public void setSpeed(String str) {
        mFloatView.setText(str);
        //new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()) + "\n" + "SD卡剩余:" + getSDAvailableSize() + "\n" + "手机剩余:" + getRomAvailableSize()


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatLayout != null && mWindowManager != null) {
            mWindowManager.removeView(mFloatLayout);// 移除悬浮窗口
        }
        startService(new Intent(this, FloatWindowService.class));
    }

    public class ServiceBinder extends Binder {
        public FloatWindowService getService() {
            return FloatWindowService.this;
        }
    }

}