package nico.styTool;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.widget.Button;

import java.util.List;

/**
 * Created by lum on 2017/11/21.
 */

public class peService extends BaseAccessibilityService {

    //锁屏、解锁相关
    private KeyguardManager.KeyguardLock kl;

    //唤醒屏幕相关
    private PowerManager.WakeLock wl = null;

    private long delayTime = 40;//延迟抢的时间

    /**
     * 描述:所有事件响应的时候会回调
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/6 上午9:26
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        SharedPreferences sharedPreferences = getSharedPreferences("nico.styTool_preferences", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("ok_c", true);
        //Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("妮哩");
            builder.setContentText("QQ抢红包正在运行");
            builder.setOngoing(true);
            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(57, notification);
        } else {

        }
        //验证抢红包的开关
        if (!invalidEnable()) {
            return;
        }

        //事件类型
        int eventType = event.getEventType();

        //获取包名
        CharSequence packageName = event.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return;
        }

        switch (eventType) {

            //状态栏变化
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:

                if (QQConstant.QQ_PACKAGE_NAME.equals(packageName)) {
                    //处理状态栏上QQ的消息，如果是红包就跳转过去
                    progressQQStatusBar(event);
                }
                break;

            //窗口切换的时候回调
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                if (QQConstant.QQ_PACKAGE_NAME.equals(packageName)) {
                    //处理正在QQ聊天窗口页面，有其他群或者人有新的红包提醒，跳转过去。
                    progressNewMessage(event);
                    //处理聊天页面的红包
                    progressQQChat(event);
                }

                break;
        }


    }

    /**
     * 描述:处理新消息
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午11:21
     */
    private void progressNewMessage(AccessibilityEvent event) {

        if (event == null) {
            return;
        }
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }
        //根据event的source里的text，来判断这个消息是否包含[QQ红包]的字眼，有的话就跳转过去
        CharSequence text = source.getText();
        if (!TextUtils.isEmpty(text) && text.toString().contains(QQConstant.QQ_ENVELOPE_KEYWORD)) {
            performViewClick(source);
        }
    }

    /**
     * 描述:验证抢红包是否开启
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午4:57
     */
    private boolean invalidEnable() {
        return SettingConfig.getInstance().getReEnable();
    }


    /**
     * 描述:处理QQ状态栏
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/1 下午1:49
     */
    public void progressQQStatusBar(AccessibilityEvent event) {
        List<CharSequence> text = event.getText();
        //开始检索界面上是否有QQ红包的文本，并且他是通知栏的信息
        if (text != null && text.size() > 0) {
            for (CharSequence charSequence : text) {
                if (charSequence.toString().contains(QQConstant.QQ_ENVELOPE_KEYWORD)) {
                    //说明存在红包弹窗，马上进去
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        if (notification == null) {
                            return;
                        }
                        PendingIntent pendingIntent = notification.contentIntent;
                        if (pendingIntent == null) {
                            return;
                        }
                        try {
                            //要跳转之前，先进行解锁屏幕，然后再跳转，有可能你现在屏幕是锁屏状态，先进行解锁，然后打开页面,有密码的可能就不行了
                            wakeUpAndUnlock(HApp.context);
                            //跳转
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 描述:处理QQ聊天红包
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/1 下午1:56
     */
    public void progressQQChat(AccessibilityEvent event) {

        if (TextUtils.isEmpty(event.getClassName())) {
            return;
            //如果当前页面是聊天页面或者当前的描述信息是"返回消息界面"，就肯定是对话页面
        }

        //验证当前事件是否符合查询页面上的红包
        if (!invalidEnvelopeUi(event)) {
            return;
        }

        //延迟点击红包，防止被检测到开了抢红包，不过感觉还是感觉会被检测到，应该有的效果吧...
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //普通红包,检索点击拆开的字眼。
        List<AccessibilityNodeInfo> envelope = findViewListByText(QQConstant.QQ_CLICK_TAKE_APART, false);
        //处理普通红包
        progressNormal(envelope);

        //口令红包,检索口令红包的字眼。
        List<AccessibilityNodeInfo> passwordList = findViewListByText(QQConstant.QQ_CLICK_PASSWORD_DIALOG, false);
        //处理口令红包
        progressPassword(passwordList);
    }


    /**
     * 描述:验证是否现在是在聊天页面，可以进行抢红包处理
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 上午11:52
     *
     * @param event
     */

    public boolean invalidEnvelopeUi(AccessibilityEvent event) {

        //判断类名是否是聊天页面
        if (!QQConstant.QQ_IM_CHAT_ACTIVITY.equals(event.getClassName().toString())) {
            return true;
        }

        //判断页面中的元素是否有点击拆开的文本，有就返回可以进行查询了
        int recordCount = event.getRecordCount();
        if (recordCount > 0) {
            for (int i = 0; i < recordCount; i++) {
                AccessibilityRecord record = event.getRecord(i);
                if (record == null) {
                    break;
                }
                List<CharSequence> text = record.getText();
                if (text != null && text.size() > 0 && text.contains(QQConstant.QQ_CLICK_TAKE_APART)) {
                    //如果文本中有点击拆开的字眼，就返回可以进行查询了
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 回到系统桌面
     */
    private void back2Home(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    /**
     * 描述:处理普通红包
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/1 下午5:02
     */
    public void progressNormal(List<AccessibilityNodeInfo> passwordList) {
        if (passwordList != null && passwordList.size() > 0) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : passwordList) {
                if (accessibilityNodeInfo != null && !TextUtils.isEmpty(accessibilityNodeInfo.getText()) && QQConstant.QQ_CLICK_TAKE_APART.equals(accessibilityNodeInfo.getText().toString())) {

                    //点击拆开红包
                    performViewClick(accessibilityNodeInfo);

                    //回复感谢信息，根据配置文件中配置的回复信息回复
                    String reReplyMessage = SettingConfig.getInstance().getReReplyMessage();
                    if (!TextUtils.isEmpty(reReplyMessage)) {
                        replyMessage(reReplyMessage);
                    }
                }
            }
            //最后延迟事件触发返回事件，关闭红包页面
            performBackClick(1200);
        }
    }

    /**
     * 描述:处理口令红包
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/1 下午4:58
     *
     * @param passwordList
     */
    public void progressPassword(List<AccessibilityNodeInfo> passwordList) {
        if (passwordList != null && passwordList.size() > 0) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : passwordList) {
                if (accessibilityNodeInfo != null && !TextUtils.isEmpty(accessibilityNodeInfo.getText()) && QQConstant.QQ_CLICK_PASSWORD_DIALOG.equals(accessibilityNodeInfo.getText().toString())) {
                    //如果口令红包存在，就在输入框中进行输入，然后发送
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    if (parent != null) {
                        CharSequence contentDescription = parent.getContentDescription();
                        if (!TextUtils.isEmpty(contentDescription)) {
                            //1. 获取口令
                            String key = (String) contentDescription;
                            if (key.contains(",") && key.contains("口令:")) {
                                key = key.substring(key.indexOf("口令:") + 3, key.lastIndexOf(","));
                            }
                            // Log.e("口令", key);

                            //2. 填写口令到编辑框上然后进行发送
                            replyMessage(key);

                            //返回，关闭红包页面
                            performBackClick(1200);
                        }
                    }
                }
            }
        }
    }


    /**
     * 唤醒屏幕并解锁权限
     * <uses-permission android:name="android.permission.WAKE_LOCK" />
     */
    @SuppressLint("Wakelock")
    @SuppressWarnings("deprecation")
    public void wakeUpAndUnlock(Context context) {
        // 点亮屏幕
        wl.acquire(10 * 60 * 1000L /*10 minutes*/);
        // 释放
        wl.release();
        // 解锁
        kl.disableKeyguard();
    }

    /**
     * 描述:回复消息,无延迟
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午5:10
     */
    public void replyMessage(String key) {
        replyMessage(key, 0);
    }

    /**
     * 描述:回复消息
     * 作者:卜俊文
     * 邮箱:344176791@qq.com
     * 日期:2017/11/3 下午5:10
     */
    public void replyMessage(String key, int time) {

        //延迟
        if (time > 0) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //获取QQ聊天页面输入框
        AccessibilityNodeInfo chat_edit = findViewByID(QQConstant.QQ_CHAT_MESSAGE_INPUT);
        if (chat_edit != null) {

            //把口令粘贴到输入框中
            pastaText(chat_edit, HApp.context, key);

            //获取QQ聊天页面发送消息按钮
            AccessibilityNodeInfo sendMessage = findViewByID(QQConstant.QQ_CHAT_MESSAGE_SEND);

            //然后就按下发送按钮
            if (sendMessage != null && Button.class.getName().equals(sendMessage.getClassName())) {
                performViewClick(sendMessage);
            }
        }

    }

    @Override
    public void onInterrupt() {
    }


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        // 获取电源管理器对象
        PowerManager pm = (PowerManager) HApp.context.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

        KeyguardManager km = (KeyguardManager) HApp.context.getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");

        //初始化屏幕的监听
        ScreenListener screenListener = new ScreenListener(HApp.context);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                // Log.e("ScreenListener", "屏幕打开了");
            }

            @Override
            public void onScreenOff() {
                //在屏幕关闭的时候，进行锁屏，不执行的话，锁屏就失效了，因为要实现锁屏状态下也可以进行抢红包。
                //Log.e("ScreenListener", "屏幕关闭了");
                if (kl != null) {
                    kl.disableKeyguard();
                    kl.reenableKeyguard();
                }
            }

            @Override
            public void onUserPresent() {
                //Log.e("ScreenListener", "解锁了");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}