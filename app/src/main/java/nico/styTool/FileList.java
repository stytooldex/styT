package nico.styTool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileList extends dump.z.BaseActivity {

    ListView list;
    static List<String> dirs_name;
    static List<String> files_name;
    //缓存上层目录
    /*******************
     *缓存有两种情况被清空
     *1，按返回上层目录之后。
     *2，重新载入Activity后
     ********************/
    static List<String> dirs_name_cache;
    static List<String> files_name_cache;

    Map<String, String> map_paths_name, map_files_name;
    /**
     * Called when the activity is first created.
     */
    android.support.v7.widget.Toolbar toolbar;

    static String broadcastAction = "";
    static String selectedFilePath = null;
    static String curpath = Environment.getExternalStorageDirectory().getPath();
    static String filter = "";
    List<Map<String, Object>> items;
    Map<String, Object> item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);

        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
        toolbar.setTitle("选择");
        toolbar.setSubtitle("如选择完请务必手动返回");
//		设置标题

//		设置副标题
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                finish();
            }
        });
//		用Toolbar取代ActionBar
        //toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
        //toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

        list = (ListView) findViewById(R.id.file_list);

        try {
            //getListFromPath(curpath);
            loadList();
        } catch (Exception e) {//错误时获取根目录
            curpath = "/";
            getListFromPath(curpath);
            loadList();
        }
        //这些事件放在载入列表之后，可以加快载入列表的速度
        //上下文菜单事件
        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu p1, View p2, ContextMenu.ContextMenuInfo p3) {
                if (((AdapterContextMenuInfo) p3).position > (dirs_name.size() - 1))
                    p1.add(0, 0, 0, "查看图片");
            }
        });
        //设置列表单击事件
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int position, long p4) {
                // TODO: Implement this method
                //点击返回上级时
                if (position == 0 && !curpath.equals("/")) {
                    try {
                        curpath = new File(curpath).getParent();
                        //缓存都不为空时才能使用缓存
                        if (dirs_name_cache != null && files_name_cache != null) {
                            dirs_name = dirs_name_cache;
                            files_name = files_name_cache;
                            //使用后清空缓存
                            dirs_name_cache = null;
                            files_name_cache = null;
                        } else {
                            getListFromPath(curpath);
                        }

                        loadList();
                    } catch (Exception e) {
                        showToast("发生了错误！");
                    }

                }

                //点击文件夹时
                else if ((position == 0 && curpath.equals("/")) || (position < dirs_name.size() && position != 0)) {
                    try {
                        dirs_name_cache = dirs_name;
                        files_name_cache = files_name;

                        //对根目录的处理
                        if (curpath.equals("/"))
                            curpath = "";
                        curpath = curpath + "/" + dirs_name.get(position);
                        getListFromPath(curpath);
                        loadList();
                    } catch (Exception e) {
                        //发生异常时，当前路径不变，并重新载入列表
                        File f = new File(curpath);
                        curpath = f.getParent();
                        getListFromPath(curpath);
                        loadList();
                        showToast("发生了错误！");
                    }
                }
                //点击文件时
                else if (position > (dirs_name.size() - 1)) {
                    //if ((curpath.charAt(curpath.length() - 1) == '/'))
                    //curpath = curpath.substring(0, curpath.length() - 1);
                    selectedFilePath = curpath + "/" + files_name.get(position - dirs_name.size());

                    //showToast(selectedFilePath);
                    //结束
                    //overridePendingTransition(R.anim.null_anim, R.anim.out_to_bottom);
                    //FileList.this.finish();
                    //发送广播
                    Intent intent = new Intent();
                    intent.setAction(broadcastAction);
                    sendBroadcast(intent);
                }

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
            //预览
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(curpath + "/" + files_name.get(menuInfo.position - dirs_name.size())));
            intent.setDataAndType(uri, "image/*");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    //在调用该方法之前务必先调用getListFromPath方法
    public void loadList() {
        //getListFromPath(curpath);
        items = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < dirs_name.size(); i++) {
            item = new HashMap<String, Object>();
            item.put("imageItem", R.drawable.ic_save_white_24dp);
            item.put("textItem", dirs_name.get(i));
            items.add(item);
        }
        for (int i = 0; i < files_name.size(); i++) {
            item = new HashMap<String, Object>();
            item.put("imageItem", R.drawable.ic_image_box);
            item.put("textItem", files_name.get(i));
            items.add(item);
        }

        //实例化一个适配器
        SimpleAdapter adapter =
                new SimpleAdapter(this, items, R.layout.filelist_item, new String[]{"imageItem", "textItem", ""}, new int[]{R.id.filelist_item_iv, R.id.filelist_item_tv});
        list.setAdapter(adapter);
        //显示的更加人性化
        setTitle(getShortPath(curpath));
        //getActionBar().setTitle(getShortPath(curpath));
    }

    public static String getShortPath(String path) {
        int n = 0, p = 0;
        for (int i = 0; i < path.length(); i++)
            if (path.charAt(i) == '/')
                n++;

        if (n >= 3) {
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == '/')
                    p++;
                if (p == n - 1) {
                    return "..." + path.substring(i, path.length());
                }
            }
        }

        return path;
    }

    //载入列表到数组
    public static void getListFromPath(String path) {
        files_name = new ArrayList<>();
        dirs_name = new ArrayList<>();

        File file = new File(path);
        File[] files = file.listFiles();

        //不是根目录添加返回项
        if (!curpath.equals("/"))
            dirs_name.add("..");
        for (File file1 : files) {
            //添加文件夹名
            if (file1.isDirectory()) {
                dirs_name.add(file1.getName() + "");
            }
            //文件符合，添加文件名
            else if (file1.isFile() && file1.getName().matches(filter)) {
                files_name.add(file1.getName());
            }
        }
        //对数组进行排列
        Collections.sort(dirs_name, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(files_name, String.CASE_INSENSITIVE_ORDER);
    }

    public void showToast(String text) {
        Toast.makeText(FileList.this, text, Toast.LENGTH_SHORT).show();
    }

}
