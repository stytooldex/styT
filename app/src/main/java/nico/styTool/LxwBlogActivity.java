package nico.styTool;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by luxin on 15-12-23.
 */
public class LxwBlogActivity extends AppCompatActivity
{

    private WebView webview;

    android.support.v7.widget.Toolbar toolbar;

    private FloatingActionButton fabBtn;

    private void rootViewshowLoginDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setMessage("选择一个模式［确定要刷的QQ号］")
	    .setNeutralButton("刷棒棒糖", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
		    EditText editText = (EditText)findViewById(R.id.tv_no);
		    String strurl = String.valueOf(editText.getText());
		    webview.loadUrl(Constant.iddex + strurl);
		}
	    })
	    .setNegativeButton("QQ音乐", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
		    EditText editText = (EditText)findViewById(R.id.tv_no);
		    String strurl = String.valueOf(editText.getText());
		    webview.loadUrl(Constant.httpkdex + strurl);

		}
	    }).setPositiveButton("QQ拉圈圈", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
		    EditText editText = (EditText)findViewById(R.id.tv_no);
		    String strurl = String.valueOf(editText.getText());
		    webview.loadUrl(Constant.httpkres + strurl);

		}
	    }).create();
        alertDialog.show();
    }
    @Override
    protected void onStart()
    {
	// TODO: Implement this method
	super.onStart();
//		设置副标题
	toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
//		设置导航图标
	toolbar.setNavigationOnClickListener(new OnClickListener()
	    {
		@Override
		public void onClick(View p1)
		{
		    finish();
		}
	    });
    }
    private void initInstances()
    {
	fabBtn = (FloatingActionButton) findViewById(R.id.fab);
	fabBtn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v)
		{
		    rootViewshowLoginDialog();
		}
	    });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.lxw_blog);

	initInstances();
	toolbar = (android.support.v7.widget.Toolbar)
	    findViewById(R.id.toolbar);
//		初始化Toolbar控件
	setSupportActionBar(toolbar);
//		用Toolbar取代ActionBar
	//toolbar.setTitleTextColor(getResources().getColor(R.color.text_font_white));//标题颜色
	//	toolbar.setSubtitleTextColor(getResources().getColor(R.color.text_font_white));//副标题颜色

	webview = (WebView) findViewById(R.id.webView);
//设置WebView属性，能够执行Javascript脚本

	webview.setVisibility(View.GONE);

	webview.getSettings().setJavaScriptEnabled(true);
//加载需要显示的网页
//设置Web视图
	webview.setWebViewClient(new HelloWebViewClient());
    }


    private class HelloWebViewClient extends WebViewClient
    {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
	    view.loadUrl(url);
	    return true;
	}
//Web视图
	@Override 
	public void onPageFinished(WebView view, String url) 
	{ 
            finish();
	    Toast.makeText(LxwBlogActivity.this, R.string.mt_apktool, Toast.LENGTH_SHORT).show();

	}}}
