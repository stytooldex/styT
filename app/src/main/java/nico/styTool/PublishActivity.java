package nico.styTool;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import dump.k.i_a;
import dump.z.BaseActivity_;

/**
 * Created by luxin on 15-12-10.
 * http://luxin.gitcafe.io
 */
public class PublishActivity extends BaseActivity_ implements View.OnClickListener {


    private EditText ediContent;

    private MyUser myUser = null;
    private HorizontalScrollView scrollPicContent;
    private LinearLayout layPicContent;

    private LinearLayout btnCamera;
    private LinearLayout btnEmotion;

    private LinearLayout btnSend;

    private ViewPager emojPager;

    private boolean isOpen = false;

    private ArrayList<GridView> mGridViews;

    private static final int REQUEST_CODE = 1;

    private ProgressDialog mProgressDialog;

    private List<String> filePhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_help);

        myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser.getAuvter() != null) {

        } else {
            Intent a = new Intent(PublishActivity.this, UserProfileActivity.class);
            startActivity(a);
            finish();
            Toast.makeText(PublishActivity.this, "请上传头像", Toast.LENGTH_SHORT).show();
        }
        xft();
        initView();
        initEmojGridview();
        initEvent();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresUI();
    }

    private void xft() {

        BmobQuery<i_a> query = new BmobQuery<>();
        query.getObject("03bf357e85", new QueryListener<i_a>() {

            @Override
            public void done(i_a movie, BmobException e) {
                if (e == null) {
                    String s = movie.getContent();
                    String sr = nico.styTool.Constant.a_mi + "\n" + nico.styTool.Constant.a_miui;
                    if (s.equals(sr)) {

                    } else {
                        nico.styTool.ToastUtil.show(PublishActivity.this, "版本不一致，请更新", Toast.LENGTH_SHORT);
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void initEmojGridview() {

        mGridViews = new ArrayList<GridView>();
        LayoutInflater inflater = LayoutInflater.from(this);
        mGridViews.clear();
        for (int i = 0; i < 6; i++) {
            final int j = i;
            GridView gridView = (GridView) inflater.inflate(R.layout.lxw_emoj_gridview, null, false);
            gridView.setAdapter(new EmotionGridViewAdapter(this, i));
            mGridViews.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0 && (position % 20 == 0) || (j == 5 && position == 5)) {
                        int selectionStart = ediContent.getSelectionStart();
                        String str = ediContent.getText().toString();
                        String strTemp = str.substring(0, selectionStart);
                        if (!TextUtils.isEmpty(str)) {
                            int i = strTemp.lastIndexOf("]");
                            if (i == strTemp.length() - 1) {
                                int j = strTemp.lastIndexOf("[");
                                ediContent.getEditableText().delete(j, selectionStart);
                            } else {
                                ediContent.getEditableText().delete(strTemp.length() - 1, selectionStart);
                            }
                        }
                    } else {
                        //Log.e(TAG, "=====onItemClick===" + position);
                        String str = Expression.emojName[position + j * 20];
                        SpannableString spannableString = new SpannableString(str);
                        //Log.e(TAG, "====Expression.getIdAsName(str)===" + Expression.getIdAsName(str));
                        Drawable drawable = PublishActivity.this.getResources().getDrawable(Expression.getIdAsName(str));
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                        spannableString.setSpan(imageSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        int cuors = ediContent.getSelectionStart();
                        ediContent.getText().insert(cuors, spannableString);
                    }
                }
            });
        }
    }

    private void initView() {

        ediContent = (EditText) findViewById(R.id.id_lxw_push_content);

        scrollPicContent = (HorizontalScrollView) findViewById(R.id.id_lxw_push_scrollPicContent);
        layPicContent = (LinearLayout) findViewById(R.id.id_lxw_push_layPicContent);

        btnCamera = (LinearLayout) findViewById(R.id.id_lxw_push_btn_btnCamera);
        btnEmotion = (LinearLayout) findViewById(R.id.id_lxw_push_btn_btnEmotion);
        btnSend = (LinearLayout) findViewById(R.id.btnSend);

        emojPager = (ViewPager) findViewById(R.id.id_lxw_push_emoj_viewpager);
    }

    private void initEvent() {
        btnCamera.setOnClickListener(this);
        btnEmotion.setOnClickListener(this);
        emojPager.setOnClickListener(this);
        btnSend.setOnClickListener(this);


        ediContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isOpen) {
                    openKeyBoard();
                    isOpen = false;
                    showEmotion(isOpen);
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_lxw_push_btn_btnCamera:
                Intent intent = new Intent(this, ChoseImgActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.id_lxw_push_btn_btnEmotion:
                //Log.e(TAG, "=============>emotion");
                if (isOpen) {
                    isOpen = false;
                } else {
                    isOpen = true;
                }
                showEmotion(isOpen);
                break;
            case R.id.btnSend:
                openKeyBoard();
                pushHelp();
                break;
        }
    }

    private void showEmotion(boolean isOpen) {
        if (isOpen) {
            //  hidenkeyBoard();
            openKeyBoard();
            emojPager.setVisibility(View.VISIBLE);

            initEmotionUp();
        } else {
            emojPager.setVisibility(View.GONE);
        }
    }


    public void openKeyBoard() {

        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 隐藏软键盘
     */
    private void hidenkeyBoard() {
        if (this.getCurrentFocus() != null) {
            ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private void initEmotionUp() {
        //  Log.e(TAG, "======initEmotionUp=========");
        emojPager.setAdapter(new EmotionPagerAdapter(this, mGridViews));
        emojPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            Bundle datas = data.getExtras();
            // String imgs[]=datas.getStringArray("pics");
            refresUI();
        }
    }


    /**
     *
     */
    private void refresUI() {
        Set<String> Imgs = ImageChoseAdapter.mSelectImg;
        if (Imgs.size() == 0) {
            scrollPicContent.setVisibility(View.GONE);
            return;
        }
        if (Imgs.size() > 0) {
            if (layPicContent != null) {
                layPicContent.removeAllViews();
                scrollPicContent.setVisibility(View.VISIBLE);
            }
            for (String path : Imgs) {
                View itemView = LayoutInflater.from(PublishActivity.this).inflate(R.layout.lxw_item_publish_pic, null);
                ImageView img = (ImageView) itemView.findViewById(R.id.id_lxw_publish_pic_img);

                itemView.setTag(path);
                itemView.setOnClickListener(onPicTureClickListener);

                ImageLoader.getInstance(2, ImageLoader.Type.LIFO).loaderImage(path, img, false);
                if (layPicContent != null) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layPicContent.addView(itemView, lp);
                }
            }
        } else {
            if (scrollPicContent != null) {
                scrollPicContent.setVisibility(View.GONE);
            }
        }

    }


    private View.OnClickListener onPicTureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final String path = v.getTag().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(PublishActivity.this);
            AlertDialog alert = builder.setMessage("确认删除图片？")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageChoseAdapter.mSelectImg.remove(path);
                            for (int i = 0; i < layPicContent.getChildCount(); i++) {
                                View view = layPicContent.getChildAt(i);
                                if (view.getTag().toString().equals(path)) {
                                    layPicContent.removeView(view);
                                    break;
                                }
                            }
                            if (ImageChoseAdapter.mSelectImg == null || ImageChoseAdapter.mSelectImg.size() == 0) {
                                scrollPicContent.setVisibility(View.GONE);
                            }
                        }
                    }).create();
            alert.show();
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110) {
                //  mProgressDialog.dismiss();
            }
        }
    };

    private void pushHelp() {

        final String content = ediContent.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            return;
        }
        mProgressDialog = ProgressDialog.show(this, null, "正在上传");

        new Thread() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>(ImageChoseAdapter.mSelectImg);
                if (list.size() > 0) {
                    getCacheImgFiles(PublishActivity.this, list);
                    uploader(filePhotos, content);
                } else {
                    saveText(content);
                }
                mHandler.sendEmptyMessage(0x110);
            }
        }.start();
        // savePulish(title, content, list);

    }

    private void saveText(String content) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BILIBILI helps = new BILIBILI();
        helps.setUser(user);
        helps.setContent(content);
        helps.setState(0);
        helps.setLikeNum(1);
        helps.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    finish();
                    mProgressDialog.dismiss();
                    //Log.i("bmob", "保存成功");
                } else {
                    mProgressDialog.dismiss();
                    //Log.i("bmob", "保存失败：" + e.getMessage());
                }
            }
        });
    }

    private void savePulish(String content, PhontoFiles files) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BILIBILI helps = new BILIBILI();
        helps.setUser(user);
        helps.setContent(content);
        helps.setState(0);
        helps.setLikeNum(1);
        helps.setPhontofile(files);
        helps.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    finish();
                    mProgressDialog.dismiss();
                } else {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param list
     * @return
     */
    private void uploader(List<String> list, final String content) {
        final PhontoFiles phontoFiles = new PhontoFiles();
        final PhontoFiles ps = new PhontoFiles();
        if (list.size() == 1) {
            File file = new File(list.get(0));
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.uploadblock(new UploadFileListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        //bmobFile.getFileUrl()--返回的上传文件的完整地址
                        phontoFiles.setPhoto(bmobFile.getFileUrl());
                        phontoFiles.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    ps.setObjectId(phontoFiles.getObjectId());
                                    //Log.e(TAG, "hjkhjkh=======>" + phontoFiles.getObjectId());
                                    savePulish(content, phontoFiles);
                                } else {
                                    finish();
                                }
                            }
                        });
                    } else {
                        //toast("上传文件失败：" + e.getMessage());
                    }

                }

                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        } else {
            String[] filePaths = list.toArray(new String[list.size()]);
            //final String[] filePaths = new String[2];
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

                @Override
                public void onSuccess(final List<BmobFile> files, final List<String> urls) {

                    phontoFiles.setPhotos(files);
                    phontoFiles.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            //// if (urls.size() == 1) {//如果第一个文件上传完成

                            //  } else if (urls.size() == 2) {//第二个文件上传成功

                            //   }
                            if (e == null) {
                                savePulish(content, phontoFiles);
                                //finish();
                                //mProgressDialog.dismiss();
                            } else {
                                //mProgressDialog.dismiss();
                            }
                        }
                    });
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    //ShowToast("错误码"+statuscode +",错误描述："+errormsg);
                    ToastUtil.show(PublishActivity.this, "错误码" + statuscode + ",错误描述：" + errormsg, Toast.LENGTH_SHORT);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }
            });

        }

        //Log.e(TAG, "===ps++++objectId" + ps.getObjectId());

    }


    /**
     * 获取缓存图片地址
     *
     * @param context
     * @param list
     */
    private void getCacheImgFiles(Context context, List<String> list) {
        filePhotos = new ArrayList<String>();
        for (String path : list) {
            filePhotos.add(compressBitmap(context, path));
        }
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，并获取到缓存后的图片路径
     *
     * @param context
     * @param path
     * @return
     */
    private String compressBitmap(Context context, String path) {
        Bitmap bitmap = compressBitmapFromFile(path);
        File srcFile = new File(path);
        String desPath = getImageCacheDir(context) + srcFile.getName();
        File file = new File(desPath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);//50
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return desPath;
    }

    /**
     * 获取图片缓存路径
     *
     * @param context
     * @return
     */
    private String getImageCacheDir(Context context) {
        String cachepath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachepath = context.getExternalCacheDir().getPath();
        } else {
            cachepath = context.getCacheDir().getPath();
        }
        return cachepath;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 基于质量的压缩算法,保证图片大小小于200k
     *
     * @param bitmap
     * @return
     */

    private Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream byins = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(byins, null, null);
    }


    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path
     * @return
     */
    private Bitmap compressBitmapFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        options.inJustDecodeBounds = false;
        int width = options.outWidth;
        int height = options.outHeight;

        float widthRadio = 1080f;
        float heightRadio = 1920f;
        int inSampleSize = 1;
        if (width > height && width > widthRadio) {
            inSampleSize = (int) (width * 1.0f / widthRadio);
        } else if (width < height && height > heightRadio) {
            inSampleSize = (int) (height * 1.0f / heightRadio);
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        bitmap = BitmapFactory.decodeFile(path, options);
        return compressBitmap(bitmap);
    }

}
