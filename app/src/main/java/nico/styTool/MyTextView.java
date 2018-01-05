package nico.styTool;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by 26526 on 2017/12/27.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeTypeFace(context, attrs);
    }

    /**
     * 改变字体类型
     *
     * @param context
     * @param attrs
     */
    private void changeTypeFace(Context context, AttributeSet attrs) {
        if (attrs != null) {
            //TypedArray a = context.obtainStyledAttributes(attrs,
            //R.styleable.TextView_Typefaces);
            //            tf = a.getInt(R.styleable.TextView_Typefaces_tf, tf);
            Typeface mtf = Typeface.createFromAsset(context.getAssets(),"bauhaus.ttf");
            super.setTypeface(mtf);
        }
    }
}