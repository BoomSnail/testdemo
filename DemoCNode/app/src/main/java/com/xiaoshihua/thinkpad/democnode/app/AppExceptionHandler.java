package com.xiaoshihua.thinkpad.democnode.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaoshihua.thinkpad.democnode.ui.activities.CrashLogActivity;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class AppExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context context;

    public AppExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Intent intent = new Intent(context, CrashLogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable("e",throwable);
        intent.putExtras(bundle);
        context.startActivity(intent);
        System.exit(1);
    }
}
