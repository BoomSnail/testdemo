package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.ui.activities.LoginActivity;
import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.utils.HandlerUtils;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;

import presenter.contract.ITopHeaderPresenter;
import presenter.contract.ITopicItemReplyPresenter;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public final class TopicJavaScriptInterface {
    public static final String NAME = "topicBridge";

    private Activity activity;
    private ITopicReplyView iTopicReplyView;
    private ITopHeaderPresenter iTopHeaderPresenter;
    private ITopicItemReplyPresenter iTopicItemReplyPresenter;

    public TopicJavaScriptInterface(Activity activity, ITopicReplyView iTopicReplyView, ITopHeaderPresenter iTopHeaderPresenter, ITopicItemReplyPresenter iTopicItemReplyPresenter) {
        this.activity = activity;
        this.iTopicReplyView = iTopicReplyView;
        this.iTopHeaderPresenter = iTopHeaderPresenter;
        this.iTopicItemReplyPresenter = iTopicItemReplyPresenter;
    }

    public void collectTopic(String topicId){
        if (LoginActivity.startForResultWithAccessTokenCheck(activity)) {
            iTopHeaderPresenter.collectTopicAsyncTask(topicId);
        }
    }

    public void decollectTopic(String topicId){
        if (LoginActivity.startForResultWithAccessTokenCheck(activity)) {
            iTopHeaderPresenter.decollectTopicAsyncTask(topicId);
        }
    }

    public void upReply(String replyJson){
        if (LoginActivity.startForResultWithAccessTokenCheck(activity)) {
            Reply reply = EntityUtils.gson.fromJson(replyJson,Reply.class);
            if (reply.getAuthor().getLoginName().equals(LoginShared.getLoginName(activity))) {
                ToastUtils.getInstance(activity).show(R.string.can_not_up_yourself_reply);
            }else {
                iTopicItemReplyPresenter.upReplyAsyncTask(reply);

            }
        }
    }

    public void  at(final String targetJson, final int targetPosition){
        if (LoginActivity.startForResultWithAccessTokenCheck(activity)) {
            HandlerUtils.post(new Runnable() {
                @Override
                public void run() {
                    Reply target  = EntityUtils.gson.fromJson(targetJson,Reply.class);
                    iTopicReplyView.onAt(target,targetPosition);
                }
            });
        }
    }


    public void openUser(String loginName){
        UserDetailActivity.start(activity,loginName);
    }
}
