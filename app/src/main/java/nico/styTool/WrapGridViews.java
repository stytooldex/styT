package nico.styTool;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by luxin on 15-12-15.
 * http://luxin.gitcafe.io
 */
public class WrapGridViews extends GridView {

    public WrapGridViews(Context context) {
        super(context);
    }

    public WrapGridViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapGridViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int HeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, HeightSpec);
    }
}
