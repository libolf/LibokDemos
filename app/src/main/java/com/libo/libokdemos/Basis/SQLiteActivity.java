package com.libo.libokdemos.Basis;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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

    private MySQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        ButterKnife.bind(this);
        mSQLiteHelper = DBManager.getInstance(this);
    }

    @OnClick(R.id.create_database)
    public void onViewClicked() {
        SQLiteDatabase database = mSQLiteHelper.getWritableDatabase();
    }
}
