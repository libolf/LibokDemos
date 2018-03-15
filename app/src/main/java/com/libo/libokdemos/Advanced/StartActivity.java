package com.libo.libokdemos.Advanced;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-12 下午 04:05
 * @e-mail libolf@outlook.com
 * @description APP启动页
 */

public class StartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Utils.transparentNavigationAndStatusBar(this);
    }
}