package com.libo.libokdemos.Basis;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.DBManager;
import com.libo.libokdemos.Utils.MySQLiteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by libok on 2018-01-26.
 */

public class SQLiteActivity extends AppCompatActivity {

    @BindView(R.id.create_database)
    Button mCreateDatabase;
    @BindView(R.id.list_database)
    ListView mListDatabase;

    private MySQLiteHelper mSQLiteHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        mSQLiteHelper = DBManager.getInstance(this);
        mDatabase = mSQLiteHelper.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from Person", null);

        //将数据源数据加载到适配器中
        //要想使用此Adapter必须有个_id的主键
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.sqlite_list_item, cursor,
                new String[]{"_id", "name", "age"},
                new int[]{R.id.sqlite_list_item_id, R.id.sqlite_list_item_name, R.id.sqlite_list_item_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mListDatabase.setAdapter(cursorAdapter);
    }

    @OnClick(R.id.create_database)
    public void onViewClicked() {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
    }
}
