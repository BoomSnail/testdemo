package com.xiaoshihua.thinkpad.democnode.model.modelUtil;

import android.os.Build;
import com.xiaoshihua.thinkpad.democnode.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class HttpUtils {

    public static final String USER_AGENT = "CNodeMD/" +
            BuildConfig.VERSION_NAME +
            " (Android " + Build.VERSION.RELEASE +
            "; " + Build.MANUFACTURER + " - " +
            Build.MODEL + ")";


    public static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new UserAgentInterceptor(USER_AGENT))
            .addInterceptor(createHttpLoggingInterceptor())
            .build();

    private static HttpLoggingInterceptor createHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }
}
