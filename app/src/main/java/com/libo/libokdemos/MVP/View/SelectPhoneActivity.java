package com.libo.libokdemos.MVP.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.MVP.Model.PhoneInfo;
import com.libo.libokdemos.MVP.Presenter.MainPresenter;
import com.libo.libokdemos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPhoneActivity extends AppCompatActivity implements MvpMainView {

    private static final String TAG = "SelectPhoneActivity";

    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.province)
    TextView mProvince;
    @BindView(R.id.yunying)
    TextView mYunying;
    @BindView(R.id.guishu)
    TextView mGuishu;

    private MainPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phone);
        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this, this);
        mEditPhone.setFocusable(true);
        mEditPhone.requestFocus();

        Log.e(TAG, "onCreate: " + mEditPhone.hasFocus());

    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {

        mPresenter.searchPhone(mEditPhone.getText().toString());
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(PhoneInfo phoneInfo) {
        mPhone.setText("手机号：" + mEditPhone.getText().toString());
        mProvince.setText("省份：" + phoneInfo.getData().getArea());
        mYunying.setText("运营商：" + phoneInfo.getData().getOperator());
        mGuishu.setText("归属地：" + phoneInfo.getData().getArea_operator());
    }

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();
    }

    @Override
    public void hidenLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
