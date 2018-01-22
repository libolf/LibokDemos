package com.libo.libokdemos.Custom.CustomView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.libo.libokdemos.Custom.CustomUtil.Angle;
import com.libo.libokdemos.Custom.CustomUtil.AngleEvaluator;
import com.libo.libokdemos.Utils.DisplayUtils;

import java.lang.reflect.Field;

/**
 * Created by libok on 2018-01-19.
 */

public class TestView extends View {

    private static final String TAG = "TestView";

    private Context mContext;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mCenterX;
    private float mCenterY;
    private RectF mArcRectF;
    private int sweepAngle;
    private int sweepStart;

    private int mNumber;

    private Handler mHandler;
    private Angle mAngle;
    private Angle currentAngle;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "run: ");
            if (sweepAngle > 0) {

                sweepAngle -= (360 / mNumber);
                startMyAnimation(270, sweepAngle + (360 / mNumber), sweepAngle);
//                invalidate();
            }
        }
    };

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);

        mArcRectF = new RectF();

        sweepAngle = 360;
        sweepStart = 360;

        mHandler = new Handler();

        mNumber = 5;

        mAngle = new Angle(270, 360);
//        currentAngle = new Angle(270, 360);
    }

    public void setNumber(int number) {
        mNumber = number;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e(TAG, "measureWidth: " + specSize);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200; //DisplayUtils.dp2px(mContext, 200);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "measureHeight: " + specSize);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 100; //DisplayUtils.dp2px(mContext, 200);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e(TAG, "onDraw: " + currentAngle.getSweepAngle());
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, 20, mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
//        canvas.drawArc(mArcRectF, mAngle.getStartAngle(), currentAngle.getSweepAngle(), false, mPaint);
//        if (currentAngle == null || currentAngle.getSweepAngle() % (360/mNumber) == 0) {
//            Log.e(TAG, "onDraw: Runnable = " + currentAngle.getSweepAngle() % (360/mNumber));
//            mHandler.removeCallbacks(mRunnable);
//            mHandler.post(mRunnable);
//        }
        if (currentAngle == null) {
            startMyAnimation(mAngle.getStartAngle(), mAngle.getSweepAngle(), mAngle.getSweepAngle() - mAngle.getSweepAngle() / mNumber);
//            canvas.drawArc(mArcRectF, mAngle.getStartAngle(), currentAngle.getSweepAngle(), false, mPaint);
        } else {
            canvas.drawArc(mArcRectF, mAngle.getStartAngle(), currentAngle.getSweepAngle(), false, mPaint);
        }
    }

    private void startMyAnimation(int start, int sweepStart, int sweepEnd) {
        Log.e(TAG, "startMyAnimation: start = " + sweepStart + " end = " + sweepEnd);
        Angle startAngle = new Angle(start, sweepStart);
        Angle endAngle = new Angle(start, sweepEnd);
        ValueAnimator animator = ValueAnimator.ofObject(new AngleEvaluator(), startAngle, endAngle);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (Angle) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate: " + animation.getAnimatedFraction() + " " + currentAngle.getSweepAngle());
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentAngle != null && currentAngle.getSweepAngle() > 0) {
                    startMyAnimation(currentAngle.getStartAngle(), currentAngle.getSweepAngle(), currentAngle.getSweepAngle() - mAngle.getSweepAngle() / mNumber);
                }
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(1000);
        Log.e(TAG, "startMyAnimation: " + animator.getDuration());
        animator.start();
        try {
            Class c = Class.forName(ValueAnimator.class.getName());
            Field field = c.getDeclaredField("sDurationScale");
            field.setAccessible(true);
            Log.e(TAG, "startMyAnimation: " + field.getFloat(animator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;

        mArcRectF.left = mCenterX - 200;
        mArcRectF.top = mCenterY - 200;
        mArcRectF.right = mCenterX + 200;
        mArcRectF.bottom = mCenterY + 200;
    }
}
