package nico.styTool;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

/**
 * 实体类
 * 应用程序
 */
public class AppInfo {

    public String packageName;// 包名
    public String versionName;// 版本名
    public int versionCode;// 版本号
    public long firstInstallTime;// 第一次安装
    public long lastUpdateTime;// 最近一次安装
    public String appName; // 应用名
    public Drawable icon=null;// 图标
    public long byteSize;// 大小:字节(单位)
    public String size;// 大小: MB

    public ApplicationInfo applicationInfo;

    @Override
    public String toString() {
        return null;
    }

}
