package com.libo.libokdemos.Basis;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollChangeActivity extends AppCompatActivity {

    @BindView(R.id.scroll_tool)
    Toolbar mScrollTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
        }
        setContentView(R.layout.activity_scroll_change);
        ButterKnife.bind(this);

        setSupportActionBar(mScrollTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
