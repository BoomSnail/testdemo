package com.xiaoshihua.thinkpad.democnode.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ThinkPad on 2016/8/6.
 */
public class ProgressDialog extends AppCompatDialog {

    @BindView(R.id.tv_message)
    TextView tvMessage;


    public static ProgressDialog createWithAutoTheme(Activity activity) {
        return new ProgressDialog(activity,SettingShared.isEnableThemeDark(activity)?
                R.style.AppDialogDark_Alert:R.style.AppDialogLight_Alert);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        ButterKnife.bind(this);
    }

    public void setMessage(CharSequence text){
        tvMessage.setText(text);
        tvMessage.setVisibility(TextUtils.isEmpty(text)? View.GONE:View.VISIBLE);
    }

    public void setMessage(int resId){
        tvMessage.setText(resId);
        tvMessage.setVisibility(resId == 0? View.GONE:View.VISIBLE);

    }

}
