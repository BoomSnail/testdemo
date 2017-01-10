package presenter.contract;


/**
 * Created by ThinkPad on 2016/8/7.
 */
public interface IUserDetailPresenter {

    void getUserAsyncter(String loginName);

    void getCollectTopicListAsyncTask(String loginName);
}
