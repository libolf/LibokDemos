package com.libo.libokdemos.Basis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceActivity extends AppCompatActivity {
    private static final String TAG = "ServiceActivity";

    @BindView(R.id.button_start_service)
    Button mButtonStartService;
    @BindView(R.id.button_stop_service)
    Button mButtonStopService;
    @BindView(R.id.button_bind_service)
    Button mButtonBindService;
    @BindView(R.id.button_unbind_service)
    Button mButtonUnbindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_start_service, R.id.button_stop_service, R.id.button_bind_service, R.id.button_unbind_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_start_service:
                break;
            case R.id.button_stop_service:
                break;
            case R.id.button_bind_service:
                break;
            case R.id.button_unbind_service:
                break;
        }
    }
}
