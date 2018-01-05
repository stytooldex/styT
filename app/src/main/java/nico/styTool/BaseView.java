package nico.styTool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import static android.view.View.MeasureSpec.getMode;
import static android.view.View.MeasureSpec.getSize;

/**
 * View
 * com.hayukleung.view
 * BaseView.java
 *
 * by hayukleung
 * at 2016-12-21 18:18
 */

public abstract class BaseView extends View
{

	public BaseView(Context context)
	{
		this(context, null);
	}

	public BaseView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public BaseView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr, 0);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * 构造函数里需要处理的都在这里进行
	 *
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	protected abstract void init(Context context, AttributeSet attrs, int defStyleAttr,
								 int defStyleRes);

	/**
	 * Compare to: {@link android.view.View#getDefaultSize(int, int)}
	 * If mode is AT_MOST, return the child size instead of the parent size
	 * (unless it is too big).
	 *
	 * int result = size;
	 * int specMode = MeasureSpec.getMode(measureSpec);
	 * int specSize = MeasureSpec.getSize(measureSpec);
	 * switch (specMode) {
	 * case MeasureSpec.UNSPECIFIED:
	 * result = size;
	 * break;
	 * case MeasureSpec.AT_MOST:
	 * case MeasureSpec.EXACTLY:
	 * result = specSize;
	 * break;
	 * }
	 * return result;
	 */
	protected static int getDefaultSize2(int size, int measureSpec)
	{
		int result = size;
		int specMode = getMode(measureSpec);
		int specSize = getSize(measureSpec);

		switch (specMode)
		{
			case MeasureSpec.UNSPECIFIED:
				result = size;
				break;
			case MeasureSpec.AT_MOST:
				result = Math.min(size, specSize);
				break;
			case MeasureSpec.EXACTLY:
				result = specSize;
				break;
		}
		return result;
	}
}
