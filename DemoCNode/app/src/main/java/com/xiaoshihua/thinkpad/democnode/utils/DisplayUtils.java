package com.xiaoshihua.thinkpad.democnode.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class DisplayUtils {

    public static void adaptStatusBar(Context context, View statusBar) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ResUtils.getStatusBarHeight(context);
            statusBar.setLayoutParams(layoutParams);
        }
    }
}
