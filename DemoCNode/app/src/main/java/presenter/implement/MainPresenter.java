package presenter.implement;

import android.app.Activity;
import android.text.TextUtils;

import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.api.CallbackAdapter;
import com.xiaoshihua.thinkpad.democnode.app.AppController;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.TabType;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.User;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.view.IMainView;

import java.util.List;

import presenter.contract.IMainPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class MainPresenter implements IMainPresenter{

    private final Activity activity;
    private final IMainView iMainView;

    public MainPresenter(Activity activity, IMainView iMainView) {
        this.activity = activity;
        this.iMainView = iMainView;
    }

    @Override
    public void refreshTopicListAsyncTask(final TabType tabType, Integer limit) {
        Call<Result.Data<List<Topic>>> call = ApiClient.service.getTopicList(tabType,1,limit, ApiDefine.MD_RENDER);
        call.enqueue(new CallbackAdapter<Result.Data<List<Topic>>>(){

            //返回结果成功
            @Override
            public boolean onResultOk(Response<Result.Data<List<Topic>>> response, Result.Data<List<Topic>> result) {
                //mainView刷新完成
                return iMainView.onRefreshTopicListResultOk(tabType,result);
            }

            //返回结果错误
            @Override
            public boolean onResultError(Response<Result.Data<List<Topic>>> response, Result.Error error) {
                //
                return iMainView.onRefreshTopicListResultErrorOrCallException(tabType,error);
            }

            @Override
            public boolean onCallException(Throwable t, Result.Error error) {
                return iMainView.onRefreshTopicListResultErrorOrCallException(tabType,error);
            }

            @Override
            public void onFinish() {
                iMainView.onRefreshTopicListFinish();
            }
        });


    }

    @Override
    public void loadMoreTopicListAsyncTask(final TabType tabType, final Integer page, Integer limit) {
        Call<Result.Data<List<Topic>>> call = ApiClient.service.getTopicList(tabType,page + 1,limit,ApiDefine.MD_RENDER);
        call.enqueue(new CallbackAdapter<Result.Data<List<Topic>>>(){
            @Override
            public boolean onResultOk(Response<Result.Data<List<Topic>>> response, Result.Data<List<Topic>> result) {
                return iMainView.onLoadMoreTopicListResultOk(tabType,page,result);
            }

            @Override
            public boolean onResultError(Response<Result.Data<List<Topic>>> response, Result.Error error) {
                return iMainView.onLoadMoreTopicListResultErrorOrCallException(tabType,page,error);
            }

            @Override
            public boolean onCallException(Throwable t, Result.Error error) {
                return iMainView.onLoadMoreTopicListResultErrorOrCallException(tabType,page,error);
            }

            @Override
            public void onFinish() {
                iMainView.onLoadMoreTopicListFinish();
            }
        });
    }

    @Override
    public void getUserAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if (!TextUtils.isEmpty(accessToken)) {
            Call<Result.Data<User>> call = ApiClient.service.getUser(LoginShared.getLoginName(activity));
            call.enqueue(new CallbackAdapter<Result.Data<User>>(){
                @Override
                public boolean onResultOk(Response<Result.Data<User>> response, Result.Data<User> result) {
                    if (TextUtils.equals(accessToken,LoginShared.getAccessToken(activity))){

                        LoginShared.upDate(activity,result.getData());
                        iMainView.updateUserInfoViews();
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void getMessageCountAsyncTask() {
        final String accessToken = LoginShared.getAccessToken(activity);
        if (!TextUtils.isEmpty(accessToken)){
            Call<Result.Data<Integer>> call = ApiClient.service.getMessageCount(accessToken);
            call.enqueue(new CallbackAdapter<Result.Data<Integer>>(){
                @Override
                public boolean onResultOk(Response<Result.Data<Integer>> response, Result.Data<Integer> result) {
                    if (TextUtils.equals(accessToken,LoginShared.getLoginName(activity))){
                        iMainView.updateMessageCountViews(result);
                    }
                    return false;
                }
            });
        }
    }
}
