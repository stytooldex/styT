package nico.styTool;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dump.o.SharedPreferencesUtil;
import dump.z.BaseActivity_;


public class ChoseImg_styT extends BaseActivity_ {
    private GridView mGridview;
    private ImageChoseAdapter_sty mAdapter;
    private RelativeLayout mButtons;

    private TextView mDirName;//bu
    private TextView mDirCount;

    private List<String> mImages;
    private File mCurrentDir;

    private int mMaxCount;

    private ProgressDialog mProgressDialog;


    private List<FloderBean> mFloderBeans = new ArrayList<FloderBean>();

    private ListImageDirPopupWindow mPopupWindow;

    private static final int DEFUALT_MSG = 0x110;

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public void createIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Toast.makeText(this, "禁止再次生成到设备成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "请清除本程序数据\n" + "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile() && file.delete();
    }

    public void deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            // System.out.println("删除目录失败：" + dir + "不存在！");
            return;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                //flag = deleteDirectory(file.getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Toast.makeText(this, "删除目录失败", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dirFile.delete()) {
            Toast.makeText(this, "删除目录" + dir + "成功！", Toast.LENGTH_SHORT).show();

        } else {
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DEFUALT_MSG) {
                mProgressDialog.dismiss();
                data2View();
                initDirPopupWindow();
            }
        }
    };

    /**
     * 初始化popupwindow
     */
    private void initDirPopupWindow() {
        mPopupWindow = new ListImageDirPopupWindow(this, mFloderBeans);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        mPopupWindow.setOnDirSelectedListener(new ListImageDirPopupWindow.OnDirSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void OnSelected(FloderBean floderBean) {
                try {
                    mCurrentDir = new File(floderBean.getDir());
                    mImages = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png");
                        }
                    }));
                    mAdapter = new ImageChoseAdapter_sty(ChoseImg_styT.this, mImages, mCurrentDir.getAbsolutePath());
                    mGridview.setAdapter(mAdapter);

                    changeImgOptionMenuSize();
                    mDirName.setText(floderBean.getName());

                    //Toast.makeText(ChoseImg_styT.this, floderBean.getName(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChoseImg_styT.this, String.valueOf(mCurrentDir) + "\n\n\n", Toast.LENGTH_SHORT).show();
                    String str1 = String.valueOf(mCurrentDir);
                    String newStr2 = str1.replaceAll(floderBean.getName(), "");
                    //Toast.makeText(ChoseImg_styT.this, newStr2 + "\n\n" + String.valueOf(mCurrentDir), Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.putData("ChoseImg", "" + String.valueOf(mCurrentDir));
                    SharedPreferencesUtil.putData("ChoseImg2", "" + floderBean.getName().replaceAll("/", ""));

                    mDirCount.setText(mImages.size() + "");
                    mPopupWindow.dismiss();

                    LayoutInflater inflater = LayoutInflater.from(ChoseImg_styT.this);
                    View view = inflater.inflate(R.layout.chose_, null);
                    TextView ediComment = (TextView) view.findViewById(R.id.lxw_id_push_helps_comm);

                    final android.support.v7.widget.AppCompatCheckBox myCheckBox = (android.support.v7.widget.AppCompatCheckBox) view.findViewById(R.id.lxw_id_push_helps_c_);
                    ediComment.setText("目标: " + String.valueOf(mCurrentDir) + "\n子目标: " + String.valueOf(SharedPreferencesUtil.getData("ChoseImg2", "")));
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChoseImg_styT.this);
                    builder.setView(view).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferencesUtil.putData(String.valueOf(SharedPreferencesUtil.getData("ChoseImg2", "")), "" + String.valueOf(SharedPreferencesUtil.getData("ChoseImg", "")));

                            deleteDirectory(String.valueOf(mCurrentDir));
                            if (myCheckBox.isChecked()) {
                                createIfNotExist(String.valueOf(SharedPreferencesUtil.getData("ChoseImg", "")));
                                //Toast.makeText(ChoseImg_styT.this, "t", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(ChoseImg_styT.this, "n", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChoseImg_styT.this, "目标不存在,可能删除了", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 已选择的图片数量
     */
    private void changeImgOptionMenuSize() {
        mAdapter.setmChangeImgSize(new ImageChoseAdapter_sty.OnChangeImageSize() {
            @Override
            public void ChangeSlect(Set<String> mSlectImgs) {
                //更新menu
                invalidateOptionsMenu();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void data2View() {
        if (mCurrentDir == null) {
            Toast.makeText(this, "未扫描到所有图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImages = Arrays.asList(mCurrentDir.list());
        mAdapter = new ImageChoseAdapter_sty(this, mImages, mCurrentDir.getAbsolutePath());
        mGridview.setAdapter(mAdapter);

        mDirCount.setText(mMaxCount + "");

        //Toast.makeText(ChoseImg_styT.this, String.valueOf(mCurrentDir) + "bb", Toast.LENGTH_SHORT).show();

        mDirName.setText(mCurrentDir.getName());
        changeImgOptionMenuSize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lxw_chose_img);
        setTitle("选择图片");

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mGridview = (GridView) findViewById(R.id.id_lxw_chose_img_gridview);
        mButtons = (RelativeLayout) findViewById(R.id.id_lxw_chose_img_lybtn);

        mDirName = (TextView) findViewById(R.id.id_lxw_chose_img_dirName);
        mDirCount = (TextView) findViewById(R.id.id_lxw_chose_img_dirCount);

    }

    /**
     * 利用ContentProvider扫描手机中的所有图片
     */
    private void initData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "当前存储卡不可用!", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog = ProgressDialog.show(this, null, "正在加载");
        new Thread() {
            @Override
            public void run() {
                Uri url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = ChoseImg_styT.this.getContentResolver();
                Cursor cursor = cr.query(url, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED + " desc");
                Set<String> mDirPath = new HashSet<String>();
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }

                    String dirPath = parentFile.getAbsolutePath();
                    FloderBean floderBean = null;

                    if (mDirPath.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPath.add(dirPath);
                        floderBean = new FloderBean();
                        floderBean.setDir(dirPath);
                        floderBean.setFirsterImagePath(path);
                    }
                    if (parentFile.list() == null) {
                        continue;
                    }
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg");
                        }
                    }).length;

                    floderBean.setCount(picSize);
                    mFloderBeans.add(floderBean);

                    if (picSize > mMaxCount) {
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();
                mHandler.sendEmptyMessage(DEFUALT_MSG);
            }
        }.start();
    }

    private void initEvent() {
        mButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
                mPopupWindow.showAsDropDown(mButtons, 0, 0);
                lightOff();
            }
        });
    }

    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

}
