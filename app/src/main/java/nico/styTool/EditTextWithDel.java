package nico.styTool;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * @author tpnet
 * 2016-04-15
 */
public class EditTextWithDel extends AppCompatEditText
{

    private Drawable imgAble;  //有内容时候显示的图片
    private static Context mContext;  //上下文
    public EditTextWithDel(Context context)
    {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 初始
     * **/
    private void init()
    {
        imgAble = mContext.getResources().getDrawable(R.drawable.ic_settings_remote_white_24dp);
        setDrawable();
        addTextChangedListener(new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{}
		@Override
		public void afterTextChanged(Editable s)
		{
		    setDrawable();
		}
	    });
    }

    //设置删除图片
    public void setDrawable()
    {
        if (length() <= 0)
	{
            imgAble.setBounds(0, 0, 0, 0);
            setCompoundDrawables(null, null, imgAble, null);
        }
        else
	//setBounds(x,y,width,height); x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的
            imgAble.setBounds(0, 0, dip2px(22), dip2px(23));
	setCompoundDrawables(null, null, imgAble, null);
	//setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP)
	{
            //获取点击坐标
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            //Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            //获取当前View在屏幕坐标中的可视区域，放到rect
            getGlobalVisibleRect(rect);
            //图标的左右位置
            rect.left = rect.right - imgAble.getIntrinsicWidth() - dip2px(22);

            //判断点击位置是否在 图标上面
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
    }

    //dp=专为px
    public static int dip2px(float dipValue)
    {
        float scale=mContext.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }


}
