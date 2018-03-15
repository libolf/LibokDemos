package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.libo.libokdemos.Utils.DisplayUtils;

/**
 * alt+shift+M 快速提取方法
 * https://www.jianshu.com/p/d6b4a9ad022e ChromeRefresh
 * http://blog.csdn.net/zjws23786/article/details/51692774 Index
 * http://blog.csdn.net/u010386612/article/details/51372696 通用下拉刷新
 * https://www.cnblogs.com/zhaoyanjun/p/5535651.html RxBinding
 */

public class RefreshLayout extends ViewGroup {

    private static final String TAG = "RefreshLayout";

    private static final float magicNumber = 0.55228475f;
    private static final float magicNumber1 = 0.551784f;

    private Context mContext;

    private int mWidth = 0;
    private int mHeight = 0;

    private PointF[] top = null;
    private PointF[] right = null;
    private PointF[] bottom = null;
    private PointF[] left = null;
    private PointF center;
    private float radius;
    private float longRadius;
    private float shortRadius;
    private float degree = 520;

    private Paint mPaint;
    private Path mPath;

    private View targetView = null;
    private View refreshHeader = null;
    private int activePointerId;
    private boolean isTouch;
    private boolean hasSendCancelEvent;
    private boolean mIsBeginDragged;
    private int currentTargetOffsetTop;
    private int lastTargetOffsetTop;
    private float lastMotionX;
    private float initDownX;
    private float lastMotionY;
    private float initDownY;
    private MotionEvent lastEvent;
    private boolean hasMeasureHeader;
    private int headerHeight;
    private int totalDragDistance;
    private static final float DRAG_RATE = .5f;
    private int touchSlop;
    private float mDownX;
    private float mDownY;
    private float mLastX;
    private float mLastY;
    private float distance;
    private float allDistance;
    private float lastDistance;


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        // 添加默认的头部，先简单的用一个ImageView代替头部
        ImageView imageView = new ImageView(context);
//        imageView.setImageResource(R.drawable.see);
        imageView.setBackgroundColor(Color.TRANSPARENT);
        RefreshView view = new RefreshView(context);
        setRefreshHeader(imageView);
        Log.e(TAG, "111111RefreshView: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "111111onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1080, 2048);
        if (targetView == null) {
            ensureTarget();
        }

        if (targetView == null) {
            throw new NullPointerException("没有刷新子控件");
        }

        // target占满整屏
        targetView.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));


        measureChild(refreshHeader, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeader) { // 防止header重复测量
            hasMeasureHeader = true;
            headerHeight = refreshHeader.getMeasuredHeight(); // header高度
            totalDragDistance = headerHeight;   // 需要pull这个距离才进入松手刷新状态
//            headerHeight = DisplayUtils.dp2px(mContext, 120);
        }
    }

    /**
     * canvas.rotate 简单来看，就是把canvas的坐标系进行旋转
     *
     * @param //canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "111111onDraw: ");
        super.onDraw(canvas);

//        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeWidth(30);
//        canvas.drawCircle(center.x, center.y, radius, mPaint);

//        canvas.translate(mWidth / 2f, mHeight / 2f);
//        if (degree != 520) {
//            canvas.rotate(degree);
//        }
//
//        mPaint.setColor(Color.RED);
//        mPaint.setStrokeWidth(18);
//        canvas.drawPath(mPath, mPaint);

        canvas.drawText("刷新", mWidth/2f, -10, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPath(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || targetView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mLastY = mDownY;
                break;
            case MotionEvent.ACTION_MOVE:

                distance = ev.getY() - mLastY;
                if ((distance < 0 && lastDistance > 0)) {
                    mDownY = ev.getY();
                }
                allDistance = ev.getY() - mDownY;
                Log.e(TAG, "dispatchTouchEvent: " + canChildScrollUp() + " " + allDistance + " " + targetView.getY());
                mLastX = ev.getX();
                mLastY = ev.getY();
                lastDistance = distance;
                //包括已经滑动过
                if (canChildScrollUp()) {
                    return super.dispatchTouchEvent(ev);
                }
                //在最顶部没有进行过滑动
                else {
                    Log.e(TAG, "dispatchTouchEvent: " + canChildScrollUp());
                    if (distance > 0) {//下滑
                        if (distance + targetView.getY() > headerHeight) {
                            distance = headerHeight - targetView.getY();
                            allDistance = headerHeight;
                        }
                        if (allDistance <= headerHeight && allDistance > 0) {
                            moveSpinner(distance);
                        } else if (allDistance > headerHeight) {
                            moveSpinner(headerHeight - targetView.getY());
                        }
                    } else {//上滑
                        if (targetView.getY() == 0.0) {
                            return super.dispatchTouchEvent(ev);
                        } else {
                            moveSpinner(distance);
                        }
                    }
//                    dispatchTouchEvent(ev);
                    return true;
                }

//                if (!canChildScrollUp() && allDistance <= headerHeight && allDistance > 0) {
//                    moveSpinner(distance);
//                    return true;
//                } else if (canChildScrollUp() && allDistance > 0 && targetView.getY() > 0) {
//                    moveSpinner(distance);
//                    return true;
//                } else if (canChildScrollUp() && allDistance > headerHeight) {
//                    return super.dispatchTouchEvent(ev);
//                } else {
//                    return super.dispatchTouchEvent(ev);
//                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                distance = 0;
                Log.e(TAG, "dispatchTouchEvent: " + targetView.getScrollY());
                moveSpinner(-targetView.getY());
                allDistance = 0;
//                targetView.setScrollY(0);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (!isEnabled() || targetView == null) {
//            return super.dispatchTouchEvent(ev);
//        }
//
//        final int actionMasked = ev.getActionMasked(); // support Multi-touch
//        switch (actionMasked) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e(TAG, "ACTION_DOWN");
//                activePointerId = ev.getPointerId(0);
//                isTouch = true;  // 手指是否按下
//                hasSendCancelEvent = false;
//                mIsBeginDragged = false;  // 是否开始下拉
//                lastTargetOffsetTop = currentTargetOffsetTop; // 上一次target的偏移高度
//                currentTargetOffsetTop = targetView.getTop(); // 当前target偏移高度
//                initDownX = lastMotionX = ev.getX(0); // 手指按下时的坐标
//                initDownY = lastMotionY = ev.getY(0);
//                super.dispatchTouchEvent(ev);
//                return true;    // return true，否则可能接收不到move和up事件
//            case MotionEvent.ACTION_MOVE:
//                if (activePointerId == INVALID_POINTER) {
//                    Log.e(TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
//                    return super.dispatchTouchEvent(ev);
//                }
//                lastEvent = ev; // 最后一次move事件
//                float x = ev.getX(MotionEventCompat.findPointerIndex(ev, activePointerId));
//                float y = ev.getY(MotionEventCompat.findPointerIndex(ev, activePointerId));
//                float xDiff = x - lastMotionX;
//                float yDiff = y - lastMotionY;
//                float offsetY = yDiff * DRAG_RATE;
//                lastMotionX = x;
//                lastMotionY = y;
//
//                if (!mIsBeginDragged && Math.abs(y - initDownY) > touchSlop) {
//                    mIsBeginDragged = true;
//                }
//
//                if (mIsBeginDragged) {
//                    boolean moveDown = offsetY > 0; // ↓
//                    boolean canMoveDown = canChildScrollUp();
//                    boolean moveUp = !moveDown;     // ↑
//                    boolean canMoveUp = currentTargetOffsetTop > headerHeight;
//
//                    if (y - initDownY < headerHeight) {
//                        moveSpinner(offsetY);
//                        return true;
//                    }
//
//                    // 判断是否拦截事件
////                    if ((y - initDownY > headerHeight) && ((moveDown && !canMoveDown) || (moveUp && canMoveUp))) {
////                        moveSpinner(offsetY);
////                        return true;
////                    }
//                }
//                break;
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                isTouch = false;
//                activePointerId = INVALID_POINTER;
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN:
//                int pointerIndex = MotionEventCompat.getActionIndex(ev);
//                if (pointerIndex < 0) {
//                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
//                    return super.dispatchTouchEvent(ev);
//                }
//                lastMotionX = ev.getX(pointerIndex);
//                lastMotionY = ev.getY(pointerIndex);
//                activePointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
//                break;
//
//            case MotionEvent.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                lastMotionY = ev.getY(ev.findPointerIndex(activePointerId));
//                lastMotionX = ev.getX(ev.findPointerIndex(activePointerId));
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "111111onLayout: ");
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }

        if (targetView == null) {
            ensureTarget();
        }
        if (targetView == null) {
            throw new NullPointerException("没有刷新子控件");
        }

        // onLayout执行的时候，要让target和header加上偏移距离（初始0），因为有可能在滚动它们的时候，child请求重新布局，从而导致target和header瞬间回到原位。

        // target铺满屏幕
        final View child = targetView;
        Log.e(TAG, "onLayout: " + child.toString());
        final int childLeft = getPaddingLeft();
        final int childTop = (int) (getPaddingTop() + distance);
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        // header放到target的上方，水平居中
        int refreshViewWidth = refreshHeader.getMeasuredWidth();
        refreshHeader.layout((width / 2 - refreshViewWidth / 2),
                (int) (-headerHeight + distance),
                (width / 2 + refreshViewWidth / 2),
                (int) distance);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "111111onSizeChanged: ");
        mWidth = getWidth();
        mHeight = getHeight();
        center.x = 0;//mWidth / 2f;
        center.y = 0;//mHeight / 2f;
        setPath(-1, -1);
        Log.e(TAG, "onSizeChanged: " + mWidth + " " + mHeight);
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
            longRadius = (float) Math.min(DisplayUtils.dp2px(mContext, 120), Math.sqrt(Math.pow(downX - mWidth / 2, 2) + Math.pow(downY - mHeight / 2, 2)));
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

    /**
     * 将第一个View作为目标View
     */
    private void ensureTarget() {
        if (targetView == null) {
            Log.e(TAG, "ensureTarget: " + getChildCount());
            for (int i = 0; i < getChildCount(); i++) {
                Log.e(TAG, "ensureTarget: " + getChildAt(i).toString());
                if (!getChildAt(i).equals(refreshHeader)) {
                    targetView = getChildAt(i);
                    break;
                }
            }
        }
    }

    public void setRefreshHeader(View view) {
        // 为header添加默认的layoutParams
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
            view.setLayoutParams(layoutParams);
        }
        refreshHeader = view;
        addView(refreshHeader);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == activePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastMotionY = ev.getY(newPointerIndex);
            lastMotionX = ev.getX(newPointerIndex);
            activePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (targetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targetView, -1) || targetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targetView, -1);
        }
    }

    private void moveSpinner(float diff) {
        int offset = Math.round(diff);
        if (offset == 0) {
            return;
        }
        // 发送cancel事件给child
        if (!hasSendCancelEvent && isTouch && currentTargetOffsetTop > 1) {
//            sendCancelEvent();
            hasSendCancelEvent = true;
        }

        int targetY = Math.max(0, currentTargetOffsetTop + offset); // target不能移动到小于0的位置……
        offset = targetY - currentTargetOffsetTop;
        setTargetOffsetTopAndBottom(offset);
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        targetView.offsetTopAndBottom(offset);
        refreshHeader.offsetTopAndBottom(offset);
        lastTargetOffsetTop = currentTargetOffsetTop;
        currentTargetOffsetTop = targetView.getTop();
//        invalidate();
    }

    private void sendCancelEvent() {
        if (lastEvent == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(lastEvent);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }
}
