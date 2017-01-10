package com.xiaoshihua.thinkpad.democnode.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xiaoshihua.thinkpad.democnode.utils.Digest;

import java.util.UUID;

/**
 *
 * Created by ThinkPad on 2016/8/5.
 */
public class DeviceInfo {
    private static final String TAG = "DeviceInfo";

    private static final String KEY_DEVICE_TOKEN = "deviceToken";

    private volatile static String deviceToken = null;

    private static SharedPreferences getSharePreference(Context context){
//        return  context.getSharedPreferences();
        return context.getSharedPreferences(Digest.MD5.getHex(TAG),Context.MODE_PRIVATE);
    }


    public static String getDeviceToken(Context context){
        if (TextUtils.isEmpty(deviceToken)){
            synchronized (DeviceInfo.class){
                deviceToken = getSharePreference(context).getString(KEY_DEVICE_TOKEN,null);
            }
        }
        if (TextUtils.isEmpty(deviceToken)){
            deviceToken = UUID.randomUUID().toString();
            getSharePreference(context).edit().putString(KEY_DEVICE_TOKEN,deviceToken).apply();
        }
        return deviceToken;
    }

}
