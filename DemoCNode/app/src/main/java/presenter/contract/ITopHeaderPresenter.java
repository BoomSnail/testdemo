package presenter.contract;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public interface ITopHeaderPresenter {

    void collectTopicAsyncTask(String topicId);

    void decollectTopicAsyncTask(String topicId);
}
