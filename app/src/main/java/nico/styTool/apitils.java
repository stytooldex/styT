package nico.styTool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 开发工具类
 */
public class apitils {

    public static String KEY = "";//全局搜索的关键

    public static List<AppInfo> getAppList(Context context) {

        List<AppInfo> list = new ArrayList<AppInfo>();// 声明并实例化1个集合
        PackageManager pm = context.getPackageManager();//获取包管理者
        List<PackageInfo> pList = pm.getInstalledPackages(0);// 获取所有的应用程序集合
        // 循环遍历
        for (int i = 0; i < pList.size(); i++) {
            PackageInfo packageInfo = pList.get(i);// 获取每一个应用的信息

            if (isThirdPartyApp(packageInfo.applicationInfo)
                    // 不能包含本应用(不删除自己)
                    && !packageInfo.packageName.equals(context.getPackageName())) {

                // 从右边装到左边
                AppInfo appInfo = new AppInfo();
                appInfo.packageName = packageInfo.packageName;
                appInfo.versionName = packageInfo.versionName;
                appInfo.versionCode = packageInfo.versionCode;
                appInfo.firstInstallTime = packageInfo.firstInstallTime;
                appInfo.lastUpdateTime = packageInfo.lastUpdateTime;
                // 程序名称
                appInfo.appName = ((String) packageInfo.applicationInfo.loadLabel(pm)).trim();
                // 过渡
                appInfo.applicationInfo = packageInfo.applicationInfo;
                // 这行代码在运行时解除注释
                //appInfo.icon = packageInfo.applicationInfo.loadIcon(pm);
                // 计算应用的空间
                //publicSourceDir 是app的安装路径（文件夹）
                String dir = packageInfo.applicationInfo.publicSourceDir;
                long byteSize = new File(dir).length();
                appInfo.byteSize = byteSize;// 1024*1024 Byte字节
                appInfo.size = getSize(byteSize);// 1MB

                list.add(appInfo);// 添加到集合
            }// if

        }

        return list;
    }

    /**
     * 字节--> Mb, 保留两位小数: 2.35M
     *
     * @param size
     * @return
     */
    public static String getSize(long size) {
        return new DecimalFormat("0.##").format(size * 1.0 / (1024 * 1024));
    }

    /**
     * 时间转化函数
     *
     * @param millis
     * @return
     */
    public static String getTime(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 判断应用是否是第三方应用
     *
     * @param appInfo
     * @return
     */
    public static boolean isThirdPartyApp(ApplicationInfo appInfo) {
        boolean flag = false;
        if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            // 可更新的系统应用
            flag = true;
        } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // 非系统应用(第三方:用户自己安装)
            flag = true;
        }
        return flag;
    }

    /**
     * 打开应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean openPackage(Context context, String packageName) {

        // 系统调用
        try {
            Intent intent =// 获取可以启动该应用的意图
                    context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                // 添加旗标-Flag
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//在新的进程里启动
                context.startActivity(intent);// 普通的发送
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 卸载应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @param requestCode 请求码
     */
    public static void uninstallApk(Activity context,
                                    String packageName, int requestCode) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(
                Intent.ACTION_DELETE,// 动作:删除
                packageURI // 所要删除程序的地址
        );
        context.startActivityForResult(intent, requestCode);
        //ForResult 等待返回值的发送(扔飞镖)
    }

    //面包框里的文字信息
    public static void toast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    /**
     * 格式转换应用大小 单位"B,KB,MB,GB"
     */
    public static String getSize2(float length) {
        long kb = 1024;
        long mb = 1024 * kb;
        long gb = 1024 * mb;
        if (length < kb) {
            return String.format("%dB", (int) length);
        } else if (length < mb) {
            return String.format("%.2fKB", length / kb);
        } else if (length < gb) {
            return String.format("%.2fMB", length / mb);
        } else {
            return String.format("%.2fGB", length / gb);
        }
    }

    public static List<AppInfo> getSearchResult(List<AppInfo> list, String keyword) {
//返回实体集合
        List<AppInfo> searchResultList = new ArrayList<AppInfo>();
        //循环遍历
        for (int i = 0; i < list.size(); i++) {
            AppInfo app = list.get(i);//拿到单个的实体类
//拿关键字和实体类比较
            if (app.appName.toLowerCase().contains(keyword.toLowerCase())) {

                searchResultList.add(app);//添加到结果集
            }

        }
        return searchResultList;
    }
}
