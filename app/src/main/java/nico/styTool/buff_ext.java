package nico.styTool;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import dump.o.SilentInstall;

public class buff_ext extends dump.z.BaseActivity_ implements AdapterView.OnItemClickListener, IUninstall, AdapterView.OnItemLongClickListener {

    TextView apkPathText;
    List<AppInfo> list;// 数据
    List<AppInfo> allList = new ArrayList<>();
    ListView lv; // 列表视图
    //ImageView img;
    apiAdapter adapter; // 适配器

    public static final int SORT_NAME = 0;//按名称排序
    public static final int SORT_DATE = 1;//按日期排序
    public static final int SORT_SIZE = 2;//按大小排序

    // final String[] sorts = {"名称", "日期", "大小"};

    int currSort = SORT_DATE;//当前排序
    Comparator<AppInfo> comparator = null;// 当前所使用的比较器

    String apkPath;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.apk_main, menu);

        search = menu.findItem(R.id.search);//容器
        sv = (android.support.v7.widget.SearchView) search.getActionView();//真正的搜索对象
        // sv.setIconifiedByDefault(false);//图标显示在外侧
        //sv.setSubmitButtonEnabled(true);//让提交按钮可用
        // sv.setQueryHint("请输入应用名");//提示用户信息
        //sv.setOnQueryTextListener(buff_ext.this);//关联提交事件

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.sort_name) {
            currSort = SORT_NAME;// 给排序状态赋值
        } else if (id == R.id.sort_date) {
            currSort = SORT_DATE;
        } else if (id == R.id.sort_size) {
            currSort = SORT_SIZE;
        }


        update_sort();// 调用统一的排序方法

        asc *= -1;//负数,正数

        return super.onOptionsItemSelected(item);
    }

    // 带排序的更新
    private void update_sort() {
        if (currSort == SORT_NAME) {
            comparator = nameComparator;// 选择不同的比较器
        }
        if (currSort == SORT_DATE) {
            comparator = dateComparator;
        }
        if (currSort == SORT_SIZE) {
            comparator = sizeComparator;
        }
        Collections.sort(list, comparator);// 这里才是排序的操作
        adapter.setList(list);
        adapter.notifyDataSetChanged();// 刷新视图
        update_infobar();
    }

    int asc = 1; // 可以帮助在正序和倒序之间进行切换
    // 日期比较器
    Comparator<AppInfo> dateComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {


            if (rhs.lastUpdateTime > lhs.lastUpdateTime
                    ) {
                return -1 * asc;
            } else if (rhs.lastUpdateTime == lhs.lastUpdateTime) {
                return 0;
            } else {
                return asc;
            }
        }//
    };

    // 大小比较器
    Comparator<AppInfo> sizeComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            if (rhs.byteSize > lhs.byteSize) {
                return -1 * asc;
            } else if (rhs.byteSize == lhs.byteSize) {
                return 0;
            } else {
                return asc;
            }
        }
    };

    // 应用名比较器
    Comparator<AppInfo> nameComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            // 为了适应汉字的比较
            Collator c = Collator.getInstance(Locale.CHINA);
            return (asc == 1) ? c.compare(lhs.appName, rhs.appName)
                    : c.compare(rhs.appName, lhs.appName);
        }
    };


    // 1声明进度框对象
    ProgressDialog pd;

    // 显示一个环形进度框
    public void showProgressDialog() {
        // 实例化
        pd = new android.app.ProgressDialog(this);
        // "旋转"风格
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pd.setMessage("正在加载应用列表");
        pd.show();// 显示
    }

    Handler handler = new Handler() {// 内部类

        @Override
        public void handleMessage(Message msg) {
            // 重写方法
            if (msg.what == 1) {// UI 线程的回调处理
                pd.dismiss();
                // 更新列表
                adapter.notifyDataSetChanged();
                //  size();

                //Toast.makeText(MainActivity.this, "应用数:" + list.size(), Toast.LENGTH_LONG).show();
                update_sort();
            }
        }
    };

    //3.子线程
    private void updateData() {

        // (1)--启动新线程,处理耗时操作
        new Thread() {
            public void run() {
                // 获得数据(所有的应用)
                list = apitils.getAppList(buff_ext.this);
                allList.clear();// 清空
                allList.addAll(list);// 复制集合
                adapter.setList(list);
                // 给主线程发消息

                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);// msg.what=1
            }

        }.start();
        // (2) --
        showProgressDialog();// 显示进度框
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 获取本行的应用信息对象
        AppInfo app = (AppInfo) parent.getItemAtPosition(position);
        // 运行应用
        apitils.openPackage(this, app.packageName);
    }

    @Override
    public void onBtnClick(int pos, String packageName) {
        apitils.uninstallApk(this, packageName, 0);//0-requestCode
    }

    /*
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data)
     {
     // 接收窗体返回值
     if (requestCode == 0)
     {
     // 刷新列表
     updateData();
     }

     super.onActivityResult(requestCode, resultCode, data);
     }
     */
    ImageView iv_asc;
    TextView sort, count, size;

    // 更新顶部信息栏中内容
    private void update_infobar() {


        if (asc == 1) {
            //   iv_asc.setImageResource(android.R.drawable.arrow_up_float);
        } else {
            //   iv_asc.setImageResource(android.R.drawable.arrow_down_float);
        }


        //sort.setText("排序: " + sorts[currSort]);

        //sort.setOnClickListener(this);
        count.setText("已安装:" + list.size());
        size.setText("占用内存:" + getListSize());
    }

    /**
     * 遍历数据集合,累加全部的Size
     *
     * @return
     */
    private String getListSize() {
        long sum = 0;// 总和
        for (AppInfo app : list) {// foreach
            sum += app.byteSize;
        }
        return apitils.getSize2(sum);
    }

    public void clickImg(View v) {
        // (1)切换正序/倒序
        update_sort();
        asc *= -1;

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AppInfo app = (AppInfo) parent.getItemAtPosition(position);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        String pkg = "com.android.settings";
        String cls = "com.android.settings.applications.InstalledAppDetails";
        intent.setComponent(new ComponentName(pkg, cls));
        intent.setData(Uri.parse("package:" + app.packageName));//指明要打开的应用
        startActivity(intent);// 用普通的方法去打开界面
        return true;// 消化掉事件
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buff);


        apkPathText = (TextView) findViewById(R.id.apkPathText);
        apitils.KEY = "";//初始化
        // 初始化控件
        lv = (ListView) findViewById(R.id.lv_main);
        adapter = new apiAdapter(this);
        adapter.setList(list);
        adapter.setUninstall(buff_ext.this);// 传入接口
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(buff_ext.this);
        lv.setOnItemLongClickListener(buff_ext.this);
        sort = (TextView) findViewById(R.id.sort);
        count = (TextView) findViewById(R.id.count);
        size = (TextView) findViewById(R.id.size);
        iv_asc = (ImageView) findViewById(R.id.iv_asc);
        //img = (ImageView) findViewById(R.id.img3);
        // donghua();
        updateData();// 子线程--拿数据
    }

    //声明变量3
    android.support.v7.widget.SearchView sv;
    MenuItem search;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            apkPath = data.getStringExtra("apk_path");
            apkPathText.setText(apkPath);
        }
    }

    public void onSilentInstall(View view) {
        if (!isRoot()) {
            Toast.makeText(this, "没有ROOT权限", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apkPath)) {
            Intent intent = new Intent(this, FileExplorer.class);
            startActivityForResult(intent, 0);
            return;
        }
        final Button button = (Button) view;
        button.setText("安装中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SilentInstall installHelper = new SilentInstall();
                final boolean result = installHelper.install(apkPath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(buff_ext.this, "安装成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(buff_ext.this, "安装失败！", Toast.LENGTH_SHORT).show();
                        }
                        button.setText("root秒装");
                    }
                });

            }
        }).start();

    }

    public void onForwardToAccessibility(View view) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    public void onSmartInstall(View view) {
        if (TextUtils.isEmpty(apkPath)) {

            return;
        }
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    /**
     * 判断手机是否拥有Root权限。
     *
     * @return 有root权限返回true，否则返回false。
     */
    public boolean isRoot() {
        boolean bool = false;
        try {
            bool = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }
}
