package dump_dex.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import dump.z.BaseActivity_;
import nico.SPUtils;
import nico.styTool.R;


public class MainActivity extends BaseActivity_ implements View.OnClickListener {
    private WebView sMm;

    private class qq extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }
        //@Override
        //public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        //{
        //view.loadUrl(request.toString());
        //return true;
        //}


        //页面完成加载时
        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);

            // mProgressbar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    }


    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            /*
            if (newProgress == 100) {
                toolbar.setTitle("流莉");
            } else {
                toolbar.setTitle(String.valueOf(newProgress));
            }*/
            super.onProgressChanged(view, newProgress);
        }

        //配置权限（同样在WebChromeClient中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final android.webkit.GeolocationPermissions.Callback callback) {
            final boolean remember = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("位置信息");
            builder.setMessage(origin + "允许获取您的地理位置信息吗？").setCancelable(true).setPositiveButton("允许", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    callback.invoke(origin, true, remember);
                }
            })
                    .setNegativeButton("不允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            callback.invoke(origin, false, remember);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    }

    private class MyDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(final String r, String userAgent, String contentDisposition, String mimetype, long contentLength) {


        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings() {

        WebSettings settings = sMm.getSettings();
        settings.setUserAgentString("" + SPUtils.get(MainActivity.this, "if_7", ""));//UA
        //支持获取手势焦点
        sMm.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled((Boolean) SPUtils.get(MainActivity.this, "if_1", true));
        //支持插件

        // settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom((Boolean) SPUtils.get(MainActivity.this, "if_3", false)); // 支持缩放
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(false);
        //设置缓存模式
        settings.setGeolocationEnabled((Boolean) SPUtils.get(MainActivity.this, "if_2", true));//允许地理位置可用
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled((Boolean) SPUtils.get(MainActivity.this, "if_4", true));
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(sMm.getContext().getCacheDir().getAbsolutePath());
        //settings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setLoadsImagesAutomatically(false);

        // settings.setNeedInitialFocus(true);
        //设置编码格式
        //settings.setDefaultTextEncodingName("UTF-8");

    }

    private EditText editText_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_33main);
        init();
        sMm = (WebView) findViewById(R.id.WebView_query);
        sMm.setWebChromeClient(new MyWebChromeClient());
        sMm.setDownloadListener(new MyDownLoadListener());
        sMm.setWebViewClient(new qq());
        initWebSettings();
    }

    @SuppressLint("SetTextI18n")
    public void init() {

        editText_id = (EditText) findViewById(R.id.edittext_name);
        editText_id.setText("" + SPUtils.get(MainActivity.this, "anged", ""));
        Button button_query = (Button) findViewById(R.id.button_query);
        button_query.setOnClickListener(this);

        // 对EditText内容的实时监听
        editText_id.addTextChangedListener(new TextWatcher() {
            // 第二个执行
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 第一个执行
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // 第三个执行
            @Override
            public void afterTextChanged(Editable s) { // Edittext中实时的内容
                SPUtils.put(MainActivity.this, "anged", s + "");
            }
        });

    }

    @Override
    public void onClick(View v) {
        final String username = editText_id.getText().toString().trim();
        sMm.loadUrl(username);
    }
}
