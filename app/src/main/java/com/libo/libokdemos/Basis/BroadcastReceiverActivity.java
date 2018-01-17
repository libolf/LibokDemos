package com.libo.libokdemos.Basis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BroadcastReceiverActivity extends AppCompatActivity {

    private static final String TAG = "BroadcastReceiver";
    private static int num = 1;
    @BindView(R.id.send_broadcast)
    Button mSendBroadcast;

    private IntentFilter mIntentFilter;
    private TimeChangeReceiver mTimeChangeReceiver;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        ButterKnife.bind(this);
        mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//(Intent.ACTION_TIME_CHANGED);//(Intent.ACTION_TIME_TICK);
//        mTimeChangeReceiver = new TimeChangeReceiver();
//        registerReceiver(mTimeChangeReceiver, mIntentFilter);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mIntentFilter.addAction("local");
        mLocalReceiver = new LocalReceiver();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mTimeChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

    @OnClick(R.id.send_broadcast)
    public void onViewClicked() {
//        Intent intent = new Intent("libo");
//        sendBroadcast(intent);
//        sendOrderedBroadcast(intent, null);
        Intent intent = new Intent("local");
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    class TimeChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            num++;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, networkInfo.getTypeName() + "网络可用", Toast.LENGTH_SHORT).show();
            } else if ((num % 2) == 0) {
                Toast.makeText(context, "网络不可用 " + num, Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(context, "time change", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "onReceive: time change");
        }

    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Local", Toast.LENGTH_SHORT).show();
        }
    }
    

}
