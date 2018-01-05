package com.libo.libokdemos.Basis;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BasisActivity extends AppCompatActivity {

    private static final String TAG = "BasisActivity";
    @BindView(R.id.show_dialog)
    Button mButtonShowDialog;
    private int mI = -1;
    private boolean isShow = false;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basis);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            mI = savedInstanceState.getInt("key", -1);
        }
        L.d(TAG, "onCreate = " + mI);
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d(TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        L.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("key", 520);
        super.onSaveInstanceState(outState);
        L.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int i = savedInstanceState.getInt("key", -1);
        super.onRestoreInstanceState(savedInstanceState);
        L.d(TAG, "onRestoreInstanceState = " + i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy");
    }

    @OnClick(R.id.show_dialog)
    public void onViewClicked() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("测试")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BasisActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BasisActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).create();
//        alertDialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popupwindow_basis, null);
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mPopupWindow.showAtLocation(mButtonShowDialog, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);

        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.show();
        startActivity(new Intent(this, Basis2Activity.class));
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
