package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_notification)
    SwitchCompat switchNotification;
    @BindView(R.id.switch_theme_dark)
    SwitchCompat switchThemeDark;
    @BindView(R.id.switch_topic_draft)
    SwitchCompat switchTopicDraft;
    @BindView(R.id.switch_topic_sign)
    SwitchCompat switchTopicSign;
    @BindView(R.id.btn_modify_topic_sign)
    TextView btnModifyTopicSign;
    @BindView(R.id.switch_topic_render_compat)
    SwitchCompat switchTopicRenderCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_actvity);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        switchNotification.setChecked(SettingShared.isEnableNotification(this));

        switchNotification.setChecked(SettingShared.isEnableNotification(this));
        switchThemeDark.setChecked(SettingShared.isEnableThemeDark(this));
        switchTopicDraft.setChecked(SettingShared.isEnableTopicDraft(this));
        switchTopicSign.setChecked(SettingShared.isEnableTopicSign(this));
        btnModifyTopicSign.setEnabled(SettingShared.isEnableTopicSign(this));
        switchTopicRenderCompat.setChecked(SettingShared.isEnableTopicRenderCompat(this));
    }


    @OnClick({R.id.btn_notification, R.id.switch_theme_dark, R.id.btn_topic_draft, R.id.btn_topic_sign, R.id.btn_modify_topic_sign, R.id.btn_topic_render_compat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_notification:
                switchNotification.toggle();
                SettingShared.setKeyEnableNotification(this,switchNotification.isChecked());
                break;
            case R.id.switch_theme_dark:
                switchThemeDark.toggle();
                SettingShared.setKeyEnableThemeDark(this,switchTopicDraft.isChecked());

                // TODO: 2016/8/7
                break;

            case R.id.btn_topic_draft:
                switchTopicDraft.toggle();
                SettingShared.setEnableTopicDraft(this,switchTopicDraft.isChecked());
//                btnModifyTopicSign.setEnabled(switchTopicDraft.isChecked());

            case R.id.btn_topic_sign:
                switchTopicSign.toggle();
                SettingShared.setEnableTopicSign(this, switchTopicSign.isChecked());
                btnModifyTopicSign.setEnabled(switchTopicSign.isChecked());
                break;
            case R.id.btn_modify_topic_sign:
                // TODO: 2016/8/7
                startActivity(new Intent(this,ModifyTopicSignActivity.class));
                break;
            case R.id.btn_topic_render_compat:
                switchTopicRenderCompat.toggle();
                SettingShared.setEnableTopicRenderCompat(this, switchTopicRenderCompat.isChecked());
                break;
        }
    }
}
