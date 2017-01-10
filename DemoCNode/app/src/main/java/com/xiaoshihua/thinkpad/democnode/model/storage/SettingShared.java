package com.xiaoshihua.thinkpad.democnode.model.storage;

import android.content.Context;
import android.os.Build;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class SettingShared {
    private static final String TAG = "SettingShared";

    private static final String KEY_ENABLE_NOTIFICATION = "enableNotification";
    private static final String KEY_ENABLE_THEME_DARK = "enableThemeDark";
    private static final String KEY_ENABLE_TOPIC_DRAFT = "enableTopicDraft";
    private static final String KEY_ENABLE_TOPIC_SIGN = "enableTopicSign";
    private static final String KEY_TOPIC_SIGN_CONTENT = "topicSignContent";
    private static final String KEY_ENABLE_TOPIC_RENDER_COMPAT = "enableTopicRenderCompat";
    private static final String KEY_SHOW_TOPIC_RENDER_COMPAT_TIP = "showTopicRenderCompatTip";

    public static final String DEFAULT_TOPIC_SIGN_CONTENT = "来自酷炫的 [CNodeMD](https://github.com/TakWolf/CNode-Material-Design)";

    public static boolean isEnableNotification(Context context){
        return SharedWrapper.getInstance(context,TAG).getBoolean(KEY_ENABLE_NOTIFICATION,true);
    }

    public static void setKeyEnableNotification(Context context,boolean enable){
        SharedWrapper.getInstance(context,TAG).setBoolean(KEY_ENABLE_NOTIFICATION,enable);
    }

    public static boolean isEnableThemeDark(Context context){
        return SharedWrapper.getInstance(context,TAG).getBoolean(KEY_ENABLE_THEME_DARK,false);
    }

    public static void  setKeyEnableThemeDark(Context context,boolean enable){
        SharedWrapper.getInstance(context,TAG).setBoolean(KEY_ENABLE_THEME_DARK,enable);
    }


    public static boolean isEnableTopicDraft(Context context) {
        return SharedWrapper.getInstance(context,TAG).getBoolean(KEY_ENABLE_TOPIC_DRAFT,true);
    }

    public static void setEnableTopicDraft(Context context, boolean enable) {
        SharedWrapper.getInstance(context, TAG).setBoolean(KEY_ENABLE_TOPIC_DRAFT, enable);
    }

    public static boolean isEnableTopicSign(Context context) {
        return SharedWrapper.getInstance(context, TAG).getBoolean(KEY_ENABLE_TOPIC_SIGN, true);
    }

    public static void setEnableTopicSign(Context context, boolean enable) {
        SharedWrapper.getInstance(context, TAG).setBoolean(KEY_ENABLE_TOPIC_SIGN, enable);
    }

    public static String getTopicSignContent( Context context) {
        return SharedWrapper.getInstance(context, TAG).getString(KEY_TOPIC_SIGN_CONTENT, DEFAULT_TOPIC_SIGN_CONTENT);
    }

    public static void setTopicSignContent( Context context,  String content) {
        SharedWrapper.getInstance(context, TAG).setString(KEY_TOPIC_SIGN_CONTENT, content);
    }

    public static boolean isEnableTopicRenderCompat( Context context) {
        return SharedWrapper.getInstance(context, TAG).getBoolean(KEY_ENABLE_TOPIC_RENDER_COMPAT, Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP);
    }

    public static void setEnableTopicRenderCompat( Context context, boolean enable) {
        SharedWrapper.getInstance(context, TAG).setBoolean(KEY_ENABLE_TOPIC_RENDER_COMPAT, enable);
    }

    /**
     * 检测是否真正使用兼容模式
     */

    public static boolean isReallyEnableTopicRenderCompat( Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && isEnableTopicRenderCompat(context);
    }

    public static boolean isShowTopicRenderCompatTip( Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && SharedWrapper.getInstance(context, TAG).getBoolean(KEY_SHOW_TOPIC_RENDER_COMPAT_TIP, true);
    }

    public static void markShowTopicRenderCompatTip( Context context) {
        SharedWrapper.getInstance(context, TAG).setBoolean(KEY_SHOW_TOPIC_RENDER_COMPAT_TIP, false);
    }

}
