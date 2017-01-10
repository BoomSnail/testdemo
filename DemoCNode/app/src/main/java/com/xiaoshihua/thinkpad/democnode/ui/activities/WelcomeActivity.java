package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.ui.base.BaseActivity;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;

public class WelcomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityUtil.isAlive(WelcomeActivity.this)){
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
            }
        },2000);
//        HandlerUtils.postDelayed(this,2000);
    }

//    @Override
//    public void run() {
//        if (ActivityUtil.isAlive(this)){
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
//        }
//    }
}
