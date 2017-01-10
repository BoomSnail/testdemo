package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.User;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public interface IUserDetialView {

    void onGetUserstart();

    boolean onGetUserResultOk(Result.Data<User> result);

    boolean onGetUserResultError(Result.Error error);

    boolean onGetUserLoadError();

    void onGetUserFinish();

    void updateUserInfoViews(User user);

    boolean onGetCollectTopicListResultOk(Result.Data<List<Topic>> result);
}
