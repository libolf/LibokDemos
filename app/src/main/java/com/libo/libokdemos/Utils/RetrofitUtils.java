package com.libo.libokdemos.Utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.libo.libokdemos.MVP.CustomGsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by libok on 2018-01-09.
 */

public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";

    private static RetrofitUtils sRetrofitUtils = null;
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;

    private RetrofitUtils(String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                L.e(TAG, "okhttp log==== " + message);
            }
        });
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        sOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create(gson))   //自定义GsonConverterFactory，防止json出错https://www.jianshu.com/p/5b8b1062866b
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Log.d(TAG, "RetrofitUtils: " + baseUrl);
    }

    public static RetrofitUtils getInstance(String baseUrl) {
        if (sRetrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (sRetrofitUtils == null) {
                    sRetrofitUtils = new RetrofitUtils(baseUrl);
                }
            }
        }
        return sRetrofitUtils;
    }

    public Retrofit getRetrofit() {
        if (sRetrofit == null) {
            throw new NullPointerException("先getInstance后再获取Retrofit");
        }
        return sRetrofit;
    }
}
