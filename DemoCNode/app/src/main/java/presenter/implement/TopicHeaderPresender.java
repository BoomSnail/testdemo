package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.view.ITopHeaderView;

import presenter.contract.ITopHeaderPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class TopicHeaderPresender implements ITopHeaderPresenter {
    private Activity activity;
    private ITopHeaderView iTopHeaderView;

    public TopicHeaderPresender(Activity activity, ITopHeaderView iTopHeaderView) {
        this.activity = activity;
        this.iTopHeaderView = iTopHeaderView;
    }

    @Override
    public void collectTopicAsyncTask(String topicId) {
        Call<Result> call = ApiClient.service.collectTopic(LoginShared.getAccessToken(activity), topicId);
        call.enqueue(new DefaultToastCallback<Result>(activity) {
            @Override
            public boolean onResultOk(Response<Result> response, Result result) {
                return iTopHeaderView.onCollectTopicResultOk(result);
            }
        });
    }

    @Override
    public void decollectTopicAsyncTask(String topicId) {
        Call<Result> call = ApiClient.service.decollectTopic(LoginShared.getAccessToken(activity), topicId);
        call.enqueue(new DefaultToastCallback<Result>(activity) {
            @Override
            public boolean onResultOk(Response<Result> response, Result result) {
                return iTopHeaderView.onDecollectTopicResultOk(result);
            }
        });
    }
}
