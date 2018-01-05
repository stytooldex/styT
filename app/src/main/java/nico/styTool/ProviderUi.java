package nico.styTool;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ProviderUi extends dump.z.BaseActivity {
    int type = 0;
    String path = "";//视频路径
    MediaExtractor mediaExtractor;
    MediaMuxer mediaMuxer;
    final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();//SD卡根目录
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iy);

        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
        toolbar.setTitle("视频转换为MP3");
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
    }

    public void on1(View v) {
        //调用文件管理器
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void on2(View v) {
        File out = new File(SDCARD_PATH, "Android");
        int p = 0;
        String wjm = path.substring(path.lastIndexOf("/") + 1);
        while (new File(out, wjm + p + ".mp3").exists()) p++;
        new Thread(new zh(path, out + "/" + wjm + p + ".mp3")).start();
    }

    public void on3(View v) {
        final EditText t1 = new EditText(this);
        t1.setHint("");
        new AlertDialog.Builder(this).setTitle("")
                // setIcon(R.drawable.ic_launcher).setView(t1)
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        path = t1.getText().toString();
                        handler.obtainMessage(2, path).sendToTarget();
                        type = 2;
                    }
                })
                .setNegativeButton("", null).show();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            TextView k = (TextView) findViewById(R.id.mainTextView2);
            k.setText(msg.obj.toString() + "");
            /*
             AlertDialog.Builder builder = new AlertDialog.Builder(oO.this);
			 AlertDialog alertDialog = builder.setMessage(msg.obj.toString())
			 .setNegativeButton("o", new DialogInterface.OnClickListener() {
			 @Override
			 public void onClick(DialogInterface dialog, int which) {

			 }
			 }).setPositiveButton("k", new DialogInterface.OnClickListener() {
			 @Override
			 public void onClick(DialogInterface dialog, int which) {

			 }
			 }).create();
			 alertDialog.show();*/

        }
    };

    private void muxerAudio(String file_in, String file_out) {
        if (type == 2) handler.obtainMessage(2, null).sendToTarget();
        mediaExtractor = new MediaExtractor();
        MediaPlayer md = new MediaPlayer();
        try {
            md.setDataSource(file_in);
            md.prepare();
        } catch (SecurityException | IllegalArgumentException | IllegalStateException | IOException ignored) {
        }
        int audioIndex = -1;//音频通道
        int count = 0;//已转换的时长
        int size = md.getDuration();//获取视频时长
        try {
            mediaExtractor.setDataSource(file_in);//设置视频路径，可以是网络链接
            int trackCount = mediaExtractor.getTrackCount();//获取通道总数
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                    audioIndex = i;
                }//获取音频通道
            }
            mediaExtractor.selectTrack(audioIndex);//切换到音频通道
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioIndex);
            mediaMuxer = new MediaMuxer(file_out, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeAudioIndex = mediaMuxer.addTrack(trackFormat);
            mediaMuxer.start();
            ByteBuffer byteBuffer = ByteBuffer.allocate(trackFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE));
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            long stampTime = 0;
            //获取相邻帧之间的间隔时间
            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    mediaExtractor.advance();
                }
                mediaExtractor.readSampleData(byteBuffer, 0);
                long secondTime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                mediaExtractor.readSampleData(byteBuffer, 0);
                long thirdTime = mediaExtractor.getSampleTime();
                stampTime = Math.abs(thirdTime - secondTime);
            }
            mediaExtractor.unselectTrack(audioIndex);
            mediaExtractor.selectTrack(audioIndex);
            //开始提取音频
            while (true) {
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor.advance();//移动到下一帧
                bufferInfo.size = readSampleSize;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bufferInfo.flags = MediaCodec.BUFFER_FLAG_KEY_FRAME;
                }
                bufferInfo.offset = 0;
                bufferInfo.presentationTimeUs += stampTime;
                count += stampTime;
                handler.obtainMessage(1, count / 1000 + "/" + size + "  " + Math.round(count / 10f / size) + "%").sendToTarget();
                mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaExtractor.release();
            handler.obtainMessage(1, "转换成功，已经保存到:" + file_out).sendToTarget();
        } catch (IOException e) {
            handler.obtainMessage(1, "");
        }
    }

    //通过文件管理器获取视频路径
    /*
     @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data)
	 {
	 if (resultCode == RESULT_OK)
	 {
	 Uri uri = data.getData();
	 Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
	 if (cursor.moveToFirst())
	 {
	 path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
	 handler.obtainMessage(2, path).sendToTarget();
	 }
	 //Toast.makeText(this,path,3000).show();
	 }
	 super.onActivityResult(requestCode, resultCode, data);
	 }
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                path = nico.GetPathFromUri4kitkat.getPath(this, uri);
                handler.obtainMessage(2, path).sendToTarget();
                //
            }
        }
    }


    //提取音频的线程
    private class zh implements Runnable {
        String out, in;

        zh(String a, String b) {
            in = a;
            out = b;
        }

        @Override
        public void run() {
            muxerAudio(in, out);
        }
    }
}

