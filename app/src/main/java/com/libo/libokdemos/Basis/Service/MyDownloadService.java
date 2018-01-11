package com.libo.libokdemos.Basis.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.libo.libokdemos.Basis.DownloadTask;
import com.libo.libokdemos.Basis.Implement.DownloadListener;
import com.libo.libokdemos.Basis.ServiceActivity;
import com.libo.libokdemos.R;

import java.io.File;

public class MyDownloadService extends Service {

    private DownloadTask mDownloadTask;
    private String downloasUrl;

    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Download...", progress));
        }

        @Override
        public void onSuccess() {
            mDownloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(MyDownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFaild() {
            mDownloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Filed", -1));
            Toast.makeText(MyDownloadService.this, "Download Faild", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            mDownloadTask = null;
            getNotificationManager().notify(1, getNotification("Download Paused", -1));
            Toast.makeText(MyDownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            mDownloadTask = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Canceled", -1));
            Toast.makeText(MyDownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    public MyDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private NotificationManager getNotificationManager() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        return notificationManager;
    }

    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }

        return builder.build();
    }


    public class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (mDownloadTask == null) {
                downloasUrl = url;
                mDownloadTask = new DownloadTask(mDownloadListener);
                mDownloadTask.execute(downloasUrl);
                startForeground(1, getNotification("Downloading...", 0));

                Toast.makeText(MyDownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (mDownloadTask != null) {
                mDownloadTask.cancelDownload();
            } else {
                if (downloasUrl != null) {
                    String fileName = downloasUrl.substring(downloasUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(MyDownloadService.this, "Cancled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
