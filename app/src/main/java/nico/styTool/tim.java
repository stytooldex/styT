package nico.styTool;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class tim extends WebViewClient
{

    int count=0;

    private Context context;
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
	view.loadUrl(url);
	return true;
    }
    @Override 
    public void onPageFinished(WebView view, String url) 
    { 
	Toast.makeText(context, "已轰炸" + String.valueOf(count), Toast.LENGTH_SHORT).show();
	count++;
    }}
