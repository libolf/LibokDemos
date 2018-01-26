package com.libo.libokdemos.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by libok on 2018-01-26.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";

    private static final String TABLE_PERSION = "create table Person(\n" +
            "    _id Integer primary key,\n" +
            "    name varchar(10),\n" +
            "    age Integer)";

    public MySQLiteHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    /**
     *
     * @param context
     * @param name
     * @param factory 游标
     * @param version >= 1
     */
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 当数据库创建时
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "database onCreate: ");
        db.execSQL(TABLE_PERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.e(TAG, "database onOpen: ");
    }

    /**
     * 当数据库版本更新时
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "database onUpgrade: ");
    }
}
