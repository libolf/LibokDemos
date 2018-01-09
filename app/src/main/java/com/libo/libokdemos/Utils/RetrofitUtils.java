package com.libo.libokdemos.Utils;

import com.google.gson.GsonBuilder;

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
        sOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
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

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            throw new NullPointerException("先getInstance后再获取Retrofit");
        }
        return sRetrofit;
    }
}
