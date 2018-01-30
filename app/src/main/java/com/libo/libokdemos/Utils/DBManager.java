package com.libo.libokdemos.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.libo.libokdemos.Bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libok on 2018-01-26.
 */

public class DBManager {
    private static final String TAG = "DBManager";
    private static MySQLiteHelper sMySQLiteHelper = null;

    public static MySQLiteHelper getInstance(Context context) {
        if (sMySQLiteHelper == null) {
            synchronized (DBManager.class) {
                if (sMySQLiteHelper == null) {
                    sMySQLiteHelper = new MySQLiteHelper(context);
                }
            }
        }
        return sMySQLiteHelper;
    }

    public static List<Person> cursorToList(Cursor cursor) {
        List<Person> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(person);
        }

        return list;
    }

    public static List<Person> pageCursor(SQLiteDatabase database, String selectSql, int currentPage, int size) {
        Log.e(TAG, "pageCursor: currentPage = " + currentPage + " size = " + size);
        List<Person> list = null;
        if (database != null) {
            Cursor cursor = database.rawQuery(selectSql, new String[]{currentPage + "", size + ""});
            list = cursorToList(cursor);
        }

        return list;
    }

    public static int getDataNum(SQLiteDatabase database) {
        if (database != null) {
            Cursor cursor = database.rawQuery("select * from Person", null);
            return cursor.getCount();
        }
        return -1;
    }

//    public static <T> List<T> cursorToList1(Cursor cursor) {
//
//    }
//
//    public static <T> List<T> getDatas(SQLiteDatabase database, String selectSql, int currentPage, int size) {
//        Cursor cursor = database.rawQuery(selectSql, new String[]{currentPage + "", size + ""});
//        return cursorToList1(cursor);
//    }
}
