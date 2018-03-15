package com.libo.libokdemos.Advanced;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libo.libokdemos.R;

/**
 * @author 李波
 * @date 2018-03-13 下午 12:22
 * @e-mail libolf@outlook.com
 * @description 引导页 Fragment
 */

public class MyFragment extends Fragment {

    private View mView;
    private Bundle mArguments;
    private String mTitle;
    private String mContent;
    private int mImageRes;
    private ImageView mImageLogo;
    private TextView mTextContent;
    private TextView mTextTitle;
    private RelativeLayout mRelative;

    private int[] color = {
            Color.RED, Color.GREEN,
            Color.YELLOW, Color.BLUE,
            Color.GRAY, Color.CYAN
    };
    private int mIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.my_fragment_layout, null);

        mArguments = getArguments();
        if (mArguments != null) {
            mTitle = mArguments.getString("title");
            mContent = mArguments.getString("content");
            mImageRes = mArguments.getInt("imageRes");
            mIndex = mArguments.getInt("index");
        }

        mRelative = mView.findViewById(R.id.fragment_relative);
        mTextTitle = mView.findViewById(R.id.fragment_text_title);
        mTextContent = mView.findViewById(R.id.fragment_text_content);
        mImageLogo = mView.findViewById(R.id.fragment_image_logo);

        mRelative.setBackgroundColor(color[mIndex]);
        mTextTitle.setText(mTitle);
        mTextContent.setText(mContent);
        mImageLogo.setImageResource(mImageRes);

        return mView;
    }

    public static MyFragment newInstance(Bundle arguments) {

        MyFragment fragment = new MyFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
}
