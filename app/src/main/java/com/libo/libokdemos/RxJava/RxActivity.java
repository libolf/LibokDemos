package com.libo.libokdemos.RxJava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.libo.libokdemos.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 李波
 * @date 2018-03-11 下午 08:53
 * @e-mail libolf@outlook.com
 * @description
 */
public class RxActivity extends AppCompatActivity {

    private static final String TAG = "RxActivity";

    private List<String> mStringList;
    private String mStringValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        mStringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mStringList.add(i + "");
        }

//        createObservable();
//        mapObservable();
//        filterObservable();
//        combineObservable();
        schedulerObservable();
    }

    /**
     * 调度Observable
     */
    private void schedulerObservable() {
        Observable<Integer> integerObservable = Observable.just(1);
        // IO操作 网络请求、磁盘读写
        integerObservable.observeOn(Schedulers.io());
        // 计算 是 buffer debounce delay interval sample skip的默认调度器
        integerObservable.observeOn(Schedulers.computation());
        // 新线程
        integerObservable.observeOn(Schedulers.newThread());
        // 单独线程
        integerObservable.observeOn(Schedulers.single());
        // 按顺序处理队列，并运行队列中的每一个任务 repeat retry的默认调度器
        integerObservable.observeOn(Schedulers.trampoline());

        // Android主线程
        integerObservable.observeOn(AndroidSchedulers.mainThread());

        // 被观察者数据产生的线程
        integerObservable.subscribeOn(Schedulers.io());
        // 观察者数据处理的线程
        integerObservable.observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 组合Observable
     */
    private void combineObservable() {

        Observable<Integer> mIntegerObservable = Observable.fromArray(new Integer[]{1, 2, 3, 4});
        Observable<Integer> mIntegerObservable1 = Observable.fromArray(new Integer[]{2, 4, 6});
        Observable<String> mStringObservable = Observable.fromArray(new String[]{"libo", "yjf", "yang"});

        //Zip 将数据源按照一定的规则进行组合 取两者Size最小的作为新的Observable的Size 注意，当Size不匹配时并不是进入了onError，而是内部给消化了
        //  当其中的一个Observable发送数据结束时或出现异常时，另一个也随即停止发送数据
//        Observable
//                .zip(mIntegerObservable, mIntegerObservable1, new BiFunction<Integer, Integer, Object>() {
//                    @Override
//                    public Object apply(Integer integer, Integer integer2) throws Exception {
//                        Log.e(TAG, "apply: " + integer + "  " + integer2);
//                        return integer * integer2;
//                    }
//                })
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        Log.e(TAG, "accept: " + o);
//                    }
//                });

//        Observable
//                .zip(mIntegerObservable, mStringObservable, new BiFunction<Integer, String, Object>() {
//                    @Override
//                    public Object apply(Integer integer, String s) throws Exception {
//                        Log.e(TAG, "apply: " + integer + "  " + s);
//                        return integer + " ||| " + s;
//                    }
//                })
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        Log.e(TAG, "onNext: " + o);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });


        //Merge 按时间顺序将数据源进行组合
//        Observable
//                .interval(0, 4, TimeUnit.SECONDS)
//                .mergeWith(Observable.interval(2, 4, TimeUnit.SECONDS))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "accept: " + aLong);
//                    }
//                });


        //StartWith 在数据源的第一个之前插入一个数据
//        mIntegerObservable.startWith(mIntegerObservable1)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //CombineLatest 后一个序列结合前一个序列的最后一个数据进行自定义运算
        // TODO: 2018-03-11
        boolean disposed = Observable
                .combineLatest(mIntegerObservable, mIntegerObservable1, new BiFunction<Integer, Integer, Object>() {
                    @Override
                    public Object apply(Integer integer, Integer integer2) throws Exception {
                        Log.e(TAG, "apply: " + integer + " " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.e(TAG, "accept: " + o);
                    }
                }).isDisposed();

        Log.e(TAG, "combineObservable: " + disposed);


        //Join


        //GroupJoin


        //SwitchOnNext


    }

    /**
     * 过滤Observable
     */
    private void filterObservable() {

        Observable<Integer> mNumObservable = Observable.just(1, 2, 3, 4, 5);
        Observable<String> mStrObservable = Observable.just("", "s", "st", "str");

        //Debounce  产生一个数据之后如果在指定时间内没有继续产生，则会把这个数据发射出去，如果后续又产生了N多数据后又间隔一段时间没有产生数据，则把最后那个数据发射出去
        // TODO: 2018-03-11
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                try {
//                    for (int i = 0; i < 10; i++) {
//                        Thread.sleep(2000);
//                        e.onNext(i);
//                    }
//                    e.onComplete();
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                    e.onError(e1);
//                }
//            }
//        })
//        .debounce(1, TimeUnit.SECONDS)
//        .subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer o) throws Exception {
//                Log.e(TAG, "accept: " + o);
//            }
//        });


        //Distinct  去重操作
//        Observable.just(1, 2, 3, 2, 3)
//                .distinct()
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //ElementAt 类似List中的childAt 如果没有目标数据，则输出defaultItem
//        mNumObservable.elementAt(1, 10)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //Filter    按自定义规则过滤
//        mNumObservable.filter(new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer integer) throws Exception {
//                return integer > 2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e(TAG, "accept: " + integer);
//            }
//        });
//
//        mStrObservable.filter(new Predicate<String>() {
//            @Override
//            public boolean test(String s) throws Exception {
//                return s.length() != 0;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, "accept: 11111111  " + s);
//            }
//        });


        //First     只取第一个数据
//        mNumObservable.first(3)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });
//
//        Observable.fromArray(new Integer[]{})
//                .first(520)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //IgnoreElements    忽略所有的元素，只回调完成或错误方法
//        mNumObservable.ignoreElements()
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e(TAG, "onSubscribe: ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "onComplete: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ");
//                    }
//                });


        //Last  最后的数据 可以参考First
//        mNumObservable.last(3)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //Sample    取样
        // TODO: 2018-03-11  


        //Skip  跳过前几个
//        mNumObservable.skip(2)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //SkipLast  跳过后面几项 可参考Skip
//        mNumObservable.skipLast(2)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //Take  只取前几项
//        mNumObservable.take(2)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });


        //TakeLast  只取后几项 可参考Take
        mNumObservable.takeLast(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "accept: " + integer);
                    }
                }).isDisposed();
    }

    /**
     * 转换Observable
     */
    private void mapObservable() {

        //Map 转换
        Observable<String> mapStringObservable = Observable.just(123)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + "456";
                    }
                });

        //FlatMap 转成另一个Observable 依次平铺发射
        Observable<String> flatMapStringObservable = Observable.just(1, 2, 3, 4, 5, 6)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just(integer + " libo");
                    }
                });

        //GroupBy   将数据进行分组
//        Observable.just(1, 2, 3, 4)
//                .groupBy(new Function<Integer, Object>() {
//                    @Override
//                    public Object apply(Integer integer) throws Exception {
//                        return integer > 2;
//                    }
//                })
//                .subscribe(new Observer<GroupedObservable<Object, Integer>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(final GroupedObservable<Object, Integer> objectIntegerGroupedObservable) {
//                        objectIntegerGroupedObservable.subscribe(new Observer<Integer>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(Integer integer) {
//                                Log.e(TAG, "onNext: " + objectIntegerGroupedObservable.getKey() + " " + integer);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        //Buffer    将数据进行缓存后一并发出
//        Observable.just(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f)//.range(10, 6)
//                .buffer(2)
//                .subscribe(new Consumer<List<Float>>() {
//                    @Override
//                    public void accept(List<Float> floats) throws Exception {
//                        Log.e(TAG, "accept: " + floats.toString());
//                    }
//                });

        //skip 跳过第几个，10 11 12 13 14 15 跳过12和15
//        Observable.range(10, 6)
//                .buffer(2, 3)
//                .subscribe(new Consumer<List<Integer>>() {
//                    @Override
//                    public void accept(List<Integer> integers) throws Exception {
//                        Log.e(TAG, "accept: " + integers.toString());
//                    }
//                });

        //Scan  输入，在一系列连续的事务中将前两个做操作后重新作为第一个，如此往复，有点类似斐波那契数列
//        Observable.range(1, 5)
//                .scan(new BiFunction<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer, Integer integer2) throws Exception {
//                        Log.e(TAG, "apply: " + integer + " " + integer2);
//                        return integer + integer2;
//                    }
//                })
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });

        //Window    与Buffer类似
//        Observable.range(1, 5)
//                .window(2, 2)
//                .subscribe(new Consumer<Observable<Integer>>() {
//                    @Override
//                    public void accept(Observable<Integer> integerObservable) throws Exception {
//                        integerObservable.subscribe(new Consumer<Integer>() {
//                            @Override
//                            public void accept(Integer integer) throws Exception {
//                                Log.e(TAG, "accept: " + integer);
//                            }
//                        });
//                    }
//                });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RxActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

//        mapStringObservable.subscribe(observer);
        flatMapStringObservable.subscribe(observer);
    }

    /**
     * 创建Observable
     */
    private void createObservable() {

        //Create
        Observable<String> createStringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("123");
                e.onComplete();
            }
        });

        //Just
        Observable<String> justStringObservable = Observable.just("908");

        //From
        Observable<String> fromArrayStringObservable = Observable.fromArray(new String[]{"1", "2", "3", "4",});
        Observable<String> fromIterableStringObservable = Observable.fromIterable(mStringList);
//        Observable<String> stringObservable4 = Observable.fromFuture()

        mStringValue = "string";
        //Defer 在订阅时才会创建的被观察者
        Observable<String> deferStringObservable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just(mStringValue);
            }
        });
        mStringValue = "testString";

        //Interval 初始延时 后续间隔
        Observable<String> intervalStringObservable = Observable.interval(5, 10, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception {
                        return Observable.just("" + aLong);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        //Range 起始数值    个数  顺序分发
        Observable<String> rangeStringObservable = Observable.range(10, 2)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        return Observable.just("" + integer);
                    }
                });

        //Repeate   重复
        Observable<String> repeatStringObservable = rangeStringObservable.repeat(2);

        //Start     在某个之前
        Observable<String> startStringObservable = rangeStringObservable.startWith("libo");

        //Timer已经可以用Interval来代替了

        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: subscriber " + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RxActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };
//        createStringObservable.subscribe(observer);
//        justStringObservable.subscribe(observer);
//        fromArrayStringObservable.subscribe(observer);
//        fromIterableStringObservable.subscribe(observer);
//        deferStringObservable.subscribe(observer);
//        intervalStringObservable.subscribe(observer);
//        rangeStringObservable.subscribe(observer);
//        repeatStringObservable.subscribe(observer);
        startStringObservable.subscribe(observer);
    }
}
