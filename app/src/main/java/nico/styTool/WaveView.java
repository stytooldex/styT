package nico.styTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Wave View
 */
public class WaveView extends BaseView {

  private Paint mPaint;
  private Path mPath;
  private float mTheta = 0f;

  public WaveView(Context context) {
    super(context);
  }

  public WaveView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 拉勾绿
    mPaint.setColor(Color.argb(255, 32, 202, 119));
    mPath = new Path();
  }

  @Override protected void onDraw(Canvas canvas) {

    // 振幅
    int amplitude = 20;

    int height = getHeight();
    // 波长
    int width = getWidth();
    int index = 0;

    // while (index < width) {
    // float endY = (float) (Math.sin((float) index / (float) width * 2f * Math.PI + mTheta) * (float) amplitude + height - amplitude);
    // canvas.drawLine(index, 0, index, endY, mPaint);
    // index++;
    // }

    mPath.reset();
    mPath.moveTo(0, 0);
    while (index <= width) {
      float endY = (float) (Math.sin((float) index / (float) width * 2f * Math.PI + mTheta)
          * (float) amplitude + height - amplitude);
      mPath.lineTo(index, endY);
      // Log.e("xxx", String.format("(%.4f, %.4f)", (float) index, endY));
      index++;
    }
    mPath.lineTo(index - 1, 0);
    mPath.close();

    canvas.drawPath(mPath, mPaint);

    mTheta += 0.1;
    if (mTheta >= 2f * Math.PI) {
      mTheta -= (2f * Math.PI);
    }
    postInvalidateDelayed(80);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
