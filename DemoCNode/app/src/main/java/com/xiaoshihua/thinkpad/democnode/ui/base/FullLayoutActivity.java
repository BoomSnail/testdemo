package com.xiaoshihua.thinkpad.democnode.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 *
 * Created by ThinkPad on 2016/8/5.
 */
public abstract class FullLayoutActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
