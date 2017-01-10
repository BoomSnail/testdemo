package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class NavigationFinishClickListener implements View.OnClickListener {

    private final Activity activity;

    public NavigationFinishClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        ActivityCompat.finishAfterTransition(activity);
    }
}
