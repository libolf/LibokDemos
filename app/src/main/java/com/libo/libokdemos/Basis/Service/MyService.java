package com.libo.libokdemos.Basis.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.libo.libokdemos.Basis.ServiceActivity;
import com.libo.libokdemos.R;

public class MyService extends Service {

    private static final String TAG = "MyService";

    private MyDownloadBinder mBinder = new MyDownloadBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("This is tltle")
                .setContentText("This is content")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + flags + " " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        stopForeground(true);
    }

    public class MyDownloadBinder extends Binder {

        public void startDownload() {
            Log.e(TAG, "startDownload execued");
        }

        public int getProgress() {
            Log.e(TAG, "getProgress execued");
            return 0;
        }
    }

}
