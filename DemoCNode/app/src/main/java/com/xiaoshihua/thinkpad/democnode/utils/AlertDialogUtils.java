package com.xiaoshihua.thinkpad.democnode.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class AlertDialogUtils {
    public static AlertDialog.Builder createBuilderWithAutoTheme(Activity activity){
        return new AlertDialog.Builder(activity, SettingShared.isEnableThemeDark(activity) ?
                R.style.AppDialogDark_Alert : R.style.AppDialogLight_Alert);
    }
}
