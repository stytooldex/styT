package nico.styTool;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * Created by luxin on 15-12-21.
 * 观看鸿洋大神blog   http://blog.csdn.net/lmj623565791/article/details/39474553
 * http://luxin.gitcafe.io
 */
public class LxwImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {


    private static final String TAG = "LxwImageView";

    private boolean mOnce = false;

    private float mInitScale;

    private float mMidScale;

    private float mMaxScale;

    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGestureDetector;


    //-------------------
    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;

    private boolean isCanDrag = false;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //-------------
    private GestureDetector mGestureDetector;

    private boolean isAutoScale = false;

    public LxwImageView(Context context) {
        this(context, null);
    }

    public LxwImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LxwImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "====lxwImageView===");
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) return true;
                float x = e.getX();
                float y = e.getY();
                Log.e(TAG, "===on double scale==");
                if (getScale() < mMidScale) {
                    // mScaleMatrix.postScale(mMidScale / getScale(), mMidScale / getScale(), x, y);
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;

                } else {
                    //   mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;

                }
                setImageMatrix(mScaleMatrix);
                return true;
            }
        });
        setOnTouchListener(this);
        super.setScaleType(ScaleType.MATRIX);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    /**
     * 获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {

            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();

            float scale = 1.0f;

            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }

            if (dh > height && dw > width) {
                scale = Math.min(height * 1.0f / dh, width * 1.0f / dw);
            }

            if (dh < height && dw < width) {
                scale = Math.min(height * 1.0f / dh, width * 1.0f / dw);
            }

            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            int dx = width / 2 - dw / 2;
            int dy = height / 2 - dh / 2;


            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);
            mOnce = true;
        }

    }


    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            Log.e(TAG, "=====onscale====getDrawble==is null=");
            return true;
        }

        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }

            Log.e(TAG, "===onscale====");
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }

        return true;
    }


    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        if (rectF.width() < width) {
            deltaX = width / 2.0f - rectF.right + rectF.width() / 2.0f;
        }
        if (rectF.height() < height) {
            deltaY = height / 2.0f - rectF.bottom + rectF.height() / 2.0f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        if (mGestureDetector.onTouchEvent(event)) {
            Log.e(TAG, "===log====ondowble==touch===");
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        RectF rectFs = getMatrixRectF();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (rectFs.width() > getWidth() + 0.01 || rectFs.height() > getHeight() + 0.01) {
                    // if (getParent() instanceof ViewPager)
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                RectF rectFss = getMatrixRectF();
                if (rectFss.width() > getWidth() + 0.01 || rectFss.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);

                }
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;

        }
        Log.e(TAG, "=====ontouch====");
        return true;
    }

    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltx = 0;
        float delty = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            delty = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            delty = height - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltx = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltx = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltx, delty);

    }

    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }


    private class AutoScaleRunnable implements Runnable {

        private float mTargeScale;
        private float x;
        private float y;
        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float temScale;

        AutoScaleRunnable(float mTargeScale, float x, float y) {
            this.mTargeScale = mTargeScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargeScale) {
                temScale = BIGGER;
            }
            if (getScale() > mTargeScale) {
                temScale = SMALL;
            }
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(temScale, temScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
            float mcurrentScale = getScale();
            if ((temScale > 1.0f) && (mcurrentScale < mTargeScale) || (temScale < 1.0f) && (mcurrentScale > mTargeScale)) {
                postDelayed(this, 16);
            } else {
                float scale = mTargeScale / mcurrentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }
    }
}
