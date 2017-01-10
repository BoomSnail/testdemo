package com.xiaoshihua.thinkpad.democnode.ui.base;

import android.support.v7.app.AppCompatActivity;


/**
 * obclickAgent用来统计分析当前应用进入的页面
 * Created by ThinkPad on 2016/8/5.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
