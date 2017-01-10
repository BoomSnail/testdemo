package presenter.implement;

import android.app.Activity;
import android.text.TextUtils;

import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.model.bean.Author;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;

import org.joda.time.DateTime;

import java.util.ArrayList;

import presenter.contract.ITopicReplyPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public class TopicReplyPresenter implements ITopicReplyPresenter {

    private Activity activity;
    private ITopicReplyView iTopicReplyView;

    public TopicReplyPresenter(Activity activity, ITopicReplyView iTopicReplyView) {
        this.activity = activity;
        this.iTopicReplyView = iTopicReplyView;
    }

    @Override
    public void replyTopicAsyncTask(String topicId, String content, final String targetId) {
        if (TextUtils.isEmpty(content)) {
            iTopicReplyView.onCreateEmpytError();
        }else {
            String finalContent;
            if (SettingShared.isEnableTopicSign(activity)) {
                //添加小尾巴
                finalContent = content + "\n\n" + SettingShared.getTopicSignContent(activity);
            }else {
                finalContent = content;
            }

            iTopicReplyView.onTeplyTopicStart();
            Call<Result.ReplyTopic> call = ApiClient.service.replyTopic(topicId, LoginShared.getAccessToken(activity),
                    finalContent,targetId);
            call.enqueue(new DefaultToastCallback<Result.ReplyTopic>(activity){
                @Override
                public boolean onResultOk(Response<Result.ReplyTopic> response, Result.ReplyTopic result) {
                    Reply reply = new Reply();
                    reply.setId(reply.getReplyId());
                    Author author = new Author();
                    author.setLoginName(LoginShared.getLoginName(activity));
                    author.setAvatarUrl(LoginShared.getAvatarUrl(activity));

                    reply.setAuthor(author);
                    // TODO: 2016/8/9
                    reply.setCreateAt(new DateTime());
                    reply.setUpList(new ArrayList<String>());
                    reply.setReplyId(targetId);
                    return iTopicReplyView.onReplyTopicResultOk(reply);
                }

                @Override
                public void onFinish() {
                    iTopicReplyView.onReplyTopicFinish();
                }
            });
        }
    }
}
