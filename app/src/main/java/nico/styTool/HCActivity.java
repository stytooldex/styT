package nico.styTool;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HCActivity extends dump.z.BaseActivity implements Runnable, OnClickListener {
    /**
     * Called when the activity is first created.
     */
    android.support.v7.widget.Toolbar toolbar;

    SharedPreferences sp;
    GridView list;
    Bitmap bitmapItem = null;
    ProgressDialog pa;
    Button bn_add, bn_clear, bn_hc;
    n ag;
    List<Map<String, Object>> items;
    Map<String, Object> item;
    SimpleAdapter adapter;
    SimpleDateFormat formatter;
    String preferencePath;
    String nowStr;
    AlertDialog.Builder adlog;
    final String THIS_ACTION = "hcAction";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hc);

        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
        bn_add = (Button) findViewById(R.id.bn_add);
        bn_clear = (Button) findViewById(R.id.bn_clear);
        bn_hc = (Button) findViewById(R.id.bn_hc);
        list = (GridView) findViewById(R.id.list);

        bn_add.setOnClickListener(this);
        bn_clear.setOnClickListener(this);
        bn_hc.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(THIS_ACTION);
        registerReceiver(br, intentFilter);
        //FileList.curpath = "/mnt/sdcard";
        FileList.curpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileList.broadcastAction = THIS_ACTION;
        initAdapter();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
        //广告初始化

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu p1, View p2, ContextMenu.ContextMenuInfo p3) {
                // TODO: Implement this method
                //p1.setHeaderTitle("选项");
                //第二个参数是id
                p1.add(0, 0, 0, "设置持续时间");
                p1.add(0, 1, 0, "移除该图片");

            }
        });
    }


    //点击Context菜单的项时发生
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo menuInfo =
                (AdapterContextMenuInfo) item.getMenuInfo();
        // TODO: Implement this method
        if (item.getItemId() == 0) {
            //修改持续时间
            showEditTextDialog(HCActivity.this, "设置该图持续的时间(秒)", menuInfo.position);
        } else if (item.getItemId() == 1) {
            //移除项
            items.remove(menuInfo.position);
            adapter.notifyDataSetChanged();
            showToast("移除成功");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        // TODO: Implement this method
        ag = new n();
        //如果不使用第一帧的大小
        if (!sp.getBoolean("cb_use_first_size", true))
            ag.setSize(
                    Integer.parseInt(sp.getString("edit_pic_width", "480")),
                    Integer.parseInt(sp.getString("edit_pic_height", "800"))
            );

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        nowStr = formatter.format(curDate);//转换时间格式

        preferencePath = sp.getString("edit_output_path1", Environment.getExternalStorageDirectory().getPath() + "/Android/gif");
        //if(preferencePath.charAt(preferencePath.length()-1)=='/')
        //preferencePath=preferencePath.substring(0,preferencePath.length()-2);

        ag.start(preferencePath + "/" + nowStr + ".gif");
        ag.setRepeat(0);
        for (int i = 0; i < items.size(); i++) {
            ag.setDelay((int) (Float.parseFloat(items.get(i).get("delay").toString()) * 1000));
            try {
                ag.addFrame(BitmapFactory.decodeFile(items.get(i).get("filePath").toString()));
            }
            //要生成的图片太大，内存不足，捕获异常
            catch (OutOfMemoryError oome) {
                handler.sendEmptyMessage(1);
                return;
            }

        }
        ag.finish();
        handler.sendEmptyMessage(0);
    }

    //载入列表，可以节省时间
    Runnable loadFileList = new Runnable() {

        @Override
        public void run() {
            // TODO: Implement this method
            FileList.filter = ".*.jpg|.*.png|.*.bmp|.*.gif$";
            //FileList.curpath=;
            FileList.getListFromPath(FileList.curpath);
        }


    };

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO: Implement this method
            if (msg.what == 0) {
                pa.dismiss();
                adlog = new AlertDialog.Builder(HCActivity.this);
                adlog.setTitle("合成完成")
                        .setMessage("合成已完成" + "\n保存路径：" + sp.getString("edit_output_path1", Environment.getExternalStorageDirectory().getPath() + "/Android/gif") + "/" + nowStr + ".gif")
                        .setNegativeButton("确定", null)
                        //.setIcon(android.R.drawable.ic_menu_help)
                        .show();
                showToast("合成完成");

            } else if (msg.what == 1) {
                pa.dismiss();
                adlog = new AlertDialog.Builder(HCActivity.this);
                adlog.setTitle("合成未完成")
                        .setMessage("要生成的图片太大，无法生成")
                        .setNegativeButton("确定", null)
                        //.setIcon(android.R.drawable.ic_menu_help)
                        .show();
                showToast("图片生成失败");
            }
        }

    };

    //按钮事件*********
    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
        if (p1.getId() == R.id.bn_add) {

            Intent intent = new Intent();
            intent.setClass(HCActivity.this, FileList.class);
            startActivity(intent);
            //overridePendingTransition(R.anim.out_to_bottom, R.anim.in_from_bottom);
        } else if (p1.getId() == R.id.bn_clear) {
            if (items.size() > 0) {

                adlog = new AlertDialog.Builder(this);
                adlog.setTitle("提示")
                        .setMessage("确定清空列表吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface p1, int p2) {
                                // TODO: Implement this method
                                items.clear();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        } else if (p1.getId() == R.id.bn_hc) {
            if (items.size() > 0) {
                pa = ProgressDialog.show(HCActivity.this, null, "正在合成");
                //让它不可返回
                pa.setCancelable(false);
                new Thread(this).start();
            } else
                showToast("请先添加图片");

        }
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        // TODO: Implement this method
        return super.registerReceiver(receiver, filter);
    }

    //实例化一个广播接收器
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context p1, Intent p2) {
            // TODO: Implement this method
            if (p2.getAction().equals(THIS_ACTION)) {
                bitmapItem = IO.getImageFromPath(FileList.selectedFilePath);

                item = new HashMap<String, Object>();
                item.put("imageItem", bitmapItem);
                item.put("filePath", FileList.selectedFilePath);
                item.put("fileName", new File(FileList.selectedFilePath).getName());
                item.put("delay", sp.getString("edit_dely", "0.5"));

                items.add(item);

                adapter.notifyDataSetChanged();

                list.setSelection(items.size() - 1);
            }
        }
    };

    //设置时间的对话框
    public void showEditTextDialog(Context context, String title, final int itemPosition) {
        final EditText edit = new EditText(context);
        //设置只能输入小数
        edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edit.setText(items.get(itemPosition).get("delay") + "");
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(edit)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface p1, int p2) {
                                // TODO: Implement this method
                                items.get(itemPosition).put("delay", edit.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lxw_action_edi_user_profile) {

            startActivity(new Intent(HCActivity.this, SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showToast(String text) {
        Toast.makeText(HCActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    //初始化adapter
    public void initAdapter() {
        items = new ArrayList<Map<String, Object>>();
        //实例化一个适配器
        adapter = new SimpleAdapter(HCActivity.this, items,
                R.layout.hc_list_item,
                new String[]{"imageItem", "fileName", "delay", "filePath"},
                new int[]{R.id.image_item, R.id.hc_tv_filename, R.id.tv_delay}
        );

        list.setAdapter(adapter);
        adapter.setViewBinder(new ViewBinder() {

            @Override
            public boolean setViewValue(View p1, Object p2, String p3) {
                if (p1 instanceof ImageView && p2 instanceof Bitmap) {
                    ImageView iv = (ImageView) p1;
                    iv.setImageBitmap((Bitmap) p2);
                    return true;
                } else
                    return false;

            }
        });
    }


    long cur = 0;


    @Override
    protected void onStart() {
        // TODO: Implement this method
        super.onStart();
        toolbar.setTitle("GIF合成");
//		设置标题
//		设置副标题
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO: Implement this method
        //当Activity被重新激活时
        //要重新给动作和筛选条件重新赋值
        //不让两个Activity的ListView和选择文件时乱
        FileList.broadcastAction = THIS_ACTION;
        FileList.filter = ".*.jpg|.*.png|.*.bmp|.*.gif$";
        //清空缓存数组
        FileList.dirs_name_cache = null;
        FileList.files_name_cache = null;
        //开启线程载入列表
        new Thread(loadFileList).start();
        //FileList.dirs_name=null;
        //FileList.files_name=null;
        //showToast(THIS_ACTION);
        super.onResume();
    }

}

