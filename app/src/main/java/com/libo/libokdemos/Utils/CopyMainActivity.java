package com.libo.libokdemos.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {
 * "result": 1,
 * "msg": "",
 * "data": {
 * "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjM1LCJpc3MiOiJodHRwOi8vNDcuOTQuMTguMTMwOjgxL2FwaS9pbmRleC9sb2dpbiIsImlhdCI6MTUxMjAwNzIxNiwiZXhwIjoxNTEyNjEyMDE2LCJuYmYiOjE1MTIwMDcyMTYsImp0aSI6IjRQcUxQbW1McUZISWlhN3oifQ.T5mvfrKuviDmPpxJum7ypD_gGTWJubTJrySAAANzp74"
 * },
 * "uid": 35
 * }
 */

/**
 * http://www.jianshu.com/p/031745744bfa
 * http://www.jianshu.com/p/a7635e39c5ac
 * http://www.jianshu.com/p/308f3c54abdd
 */

public class CopyMainActivity extends AppCompatActivity {

    ImageView image;
    private EditText main_edit1;
    private EditText main_edit2;
    private TextView textView;

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjM1LCJpc3MiOiJodHRwOi8vNDcuOTQuMTguMTMwOjgxL2FwaS9pbmRleC9sb2dpbiIsImlhdCI6MTUxMjAwNzIxNiwiZXhwIjoxNTEyNjEyMDE2LCJuYmYiOjE1MTIwMDcyMTYsImp0aSI6IjRQcUxQbW1McUZISWlhN3oifQ.T5mvfrKuviDmPpxJum7ypD_gGTWJubTJrySAAANzp74";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        main_edit1 = findViewById(R.id.main_exit1);
//        main_edit1 = findViewById(R.id.main_exit2);
//        textView = findViewById(R.id.main_jieguo);

        ButterKnife.bind(this);

//        image.setImageResource(R.mipmap.ic_launcher_round);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("tag", "okhttp === " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://rongyisz.com/")
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

//        LoginModel loginModel = mRetrofit.create(LoginModel.class);
//        final FeedBackModel feedBackModel = mRetrofit.create(FeedBackModel.class);

        final Map<String, String> feedParams = new HashMap<>();
        feedParams.put("uid", "35");
        feedParams.put("mobile", "18553651716");
        feedParams.put("content", "测试Retrofit");
        String uid = "35";
        String mobile = "18553651716";
        String content = "测试Retrofit";

//        UidMobileContentModel uidMobileContentModel = new UidMobileContentModel();
//        uidMobileContentModel.setUid(uid);
//        uidMobileContentModel.setMobile(mobile);
//        uidMobileContentModel.setContent(content);

//        Observable<ResponseBody> feedObservable = feedBackModel.postFeed1(token, params);

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("mobile", "18553651716");
        loginMap.put("password", "123456");

//        loginModel.login(loginMap)
//                .flatMap(new Function<LoginBean, ObservableSource<FeedBackBean>>() {
//                    @Nullable
//                    @Override
//                    public ObservableSource<FeedBackBean> apply(@android.support.annotation.NonNull LoginBean loginBean) throws Exception {
//                        String token = loginBean.getData().getToken();
//                        if (!TextUtils.isEmpty(token)) {
//                            return feedBackModel.postFeed1(token, feedParams);
//                        } else {
//                            return null;
//                        }
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Consumer<FeedBackBean>() {
//                    @Override
//                    public void accept(@android.support.annotation.NonNull FeedBackBean feedBackBean) throws Exception {
//                        Log.e("tag", "loginfeed onNext = " + feedBackBean.toString());
//                    }
//                });
//                .subscribe(new Observer<FeedBackBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("tag", "loginfeed onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(FeedBackBean feedBackBean) {
//                        Log.e("tag", "loginfeed onNext = " + feedBackBean.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("tag", "loginfeed onError = " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("tag", "loginfeed onComplete");
//                    }
//                });

//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("tag", "onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody feedBackBean) {
//                        try {
//                            Log.e("tag", "onNext = " + feedBackBean.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("tag", "onError");
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("tag", "onComplete");
//
//                    }
//                });

//        Call<FeedBackBean> call = feedBackModel.postFeed(token, uid, mobile, content);//loginModel.getBlog();
//        call.enqueue(new Callback<FeedBackBean>() {
//            @Override
//            public void onResponse(Call<FeedBackBean> call, Response<FeedBackBean> response) {
//                Log.e("tag", "login ok = " + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<FeedBackBean> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        Observable.just(1, 2, 3, 4)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("flatMap = " + integer);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("tag", "flatMap onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("tag", "flatMap onNext = " + s);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("tag", "flatMap onError");

                    }

                    @Override
                    public void onComplete() {
                        Log.e("tag", "flatMap onComplete");

                    }
                });

        /**
         * concatMap
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@android.support.annotation.NonNull @NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e("tag", "concatMap : accept : " + s + "\n");
                    }
                });

        /**
         * Map
         */
        Observable.just(123).map(new Function<Integer, String>() {
            @android.support.annotation.NonNull
            @Override
            public String apply(@android.support.annotation.NonNull Integer integer) throws Exception {
                return integer.intValue() + " ";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                Log.e("tag", "map onNext = " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("tag", "map onComplete");
            }
        });


//        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
//            @Override
//            public void subscribe(ObservableEmitter e) throws Exception {
//                e.onNext("hello world");
//                e.onComplete();
//            }
//        });
//
//        Observer observer = new Observer() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e("tag", "1-onSubscribe");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.e("tag", "1-onNext = " + o);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("tag", "1-onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("tag", "1-onComplete");
//            }
//        };
//
//        observable1.subscribe(observer);

        /*Observable.just(1, 2, 3, 4, 5, 6)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("tag", "just6 = " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

        /*Observable.just("你好啊")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("tag", "just = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/


    }

    //1
    private Observable<String> createTextChangeObservable() {
        //2
        Observable<String> textChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@android.support.annotation.NonNull final ObservableEmitter<String> emitter) throws Exception {
                //3
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    //4
                    @Override
                    public void onTextChanged(@android.support.annotation.NonNull CharSequence s, int start, int before, int count) {
                        textView.setText(Integer.valueOf(main_edit1.getText().toString()));
                        Log.e("tag", "createTextChangeObservable = " + s);
                        emitter.onNext(s.toString());
                    }
                };

                //5
                main_edit1.addTextChangedListener(watcher);

                //6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        main_edit1.removeTextChangedListener(watcher);

                    }
                });
            }
        });

        // 7
        return textChangeObservable;
    }
}
