package com.xiaoshihua.thinkpad.democnode.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class ToastUtils {
    private static ToastUtils mInstance;

    public static ToastUtils getInstance(@Nullable Context context) {
        if (mInstance == null) {
            synchronized (ToastUtils.class) {
                if (mInstance == null) {
                    mInstance = new ToastUtils(context);
                }
            }
        }
        return mInstance;
    }

    private final Toast toast;

    @SuppressLint("ShowToast")
    private ToastUtils(@NonNull Context context) {
        toast = Toast.makeText(context.getApplicationContext(), null, Toast.LENGTH_SHORT);
    }

    public void show(CharSequence msg) {
        toast.setText(msg);
        toast.show();
    }

    public void show(@StringRes int resId) {
        toast.setText(resId);
        toast.show();
    }

//
//    public void showToast(Context context, String title, int duration) {
//        Toast.makeText(context, title, duration).show();
//    }
//
//    public void showToast(Context context, int ResId, int duration) {
//        Toast.makeText(context, ResId, duration);
//    }

}
