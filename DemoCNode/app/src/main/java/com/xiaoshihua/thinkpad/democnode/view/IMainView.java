package com.xiaoshihua.thinkpad.democnode.view;

import android.support.annotation.NonNull;

import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.TabType;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public interface IMainView {

    //刷新成功后回调
    boolean onRefreshTopicListResultOk(TabType tab, Result.Data<List<Topic>> result);

    //刷新失败后回调
    boolean onRefreshTopicListResultErrorOrCallException(TabType tab, Result.Error error);

    //刷新完成后回掉
    void onRefreshTopicListFinish();

    //加载更多成功后回调
    boolean onLoadMoreTopicListResultOk(TabType tab, Integer page, Result.Data<List<Topic>> result);

    //加载更多失败后回调
    boolean onLoadMoreTopicListResultErrorOrCallException(TabType tab, Integer page, Result.Error error);

    //加载更多完成后回调
    void onLoadMoreTopicListFinish();

    //更新用回信息
    void updateUserInfoViews();

    //更新消息数目回调
    void updateMessageCountViews(Result.Data<Integer> result);

}
