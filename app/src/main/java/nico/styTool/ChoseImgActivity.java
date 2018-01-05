package nico.styTool;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

import dump.z.BaseActivity_;

/**
 * 选择图片
 * Created by luxin on 15-12-10.
 * http://luxin.gitcafe.io
 * 观看鸿阳大神视频
 * http://www.imooc.com/learn/489
 */
public class ChoseImgActivity extends BaseActivity_ {
    private GridView mGridview;
    private ImageChoseAdapter mAdapter;
    private RelativeLayout mButtons;

    private TextView mDirName;
    private TextView mDirCount;

    private List<String> mImages;
    private File mCurrentDir;

    private int mMaxCount;

    private ProgressDialog mProgressDialog;


    private List<FloderBean> mFloderBeans = new ArrayList<FloderBean>();

    private ListImageDirPopupWindow mPopupWindow;

    private static final int DEFUALT_MSG = 0x110;

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
                mCurrentDir = new File(floderBean.getDir());
                mImages = Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        return filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png");
                    }
                }));
                mAdapter = new ImageChoseAdapter(ChoseImgActivity.this, mImages, mCurrentDir.getAbsolutePath());
                mGridview.setAdapter(mAdapter);

                changeImgOptionMenuSize();
                mDirName.setText(floderBean.getName());
                mDirCount.setText(mImages.size() + "");
                mPopupWindow.dismiss();
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
        mAdapter.setmChangeImgSize(new ImageChoseAdapter.OnChangeImageSize() {
            @Override
            public void ChangeSlect(Set<String> mSlectImgs) {
                //更新menu
                invalidateOptionsMenu();
            }
        });
    }

    private void data2View() {
        if (mCurrentDir == null) {
            Toast.makeText(this, "未扫描到所有图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImages = Arrays.asList(mCurrentDir.list());
        mAdapter = new ImageChoseAdapter(this, mImages, mCurrentDir.getAbsolutePath());
        mGridview.setAdapter(mAdapter);

        mDirCount.setText(mMaxCount + "");
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
                ContentResolver cr = ChoseImgActivity.this.getContentResolver();
                //Cursor cursor = cr.query(url, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED + " desc");
                Cursor cursor = cr.query(url, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/png"}, MediaStore.Images.Media.DATE_MODIFIED + " desc");
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
                            //return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg");
                            return filename.endsWith(".png");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture_pick, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_pic_confirm);
        //item.setTitle(String.format("完成(%d/%d)", ImageChoseAdapter.mSelectImg.size(), 9));
        item.setTitle("");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_pic_confirm) {
            //savePic();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */

    private static final int RESULT_CODE = 2;

    private void savePic() {
        Intent data = new Intent();
        // data.putExtra("pics",ImageChoseAdapter.mSelectImg.toArray(new String[0]));
        setResult(RESULT_CODE, data);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            savePic();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
