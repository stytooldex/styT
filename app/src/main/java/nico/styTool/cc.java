package nico.styTool;

import android.graphics.Bitmap;
public class cc {
	/**
	 * 构造函数
	 * @param im 图片
	 * @param del 延时
	 */
	public cc(Bitmap im, int del) {
		image = im;
		delay = del;
	}
	/**图片*/
	public Bitmap image;
	/**延时*/
	public int delay;
	/**下一帧*/
	public cc nextFrame = null;
}
