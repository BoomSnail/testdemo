package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.ResUtils;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LicenseActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_license)
    TextView tvLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        if (R.raw.open_source != 0) {
            tvLicense.setText(ResUtils.getRawString(this,R.raw.open_source));
        }else {
            tvLicense.setText(null);
            ToastUtils.getInstance(this).show("获取资源失败");
        }

    }
}
