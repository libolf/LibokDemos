package com.libo.libokdemos.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * https://www.jianshu.com/p/b5c494dba0bc
 * 权限申请工具
 * Created by libok on 2018-01-18.
 */

public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    public static void request(Activity activity, String title, String message, int requestCode, RequestPermissionsListener listener, String... permissions) {
        Log.e(TAG, "request: " + ContextCompat.checkSelfPermission(activity, permissions[0]) + " " + ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0]));
        if (ContextCompat.checkSelfPermission(activity, permissions[0]) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(activity, "权限需申请", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "request: 权限需申请 " + Build.MANUFACTURER);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            activity.startActivity(intent);
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {

        }
    }

    /**
     * 打开应用详情界面
     * @param context
     */
    private static void openAppDetails(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    public interface RequestPermissionsListener {
        void showRationale();
    }
}