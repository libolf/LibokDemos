package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.libo.libokdemos.Utils.DisplayUtils;

/**
 * Created by 李波 on 2018-03-05.
 */

public class RefreshView extends View {

    private static final String TAG = "RefreshView";

    private static final float magicNumber = 0.55228475f;
    private static final float magicNumber1 = 0.551784f;

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Path mPath;
    private PointF[] top;
    private PointF[] bottom;
    private PointF[] right;
    private PointF[] left;
    private PointF center;
    private int radius;
    private int longRadius;
    private int shortRadius;
    private float degree = 520;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        top = new PointF[3];
        top[0] = new PointF();
        top[1] = new PointF();
        top[2] = new PointF();

        bottom = new PointF[3];
        bottom[0] = new PointF();
        bottom[1] = new PointF();
        bottom[2] = new PointF();

        right = new PointF[3];
        right[0] = new PointF();
        right[1] = new PointF();
        right[2] = new PointF();

        left = new PointF[3];
        left[0] = new PointF();
        left[1] = new PointF();
        left[2] = new PointF();

        center = new PointF();

        radius = DisplayUtils.dp2px(context, 60);
        longRadius = DisplayUtils.dp2px(context, 120);
        shortRadius = DisplayUtils.dp2px(context, 48);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(20);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2f, mHeight / 2f);
        if (degree != 520) {
            canvas.rotate(degree);
        }

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(18);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPath(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: move move move");
                setPath(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPath(-1, -1);
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        center.x = 0;//mWidth / 2f;
        center.y = 0;//mHeight / 2f;
        radius = Math.min(mWidth, mHeight) * 3 / 5 / 2;
        longRadius = radius * 6 / 5 / 2;
        shortRadius = radius * 4 / 5 / 2;
        setPath(-1, -1);

    }

    /**
     * Touch事件传过来的坐标与canvas的坐标无关，所以测算半径还是以屏幕中心为准
     *
     * @param downX
     * @param downY
     */
    private void setPath(float downX, float downY) {
//        Log.e(TAG, "setPath: " + downX + " " + downY);
        if (downX == -1 && downY == -1) {
            longRadius = shortRadius = radius;
        } else {
            longRadius = (int) Math.min(DisplayUtils.dp2px(mContext, 120), Math.sqrt(Math.pow(downX - mWidth / 2, 2) + Math.pow(downY - mHeight / 2, 2)));
            shortRadius = Math.min(radius - 5 * longRadius / radius, DisplayUtils.dp2px(mContext, 48));
            degree = (float) Math.toDegrees(Math.atan2(downY - mHeight / 2f, downX - mWidth / 2f));
//            Log.e(TAG, "setPath: " + degree);
            Log.e(TAG, "setPath: " + longRadius + " " + shortRadius);
        }
        top[0].x = center.x;
        top[0].y = center.y - radius;
        top[1].x = center.x + longRadius * magicNumber1;
        top[1].y = center.y - radius;
        top[2].x = center.x + longRadius;
        top[2].y = center.y - radius * magicNumber1;

        right[0].x = center.x + longRadius;
        right[0].y = center.y;
        right[1].x = center.x + longRadius;
        right[1].y = center.y + radius * magicNumber1;
        right[2].x = center.x + longRadius * magicNumber1;
        right[2].y = center.y + radius;

        bottom[0].x = center.x;
        bottom[0].y = center.y + radius;
        bottom[1].x = center.x - shortRadius * magicNumber1;
        bottom[1].y = center.y + radius;
        bottom[2].x = center.x - shortRadius;
        bottom[2].y = center.y + radius * magicNumber1;

        left[0].x = center.x - shortRadius;
        left[0].y = center.y;
        left[1].x = center.x - shortRadius;
        left[1].y = center.y - radius * magicNumber1;
        left[2].x = center.x - shortRadius * magicNumber1;
        left[2].y = center.y - radius;

        mPath.reset();
        mPath.moveTo(top[0].x, top[0].y);
        mPath.cubicTo(top[1].x, top[1].y, top[2].x, top[2].y, right[0].x, right[0].y);
        mPath.cubicTo(right[1].x, right[1].y, right[2].x, right[2].y, bottom[0].x, bottom[0].y);
        mPath.cubicTo(bottom[1].x, bottom[1].y, bottom[2].x, bottom[2].y, left[0].x, left[0].y);
        mPath.cubicTo(left[1].x, left[1].y, left[2].x, left[2].y, top[0].x, top[0].y);

        invalidate();
    }
}
