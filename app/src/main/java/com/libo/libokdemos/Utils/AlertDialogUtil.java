package com.libo.libokdemos.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by libok on 2018-01-18.
 */

public class AlertDialogUtil {

    public static void showDialog(final Context context, String title, String message, OnClickListener positiveListener, OnClickListener negativeListener) {
        showDialog(context, title, -1, -1, message, -1, -1, "了然", positiveListener,"不明",  negativeListener);
    }

    public static void showDialog(final Context context, String title, int titleColor, float titleSize, String message, int messageColor, float messageSize, String positiveText, final OnClickListener positiveListener, String negativeText, final OnClickListener negativeListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (positiveListener != null) {
                            positiveListener.onClick();
                        }
                    }
                })
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (negativeListener != null) {
                            negativeListener.onClick();
                        }
                    }
                }).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        if (titleColor == -1 && titleSize == -1 && messageColor == -1 && messageSize == -1) {
            return;
        } else {
            try {
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(alertDialog);
                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                mTitle.setAccessible(true);
                mMessage.setAccessible(true);
                TextView mTitleView = (TextView) mTitle.get(mAlertController);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                if (titleColor != -1) {
                    mTitleView.setTextColor(titleColor);
                }
                if (titleSize != -1) {
                    mTitleView.setTextSize(titleSize);
                }
                if (messageColor != -1) {
                    mMessageView.setTextColor(messageColor);
                }
                if (messageSize != -1) {
                    mMessageView.setTextSize(messageSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public interface OnClickListener {
        void onClick();
    }

}
