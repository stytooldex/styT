package dump.b;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;

import nico.styTool.R;

public class Main3Activity extends dump.z.BaseActivity_{
    private final String path = Environment.getExternalStorageDirectory() + "/2";
    private final String OutPutFileDirPath = Environment.getExternalStorageDirectory() + "/APKSTYTOOL/png";

    private TextView mTextViewInfo;
    private ImageView mImageView;
    private ExtractVideoInfoUtil mExtractVideoInfoUtil;
    private ExtractFrameWorkThread mExtractFrameWorkThread;
    private VideoEditAdapter videoEditAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                final Uri uri = data.getData();
                mExtractVideoInfoUtil = new ExtractVideoInfoUtil(nico.GetPathFromUri4kitkat.getPath(this, uri));
                onClickExtractInfo();
                Button btn_publish = (Button) findViewById(R.id.btn_mul);
                btn_publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Main3Activity.this, "已提取到"+Environment.getExternalStorageDirectory()+"/APKSTYTOOL/png/", Toast.LENGTH_SHORT).show();
                        long endPosition = Long.valueOf(mExtractVideoInfoUtil.getVideoLength());
                        long startPosition = 0;
                        int thumbnailsCount = 10;
                        int extractW = (DeviceUtils.getScreenWidth(Main3Activity.this)) / 4;
                        int extractH = DeviceUtils.dip2px(Main3Activity.this, 55);
                        mExtractFrameWorkThread = new ExtractFrameWorkThread(extractW, extractH, mUIHandler, nico.GetPathFromUri4kitkat.getPath(Main3Activity.this, uri), OutPutFileDirPath, startPosition, endPosition, thumbnailsCount);
                        mExtractFrameWorkThread.start();
                    }
                });

                //this.srcEt.setText(nico.GetPathFromUri4kitkat.getPath(this, uri));
                //this.srcEt.setEnabled(false);
                //this.srcFile = new File(nico.GetPathFromUri4kitkat.getPath(this, uri));

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3main);

        initView();
        //initData();
    }

    private void initView() {
        mTextViewInfo = (TextView) findViewById(R.id.id_tv_info);
        mImageView = (ImageView) findViewById(R.id.id_image);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.id_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        videoEditAdapter = new VideoEditAdapter(this, (DeviceUtils.getScreenWidth(this)) / 4);
        mRecyclerView.setAdapter(videoEditAdapter);
    }

    private void initData() {
        if (!new File(path).exists()) {
            //Toast.makeText(this, "视频文件不存在", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onClickExtractInfo() {
        String duration = mExtractVideoInfoUtil.getVideoLength();
        int w = mExtractVideoInfoUtil.getVideoWidth();
        int h = mExtractVideoInfoUtil.getVideoHeight();
        int degree = mExtractVideoInfoUtil.getVideoDegree();
        String mimeType = mExtractVideoInfoUtil.getMimetype();
        String stringBuilder = "" + w + "x" +
                "" + h + "\n" +
                "类型:" + mimeType;
        //stringBuilder.append("duration:").append(duration).append("ms ");
        //stringBuilder.append("degree:").append(degree).append(" ");
        mTextViewInfo.setText(stringBuilder);
    }

    public void onClickExtractOne(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
        //String path = mExtractVideoInfoUtil.extractFrames(OutPutFileDirPath);
        // Glide.with(this)
        //       .load("file://" + path)
        //       .into(mImageView);
    }

    private final MainHandler mUIHandler = new MainHandler(Main3Activity.this);

    private static class MainHandler extends Handler {
        private final WeakReference<Main3Activity> mActivity;

        MainHandler(Main3Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Main3Activity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == ExtractFrameWorkThread.MSG_SAVE_SUCCESS) {
                    if (activity.videoEditAdapter != null) {
                        VideoEditInfo info = (VideoEditInfo) msg.obj;
                        activity.videoEditAdapter.addItemVideoInfo(info);
                    }
                }
            }
        }
    }

    // @Override
    //  protected void onDestroy() {
    //     super.onDestroy();
    // mExtractVideoInfoUtil.release();
    /// if (mExtractFrameWorkThread != null) {
    //    mExtractFrameWorkThread.stopExtract();
    ///  }
    //  mUIHandler.removeCallbacksAndMessages(null);
    // / if (!TextUtils.isEmpty(OutPutFileDirPath)) {
    //PictureUtils.deleteFile(new File(OutPutFileDirPath));
    //   }
    // }
}
