package com.libo.libokdemos.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请工具
 * https://www.jianshu.com/p/b5c494dba0bc
 *
 * 拨打电话
 * Intent intent = new Intent(Intent.ACTION_CALL);
 intent.setData(Uri.parse("tel:10086"));
 activity.startActivity(intent);
 *
 * Created by libok on 2018-01-18.
 */

public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    public static void request(final Activity activity, final String title, final String message, final int requestCode, final RequestPermissionsListener listener, final String... permissions) {
        Log.e(TAG, "request: " + ContextCompat.checkSelfPermission(activity, permissions[0]) + " " + ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0]));
        //先提示需要授权原因
        AlertDialog alertDialog = AlertDialogUtil.showDialog(activity, title, message, null, null);
        final List<String> list = new ArrayList();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e(TAG, "onDismiss: ");
                for (int i = 0; i < permissions.length; i ++) {
                    Log.e(TAG, "onDismiss: " + permissions[i] + " " + ContextCompat.checkSelfPermission(activity, permissions[i]));
                    //未授权
                    if (ContextCompat.checkSelfPermission(activity, permissions[i]) == PackageManager.PERMISSION_DENIED) {
                        list.add(permissions[i]);
                        Log.e(TAG, "onDismiss: " + permissions[i] + " " + ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]));
                        //需要说明
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                            AlertDialogUtil.showDialog(activity, title, message, null, null);
                        } else {
                            ActivityCompat.requestPermissions(activity, permissions, requestCode);
                        }
                    }
                    //已授权
                    else {
                        listener.doEverything();
                    }
                }
            }
        });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e(TAG, "onCancel: ");

            }
        });
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
        void doEverything();
    }
}