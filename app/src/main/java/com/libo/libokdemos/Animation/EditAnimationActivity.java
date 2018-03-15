package com.libo.libokdemos.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-15 上午 11:36
 * @e-mail libolf@outlook.com
 * @description 仿Google play的输入框动画
 */
public class EditAnimationActivity extends AppCompatActivity {

    @BindView(R.id.animation_edit)
    EditText mAnimationEdit;
    @BindView(R.id.animation_text)
    TextView mAnimationText;

    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animation);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mAnimationEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mAnimationText.setTextColor(Color.RED);
                if (mAnimatorSet != null) {
                    mAnimatorSet.start();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mAnimationText.setPivotX(0);
            mAnimationText.setPivotY(mAnimationText.getHeight() / 2);
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(
                    ObjectAnimator.ofFloat(mAnimationText, "translationY", -mAnimationText.getHeight()),
                    ObjectAnimator.ofFloat(mAnimationText, "scaleX", 1, 0.75f),
                    ObjectAnimator.ofFloat(mAnimationText, "scaleY", 1, 0.75f)
            );
            mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorSet.setDuration(400);
        }
    }
}
