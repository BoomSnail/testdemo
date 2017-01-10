package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public interface ITopHeaderView {

    boolean onCollectTopicResultOk(Result result);

    boolean onDecollectTopicResultOk(Result result);

}
