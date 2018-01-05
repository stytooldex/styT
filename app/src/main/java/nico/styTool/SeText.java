package nico.styTool;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class SeText extends AccessibilityService implements Runnable {
    Thread th;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (null == event) return;
        boolean isFirstRun = (boolean) nico.SPUtils.get(this, "if_c", true);
        if (isFirstRun) {
            bilibili(event);
        } else {
            QQpackge(event);
        }
    }

    private void QQpackge(AccessibilityEvent event) {
        if ("com.tencent.mobileqq.activity.VisitorsActivity".equals(event.getClassName().toString())) {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null) {
                List<AccessibilityNodeInfo> nodes = null;
                try {
                    nodes = root.findAccessibilityNodeInfosByText("赞");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 5; i < nodes.size(); i++) {
                    for (int j = 5; j < 105; j++) {
                        try {
                            nodes.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void bilibili(AccessibilityEvent event) {

        if ("com.tencent.mobileqq.activity.VisitorsActivity".equals(event.getClassName().toString())) {
            new Thread(new Runnable() {
                public void run() {
                    int n = 0;
                    AccessibilityNodeInfo root = SeText.this.getRootInActiveWindow();
                    List<AccessibilityNodeInfo> nodes = null;
                    try {
                        nodes = root.findAccessibilityNodeInfosByText("\u8d5e");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int size = nodes.size();
                    int i = 4;
                    while (i < 270) {
                        if (i == 179) {
                            try {
                                nodes = SeText.this.getRootInActiveWindow().findAccessibilityNodeInfosByText("\u8d5e");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            size = nodes.size();
                            i = 2;
                        }
                        n++;
                        if (n == size) {
                            n = 7;
                        }
                        for (int j = 0; j < 170; j++) {
                            if (n < size) {
                                nodes.get(n).performAction(176);
                            }
                        }
                        i++;
                    }
                }
            }).start();
        }

    }

    @Override
    public void onInterrupt() {
        // TODO 自动生成的方法存根

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        //接收按键事件
        return super.onKeyEvent(event);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //连接服务后,一般是在授权成功后会接收到
    }

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        try {
            Thread.sleep(202200);
            //QQpackge(null);
        } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


}
