package dump.z;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Patterns;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import dump.y.AnimationUtils;
import dump.y.x5_MainActivity;
import nico.SPUtils;
import nico.styTool.ProperTies;
import nico.styTool.R;

public class Main2Activity extends dump.z.BaseActivity_ {

    private Spinner spinner;
    private WebView sMm;
    private ActionMode mActionMode;
    private List<String> mActionList = new ArrayList<>();
    private long exitTime = 0;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private String data = null;
    private String url_ = null;

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        try {
            startActivityForResult(Intent.createChooser(i, "选择文件"), FILE_CHOOSER_RESULT_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatSize(String target_size) {
        return Formatter.formatFileSize(this, Long.valueOf(target_size));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -1:
                //不做任何处理
                break;
            case 2:
                //Toast.makeText(this, data.getStringExtra("url"), Toast.LENGTH_SHORT).show();
                sMm.loadUrl(data.getStringExtra("url"));
                break;
        }
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null) return;
        Uri[] results = null;
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
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
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);

            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file:///")) {

            } else {
                SPUtils.put(Main2Activity.this, "_v", url);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                AlertDialog alert = builder
                        .setTitle((String) SPUtils.get(Main2Activity.this, "_v", ""))
                        .setMessage("网址启动第三方软件")
                        .setNegativeButton("允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse(SPUtils.get(Main2Activity.this, "_v", "") + "");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setPositiveButton("不允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                alert.show();
            }

            SPUtils.put(Main2Activity.this, "pos2", "" + url);

            // mProgressbar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    }


    private class MyWebChromeClient extends WebChromeClient {

        //=========多窗口的问题==========================================================

/*
        private Bitmap mDefaultVideoPoster;//默认的视频展示图

        @Override
        public Bitmap getDefaultVideoPoster() {
            if (mDefaultVideoPoster == null) {
                mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                return mDefaultVideoPoster;
            }
            return super.getDefaultVideoPoster();
        }*/

        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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
            final android.support.design.widget.FloatingActionButton btn_publish = (android.support.design.widget.FloatingActionButton) findViewById(R.id.lxw_id_push_helps_comment_float);
            //String url2 = String.valueOf(SPUtils.get(Main2Activity.this, "_v", ""));

            final String url = view.getUrl();
            String regex = ".*//(.+?\\.)?(youku|iqiyi|tudou|qq|mgtv|letv|le|sohu|acfun|pptv|yinyuetai|yy|bilibili|wasu|163|56|fun|xunyingwang|meitudata|toutiao|tangdou)\\.(com|net|cn)/.*";
            if (Pattern.matches(regex, url)) {
                    Toast.makeText(Main2Activity.this, "支持播放(选择好视频?请点击中圆角播放)", Toast.LENGTH_SHORT).show();
                    btn_publish.setVisibility(View.VISIBLE);
                    btn_publish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final SharedPreferences setting = Main2Activity.this.getSharedPreferences("__a__x5xposeo", 0);
                            Boolean user_first = setting.getBoolean("FIRST", true);
                            if (user_first) {
                                AlertDialog.Builder obuilder = new AlertDialog.Builder(Main2Activity.this);
                                AlertDialog alertDialog = obuilder.setMessage("少年要看视频要付出小代价")
                                        .setNegativeButton("推荐给朋友", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent sendIntent = new Intent();
                                                sendIntent.setAction(Intent.ACTION_SEND);
                                                sendIntent.putExtra(Intent.EXTRA_TEXT, "妮哩多顶集合功能，快速上手\n还能免费看付费电影哦！\n http://www.coolapk.com/apk/nico.styTool");
                                                sendIntent.setType("text/plain");
                                                try {
                                                    startActivity(sendIntent);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                setting.edit().putBoolean("FIRST", false).apply();
                                            }
                                        }).setCancelable(false)
                                        .create();

                                alertDialog.show();
                            } else {
                            /*
                            PopupMenu popup = new PopupMenu(Main2Activity.this, v);//第二个参数是绑定的那个view
                            //获取菜单填充器
                            MenuInflater inflater = popup.getMenuInflater();
                            //填充菜单
                            inflater.inflate(R.menu.x5_ma, popup.getMenu());
                            //绑定菜单项的点击事件
                            popup.setOnMenuItemClickListener(Main2Activity.this);
                            //显示(这一行代码不要忘记了)
                            popup.show();*/
                                if (TextUtils.isEmpty(url)) {
                                    Toast.makeText(Main2Activity.this, "URL不能为空!", Toast.LENGTH_LONG).show();
                                } else if (Patterns.WEB_URL.matcher(url).matches()) {
                                    Properties proper = ProperTies.getProperties(getApplicationContext());
                                    switch ((String) spinner.getSelectedItem()) {
                                        case "接口一":
                                            data = proper.getProperty("url01");
                                            break;
                                        case "接口二":
                                            data = proper.getProperty("url02");
                                            break;
                                        case "接口三":
                                            data = proper.getProperty("url03");
                                            break;
                                        case "接口四":
                                            data = proper.getProperty("url04");
                                            break;
                                        case "接口五":
                                            data = proper.getProperty("url05");
                                            break;
                                        case "接口六":
                                            data = proper.getProperty("url06");
                                            break;
                                        case "接口七":
                                            data = proper.getProperty("url07");
                                            break;
                                        case "接口八":
                                            data = proper.getProperty("url08");
                                            break;
                                        case "接口九":
                                            data = proper.getProperty("url09");
                                            break;
                                        case "接口十":
                                            data = proper.getProperty("url10");
                                            break;
                                        case "接口十一":
                                            data = proper.getProperty("url11");
                                            break;
                                        case "接口十二":
                                            data = proper.getProperty("url12");
                                            break;
                                        case "接口十三":
                                            data = proper.getProperty("url13");
                                            break;
                                        case "接口十四":
                                            data = proper.getProperty("url14");
                                            break;
                                    }
                                    url_ = data + url;
                                    Intent as = new Intent(Main2Activity.this, x5_MainActivity.class);
                                    as.putExtra("fio", url_);
                                    startActivity(as);
                                } else {
                                    Toast.makeText(Main2Activity.this, "URL错误,请重新输入!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                //Toast.makeText(Main2Activity.this, "t", Toast.LENGTH_SHORT).show();
            } else {
                btn_publish.setVisibility(View.GONE);
                //Toast.makeText(Main2Activity.this, "n", Toast.LENGTH_SHORT).show();
            }
            SPUtils.put(Main2Activity.this, "pos", "" + title);
            final SharedPreferences i = getSharedPreferences("Hello", 0);
            Boolean o0 = i.getBoolean("FIRST", true);
            if (o0) {//第一次
                i.edit().putBoolean("FIRST", false).apply();
            } else {
                //toolbar.setTitle("流莉");
                // toolbar.setSubtitle(title);
            }
        }
    }

    private class MyDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(final String r, String userAgent, String contentDisposition, String mimetype, long contentLength) {


            boolean sFirstRun = (boolean) SPUtils.get(Main2Activity.this, "if_st0", true);
            if (sFirstRun) {
                String str = "" + contentDisposition;
                String str1 = str.replace("attachment;filename=", "");
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                AlertDialog alert = builder.setTitle("下载文件")
                        .setMessage("名称:" + str1 + "\n" + "类型:" + mimetype + "\n" + "大小:" + formatSize(contentLength + "") + "(" + contentLength + ")")
                        .setNeutralButton("普通下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse(r);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //Intent inten = new Intent(MainActivity.this,MainActivity3.class);
                                // inten.putExtra("#", r);
                                // startActivity(inten);
                            }
                        })
                        .setNegativeButton("adm下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, r);
                                sendIntent.setType(AnimationUtils.jiemi(">&•#>、>&_$>……•+•%•￥•?"));
                                sendIntent.setComponent(new ComponentName(AnimationUtils.jiemi("•～•$•}_?•&>•_?•%•&•}_?>……•%>￥"), AnimationUtils.jiemi("•～•$•}_?•&>•_?•%•&•}_?>……•%>￥_?&%&#•&•￥>&•$>_")));
                                try {
                                    startActivity(sendIntent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setPositiveButton("复制直链链接", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToastUtil.show(Main2Activity.this, "复制成功", Toast.LENGTH_SHORT);
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                manager.setText(r);

                            }
                        }).create();
                alert.show();

            } else {

            }

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings() {

        WebSettings settings = sMm.getSettings();
        settings.setUserAgentString("" + SPUtils.get(Main2Activity.this, "if_7", ""));//UA
        //支持获取手势焦点
        sMm.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled((Boolean) SPUtils.get(Main2Activity.this, "if_1", true));
        //支持插件

        // settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom((Boolean) SPUtils.get(Main2Activity.this, "if_3", false)); // 支持缩放
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(false);
        //设置缓存模式
        settings.setGeolocationEnabled((Boolean) SPUtils.get(Main2Activity.this, "if_2", true));//允许地理位置可用
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled((Boolean) SPUtils.get(Main2Activity.this, "if_4", true));
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

        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically((Boolean) SPUtils.get(Main2Activity.this, "if_5", true));//图片
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        // settings.setNeedInitialFocus(true);
        //设置编码格式
        //settings.setDefaultTextEncodingName("UTF-8");

    }

    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
    }

    @SuppressLint("Registered")
    public class AdvanceLoadX5Service extends Service {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            initX5();
        }

        private void initX5() {
            QbSdk.initX5Environment(getApplicationContext(), cb);
        }

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //初始化完成回调
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preinitX5WebCore();
        //预加载x5内核
        Intent inte = new Intent(this, AdvanceLoadX5Service.class);
        startService(inte);
        setContentView(R.layout.activity_main2);
        final SharedPreferences setting = this.getSharedPreferences("430__a__oi", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {
            AlertDialog.Builder obuilder = new AlertDialog.Builder(Main2Activity.this);
            AlertDialog alertDialog = obuilder.setMessage("1.输入百度云链接后,请按输入法右下回车(电脑模拟器同回车)\n\n2.也许可以登录自己百度云(全部文件)\n\n进入文件页面（点击打开）")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setting.edit().putBoolean("FIRST", false).apply();
                        }
                    }).setCancelable(false)
                    .create();

            alertDialog.show();
        } else {

        }


        final EditText editText = (EditText) findViewById(R.id.main2bii);
        //editText.setText(url);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(Main2Activity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    editText.clearFocus();
                    final String urlString = editText.getText().toString();
                    if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
                        sMm.loadUrl(urlString);
                        //	return;
                    } else {
                        sMm.loadUrl("http://" + urlString);
                        //	return;
                    }


                    //httpData();
                    return true;
                }
                return false;
            }
        });
        sMm = (WebView) findViewById(R.id.mobili);

        initWebSettings();
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(sMm, true);
        } else {
            cookieManager.setAcceptCookie(true);
        }
        if (sMm.getContentHeight() * sMm.getScale() == (sMm.getHeight() + sMm.getScrollY())) {
            //已经处于底端
        }

        if (sMm.getScrollY() == 0) {
            //处于顶端
        }
        sMm.setWebChromeClient(new MyWebChromeClient());
        sMm.setDownloadListener(new MyDownLoadListener());
        sMm.setWebViewClient(new qq());

        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            sMm.loadUrl(data + "");
        } else {
            Intent bili = getIntent();
            sMm.loadUrl(bili.getStringExtra("#"));
        }
        spinner = (Spinner) findViewById(R.id.spinner_text);
    }
}
