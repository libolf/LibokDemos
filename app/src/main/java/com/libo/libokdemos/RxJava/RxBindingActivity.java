package com.libo.libokdemos.RxJava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent;
import com.jakewharton.rxbinding2.widget.RxAbsListView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.libo.libokdemos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxBindingActivity extends AppCompatActivity {

    private static final String TAG = "RxBindingActivity";

    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_qwe)
    EditText mEditQwe;
    @BindView(R.id.button_send)
    Button mButtonSend;
    @BindView(R.id.button_reset)
    Button mButtonReset;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Disposable mDisposable;

    private List<String> mStringList;
    private Observable<Long> mSendObservable;
    private Consumer<Long> mSendObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mStringList.add("Item " + (i + 1));
        }

        // 倒计时
//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(10)
//                .map(new Function<Long, Object>() {
//                    @Override
//                    public Object apply(Long aLong) throws Exception {
//                        return 10 - aLong - 1;
//                    }
//                })
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        Log.e(TAG, "accept: " + o);
//                    }
//                });

//        Log.e(TAG, "onCreate: " + Thread.currentThread().getId());

        //防抖操作  throttle 调节
//                        Log.e(TAG, "apply: " + Thread.currentThread().getId());
        mSendObservable = RxView.clicks(mButtonSend)
                .throttleFirst(5000, TimeUnit.MILLISECONDS) //防抖操作  throttle 调节
                .flatMap(new Function<Object, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Object o) throws Exception {

                        RxView.enabled(mButtonSend).accept(false);
                        RxTextView.text(mButtonSend).accept("剩余10秒");
//                        Log.e(TAG, "apply: " + Thread.currentThread().getId());
                        return Observable.interval(1, TimeUnit.SECONDS)
                                .take(10)
                                .map(new Function<Long, Long>() {
                                    @Override
                                    public Long apply(Long aLong) throws Exception {
                                        Log.e(TAG, "apply: " + aLong);
                                        return (10 - aLong - 1);
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        //                        Log.e(TAG, "accept: " + Thread.currentThread().getId());
//                        Toast.makeText(RxBindingActivity.this, "发送", Toast.LENGTH_SHORT).show();
        mSendObserver = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "accept: " + Thread.currentThread().getId());
//                        Toast.makeText(RxBindingActivity.this, "发送", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "accept: " + aLong);
                if (aLong == 0) {
                    RxView.enabled(mButtonSend).accept(true);
                    RxTextView.text(mButtonSend).accept("重新发送");
                } else {
                    RxTextView.text(mButtonSend).accept("剩余" + aLong + "秒");
                }
            }
        };

        mDisposable = mSendObservable.subscribe(mSendObserver);

        RxView.clicks(mButtonReset).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mDisposable != null && !mDisposable.isDisposed()) {
                    mDisposable.dispose();
                    mDisposable = mSendObservable.subscribe(mSendObserver);
                    RxView.enabled(mButtonSend).accept(true);
                    RxTextView.text(mButtonSend).accept("发送验证码");
                }
            }
        });

        mListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStringList));

        RxAbsListView.scrollEvents(mListview)
                .subscribe(new Consumer<AbsListViewScrollEvent>() {
                    @Override
                    public void accept(AbsListViewScrollEvent absListViewScrollEvent) throws Exception {
                        Log.e(TAG, "accept: " + absListViewScrollEvent.firstVisibleItem());
                        absListViewScrollEvent.view().setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {

                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                Log.e(TAG, "onScroll: " + totalItemCount);
                            }
                        });
                    }
                });

        RxAdapterView.itemClicks(mListview)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        Log.e(TAG, "apply: " + integer);
                        return Observable.just(mStringList.get(integer));
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RxBindingActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

        RxToolbar.navigationClicks(mToolbar)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxBindingActivity.this, "Toolbar Click", Toast.LENGTH_SHORT).show();
                    }
                });

        try {
            RxToolbar.title(mToolbar).accept("你好");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mSendObservable.unsubscribeOn(AndroidSchedulers.mainThread());
    }
}
