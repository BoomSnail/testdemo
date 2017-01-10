package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.view.ITopicItemReplyView;

import presenter.contract.ITopicItemReplyPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class TopicItemReplyPresenter implements ITopicItemReplyPresenter {
    private Activity activity;
    private ITopicItemReplyView iTopicItemReplyView;

    public TopicItemReplyPresenter(Activity activity, ITopicItemReplyView iTopicItemReplyView) {
        this.activity = activity;
        this.iTopicItemReplyView = iTopicItemReplyView;
    }

    @Override
    public void upReplyAsyncTask(final Reply reply) {
        Call<Result.UpReply> call = ApiClient.service.upReply(reply.getId(), LoginShared.getAccessToken(activity));
        call.enqueue(new DefaultToastCallback<Result.UpReply>(activity) {
            @Override
            public boolean onResultOk(Response<Result.UpReply> response, Result.UpReply result) {
                if (result.getAction() == Reply.UpAction.up) {
                    reply.getUpList().add(LoginShared.getId(activity));
                } else if (result.getAction() == Reply.UpAction.down) {
                    reply.getUpList().remove(LoginShared.getId(activity));
                }

                return iTopicItemReplyView.onUpReplyResultOk(reply);
            }
        });
    }
}
