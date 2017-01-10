package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.view.View;

import butterknife.OnClick;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public abstract class onDoubleClickListener implements View.OnClickListener {

    private final long delayTime;
    private long lastClickTime = 0;

    public onDoubleClickListener(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onClick(View view) {
        long nowClickTime = System.currentTimeMillis();
        if (nowClickTime - lastClickTime > delayTime) {
            lastClickTime = nowClickTime;
        }else {
            onDoubleClick(view);
        }
    }

    public abstract void onDoubleClick(View view);
}
