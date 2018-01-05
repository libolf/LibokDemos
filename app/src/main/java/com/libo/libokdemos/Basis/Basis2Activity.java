package com.libo.libokdemos.Basis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.L;

public class Basis2Activity extends AppCompatActivity {

    private static final String TAG = "Basis2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basis2);
        L.d(TAG, TAG + "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d(TAG, TAG + "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d(TAG, TAG + "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, TAG + "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d(TAG, TAG + "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, TAG + "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG, TAG + "onDestroy");
    }
}

