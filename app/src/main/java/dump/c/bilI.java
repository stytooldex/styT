package dump.c;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dump_dex.Software.DownloadService;
import nico.SPUtils;
import nico.styTool.R;
import nico.styTool.RandomUntil;

public class bilI extends dump.z.BaseActivity_ {

    private static final String B_URL = "http://www.bilibili.com/video/av";
    private EditText editText;
    private TextView imgSrcTextView;
    private ImageView avImg;
    private String imgSrc;

    //判断整数
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /*
            public void download(String avstring) {
                String avurl = "http://www.bilibili.com/video/av" + avstring + "/";
                Document document = null;
                try {
                    Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                    document = Jsoup.connect(avurl).get();
                    //获取所有的img标签
                    Elements elements = document.getElementsByTag("img");
                    imgSrc = elements.first().attr("abs:src");
                    if (imgSrc == null) {
                        Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                    } else {
                        //下载图片
                        OutputStream output = null;
                        URL url = new URL(imgSrc);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        String SDCard = Environment.getExternalStorageDirectory() + "";
                        String pathName = SDCard + "/APKSTYTOOL/bilibili/" + avstring + imgSrc.substring(imgSrc.length() - 4);//文件存储路径
                        File file = new File(pathName);
                        InputStream input = conn.getInputStream();
                        if (file.exists()) {
                            // System.out.println("exits");
                            return;
                        } else {
                            String dir = SDCard + "/BiliSnapshot/bilibili";
                            new File(dir).mkdir();//新建文件夹
                            file.createNewFile();//新建文件
                            output = new FileOutputStream(file);
                            //读取大文件
                            int i = 0;
                            while ((i = input.read()) != -1) {
                                output.write(i);
                            }
                            input.close();
                            output.flush();
                        }
                        Toast.makeText(this, "已下载到" + SDCard + "/APKSTYTOOL/bilibili", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "此视频不存在或无写入权限（请授权）", Toast.LENGTH_SHORT).show();
                }
            }

            public void downloadPic(View view) throws IOException {
                EditText av = (EditText) findViewById(R.id.avedit);
                String avstring = av.getText().toString();
                if (isNumeric(avstring)) {
                    download(avstring);
                } else {
                    Toast.makeText(this, "请输入av号（纯数字）", Toast.LENGTH_SHORT).show();
                }
            }
        */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    public void AddNotification(int id, String s, String name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    public void AddNotification(int id, String s, String name, int max, int dq) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

// 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(name);
        builder.setContentText(s);
        builder.setProgress(max, dq, false);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private DownloadService.DownBinder mDownloadService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadService = ((DownloadService.DownBinder) service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilii);
        // FileDownloader.setup(this);
        FileDownloader.init(this);
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    public void submit(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                editText = (EditText) findViewById(R.id.editText);
                String avstring = editText.getText().toString();
                if (isNumeric(avstring)) {
                    final String avNum = editText.getText().toString();
                    Document document = null;
                    try {
                        document = Jsoup.connect(B_URL + avNum).get();
                        // L.e(document.html());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final Element element = document.select("[class=cover_image]").first();
                    // 在UI线程中刷新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                avImg = (ImageView) findViewById(R.id.AvImg);
                                imgSrcTextView = (TextView) findViewById(R.id.imgSrc);
                                imgSrc = element.attr("src");
                                imgSrcTextView.setText("http:" + imgSrc);
                                Glide.with(bilI.this).load("http:" + imgSrc).into(avImg);

                                SPUtils.put(bilI.this, "webil", "http:" + imgSrc);
                            } catch (Exception e) {
                                Toast.makeText(bilI.this, "视频不存在，或该视频只有会员可以享用", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(bilI.this, "请输入av号（纯数字）", Toast.LENGTH_SHORT).show();
                }

            }
        }).start();
    }

    public void submit2(View view) {
        String url = String.valueOf(SPUtils.get(bilI.this, "webil", "曬太陽"));
        FileDownloader.getImpl().create(url)
                .setPath(Environment.getExternalStorageDirectory().getPath() + "/Teif" + "/" + RandomUntil.getSmallLetter(3) + "bili.png")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        AddNotification(82, "等待下载中", "下载");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        AddNotification(82, "连接中", "下载");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        DecimalFormat df = new DecimalFormat("######0.00");
                        AddNotification(82, df.format((float) soFarBytes / 1024f / 1024f) + "MB" + "/" + df.format(((float) totalBytes / 1024f / 1024f)) + "MB", "下载", totalBytes, soFarBytes);
                    }


                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        AddNotification(82, "下载完成" + Environment.getExternalStorageDirectory().getPath() + "/Teif", "下载");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        AddNotification(82, "下载错误", "下载");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
        //downloadFile("");
        //mDownloadService.startDownload(url);
    }
}