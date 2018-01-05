package nico.styTool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import dump.t.BitmapExtractor;
import dump.t.GIFEncoder;


public class Viewhtml extends dump.z.BaseActivity {

    private ProgressDialog mProgressDialog;
    private File srcFile = null;

    private enum State {INIT, READY, BUILDING, COMPLETE}

    private State state = State.INIT;
    int type = 0;
    private TextView selectVideo;
    private TextView tip;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectVideo = (TextView) findViewById(R.id.select_video);
        selectVideo.setOnClickListener(clickListener);
        tip = (TextView) findViewById(R.id.tip);
        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
        toolbar.setTitle("视频转换Gif");
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
//		用Toolbar取代ActionBar

    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    protected void onStart() {
        // TODO: Implement this method
        super.onStart();

//		设置导航按钮监听
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                SharedPreferences mySharedPreferences = getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString(Constant.id, getPath(this, uri));
                editor.apply();
                //filePath = getRealFilePath(videoUri);
                state = State.READY;
                selectVideo.setText("开始转换为Gif");

                // Toast.makeText(this, "文件路径："+uri.getPath().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data)
     {

     if (requestCode == REQUEST_SELECT_VIDEO)
     {
     if (resultCode == RESULT_OK)
     {
     Uri uri = data.getData();
     Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
     if (cursor.moveToFirst())
     {
     //path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
     //handler.obtainMessage(2, path).sendToTarget();
     String string2 = cursor.getString(cursor.getColumnIndex("_data"));
     this.srcFile = new File(string2);
     SharedPreferences mySharedPreferences= getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
     SharedPreferences.Editor editor = mySharedPreferences.edit();
     editor.putString(Constant.id, string2);
     editor.commit();
     //filePath = getRealFilePath(videoUri);
     state = State.READY;
     selectVideo.setText("开始转换为Gif");
     //Toast.makeText(this,string2,Toast.LENGTH_SHORT).show();

     }
     //Uri videoUri = data.getData();

     //tip.setText("点击按钮, 生成 GIF");
     }
     }
     }
     */
    public String getRealFilePath(Uri uri) {
        String path = uri.getPath();
        String[] pathArray = path.split(":");
        String fileName = pathArray[pathArray.length - 1];
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (state == State.INIT || state == State.COMPLETE) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

                intent.setType("video/*");

                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {

                    startActivityForResult(Intent.createChooser(intent, "选择文件"), 1);

                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(Viewhtml.this, "没有文件管理器", Toast.LENGTH_SHORT).show();

                }
                /*Intent intent = new Intent();
                 intent.setType("video/*");
				 intent.setAction(Intent.ACTION_GET_CONTENT);
				 startActivityForResult(Intent.createChooser(intent, "选择视频"), REQUEST_SELECT_VIDEO);*/

				/*
                 Intent intenta = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
				 startActivityForResult(intenta, 1);
				 type = 1;*/
                /*
                 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				 intent.setType("video/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
				 intent.addCategory(Intent.CATEGORY_OPENABLE);
				 startActivityForResult(intent, 1);
				 */

            } else if (state == State.READY) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        state = State.BUILDING;
                        //tip.setText("正在生成 GIF, 请稍候");
                        mProgressDialog = ProgressDialog.show(Viewhtml.this, null, "换转为Gif...");
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        SharedPreferences sharedPreferencesb = getSharedPreferences(Constant.android, AppCompatActivity.MODE_PRIVATE);
                        String si = sharedPreferencesb.getString(Constant.id, "");
                        BitmapExtractor extractor = new BitmapExtractor();
                        extractor.setFPS(4);
                        extractor.setScope(0, 5);
                        extractor.setSize(540, 960);
                        List<Bitmap> bitmaps = extractor.createBitmaps(si);

                        String fileName = String.valueOf(System.currentTimeMillis()) + ".gif";
                        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Android/styTool/B_GIF" + fileName;
                        GIFEncoder encoder = new GIFEncoder();
                        encoder.init(bitmaps.get(0));
                        encoder.start(filePath);
                        for (int i = 1; i < bitmaps.size(); i++) {
                            encoder.addFrame(bitmaps.get(i));
                        }
                        encoder.finish();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        state = State.COMPLETE;
                        tip.setText("转换完成~存储路径：" + Environment.getExternalStorageDirectory().getPath() + "/Android/styTool/");
                        mProgressDialog.dismiss();
                        selectVideo.setText("选择视频");
                        //Toast.makeText(getApplicationContext(), "存储路径" + "/sdcard/DCIM/Camera/VID_20170311_194347.mp4", Toast.LENGTH_LONG).show();
                    }
                }.execute();
            }
        }
    };
}
