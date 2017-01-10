package presenter.contract;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public interface ITopicReplyPresenter {
    void replyTopicAsyncTask(String topicId,String content,String targetId);
}
