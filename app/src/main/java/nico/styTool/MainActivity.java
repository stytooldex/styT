package nico.styTool;

import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends dump.z.BaseActivity_ implements View.OnClickListener {

    private TextView tvShowCaches, tvAppCache;
    private Button btnScanCache, btnClearAll;
    private PackageManager pm;
    StringBuilder sb = new StringBuilder();
    StringBuilder sbCache = new StringBuilder();

    private long cacheS;
    Handler mHadler = new Handler();
    public String toastMessage() {
        return "khook";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.art_main);


        //getCaches();
        btnScanCache = (Button) findViewById(R.id.btn_scanCache);
        btnClearAll = (Button) findViewById(R.id.btn_clearAll);
        tvShowCaches = (TextView) findViewById(R.id.tv_showAppInfo);
        tvAppCache = (TextView) findViewById(R.id.tv_appCache);
        sbCache.append("该功能如图下［清除缓存］一样。原理是清除本机全部（缓存）\n并不会删除相册什么得\n");
        tvAppCache.setText(sbCache.toString());
        btnScanCache.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cacheS = 0;
        if (v.getId() == btnScanCache.getId()) {
            getCaches();
            //隐藏
            btnScanCache.setVisibility(View.GONE);
        } else if (v.getId() == btnClearAll.getId()) {
            cleanAll(v);
            getCaches();
            //显示
        }
    }

    class MyPackageStateObserver extends IPackageStatsObserver.Stub {

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            String packageName = pStats.packageName;
            long cacheSize = pStats.cacheSize;
            long codeSize = pStats.codeSize;
            long dataSize = pStats.dataSize;
            cacheS += cacheSize;
//            sb.delete(0, sb.length());
            if (cacheSize > 0) {
                sb.append("")
                        .append("")
                        .append("")
                        .append("")
                ;

                //Log.e("aaaa", sb.toString());
            }

        }
    }


    class ClearCacheObj extends IPackageDataObserver.Stub {

        @Override
        public void onRemoveCompleted(String packageName, final boolean succeeded) throws RemoteException {
            mHadler.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Toast.makeText(getApplicationContext(), "清除" + "成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 清理全部应用程序缓存的点击事件
     *
     * @param view
     */
    public void cleanAll(View view) {
        //freeStorageAndNotify
        Method[] methods = PackageManager.class.getMethods();
        for (Method method : methods) {
            if ("freeStorageAndNotify".equals(method.getName())) {
                try {
                    method.invoke(pm, Long.MAX_VALUE, new ClearCacheObj());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    private void getCaches() {
        // scan
        pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        int max = packages.size();
        int current = 0;
        sb.delete(0, sb.length());
        sb.append("");
        sb.append("本机一共安装了：").append(max).append("个应用");
        tvShowCaches.setText(sb.toString());
        for (PackageInfo pinfo : packages) {
            String packageName = pinfo.packageName;
            try {

                Method getPackageSizeInfo = PackageManager.class
                        .getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                getPackageSizeInfo.invoke(pm, packageName, new MyPackageStateObserver());
                current++;
            } catch (Exception e) {
                current++;
                e.printStackTrace();
            }

        }
        //===到这里，数据准备完成
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnClearAll.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "已读取全部缓存", Toast.LENGTH_SHORT).show();
                sbCache.append(Formatter.formatFileSize(getApplicationContext(), cacheS)).append("\n");
                tvShowCaches.setText(sb.toString());
                tvAppCache.setText(sbCache.toString());
                sbCache.delete(0, sbCache.length());
            }
        }, 1000);
        //ok,所有应用程序信息显示完成
    }
}
