package com.libo.libokdemos.Utils;

import android.content.Context;

/**
 * Created by libok on 2018-01-26.
 */

public class DBManager {
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
}
