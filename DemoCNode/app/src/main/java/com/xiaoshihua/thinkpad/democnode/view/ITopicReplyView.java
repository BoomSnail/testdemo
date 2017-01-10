package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public interface ITopicReplyView {

    void showReplyWindow();

    void dismissReplyWindow();

    void  onAt(Reply target,Integer targetPosition);

    void onCreateEmpytError();

    void onTeplyTopicStart();

    void onReplyTopicFinish();

    boolean onReplyTopicResultOk(Reply reply);

}
