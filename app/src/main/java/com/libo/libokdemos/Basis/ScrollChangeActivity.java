package com.libo.libokdemos.Basis;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2018-01-08 自定义Behavior http://blog.csdn.net/tabolt/article/details/51821933    BottomSheetBehavior
//  http://www.ymapk.com/thread-22-1-1.html66
public class ScrollChangeActivity extends AppCompatActivity {

    private static final String TAG = "ScrollChangeActivity";

    @BindView(R.id.scroll_tool)
    Toolbar mScrollTool;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
            this.getWindow().setStatusBarColor(Color.RED);
        }
        setContentView(R.layout.activity_scroll_change);
        ButterKnife.bind(this);

        setSupportActionBar(mScrollTool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "onOffsetChanged: " + verticalOffset);
                if (verticalOffset <= -355 &&Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Toast.makeText(ScrollChangeActivity.this, "color", Toast.LENGTH_SHORT).show();
                    getWindow().setStatusBarColor(Color.BLACK);
                }
            }
        });
    }
}
