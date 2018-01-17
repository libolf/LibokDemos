package com.libo.libokdemos.Basis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends AppCompatActivity {

    @BindView(R.id.permission)
    Button mPermission;
    @BindView(R.id.content_provider)
    Button mContentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.permission, R.id.content_provider})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.permission:
                break;
            case R.id.content_provider:
                break;
        }
    }
}
