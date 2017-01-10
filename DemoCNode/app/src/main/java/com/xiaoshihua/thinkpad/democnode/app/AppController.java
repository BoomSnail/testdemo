package com.xiaoshihua.thinkpad.democnode.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.BuildConfig;

import com.umeng.analytics.MobclickAgent;

import org.joda.time.JodaTimePermission;


/**
 * Created by ThinkPad on 2016/8/5.
 */
public class AppController extends Application{

    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        //初始化JodaTimeAndroid


        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new AppExceptionHandler(this));
        }

        //有盟设置调试模式
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }
}
