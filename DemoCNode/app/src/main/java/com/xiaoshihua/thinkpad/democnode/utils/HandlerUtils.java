package com.xiaoshihua.thinkpad.democnode.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class HandlerUtils {

    private HandlerUtils(){}
    private static final Handler handler = new Handler(Looper.myLooper());

    public static boolean post(Runnable r) {
        return  handler.post(r);
    }

    public static boolean postDelayed(Runnable r,long time){
        return handler.postDelayed(r,time);
    }
}
