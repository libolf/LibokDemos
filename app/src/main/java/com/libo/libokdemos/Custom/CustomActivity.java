package com.libo.libokdemos.Custom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.Advanced.MyFragment;
import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-15 下午 04:17
 * @e-mail libolf@outlook.com
 * @description 自定义View的Activity
 */
public class CustomActivity extends AppCompatActivity {

    private static final String TAG = "CustomActivity";
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
//    @BindView(R.id.chrome_refresh)
//    ChromeLikeSwipeLayout chromeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        Utils.transparentNavigationAndStatusBar(this);

    }



    private void init1() {
        //        ChromeLikeSwipeLayout.makeConfig()
//                .addIcon(R.mipmap.ic_mail)
//                .addIcon(R.mipmap.ic_call)
//                .addIcon(R.mipmap.ic_friends)
//                .setMaxHeight(DisplayUtils.dp2px(this, 40))
//                .radius(DisplayUtils.dp2px(this, 35))
//                .listenItemSelected(new ChromeLikeSwipeLayout.IOnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(int i) {
//                        Toast.makeText(CustomActivity.this, "select " + i, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setTo(chromeRefresh);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        hideNavigationAndStatusBar();
    }
}
