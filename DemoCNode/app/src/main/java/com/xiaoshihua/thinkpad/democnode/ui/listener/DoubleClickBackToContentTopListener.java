package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.view.View;

import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class DoubleClickBackToContentTopListener extends onDoubleClickListener {

    private final IBackToContentTopView iBackToContentTopView;

    public DoubleClickBackToContentTopListener(IBackToContentTopView iBackToContentTopView) {
        super(300);

        this.iBackToContentTopView = iBackToContentTopView;
    }

    @Override
    public void onDoubleClick(View view) {
        iBackToContentTopView.backToContentTop();
    }
}
