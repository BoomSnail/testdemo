package com.xiaoshihua.thinkpad.democnode.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.utils.EditorHandler;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.contract.ITopicReplyPresenter;
import presenter.implement.TopicReplyPresenter;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class TopicReplyDialog extends AppCompatDialog implements ITopicReplyView{

    @BindView(R.id.btn_tool_close)
    ImageView btnToolClose;
    @BindView(R.id.btn_tool_send)
    ImageView btnToolSend;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.layout_target)
    FrameLayout layoutTarget;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.layout_editor_bar)
    LinearLayout layoutEditorBar;

    private final String topicId;

    private final ProgressDialog progressDialog;

    private final ITopicView iTopicView;

    private ITopicReplyPresenter iTopicReplyPresenter;


    private String targetId = null;

    public TopicReplyDialog(Activity activity, int theme, String topicId,ITopicView iTopicView) {

        super(activity, theme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_topic_reply);

        ButterKnife.bind(this);
        this.topicId = topicId;
        this.iTopicView = iTopicView;

        progressDialog = ProgressDialog.createWithAutoTheme(activity);
        progressDialog.setMessage(R.string.posting_$_);
        progressDialog.setCancelable(false);

        new EditorHandler(activity,layoutEditorBar,edtContent);

        iTopicReplyPresenter = new TopicReplyPresenter(activity,this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
    }

    @OnClick({R.id.btn_tool_close, R.id.btn_tool_send, R.id.btn_clear_target})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tool_close:
                dismissReplyWindow();
                break;
            case R.id.btn_tool_send:
                iTopicReplyPresenter.replyTopicAsyncTask(topicId,edtContent.getText().toString().trim(),
                        targetId);
                break;
            case R.id.btn_clear_target:
                targetId = null;
                layoutTarget.setVisibility(View.GONE);
                break;
        }
    }

    public static ITopicReplyView createWithAutoTheme(Activity activity, String topicId,
                                                      ITopicView iTopicView) {

        return new TopicReplyDialog(activity, SettingShared.isEnableThemeDark(activity) ?
            R.style.AppDialogDark_Alert : R.style.AppDialogLight_Alert,topicId,iTopicView);
    }

    @Override
    public void showReplyWindow() {
        show();
    }

    @Override
    public void dismissReplyWindow() {
        dismiss();
    }

    @Override
    public void onAt(Reply target, Integer targetPosition) {
        targetId = target.getId();
        layoutTarget.setVisibility(View.VISIBLE);
        tvTarget.setText("回复：" + (targetPosition + 1) + "楼");

        edtContent.getText().insert(edtContent.getSelectionEnd(),"@" + target.getAuthor().getLoginName() + "");
        showReplyWindow();
    }

    @Override
    public void onCreateEmpytError() {
        ToastUtils.getInstance(getContext()).show(R.string.content_empty_error_tip);
        edtContent.requestFocus();
    }

    @Override
    public void onTeplyTopicStart() {
        progressDialog.show();
    }

    @Override
    public void onReplyTopicFinish() {
        progressDialog.dismiss();
    }

    @Override
    public boolean onReplyTopicResultOk(Reply reply) {
            iTopicView.appendReplyAndUpdateViews(reply);
        dismissReplyWindow();
        targetId = null;
        layoutTarget.setVisibility(View.GONE);
        edtContent.setText(null);
        ToastUtils.getInstance(getContext()).show(R.string.post_success);
        return false;
    }
}
