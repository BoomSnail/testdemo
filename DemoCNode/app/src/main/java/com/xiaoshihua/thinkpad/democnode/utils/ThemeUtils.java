package com.xiaoshihua.thinkpad.democnode.utils;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;

/**
 * Created by ThinkPad on 2016/8/10.
 */
public class ThemeUtils {
    public static boolean configThemeBeforeOnCreate(Activity activity,int light,int dark){
        boolean enable = SettingShared.isEnableThemeDark(activity);
        activity.setTheme(enable ? dark:light);
        return enable;
    }

    public static void notifyThemeApply(Activity activity,boolean delay){
        if (delay) {
            ActivityUtil.recreateDelayed(activity);
        }else {
            ActivityUtil.recreate(activity);
        }
    }
}
