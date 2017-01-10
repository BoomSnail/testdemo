package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.BuildConfig;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends StatusBarActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME +
            "-build-" + BuildConfig.VERSION_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        tvVersion.setText(VERSION_TEXT);
    }

    @OnClick({R.id.btn_version, R.id.btn_open_source_url, R.id.btn_about_cnode, R.id.btn_about_author, R.id.btn_open_in_market, R.id.btn_advice_feedback, R.id.btn_open_source_license})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version:

                break;
            case R.id.btn_open_source_url:
                Navigator.openInBrowser(this,getString(R.string.open_source_url_content));
                break;
            case R.id.btn_about_cnode:
                Navigator.openInBrowser(this,getString(R.string.about_cnode_content));
                break;
            case R.id.btn_about_author:
                Navigator.openInBrowser(this,getString(R.string.about_author_content));
                break;
            case R.id.btn_open_in_market:
                Navigator.openInMarket(this);
                break;
            case R.id.btn_advice_feedback:
                Navigator.openEmail(
                        this,
                        "takwolf@foxmail.com",
                        "来自 CNodeMD-" + VERSION_TEXT + " 的客户端反馈",
                        "设备信息：Android " + Build.VERSION.RELEASE + " - " + Build.MANUFACTURER + " - " + Build.MODEL + "\n（如果涉及隐私请手动删除这个内容）\n\n"
                );
                break;
            case R.id.btn_open_source_license:
                startActivity(new Intent(this,LicenseActivity.class));
                break;
        }
    }
}
