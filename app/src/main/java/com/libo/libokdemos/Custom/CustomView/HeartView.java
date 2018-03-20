package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.libo.libokdemos.Utils.DisplayUtils;

/**
 * @author 李波
 * @date 2018-03-19 下午 04:09
 * @e-mail libolf@outlook.com
 * @description 心形曲线
 * <p>
 * 曲线方程
 * x=a*(2*cos(t)-cos(2*t))
 * y=a*(2*sin(t)-sin(2*t))
 * </P>
 */

public class HeartView extends View {

    private static final String TAG = "HeartView";
    private static final float NUM = 0.8f;

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mActuallyWidth;
    private int mActuallyHeight;
    private Paint mPaint;
    private RectF mRectF;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
//        mPaint.setColor(Color.parseColor("#ff33b5e5"));
        mPaint.setColor(Color.BLACK);
        mRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e(TAG, "measureWidth: " + widthSize + " " + widthMode);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = DisplayUtils.dp2px(mContext, 50);
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = DisplayUtils.dp2px(mContext, 50);
                break;
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "measureHeight: " + heightSize + " " + heightMode);
        int height = 0;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = DisplayUtils.dp2px(mContext, 22);
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = DisplayUtils.dp2px(mContext, 22);
                break;
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mRectF, mRectF.height() / 2f, mRectF.height() / 2f, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        Log.e(TAG, "onSizeChanged: " + NUM / (1 + NUM) + " " + mHeight*1f / mWidth + " " + mWidth + " " + mHeight);
        // 宽度大，高度小，不满足正常的宽高比
        if (mHeight*1f / mWidth < NUM / (1 + NUM)) {
            mActuallyHeight = mHeight;
            mActuallyWidth = (int) (mActuallyHeight / NUM + mActuallyHeight);
        }
        // 宽度小，高度大，不满足正常的宽高比
        else if (mHeight*1f / mWidth > NUM / (1 + NUM)) {
            mActuallyWidth = mWidth;
            mActuallyHeight = (int) (mActuallyWidth * NUM / (1f + NUM));
        }
        // 满足宽高比
        else if ((mHeight*1f / mWidth) - (NUM / (1 + NUM)) < 0.01f) {
            mActuallyWidth = mWidth;
            mActuallyHeight = mHeight;
        }

        mRectF.left = (mWidth - mActuallyWidth) / 2;
        mRectF.top = (mHeight - mActuallyHeight) / 2;
        mRectF.right = mRectF.left + mActuallyWidth;
        mRectF.bottom = mRectF.top + mActuallyHeight;
        Log.e(TAG, "onSizeChanged: " + mRectF.toString());
    }
}
