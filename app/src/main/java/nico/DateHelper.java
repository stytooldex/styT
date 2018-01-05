package nico;


import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import dump.a.MyAdapter_;
import dump.c.bilI;
import dump.z.Main2Activity;
import dump_dex.Activity.ScrollingActivity;
import dump_dex.root.rootActivity;
import nico.styTool.MainActivity;
import nico.styTool.MeiziActivity;
import nico.styTool.NativeConfigStore;
import nico.styTool.R;
import nico.styTool.ToastUtil;
import nico.styTool.api_o;
import nico.styTool.buff_ext;
import nico.styTool.sizeActivity;
import nico.styTool.smali_layout_apktool;
import nico.styTool.z;


public class DateHelper extends Fragment {

    @SuppressLint("WrongConstant")
    private boolean a() {
        String str = "com.android.settings.DisplayScalingActivity";
        String[] strArr = new String[]{"com.android.settings", "android.settings.SETTINGS"};
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str2 = strArr[i];
            Intent intent = new Intent();
            intent.addFlags(0x10000000);
            intent.setClassName(str2, str);
            try {
                startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                i++;
            }
        }
        Toast.makeText(getActivity(), "不支持这个设备", Toast.LENGTH_SHORT).show();
        return false;
    }

    private ArrayList<String> getDataq() {
        ArrayList<String> data = new ArrayList<>();
        data.add("浮窗助手");
        data.add("提取APK");
        data.add("蓝字代码");
        data.add("以图搜图");
        data.add("QQ名片赞");
        data.add("root工具");
        data.add("应用管理器");
        data.add("多进制转换");
        data.add("QQ|微信卡屏");
        data.add("嗶哩嗶哩封面");
        data.add("桌面动态壁纸");
        data.add("文件校验修改");
        data.add("QQ空间蓝色动态");
        data.add("强制清除RAM内存");
        data.add("强制发起临时会话");
        data.add("免费看vip付费电视");
        data.add("文字加密解密（转码）");
        data.add("百度云直链提取(不限速)");
        data.add("网易云音乐启动背景替换");
        data.add("清除本机全部缓存非root");
        data.add("三星设备[noroot]修改dpi");
        data.add("解除（MIUI助手）授权25秒");

        return data;
    }

    public void showIntent(Class<?> clzz) {
        Intent intent = new Intent(getActivity(), clzz);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_main33, null);
        //SharePrefHelper.getInstance(getActivity());

        RecyclerView mRecyclerViewq = (RecyclerView) view.findViewById(R.id.lbili3);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        // mRecyclerViewq.setLayoutManager(mLayoutManager);
        mRecyclerViewq.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // 设置布局管理器 GridView
        mRecyclerViewq.setAdapter(new MyAdapter_(getDataq()));
        //SharePrefHelper.put("styt", (Serializable) new MyAdapter_(getDataq()));
        MyAdapter_.setOnItemClickListener(new MyAdapter_.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView titleq = view.findViewById(R.id.item_tv);
                String tvq = titleq.getText().toString();
                ///////
                if (tvq.equals("清除本机全部缓存非root")) {
                    Intent intent8 = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent8);

                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "清除本机全部缓存非root");
                }
                if (tvq.equals("网易云音乐启动背景替换")) {
                    final String[] os = {"替换启动界图片", "恢复默认"};
                    AlertDialog.Builder builder8 = new AlertDialog.Builder(getActivity());
                    AlertDialog alert8 = builder8.setTitle("操作")
                            .setItems(os, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Intent a = new Intent(getActivity(), sizeActivity.class);
                                            startActivity(a);
                                            break;
                                        case 1:
                                            GetPathFromUri4kitkat.deleteDirectory(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/netease/cloudmusic/Ad");
                                            break;
                                    }
                                    ////showToast("你选择了" + os[which]);
                                }
                            }).create();
                    alert8.show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "网易云音乐启动背景替换");
                }
                if (tvq.equals("文字加密解密（转码）")) {
                    AlertDialog.Builder uilder = new AlertDialog.Builder(getActivity());
                    AlertDialog lertDialog = uilder.setMessage("请选择操作")
                            .setNegativeButton("密文", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(getActivity(), NativeConfigStore.class);
                                    intent1.putExtra("#", "A");
                                    startActivity(intent1);
                                }
                            }).setPositiveButton("unicode与中文转换", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(getActivity(), NativeConfigStore.class);
                                    intent1.putExtra("#", "Ab");
                                    startActivity(intent1);
                                }
                            }).create();
                    lertDialog.show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "文字加密解密（转码）");
                }
                if (tvq.equals("解除（MIUI助手）授权25秒")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    AlertDialog alertDialog = builder.setMessage("方法：开启>找到妮**权\n一般在右上角点开启就可以了\n现在只适配6.0系统7.0系统再等等").setCancelable(false)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface d, int which) {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                                    startActivity(intent);
                                }
                            }).create();
                    alertDialog.show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "解除（MIUI助手）授权25秒");
                }
                if (tvq.equals("强制发起临时会话")) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view1 = inflater.inflate(R.layout.b_bilibili, null);
                    final EditText ediComment1 = view1.findViewById(R.id.bbilibiliEditText1);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setView(view1)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("发起会话", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String comment = ediComment1.getText().toString().trim();
                            if (TextUtils.isEmpty(comment)) {
                                return;
                            }
                            Intent intent = new Intent();
                            intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + comment));
                            startActivity(intent);
                        }

                    }).setCancelable(false).create().show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "强制发起临时会话");
                }
                if (tvq.equals("root工具")) {
                    Intent w = new Intent(getActivity(), rootActivity.class);
                    startActivity(w);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "root工具");
                }
                if (tvq.equals("QQ空间蓝色动态")) {
                    LayoutInflater inflater2 = LayoutInflater.from(getActivity());
                    View view2 = inflater2.inflate(R.layout.bilibilib, null);
                    final EditText ediComment12 = view2.findViewById(R.id.bilibilibEditText1);

                    final EditText ediComment = view2.findViewById(R.id.bilibilibEditText2);
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                    builder2.setView(view2)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setPositiveButton("生成", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String commentx = ediComment.getText().toString().trim();
                            String comment = ediComment12.getText().toString().trim();
                            if (TextUtils.isEmpty(comment)) {
                                // ToastUtil.show(this, "内容不能为空", Toast.LENGTH_SHORT);
                                return;
                            }

                            ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                            manager.setText("{uin:" + commentx + ",nick:+" + comment + ",who:1}");
                            ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);

                        }

                    }).setCancelable(false).create().show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ空间蓝色动态");
                }
                if (tvq.equals("QQ|微信卡屏")) {
                    AlertDialog.Builder buiider = new AlertDialog.Builder(getActivity());
                    AlertDialog alert = buiider.setMessage("打开QQ想要刷的群或QQ并长按输入框粘贴\n发送\n或者使用 直接发送")

                            .setNeutralButton("直接发送", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        InputStream is = getActivity().getAssets().open("classes_cRC32.apk");
                                        int size = is.available();
                                        byte[] buffer = new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        String text = new String(buffer, "UTF-8");
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                                        sendIntent.setType("text/plain");
                                        startActivity(sendIntent);
                                        ToastUtil.show(getActivity(), "QQ请选择【发送给好友】\n微信请选择【发送给朋友】", Toast.LENGTH_SHORT);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            })
                            .setNegativeButton("复制卡屏【新】", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        InputStream is = getActivity().getAssets().open("classes_cRC32.apk");
                                        int size = is.available();
                                        byte[] buffer = new byte[size];
                                        is.read(buffer);
                                        is.close();
                                        String text = new String(buffer, "UTF-8");
                                        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                                        manager.setText(text);
                                        ToastUtil.show(getActivity(), "复制成功！", Toast.LENGTH_SHORT);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }).setPositiveButton("复制刷屏【原】", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(android.support.v4.app.FragmentActivity.CLIPBOARD_SERVICE);
                                    manager.setText("by:styTool\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                                    Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();

                                }
                            }).create();
                    alert.show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ|微信卡屏");
                }
                if (tvq.equals("应用管理器")) {
                    Intent li = new Intent(getActivity(), buff_ext.class);
                    startActivity(li);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "应用管理器");
                }
                if (tvq.equals("蓝字代码")) {
                    Intent intent5 = new Intent(getActivity(), MeiziActivity.class);
                    startActivity(intent5);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "蓝字代码");
                }
                if (tvq.equals("QQ名片赞")) {
                    LayoutInflater inflater3 = LayoutInflater.from(getActivity());
                    View view3 = inflater3.inflate(R.layout.a_gey, null);
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                    builder3.setView(view3)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));

                        }
                    }).setCancelable(false).create().show();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "QQ名片赞");
                }
                if (tvq.equals("提取APK")) {
                    Intent b = new Intent(getActivity(), dump.x.Main.class);
                    startActivity(b);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "提取APK");
                }
                if (tvq.equals("浮窗助手")) {
                    Intent _li = new Intent(getActivity(), api_o.class);
                    startActivity(_li);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "浮窗助手");
                }
                if (tvq.equals("百度云直链提取(不限速)")) {
                    Intent as = new Intent(getActivity(), Main2Activity.class);
                    as.putExtra("#", "https://pan.baidu.com/");
                    startActivity(as);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "百度云直链提取(不限速)");
                }

                if (tvq.equals("免费看vip付费电视")) {
                    Intent inten = new Intent(getActivity(), Main2Activity.class);
                    inten.putExtra("#", "http://m.iqiyi.com/");
                    startActivity(inten);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "免费看vip付费电视");
                }
                if (tvq.equals("桌面动态壁纸")) {
                    Intent intent2 = new Intent(getActivity(), z.class);
                    startActivity(intent2);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "桌面动态壁纸");
                }
                if (tvq.equals("以图搜图")) {
                    Intent as = new Intent(getActivity(), Main2Activity.class);
                    as.putExtra("#", "http://shitu.baidu.com/");
                    startActivity(as);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "以图搜图");
                }
                if (tvq.equals("三星设备[noroot]修改dpi")) {
                    a();
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "三星设备[noroot]修改dpi");
                }
                if (tvq.equals("多进制转换")) {
                    Intent ans = new Intent(getActivity(), ScrollingActivity.class);
                    startActivity(ans);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "多进制转换");
                }
                if (tvq.equals("强制清除RAM内存")) {
                    Intent ans1 = new Intent(getActivity(), smali_layout_apktool.class);
                    startActivity(ans1);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "强制清除RAM内存");
                }
                if (tvq.equals("文件校验修改")) {
                    Intent ans10 = new Intent(getActivity(), nico.styTool.iApp.class);
                    startActivity(ans10);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "文件校验修改");
                }
                if (tvq.equals("嗶哩嗶哩封面")) {
                    Intent ats = new Intent(getActivity(), bilI.class);
                    startActivity(ats);
                    nico.FileUtils.createIfNotExist(getActivity(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/datastyTool/" + "嗶哩嗶哩封面");
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //   TextView title = view.findViewById(R.id.item_tv);
                // String tv = title.getText().toString();
            }
        });
        return view;
    }
}