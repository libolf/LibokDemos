package com.libo.libokdemos.Basis.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by libok on 2018-01-17.
 */

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "自定义广播", Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
//        Log.e(TAG, "onReceive: Boot Complete");
        abortBroadcast();
        clearAbortBroadcast();
    }
}
