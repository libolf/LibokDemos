package com.libo.libokdemos.Custom.CustomView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author 李波
 * @date 2018-03-20 上午 12:44
 * @e-mail libolf@outlook.com
 * @description
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "CustomTextView";

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure width: " + MeasureSpec.getMode(widthMeasureSpec) + " " + MeasureSpec.getSize(widthMeasureSpec));
        Log.e(TAG, "onMeasure height: " + MeasureSpec.getMode(heightMeasureSpec) + " " + MeasureSpec.getSize(heightMeasureSpec));
    }
}
