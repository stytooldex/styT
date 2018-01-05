package nico.styTool;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivityTwo extends Activity {

	public int mScreenWidth;
	public int mScreenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		DisplayMetrics dm=new DisplayMetrics();
		WindowManager manager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(dm);
		mScreenWidth=dm.widthPixels;
		mScreenHeight=dm.heightPixels;
		setContentView();
		initViews();
        initData();
	}


	/**
	 * 设置布局文件
	 */
	public abstract void setContentView();

	/**
	 * 初始化布局文件中的控件
	 */
	public abstract void initViews();



	/** 进行数据初始化
	 * initData
	 */
	public abstract void initData();

}
