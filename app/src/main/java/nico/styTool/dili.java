package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class dili extends AccessibilityService {
    private static final String QQ_CLASSNAME_CHAT = "com.tencent.mobileqq.actvsdgbivity.SplashActivity";
    private static final String QQ_CLASSNAME_WALLET = "cooperation.qwallet.plugin.QWalc vcxvletPluginProxyActivity";
    private static final String QQ_KEYWORD_NOTIFICATION = "[TlM红包]";
    private static final String[] QQ_KEYWORD_HONGBAO = new String[]{"点击拆开", "口令红包"};
    private static final String[] QQ_KEYWORD_SEND_LIST = new String[]{"点击输入口令", "发送"};
    private static final String QQ_KEYWORD_FAILD_CLICK = "已拆开";
    private static final int NOTIFICATION_ID = 4;

    private static List<AccessibilityNodeInfo> sendNodeList;
    //返回状态，判断是否需要戳返回键
    private boolean isNeedBack = false;
    //运行状态，从开始戳红包到结束时，不应该再有其它操作
    private boolean runState = false;
    //窗口状态，这个主要是区分窗口状态改变和内容改变时对屏幕的读取
    private boolean windowState = false;

    long time;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        SharedPreferences sharedPreferences = getSharedPreferences("nico.styTool_preferences", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("ok_c", true);
        //Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("妮哩");
            builder.setContentText("Tim抢红包正在运行");
            builder.setOngoing(true);
            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, notification);
        } else {

        }
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            List<CharSequence> texts = event.getText();
            if (!texts.isEmpty()) {
                for (CharSequence text : texts) {
                    String content = text.toString();
                    if (content.contains(QQ_KEYWORD_NOTIFICATION)) {
                        openNotify(event);
                        return;
                    }
                }
            }
        }
        openHongBao(event);
    }


    @Override
    public void onInterrupt() {
    }


    /**
     * 打开通知栏消息
     */
    private void openNotify(AccessibilityEvent event) {
        if (event.getParcelableData() == null || !(event.getParcelableData() instanceof Notification)) {
            return;
        }
        Notification notification = (Notification) event.getParcelableData();
        PendingIntent pendingIntent = notification.contentIntent;
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void openHongBao(AccessibilityEvent event) {
        String className = event.getClassName().toString();
        //Log.i(TAG,className);
        if (className.equals(QQ_CLASSNAME_WALLET) && isNeedBack) {
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            isNeedBack = false;
        } else if (className.equals(QQ_CLASSNAME_CHAT) || (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && windowState)) {

            //Editor editor = sharedPreferences.edit();

            windowState = true;
            if (!runState) {
                AccessibilityNodeInfo info = event.getSource();
                if (info == null) return;
                getAllHongBao(info);
            }
        } else {
            windowState = false;
        }
    }

    private void onClick() {
        if (sendNodeList == null || sendNodeList.size() == 0) {
            AccessibilityNodeInfo info = getRootInActiveWindow();
            if (info == null) return;
            sendNodeList = new ArrayList<AccessibilityNodeInfo>();
            for (String s : QQ_KEYWORD_SEND_LIST) {
                List<AccessibilityNodeInfo> infolist = info.findAccessibilityNodeInfosByText(s);
                if (infolist.isEmpty()) {
                    /**
                     *这里如果没查找到
                     *说明前面查找到的节点不对
                     * 再继续查找显然并没有什么卵用
                     */
                    return;
                }
                for (AccessibilityNodeInfo node : infolist) {
                    if (node.getText() == null || !node.getText().toString().equals(s)) continue;
                    sendNodeList.add(node);
                }
            }
        }
        for (AccessibilityNodeInfo node : sendNodeList) {

            AccessibilityNodeInfo in = getClickableNode(node);
            if (in == null) continue;
            //Log.i(TAG,"模拟点击>>>"+node.getText().toString());
            in.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        isNeedBack = true;
    }

    private void getAllHongBao(AccessibilityNodeInfo info) {
        runState = true;
        //Log.i(TAG,"获取所有红包");
        time = System.currentTimeMillis();
        List<AccessibilityNodeInfo> list = new ArrayList<AccessibilityNodeInfo>();
        //查找出当前页面所有的红包，包括手气红包和口令红包
        for (String word : QQ_KEYWORD_HONGBAO) {
            List<AccessibilityNodeInfo> infolist = info.findAccessibilityNodeInfosByText(word);
            if (!infolist.isEmpty()) {
                for (AccessibilityNodeInfo node : infolist) {
                    //这里进行过滤可点击的红包，放到后面去过滤的话感觉非常操蛋
                    if (node.getText() == null || !node.getText().toString().equals(word) || node.getParent().getChildCount() != 3 || !node.getParent().findAccessibilityNodeInfosByText(QQ_KEYWORD_FAILD_CLICK).isEmpty())
                        continue;
                    list.add(node);
                }
            }
        }
        if (list.size() == 0) {
            runState = false;
            return;
        }
        // Log.i(TAG,"数量>>>"+list.size()+"  获取红包耗时:"+(System.currentTimeMillis()-time)+"ms");
        clickAction(list);
    }

    private void clickAction(List<AccessibilityNodeInfo> infolist) {
        for (AccessibilityNodeInfo node : infolist) {
            String text = node.getText().toString();
            AccessibilityNodeInfo info = getClickableNode(node);
            if (info == null) continue;
            //Log.i(TAG,"模拟点击>>>"+text);
            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (text.equals(QQ_KEYWORD_HONGBAO[1])) {
                onClick();
            } else {
                isNeedBack = true;
            }
        }
        runState = false;
        //Log.i(TAG,"总共耗时:"+(System.currentTimeMillis()-time)+"ms");
    }

    /**
     * 获取一个可以点击的节点
     */
    private AccessibilityNodeInfo getClickableNode(AccessibilityNodeInfo node) {
        AccessibilityNodeInfo parent = node;
        while (parent != null) {
            if (parent.isClickable()) {
                break;
            }
            parent = parent.getParent();
        }
        return parent;
    }

}