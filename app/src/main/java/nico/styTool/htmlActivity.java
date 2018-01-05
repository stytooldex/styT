package nico.styTool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class htmlActivity extends dump.z.BaseActivity implements OnClickListener {
    private String TAG = "webdatashow";
    private Button urlConnection;
    private Button httpClient;
    private TextView webDataShow;
    /**
     * Called when the activity is first created.
     */
    android.support.v7.widget.Toolbar toolbar;

    private static final int MSG_SUCCESS = 0;
    private static final int MSG_FAILURE = 1;
    private Handler mHandler = null;
    private Thread mThread;
    private Thread httpClientThread;
    private EditText ped;
    FloatingActionButton fabBtn;


    @Override
    protected void onStart() {
        // TODO: Implement this method
        super.onStart();
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

    //		设置导航按钮监听
    private void redirectByTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                ped.setText(intent.getStringExtra("via"));
            }
        }, 2000);
    }

    private void initInstances() {
        fabBtn = (FloatingActionButton) findViewById(R.id.fab);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (httpClientThread == null) {
                    httpClientThread = new Thread(httpClientRunnable);
                    httpClientThread.start();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html_main);
        initInstances();
        redirectByTime();
        ped = (EditText) findViewById(R.id.tv_no);

        toolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.toolbar);
//		初始化Toolbar控件
        setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
        //	toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
        //	toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色


        urlConnection = (Button) findViewById(R.id.urlConnection);
        webDataShow = (TextView) findViewById(R.id.webDataShow);
        urlConnection.setOnClickListener(this);
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case MSG_SUCCESS:
                        Toast.makeText(getApplicationContext(), "URLok", Toast.LENGTH_SHORT).show();
                        webDataShow.setText((String) msg.obj);
                        break;
                    case MSG_FAILURE:
                        Toast.makeText(getApplicationContext(), "URLNO", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }

        };


    }
//这里

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.urlConnection:
                if (mThread == null) {
                    mThread = new Thread(urlConnRunnable);
                    mThread.start();
                }
                break;
            default:
                break;
        }


    }

    Runnable httpClientRunnable = new Runnable() {

        @Override
        public void run() {

            httpClientWebData();

        }
    };


    Runnable urlConnRunnable = new Runnable() {

        @Override
        public void run() {

            try {
                urlConGetWebData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    private void urlConGetWebData() throws IOException {

        String pediyUrl = ped.getText().toString();


        URL url = new URL(pediyUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Log.d("TAG", "---into-----urlConnection---success--");

            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            int i;
            String content = "";
            while ((i = isr.read()) != -1) {
                content = content + (char) i;
            }
            mHandler.obtainMessage(MSG_SUCCESS, content).sendToTarget();
            isr.close();
            httpConn.disconnect();
        } else {
            //Log.d("TAG", "---into-----urlConnection---fail--");

        }

    }

    protected void httpClientWebData() {
        DefaultHttpClient httpClinet = new DefaultHttpClient();
        String pediyUrl = ped.getText().toString();

        HttpGet httpGet = new HttpGet(pediyUrl);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String content = httpClinet.execute(httpGet, responseHandler);
            mHandler.obtainMessage(MSG_SUCCESS, content).sendToTarget();
        } catch (IOException e) {

            e.printStackTrace();
        }


    }


}


