package com.libo.libokdemos.Advanced;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.libo.libokdemos.Custom.CustomActivity;
import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-15 下午 03:03
 * @e-mail libolf@outlook.com
 * @description APP引导页——页面不动，子控件动
 */
public class ViewPagerGuideActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerGuideActivity";

    @BindView(R.id.guide_viewpager)
    ViewPager mGuideViewpager;

    private int[] imageRes = {
            R.drawable.image10, R.drawable.image2,
            R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6,
    };
    private String[] mTitleString = {
            "每", "天",
            "都", "是",
            "晴", "天",
    };
    private String[] mContentString = {
            "V 1.1", "V 1.2",
            "V 1.3", "V 1.4",
            "V 1.5", "V 1.6",
    };
    private ArrayList<MyFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.transparentNavigationAndStatusBar(this);
        setContentView(R.layout.activity_view_pager_guide);
        ButterKnife.bind(this);

        prepareData();
        init();
    }

    private void init() {

        mGuideViewpager.setPageTransformer(true, new FadeInOutTransformer());
        mGuideViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }
        });

    }

    private void prepareData() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < imageRes.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title", mTitleString[i]);
            bundle.putString("content", mContentString[i]);
            bundle.putInt("imageRes", imageRes[i]);
            bundle.putInt("index", i);
            MyFragment fragment = MyFragment.newInstance(bundle);
            mFragmentList.add(fragment);
        }
    }

    private class FadeInOutTransformer implements ViewPager.PageTransformer {

        private float pageWidth;
        private float MIN_ALPHA = 0.5f;
        TextView mTextTitle = null;
        TextView mTextContent = null;
        ImageView mImageLogo = null;

        @Override
        public void transformPage(@NonNull View page, float position) {

            mTextTitle = page.findViewById(R.id.fragment_text_title);
            mTextContent = page.findViewById(R.id.fragment_text_content);
            mImageLogo = page.findViewById(R.id.fragment_image_logo);
            Log.e(TAG, "transformPage: " + mTextTitle.toString());

            pageWidth = page.getWidth();

            float alphaFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position));

            // 前一页
            if (position < 0 && position >= -1) {
                page.setTranslationX(-pageWidth * position);  //阻止消失页面的滑动
                mTextTitle.setTranslationX(200 * position);
                mTextContent.setTranslationX(200 * position);
                mImageLogo.setTranslationX(200 * position);
                //透明度改变Log
                page.setAlpha(1 + position);
            }
            // 目标滑动页
            else if (position < 1 && position >= 0) {
                page.setTranslationX(pageWidth);        //直接设置出现的页面到底
                page.setTranslationX((-pageWidth) * position);  //阻止出现页面的滑动
                mTextTitle.setTranslationX((200) * position);
                mTextContent.setTranslationX((200) * position);
                mImageLogo.setTranslationX((200) * position);
                //透明度改变Log
                page.setAlpha(1 - position);
            }
            //其他
            else {
                page.setAlpha(0);
            }

        }
    }
}
