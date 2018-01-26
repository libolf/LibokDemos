package com.libo.libokdemos.Custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomActivity extends AppCompatActivity {

    private static final String TAG = "CustomActivity";

    @BindView(R.id.animator_text)
    TextView mAnimatorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "222222onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "222222onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(1000).start();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatorText, "alpha", 1.0f, 0.2f);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "111111onAnimationStart: ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "111111onAnimationEnd: ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(1000).start();
    }
}
