package nico.styTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


public class SunBabyLoadingView extends View
{
    private static final float RATIO_LINE_START_X = 5 / 6.f;
    private static final float RATIO_LINE_START_Y = 3 / 4.f;
    private static final float RATIO_ARC_START_X = 2 / 5.f;
    private static final float SUNSHINE_SEPARATIO_ANGLE = 45;
    private static final String BG_COLOR = "nico.stytool.妮哩";
    private static final float SUNSHINE_LINE_LENGTH = 15;
    private static final float SUN_EYES_RADIUS = 6;
    private static final int DEFAULT_OFFSET_Y = 20;
    private float lineStartX, lineStartY, lineLength;
    private float textX, textY;
    private float sunRadius;
    private double sunshineStartX, sunshineStartY, sunshineStopX, sunshineStopY;
    private float maxEyesTurn;
    private float turnOffsetX;

    private boolean isDrawEyes = true;

    private float offsetY = DEFAULT_OFFSET_Y, offsetSpin, offsetAngle;

    private TextPaint mTextPaint;

    private RectF rectF;

    private float SPACE_SUNSHINE;

    private Paint mPaint;

    private Paint sunPaint;

    private Paint eyePaint;


    public SunBabyLoadingView(Context context)
    {
        this(context, null);
    }

    public SunBabyLoadingView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SunBabyLoadingView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setBackgroundColor(Color.parseColor(BG_COLOR));
    }

    

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();

        // 初始化地平线长度
        lineLength = width * RATIO_LINE_START_X;

        // 初始化地平线起始坐标X,Y值
        lineStartX = (width - lineLength) * .5f;
        lineStartY = height * RATIO_LINE_START_Y;

        // 计算文字的坐标X,Y值
        textX = width * .5f;
        textY = lineStartY + (height - lineStartY) * .5f + Math.abs(mTextPaint.descent() + mTextPaint.ascent()) * .5f;

        // 计算太阳圆圈的半径
        sunRadius = (lineLength - lineLength * RATIO_ARC_START_X) * .5f;

        // 计算两眼之间的距离，也是眼睛平移的最大距离
        maxEyesTurn = (sunRadius + sunPaint.getStrokeWidth() * .5f) * .5f;

        calcAndSetRectPoint();
        calcOffsetAngle();
    }

    /**
     * 计算由于太阳升起或者落下偏移Y值所对应对的角度
     */
    private void calcOffsetAngle()
    {
        offsetAngle = (float) (Math.asin(offsetY / sunRadius) * 180 / Math.PI);
    }

    /**
     * 计算太阳圆弧的外轮廓矩形区域顶点坐标值, 并设置给rectF
     */
    private void calcAndSetRectPoint()
    {
        float rectLeft = lineStartX + lineLength * .5f - sunRadius;
        float rectTop = lineStartY - sunRadius + offsetY;
        float rectRight = lineLength - rectLeft + 2 * lineStartX;
        float rectBottom = rectTop + 2 * sunRadius;

        rectF.set(rectLeft, rectTop, rectRight, rectBottom);
    }

    /**
     * 初始化动画驱动
     */
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawLine(lineStartX, lineStartY, lineStartX + lineLength, lineStartY, mPaint);
        canvas.drawArc(rectF, -180 + offsetAngle, 180 - offsetAngle * 2, false, sunPaint);

        if (isDrawEyes)
            drawSunEyes(canvas);

        drawSunshine(canvas);
    }

    private void drawSunshine(Canvas canvas)
    {
        for (int a = 0; a <= 360; a += SUNSHINE_SEPARATIO_ANGLE)
	{
            sunshineStartX = Math.cos(Math.toRadians(a + offsetSpin)) * (sunRadius + SPACE_SUNSHINE + sunPaint.getStrokeWidth()) + getWidth() * .5f;
            sunshineStartY = Math.sin(Math.toRadians(a + offsetSpin)) * (sunRadius + SPACE_SUNSHINE + sunPaint.getStrokeWidth()) + offsetY + lineStartY;

            sunshineStopX = Math.cos(Math.toRadians(a + offsetSpin)) * (sunRadius + SPACE_SUNSHINE + SUNSHINE_LINE_LENGTH + sunPaint.getStrokeWidth()) + getWidth() * .5f;
            sunshineStopY = Math.sin(Math.toRadians(a + offsetSpin)) * (sunRadius + SPACE_SUNSHINE + SUNSHINE_LINE_LENGTH + sunPaint.getStrokeWidth()) + offsetY + lineStartY;
            if (sunshineStartY <= lineStartY && sunshineStopY <= lineStartY)
	    {
                canvas.drawLine((float) sunshineStartX, (float) sunshineStartY, (float) sunshineStopX, (float) sunshineStopY, mPaint);
            }
        }
    }

    private void drawSunEyes(Canvas canvas)
    {
        float lcx = getWidth() * .5f - (sunRadius + sunPaint.getStrokeWidth() * .5f) * .5f + turnOffsetX;
        float lcy = lineStartY + offsetY - SUN_EYES_RADIUS;

        if (lcy + SUN_EYES_RADIUS >= lineStartY) return ;

        float rcx = getWidth() * .5f + turnOffsetX;
        float rcy = lcy;

        canvas.drawCircle(lcx, lcy, SUN_EYES_RADIUS, eyePaint);
        canvas.drawCircle(rcx, rcy, SUN_EYES_RADIUS, eyePaint);
    }

}//
