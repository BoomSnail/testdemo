package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public interface ITopicView {

    boolean onGetTopicResultOk(Result.Data<TopicWithReply> result);

    void onGetTopicFinish();

    void appendReplyAndUpdateViews(Reply reply);
}
