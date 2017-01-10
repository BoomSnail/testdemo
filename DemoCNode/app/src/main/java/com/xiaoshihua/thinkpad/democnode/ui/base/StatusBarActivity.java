package com.xiaoshihua.thinkpad.democnode.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.xiaoshihua.thinkpad.democnode.utils.ResUtils;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public abstract class StatusBarActivity extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            FrameLayout rootView = new FrameLayout(this);
            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            View contentView = LayoutInflater.from(this).inflate(layoutResID,rootView,false);
            contentView.setFitsSystemWindows(true);
            rootView.addView(contentView);

            View statusBarView = new View(this);
            statusBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            statusBarView.setBackgroundColor(ResUtils.getThemeAttrColor(this, android.R.attr.colorPrimaryDark));
            rootView.addView(statusBarView);
            super.setContentView(rootView);
        }else {
            super.setContentView(layoutResID);
        }
    }
}
