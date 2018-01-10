package com.libo.libokdemos.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.libo.libokdemos.MVP.Model.PhoneInfo;
import com.libo.libokdemos.MVP.View.MvpMainView;
import com.libo.libokdemos.Utils.APIService;
import com.libo.libokdemos.Utils.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by libok on 2018-01-10.
 */

public class MainPresenter {

    private static final String TAG = "MainPresenter";

    private Context mContext;
    private MvpMainView mMvpMainView;
    private final Retrofit mRetrofit;
    private final APIService mApiService;


    public MainPresenter(Context context, MvpMainView mvpMainView) {
        mContext = context;
        mMvpMainView = mvpMainView;
        mRetrofit = RetrofitUtils.getInstance("https://www.baifubao.com/").getRetrofit();
        mApiService = mRetrofit.create(APIService.class);
    }

    public void searchPhone(String phone) {
        if (phone.length() != 11) {
            mMvpMainView.showToast("请输入正确的手机号");
            return;
        }
        mMvpMainView.showLoading();
        getPhoneInfo(phone);
    }

    private void getPhoneInfo(String phone) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("cmd", "1059");
        paramsMap.put("callback", "phone");
        paramsMap.put("phone", phone);
        Log.e(TAG, "getPhoneInfo: " + paramsMap.toString());

//        Call<PhoneInfo> phoneInfoCall = mApiService.getPhoneInfo(paramsMap);
//        phoneInfoCall.enqueue(new Callback<PhoneInfo>() {
//            @Override
//            public void onResponse(Call<PhoneInfo> call, Response<PhoneInfo> response) {
//                Log.e(TAG, "onResponse: " + response.body().getData().getArea());
//                
//            }
//
//            @Override
//            public void onFailure(Call<PhoneInfo> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t);
//            }
//        });
        Observable<PhoneInfo> infoObservable = mApiService.getPhoneInfo(paramsMap);
        infoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhoneInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PhoneInfo phoneInfo) {
                        mMvpMainView.updateView(phoneInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        mMvpMainView.hidenLoading();
                    }
                });
    }
}
