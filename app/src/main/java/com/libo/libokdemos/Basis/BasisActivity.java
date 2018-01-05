package com.libo.libokdemos.Basis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.L;

public class BasisActivity extends AppCompatActivity {

    private static final String TAG = "BasisActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basis);
        L.d(TAG, "onCreate");
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
    public void onContentChanged() {
        super.onContentChanged();
        L.d(TAG, "onContentChanged");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy");
    }
}
