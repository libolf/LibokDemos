package com.libo.libokdemos.Basis;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.libo.libokdemos.Bean.Person;
import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.DBManager;
import com.libo.libokdemos.Utils.MyBaseAdapter;
import com.libo.libokdemos.Utils.MyCursorAdapter;
import com.libo.libokdemos.Utils.MySQLiteHelper;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by libok on 2018-01-26.
 */

public class SQLiteActivity extends AppCompatActivity {

    @BindView(R.id.create_database)
    Button mCreateDatabase;
    @BindView(R.id.list_database)
    ListView mDatabaseListView;

    private MySQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDatabase;
    private int pageNum;
    private int currentPage;
    private int pageSize;
    private int allDataSize;
    private List<Person> mDatas;
    private MyBaseAdapter mAdapter;
    private boolean isDivPage;
    private Person loadingPerson;
    private Person loadingTextPerson;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mDatas.remove(mDatas.size() - 1);
            mAdapter.notifyDataSetChanged();
            mDatas.addAll(DBManager.pageCursor(mDatabase, "select * from Person limit ?,?", currentPage * 20, pageSize));
            if (mDatas.size() < allDataSize) {
                mDatas.add(loadingTextPerson);
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        mSQLiteHelper = DBManager.getInstance(this);
        mDatabase = mSQLiteHelper.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from Person", null);
        mDatas = new ArrayList<>();
        loadingPerson = new Person().setId(-1);
        loadingTextPerson = new Person().setId(-2);
        //将数据源数据加载到适配器中
        //要想使用此Adapter必须有个_id的主键
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.sqlite_list_item, cursor,
                new String[]{"_id", "name", "age"},
                new int[]{R.id.sqlite_list_item_id, R.id.sqlite_list_item_name, R.id.sqlite_list_item_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        MyCursorAdapter mCursorAdapter = new MyCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//        mDatabaseListView.setAdapter(mCursorAdapter);
        allDataSize = DBManager.getDataNum(mDatabase);
        if (allDataSize != -1) {
            pageSize = 20;
            pageNum = (int) Math.ceil(allDataSize / (double) pageSize);
            currentPage = 0;
//            mDatas.addAll(DBManager.pageCursor(mDatabase, "select * from Person limit ?,?", currentPage, pageSize));
        }
//        if (mDatas != null && mDatas.size() > 0) {
//            mAdapter = new MyBaseAdapter(this, mDatas);
//            mDatabaseListView.setAdapter(mAdapter);
//        }
        mAdapter = new MyBaseAdapter(this, mDatas);
        mDatabaseListView.setAdapter(mAdapter);

        Observable.create(new ObservableOnSubscribe<List<Person>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Person>> e) throws Exception {
                mDatas.addAll(DBManager.pageCursor(mDatabase, "select * from Person limit ?,?", currentPage, pageSize));
                mDatas.add(loadingTextPerson);
                e.onNext(mDatas);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Person>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Person> people) {
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mDatabaseListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isDivPage && scrollState == SCROLL_STATE_IDLE) {
                    mDatas.remove(mDatas.size() - 1);
                    mDatas.add(loadingPerson);
                    mAdapter.notifyDataSetChanged();
                    currentPage++;
                    mHandler.sendMessageDelayed(Message.obtain(), 3000);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) - 1 == (currentPage + 1) * 20);
            }
        });

    }

    @OnClick(R.id.create_database)
    public void onViewClicked() {

        //SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
//        String insertSql = null;
//        mDatabase.beginTransaction();
//        for (int i = 6; i < 100; i++) {
//            insertSql = "insert into Person values (" + i + ", '你好" + i + "', " + (int)(Math.random() * 100) + ")";
//            mDatabase.execSQL(insertSql);
//        }
//        mDatabase.setTransactionSuccessful();
//        mDatabase.endTransaction();
    }
}
