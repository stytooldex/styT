package nico.styTool;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

import dump.z.BaseActivity_;

public class smali_layout_apktool extends BaseActivity_ {
    /**
     * Called when the activity is first created.
     */

    private WebView sMm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_boom);
        try {
            InputStream is = getAssets().open("Cache_dex/Cache_558c96bd2a6ea1a5");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            final String text = new String(buffer, "UTF-8");
            AlertDialog.Builder builder = new AlertDialog.Builder(smali_layout_apktool.this);
            AlertDialog alertDialog = builder.setMessage("可能会让你手机卡顿\n直到也清妮哩【需要一分钟内】\n功能不一定兼容全部ROM·另外也可以当跑分·\n注:需要网络").setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sMm.loadUrl(text + "root");
                        }
                    }).create();
            alertDialog.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sMm = (WebView) findViewById(R.id.s_mm);
        // mWebview = new WebView(this);
        //sMm.setVisibility(View.GONE);
        WebSettings webSettings = sMm.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       /*
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info.isAvailable())
        {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else
        {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);//不使用网络，只加载缓存
        }*/

        sMm.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        sMm.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.contains("[tag]")) {
                    String localPath = url.replaceFirst("^http.*[tag]\\]", "");
                    try {
                        InputStream is = getApplicationContext().getAssets().open(localPath);
                        //Log.d(TAG, "shouldInterceptRequest: localPath " + localPath);
                        String mimeType = "text/javascript";
                        if (localPath.endsWith("css")) {
                            mimeType = "text/css";
                        }
                        return new WebResourceResponse(mimeType, "UTF-8", is);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }

            }
        });
        // setContentView(mWebview);
    }
}

