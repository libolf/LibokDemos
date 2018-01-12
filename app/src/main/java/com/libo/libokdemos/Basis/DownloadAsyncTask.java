package com.libo.libokdemos.Basis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by libok on 2018-01-12.
 */

public class DownloadAsyncTask extends AsyncTask<String, Integer, Integer> {

    private static final String TAG = "DownloadAsyncTask";
    private static final String download_url = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";

    private Context mContext;
    private ProgressDialog mProgressDialog;

    //总计
    private long total = -1;
    private final OkHttpClient mOkHttpClient;
    private final Request mRequest;

    public DownloadAsyncTask(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
        mProgressDialog.setTitle("正在下载...");
        mProgressDialog.setProgressNumberFormat("");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgressDialog.dismiss();
            }
        });

        mOkHttpClient = new OkHttpClient();
        mRequest = new Request.Builder()
                .url(download_url)
                .build();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
//        mProgressDialog.getButton(ProgressDialog.BUTTON_POSITIVE).setEnabled(false);
        Log.e(TAG, "onPreExecute: ");
    }

    @Override
    protected Integer doInBackground(String... strings) {
//        通知已完成进度
//        publishProgress(10);
//        try {
//            for (int i = 0; i <= 100; i += 10) {
//                Thread.sleep(1000);
//                publishProgress(i);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Response response = mOkHttpClient.newCall(mRequest).execute();
            if (response.isSuccessful()) {
                total = response.body().contentLength();
                Log.e(TAG, "doInBackground: " + total);
                publishProgress(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "doInBackground: ");
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e(TAG, "onProgressUpdate: " + values[0]);
        mProgressDialog.setProgress(values[0]);
        float alreayDownlaod = (total * (values[0] / 100f));
        if (total != -1) {
            float f = total / 1024f / 1024f;
            mProgressDialog.setProgressNumberFormat(alreayDownlaod + "M/" + roundFloat(f) + "M");
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Log.e(TAG, "onPostExecute: ");
//        mProgressDialog.dismiss();
//        mProgressDialog.getButton(ProgressDialog.BUTTON_POSITIVE).setEnabled(true);
        mProgressDialog.setTitle("下载完成");
        mProgressDialog.getButton(ProgressDialog.BUTTON_POSITIVE).setText("确定");
    }

    private float roundFloat(float value, int length) {
        return Math.round(value * (Math.pow(10, length))) / (float)Math.pow(10, length);
    }

    private float roundFloat(float value) {
        return Math.round(value * 100) / 100f;
    }
}
