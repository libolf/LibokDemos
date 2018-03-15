package com.libo.libokdemos.Project.Download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.libo.libokdemos.Bean.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
             + File.separator + "libo" + File.separator;

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            new InitThread(fileInfo).start();
            Log.e(TAG, "onStartCommand: start " + fileInfo.toString());
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.e(TAG, "onStartCommand: stop " + fileInfo.toString());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                FileInfo fileInfo = (FileInfo) msg.obj;
                Log.e(TAG, "handleMessage: " + fileInfo.getLength() + " " + fileInfo.getLength()/1024f/1024f);
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            this.mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile accessFile = null;
            try {
                //连接网络文件
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == 200) {
                    //获取文件长度
                    length = connection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File file = new File(DOWNLOAD_PATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                //在本地创建文件
                File file1 = new File(file, mFileInfo.getFileName());
                accessFile = new RandomAccessFile(file1, "rwd");
                //设置文件长度
                accessFile.setLength(length);
                mFileInfo.setLength(length);
                handler.obtainMessage(1, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.disconnect();
                     accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
