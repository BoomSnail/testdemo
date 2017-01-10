package presenter.contract;

import com.xiaoshihua.thinkpad.democnode.model.bean.TabType;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public interface IMainPresenter {

    //刷新话题
    void refreshTopicListAsyncTask(TabType tabType,Integer limit);
    //加载话题
    void loadMoreTopicListAsyncTask(TabType tabType,Integer page,Integer limit);

    //获取个人信息
    void getUserAsyncTask();

    //获取消息数目
    void getMessageCountAsyncTask();
}
