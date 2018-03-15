package com.libo.libokdemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * @author 李波
 * @date 2018-03-15 上午 11:11
 * @e-mail libolf@outlook.com
 * @description
 * 自定义Activity基类
 * 功能有 网络检测、沉浸式
 *
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
