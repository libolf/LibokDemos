package com.libo.libokdemos.Basis;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.libo.libokdemos.Basis.Service.MyDownloadService;
import com.libo.libokdemos.Basis.Service.MyIntentService;
import com.libo.libokdemos.Basis.Service.MyService;
import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 断点续传
 */
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
    @BindView(R.id.button_intent_service)
    Button mButtonIntentService;
    @BindView(R.id.button_startdownload_service)
    Button mButtonStartdownloadService;
    @BindView(R.id.button_pausedownload_service)
    Button mButtonPausedownloadService;
    @BindView(R.id.button_canceldownload_service)
    Button mButtonCanceldownloadService;

    private ServiceConnection mServiceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mDownloadBinder = (MyService.MyDownloadBinder) service;
//            mDownloadBinder.startDownload();
//            mDownloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private MyDownloadService.DownloadBinder mDownloadBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownloadBinder = (MyDownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, MyDownloadService.class);
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
//        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask(this);
//        downloadAsyncTask.execute("123");
    }

    @OnClick({
            R.id.button_start_service,
            R.id.button_stop_service,
            R.id.button_bind_service,
            R.id.button_unbind_service,
            R.id.button_intent_service,
            R.id.button_startdownload_service,
            R.id.button_pausedownload_service,
            R.id.button_canceldownload_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_start_service:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.button_stop_service:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.button_bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, mServiceConnection1, BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind_service:
                unbindService(mServiceConnection1);
                break;
            case R.id.button_intent_service:
                Log.e(TAG, "onViewClicked: " + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            case R.id.button_startdownload_service:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                String url1 = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
                mDownloadBinder.startDownload(url1);
                break;
            case R.id.button_pausedownload_service:
                mDownloadBinder.pauseDownload();
                break;
            case R.id.button_canceldownload_service:
                mDownloadBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyDownloadService.class));
        unbindService(mServiceConnection);
    }
}
