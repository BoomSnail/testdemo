package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.CallbackAdapter;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.User;
import com.xiaoshihua.thinkpad.democnode.utils.HandlerUtils;
import com.xiaoshihua.thinkpad.democnode.view.IUserDetialView;

import java.util.List;

import presenter.contract.IUserDetailPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class UserDetailPresenter implements IUserDetailPresenter{
    private final Activity activity;
    private final IUserDetialView iUserDetialView;

    public UserDetailPresenter(Activity activity, IUserDetialView iUserDetialView) {
        this.activity = activity;
        this.iUserDetialView = iUserDetialView;
    }

    @Override
    public void getUserAsyncter(String loginName) {
        Call<Result.Data<User>> call = ApiClient.service.getUser(loginName);
        call.enqueue(new CallbackAdapter<Result.Data<User>>(){
            private long startLoadingTime = System.currentTimeMillis();

            private long getPostTime(){
                long postTime = 1000 - (System.currentTimeMillis() - startLoadingTime);
                if (postTime > 0) {
                    return postTime;
                }else {
                    return 0;
                }
            }

            @Override
            public boolean onResultOk(Response<Result.Data<User>> response, final Result.Data<User> result) {
                HandlerUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!iUserDetialView.onGetUserResultOk(result)) {
                            onFinish();
                        }
                    }
                },getPostTime());
                return false;
            }

            @Override
            public boolean onResultError(final Response<Result.Data<User>> response, final Result.Error error) {
                HandlerUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean interrupt;
                        if (response.code() == 404) {
                            interrupt = iUserDetialView.onGetUserResultError(error);
                        }else {
                            interrupt = iUserDetialView.onGetUserLoadError();
                        }

                        if (!interrupt) {
                            onFinish();
                        }
                    }
                },getPostTime());
                return true;
            }

            @Override
            public boolean onCallException(Throwable t, Result.Error error) {
                HandlerUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!iUserDetialView.onGetUserLoadError()){
                            onFinish();
                        }
                    }
                },getPostTime());
                return true;
            }

            @Override
            public void onFinish() {
                iUserDetialView.onGetUserFinish();
            }
        });
    }

    @Override
    public void getCollectTopicListAsyncTask(String loginName) {
        Call<Result.Data<List<Topic>>> call = ApiClient.service.getCollectTopicList(loginName);
        call.enqueue(new DefaultToastCallback<Result.Data<List<Topic>>>(activity){
            @Override
            public boolean onResultOk(Response<Result.Data<List<Topic>>> response, Result.Data<List<Topic>> result) {
                return iUserDetialView.onGetCollectTopicListResultOk(result);
            }
        });
    }
}
