package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.view.ITopicView;

import presenter.contract.ITopicPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class TopicPresenter implements ITopicPresenter{

    private final Activity activity;
    private final ITopicView iTopicView;

    public TopicPresenter(Activity activity, ITopicView iTopicView) {
        this.activity = activity;
        this.iTopicView = iTopicView;
    }

    @Override
    public void getTopicAsyncTask(String topicId) {
        Call<Result.Data<TopicWithReply>> call = ApiClient.service.getTop(topicId,
                LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER);
        call.enqueue(new DefaultToastCallback<Result.Data<TopicWithReply>>(activity){
            @Override
            public boolean onResultOk(Response<Result.Data<TopicWithReply>> response, Result.Data<TopicWithReply> result) {

                return iTopicView.onGetTopicResultOk(result);
            }

            @Override
            public void onFinish() {
                iTopicView.onGetTopicFinish();
            }
        });
    }
}
