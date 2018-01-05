package dump.y;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import dump.z.BaseActivity_;
import nico.styTool.R;

public class x5_MainActivity extends BaseActivity_ {
    private com.tencent.smtt.sdk.WebView sMm;
    private long exitTime = 0;

    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public void destroy() {
        if (sMm != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = sMm.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(sMm);
            }

            sMm.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            sMm.getSettings().setJavaScriptEnabled(false);
            sMm.clearHistory();
            sMm.clearView();
            sMm.removeAllViews();

            try {
                sMm.destroy();
            } catch (Throwable ignored) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (sMm.canGoBack()) {
            // webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            sMm.goBack();
            //  iua.setVisibility(View.GONE);
        } else {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                finish();

            }
        }
    }

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
        public void onPageFinished(WebView view, String url) {
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

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

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
            Toast.makeText(x5_MainActivity.this, "" + r, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings() {

        WebSettings settings = sMm.getSettings();
        //sMm.setLayerType();
        sMm.setDrawingCacheEnabled(true);
        //支持获取手势焦点
        sMm.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        //settings.setUserAgentString("Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_1_1 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3 XiaoMi/MiuiBrowser/8.9.4");//UA

        // settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false); // 支持缩放
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(false);
        //设置缓存模式
        settings.setGeolocationEnabled(true);//允许地理位置可用
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(sMm.getContext().getCacheDir().getAbsolutePath());
        //settings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.Apme);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        setContentView(R.layout.x5_main);
        sMm = (com.tencent.smtt.sdk.WebView) findViewById(R.id.web_view);
        initWebSettings();
        com.tencent.smtt.sdk.CookieManager cookieManager = com.tencent.smtt.sdk.CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(sMm, true);
        } else {
            cookieManager.setAcceptCookie(true);
        }
        sMm.setWebChromeClient(new MyWebChromeClient());
        sMm.setDownloadListener(new MyDownLoadListener());
        sMm.setWebViewClient(new qq());

        Intent nt = getIntent();
        sMm.loadUrl(nt.getStringExtra("fio"));

    }
}