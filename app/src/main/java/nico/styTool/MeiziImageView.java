package nico.styTool;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by luxin on 16-1-1.
 */
public class MeiziImageView extends android.support.v7.widget.AppCompatImageView {
    private int originalWidth;
    private int originalHeight;


    public MeiziImageView(Context context) {
        super(context);
    }

    public MeiziImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeiziImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOriginalSize(int originalwidth, int originalheight) {
        this.originalWidth = originalwidth;
        this.originalHeight = originalheight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = (float) originalWidth / (float) originalHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (int) ((float) width / ratio);
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
