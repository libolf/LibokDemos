package com.libo.libokdemos.Custom.CustomViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.Advanced.MyFragment;
import com.libo.libokdemos.Custom.CustomActivity;
import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.DisplayUtils;
import com.libo.libokdemos.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李波
 * @date 2018-03-15 下午 04:14
 * @e-mail libolf@outlook.com
 * @description 各种ViewPager切换动画
 * @see //各种效果合集 https://juejin.im/entry/59ca14386fb9a00a67615bdb
 */
public class ViewPagerTransformActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerTransformActiv";

    @BindView(R.id.transform_toolbar)
    Toolbar mTransformToolbar;
    @BindView(R.id.transform_viewpager)
    ViewPager mTransformViewpager;
    @BindView(R.id.transform_linear)
    LinearLayout mTransformLinear;

    private int[] imageRes = {
            R.drawable.image10, R.drawable.image2,
            R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6,
    };
    private String[] mStrings = {"image 1", "image 2", "image 3", "image 4", "image 5", "image 6"};
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
    private List<MyFragment> mFragmentList;
    private List<ImageView> mImageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.transparentNavigationAndStatusBar(this);
        setContentView(R.layout.activity_view_pager_transform);
        ButterKnife.bind(this);
        setSupportActionBar(mTransformToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepareData();
        init();
    }

    private void init() {
        resetWidthAndHeight();
        mTransformViewpager.setPageTransformer(false, new FadeInOutTransformer());
        mTransformViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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


    /**
     * Flyme模式的准备工作
     */
    private void initFlyme() {
        setViewPagerMargin(60);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = mImageViewList.get(position);
                Log.e(TAG, "instantiateItem: " + imageView.getHeight());
                if (imageView != null) {
                    ViewGroup parent = (ViewGroup) imageView.getParent();
                    if (parent != null) {
                        parent.removeView(imageView);
                    }
                }
                container.addView(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return imageRes.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //不能在此方法删除View
//                if (container.getChildAt(position) != null) {
//                    container.removeViewAt(position);
//                }
            }
        };

//        mViewpager.setPageMargin(-40);
        mTransformViewpager.setOffscreenPageLimit(3);
        mTransformViewpager.setAdapter(pagerAdapter);

        mTransformViewpager.setPageTransformer(true, new MyPageTransformer());

        mTransformLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch: " + event.getX() + " " + v.toString());
                if (event.getX() <= DisplayUtils.dp2px(ViewPagerTransformActivity.this, 60) && mTransformViewpager.getCurrentItem() != 0) {
                    View previousView = mTransformViewpager.getChildAt(mTransformViewpager.getCurrentItem() - 1);
                    return previousView.onTouchEvent(event);
                } else if (v.getWidth() - event.getX() <= DisplayUtils.dp2px(ViewPagerTransformActivity.this, 60) && mTransformViewpager.getCurrentItem() != mTransformViewpager.getChildCount() - 1) {
                    View lastView = mTransformViewpager.getChildAt(mTransformViewpager.getCurrentItem() + 1);
                    return lastView.onTouchEvent(event);
                } else {
                    return mTransformViewpager.onTouchEvent(event);
                }
            }
        });


    }

    private void prepareFlymeData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < imageRes.length; i++) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.width = LayoutParams.MATCH_PARENT;
//            layoutParams.height = 100;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), imageRes[i], options);
            options.inSampleSize = 2;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageRes[i], options);
            imageView.setImageBitmap(bitmap);
            imageView.setBackgroundResource(R.drawable.image_background);
            Log.e(TAG, "prepareData1: " + imageView.toString());
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewPagerTransformActivity.this, mStrings[finalI], Toast.LENGTH_SHORT).show();
                }
            });
            mImageViewList.add(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpager_transform_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.transform_hide:
                
                break;
            case R.id.transform_flyme:
                if (item.getTitle().equals("Flyme")) {
                    mTransformViewpager.setAdapter(null);
                    prepareFlymeData();
                    initFlyme();
                    item.setTitle("Hide");
//                    item.setVisible(false);
                } else if (item.getTitle().equals("Hide")) {
                    prepareData();
                    init();
                    item.setTitle("Flyme");
                }
                break;
            case R.id.transform_abc:
                Toast.makeText(this, "ABC", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetWidthAndHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTransformViewpager.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mTransformViewpager.setLayoutParams(layoutParams);
    }
    
    private void setViewPagerMargin(int m) {
        int margin = DisplayUtils.dp2px(this, m);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTransformViewpager.getLayoutParams();
        layoutParams.setMargins(margin, 0, margin, 0);
        layoutParams.height = DisplayUtils.dp2px(this, 200);
        mTransformViewpager.setLayoutParams(layoutParams);
    }
    
    /**
     * 仿Flyme应用商店模式
     */
    private class MyPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE_Y = 0.75f;
        private static final float MAX_SCALE_Y = 1.0f;
        private static final float MIN_SCALE_X = 1.0f;
        private static final float MAX_SCALE_X = 1.1f;
        private static final float MIN_ELEVATION = -5.0f;
        private static final float MAX_ELEVATION = 5.0f;

        /**
         * 每个页面的动作都会回调此函数，因此当从第0页到第1页滑动时，是两个一起调用此方法的
         *
         * @param page     当前调用此方法的View
         * @param position 当前调用此方法的View的翻页进度
         *                 当前页在静止时position为0往后的页面在前一页的基础上+1，初始化时是只有当前页和预加载页，0和1
         *                 往后翻position都是不断减小的，反之都是不断增大的
         *                 从第0页往第1页翻时，第0页的position在不断减小直至-1，第1页的position也是不断减小直至0，所以当前页是第1页
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void transformPage(@NonNull View page, float position) {
//            Log.e(TAG, "transformPage: " + position + " " + page.toString());
            // 滑动目标页
            if (position < 1 && position >= 0) {
//                Log.e(TAG, "目标页: " + position + " " + page.toString());
                if (position >= 0.5) {//1-0.5
                    page.setScaleX((position - 1) * 2 * (MAX_SCALE_X - 1) + MAX_SCALE_X);//1.25-1
                } else {//0.5-0
                    page.setScaleX((0.5f - position) * 2 * (MAX_SCALE_X - 1) + MIN_SCALE_X);//1-1.25
                }
                page.setScaleY((1 - position) * (1 - MIN_SCALE_Y) + MIN_SCALE_Y);
                page.setElevation(position * MIN_ELEVATION);
            }
            // 前一页
            else if (position < 0 && position >= -1) {
//                Log.e(TAG, "前一页: " + position + " " + page.toString());
                if (position >= -0.5) {//0- -0.5
                    page.setScaleX(position * 2 * (MAX_SCALE_X - 1) + MAX_SCALE_X);//1.25-1
                } else {//-0.5- -1
                    page.setScaleX((0.5f + position) * (-2) * (MAX_SCALE_X - 1) + MIN_SCALE_X);//1-1.25
                }
                page.setScaleY(position * (1 - MIN_SCALE_Y) + MAX_SCALE_Y);
                page.setElevation(position * MAX_ELEVATION);
            }
            // 后一页
            else if (position < 2 && position >= 1) {
//                Log.e(TAG, "后一页: " + position + " " + page.toString());
                page.setScaleX(MAX_SCALE_X);
                page.setScaleY(MIN_SCALE_Y);
                page.setElevation(MIN_ELEVATION);
            } else if (position >= 2) {
                page.setScaleX(MAX_SCALE_X);
                page.setScaleY(MIN_SCALE_Y);
                page.setElevation(MIN_ELEVATION);
            } else if (position < -2) {
                page.setScaleX(MAX_SCALE_X);
                page.setScaleY(MIN_SCALE_Y);
                page.setElevation(MIN_ELEVATION);
            }
        }
    }

    /**
     * 淡入淡出模式
     */
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
