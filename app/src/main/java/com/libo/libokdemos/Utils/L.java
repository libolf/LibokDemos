package com.libo.libokdemos.Utils;

import android.util.Log;

/**
 * Created by libok on 2018-01-04.
 */

public class L {

    private static boolean DEBUG = true;

    private L() throws Exception {
        throw new Exception("can not create new class");
    }

    public static void e(String TAG, String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }

    public static void d(String TAG, String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }
}
