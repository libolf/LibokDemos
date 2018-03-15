package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 李波 on 2018-03-03.
 */

public class BaseView extends View {

    protected String TAG;
    protected int mWidth;
    protected int mHeight;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        TAG = getClass().getSimpleName();
        Log.e(TAG, "onSizeChanged: " + mWidth + " " + mHeight);
    }
}
