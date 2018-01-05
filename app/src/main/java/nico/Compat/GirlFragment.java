package nico.Compat;

/**
 * Created by lum on 2017/11/3.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dump.z.BaseActivity_;
import nico.SPUtils;
import nico.styTool.CustomDialog;
import nico.styTool.GirlData;
import nico.styTool.GridAdapter;
import nico.styTool.PicassoUtils;
import nico.styTool.R;
import nico.styTool.RandomUntil;

public class GirlFragment extends BaseActivity_ {
    //列表
    private GridView mGridView;
    //数据
    private List<GirlData> mList = new ArrayList<>();
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //PhotoView
    //private PhotoViewAttacher mAttacher;

    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     */
    public void deleteNo(int id) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);

    }

    public void AddNotification(int id, String s, String name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(GirlFragment.this);
// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    public void AddNotification(int id, String s, String name, int max, int dq) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(GirlFragment.this);

// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        builder.setProgress(max, dq, false);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lxw_user_profile1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_1) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_girl);
        findView();
    }

    //初始化View
    private void findView() {

        mGridView = (GridView) findViewById(R.id.mGridView);
        //初始化提示框
        dialog = new CustomDialog(GirlFragment.this, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);
        iv_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            //返回值代表是否已经处理结束，后面是否需要再处理
            public boolean onLongClick(View v) {

                final String[] os = {"保存原图"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GirlFragment.this);
                AlertDialog alert =
                        builder
                                // .setTitle("管理员操作")
                                .setItems(os, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                String url = String.valueOf(SPUtils.get(GirlFragment.this, "webil", "曬太陽"));
                                                FileDownloader.getImpl().create(url)
                                                        .setPath(Environment.getExternalStorageDirectory().getPath() + "/Teif" + "/" + RandomUntil.getSmallLetter(3) + "gank.png")
                                                        .setListener(new FileDownloadListener() {
                                                            @Override
                                                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                                AddNotification(81, "等待下载中", "下载");
                                                            }

                                                            @Override
                                                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                                                AddNotification(81, "连接中", "下载");
                                                            }

                                                            @Override
                                                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                                DecimalFormat df = new DecimalFormat("######0.00");
                                                                AddNotification(81, df.format((float) soFarBytes / 1024f / 1024f) + "MB" + "/" + df.format(((float) totalBytes / 1024f / 1024f)) + "MB", "下载", totalBytes, soFarBytes);
                                                            }


                                                            @Override
                                                            protected void blockComplete(BaseDownloadTask task) {
                                                            }

                                                            @Override
                                                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                                                            }

                                                            @Override
                                                            protected void completed(BaseDownloadTask task) {
                                                                AddNotification(81, "下载完成" + Environment.getExternalStorageDirectory().getPath() + "/Teif", "下载");
                                                                /*
                                                                File oldf = new File(Environment.getExternalStorageDirectory().getPath() + "/星空视频壁纸/fcache/" + name2);
                                                                File nf = new File(Environment.getExternalStorageDirectory().getPath() + "/星空视频壁纸/" + name2);

                                                                if (oldf.renameTo(nf))
                                                                    AddNotification(dqcs, "下载完成", name2);
                                                                else
                                                                    AddNotification(dqcs, "文件初始化异常", name2); */
                                                            }

                                                            @Override
                                                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                            }

                                                            @Override
                                                            protected void error(BaseDownloadTask task, Throwable e) {
                                                                AddNotification(81, "下载错误", "下载");
                                                            }

                                                            @Override
                                                            protected void warn(BaseDownloadTask task) {
                                                            }
                                                        }).start();
                                                break;

                                        }
                                        ////showToast("你选择了" + os[which]);
                                    }
                                }).create();
                alert.show();
                //true事件处理结束，后面不需要再处理
                return true;
            }
        });
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //解析
        RxVolley.get("http://gank.io/api/random/data/" + welfare + "/" + RandomUntil.getNum(1, 99), new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                // L.i("Girl Json:" + t);
                parsingJson(t);
            }
        });

        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImaheView(GirlFragment.this, mListUrl.get(position), iv_img);
                SPUtils.put(GirlFragment.this, "webil", mListUrl.get(position));
                SPUtils.put(GirlFragment.this, "webil2", String.valueOf(position));

                FileDownloader.init(GirlFragment.this);
                //Toast.makeText(getActivity(), "" + mListUrl.get(position), Toast.LENGTH_LONG).show();
                //缩放
                //mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                //mAttacher.update();
                dialog.show();
            }
        });
        final MaterialRefreshLayout materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.main_refresh0);
        final String finalWelfare1 = welfare;
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                RxVolley.get("http://gank.io/api/random/data/" + finalWelfare1 + "/" + RandomUntil.getNum(1, 99), new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        mList.clear();
                        // 结束下拉刷新...
                        materialRefreshLayout.finishRefresh();
                        // 结束上拉刷新...
                        materialRefreshLayout.finishRefreshLoadMore();
                        parsingJson(t);
                    }
                });
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
                RxVolley.get("http://gank.io/api/random/data/" + finalWelfare1 + "/" + RandomUntil.getNum(1, 99), new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        mList.clear();
                        // 结束下拉刷新...
                        materialRefreshLayout.finishRefresh();
                        // 结束上拉刷新...
                        materialRefreshLayout.finishRefreshLoadMore();
                        parsingJson(t);
                    }
                });
            }
        });
        final String finalWelfare = welfare;
        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.appbili2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxVolley.get("http://gank.io/api/random/data/" + finalWelfare1 + "/" + RandomUntil.getNum(1, 99), new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        mList.clear();
                        // 结束下拉刷新...
                        materialRefreshLayout.finishRefresh();
                        // 结束上拉刷新...
                        materialRefreshLayout.finishRefreshLoadMore();
                        parsingJson(t);
                    }
                });
            }
        });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            GridAdapter mAdapter = new GridAdapter(GirlFragment.this, mList);
            //设置适配器
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            Toast.makeText(GirlFragment.this, "01", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
