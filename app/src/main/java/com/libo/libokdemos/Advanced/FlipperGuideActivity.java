package com.libo.libokdemos.Advanced;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-12 下午 04:02
 * @e-mail libolf@outlook.com
 * @description APP引导页
 */

public class FlipperGuideActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    @BindView(R.id.viewfliper)
    ViewFlipper mViewfliper;
    @BindView(R.id.button_guide_start_app)
    Button mButtonGuideStartApp;

    private GestureDetector mGestureDetector;
    private int index = 0;//当前页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipper_guide);
        ButterKnife.bind(this);

        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 手势滑动
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mViewfliper.setOutAnimation(this, R.anim.viewfliper_out_animation);
        mViewfliper.setInAnimation(this, R.anim.viewfliper_in_animation);
        if (e1.getX() > e2.getX()) {
            mViewfliper.showNext();
            index = index < 6 ? index + 1 : 0;
        } else if (e1.getX() < e2.getX()) {
            mViewfliper.showPrevious();
            index = index > 0 ? index - 1 : 6;
        } else {
            return false;
        }

        return true;
    }
}
