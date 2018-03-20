package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.libo.libokdemos.Utils.DisplayUtils;

/**
 * @author 李波
 * @date 2018-03-20 下午 11:40
 * @e-mail libolf@outlook.com
 * @description <p>
 * @see // https://www.cnblogs.com/Sharley/p/5783342.html
 * @see // http://blog.csdn.net/decting/article/details/8580634
 * 曲线方程
 * x=a*(2*cos(t)-cos(2*t))
 * y=a*(2*sin(t)-sin(2*t))
 * </P>
 */

public class NewHeartView extends View {

    private static final String TAG = "NewHeartView";

    private static final int what = 520; // 0 - 360 右上角开始
    private static final int what1 = 521; // 360 - 0 左上角开始
    private static final int what2 = 519; // 180 - -180 右下角开始
    private static final int what3 = 522; // -180 - 180 左下角开始

    private int mWidth;
    private int mHeight;
    private Point mCenterPoint;
    private PointF mPointF;

    private Context mContext;
    private Paint mPaint;
    private double mRadians;

    private int mAngel = 0;
    private int mCurrentWhat = what3;
    private int mDuration = 121;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == what) {
                if (mAngel + 5 <= 360) {
                    mAngel += 5;
                    invalidate();
                    this.sendMessageDelayed(this.obtainMessage(mCurrentWhat), mDuration);
                }
                if (mAngel == 360) {
                    this.removeMessages(mCurrentWhat);
                }
            } else if (msg.what == what1) {
                if (mAngel - 5 >= 0) {
                    mAngel -= 5;
                    invalidate();
                    this.sendMessageDelayed(this.obtainMessage(mCurrentWhat), mDuration);
                }
                if (mAngel == 0) {
                    this.removeMessages(mCurrentWhat);
                }

            } else if (msg.what == what2) {
                if (mAngel - 5 >= -180) {
                    mAngel -= 5;
                    invalidate();
                    this.sendMessageDelayed(this.obtainMessage(mCurrentWhat), mDuration);
                }
                if (mAngel == -180) {
                    this.removeMessages(mCurrentWhat);
                }
            } else if (msg.what == what3) {
                if (mAngel + 5 <= 180) {
                    mAngel += 5;
                    invalidate();
                    this.sendMessageDelayed(this.obtainMessage(mCurrentWhat), mDuration);
                }
                if (mAngel == 180) {
                    this.removeMessages(mCurrentWhat);
                }
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAngel + 5 <= 360) {
                mAngel += 5;
                invalidate();
            }
        }
    };

    public NewHeartView(Context context) {
        this(context, null);
    }

    public NewHeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mCenterPoint = new Point();
        mPointF = new PointF();
        if (mCurrentWhat == what) {
            mAngel = 0;
        } else if (mCurrentWhat == what1) {
            mAngel = 360;
        } else if (mCurrentWhat == what2) {
            mAngel = 180;
        } else if (mCurrentWhat == what3) {
            mAngel = -180;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(DisplayUtils.dp2px(mContext, 360), DisplayUtils.dp2px(mContext, 360));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw: " + mAngel);
        if (mCurrentWhat == what) {
            for (int i = 0; i <= mAngel; i += 5) {
                drawHeart(canvas, i);
            }
            if (mAngel == 0) {
                mHandler.sendMessage(mHandler.obtainMessage(mCurrentWhat));
            }
        } else if (mCurrentWhat == what1) {
            for (int i = 360; i >= mAngel; i -= 5) {
                drawHeart(canvas, i);
            }
            if (mAngel == 360) {
                mHandler.sendMessage(mHandler.obtainMessage(mCurrentWhat));
            }
        } else if (mCurrentWhat == what2) {
            for (int i = 180; i >= mAngel; i -= 5) {
                drawHeart(canvas, i);
            }
            if (mAngel == 180) {
                mHandler.sendMessage(mHandler.obtainMessage(mCurrentWhat));
            }
        } else if (mCurrentWhat == what3) {
            for (int i = -180; i <= mAngel; i += 5) {
                drawHeart(canvas, i);
            }
            if (mAngel == -180) {
                mHandler.sendMessage(mHandler.obtainMessage(mCurrentWhat));
            }
        }
//        if (mAngel <= 360) {
//            mHandler.postDelayed(mRunnable, 1000);
//        } else {
//            mHandler.removeCallbacks(mRunnable);
//        }

    }

    private void drawHeart(Canvas canvas, int angel) {
        mRadians = angel / 180d * Math.PI;
        mPointF.x = (float) (16 * Math.pow(Math.sin(mRadians), 3));
        mPointF.y = (float) (13 * Math.cos(mRadians) - 5 * Math.cos(2 * mRadians) - 2 * Math.cos(3 * mRadians) - Math.cos(4 * mRadians));
        canvas.drawCircle(mPointF.x * 20f + mWidth / 2f, -20 * mPointF.y + mHeight / 2f, DisplayUtils.dp2px(mContext, 4), mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mCenterPoint.x = mWidth / 2;
        mCenterPoint.y = mHeight / 2;
    }
}
