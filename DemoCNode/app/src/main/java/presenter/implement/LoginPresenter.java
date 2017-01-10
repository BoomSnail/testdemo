package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.view.ILoginView;

import presenter.contract.ILoginPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class LoginPresenter implements ILoginPresenter {
    private final Activity activity;
    private final ILoginView loginView;

    public LoginPresenter(Activity activity, ILoginView loginView) {
        this.activity = activity;
        this.loginView = loginView;
    }

    @Override
    public void loginAsyncTask(final String accessToken) {
        if (!FormatUtils.isAccessToken(accessToken)){
            loginView.onAccessTokenFormatError();
        }else {
            Call<Result.Login> call = ApiClient.service.accessToken(accessToken);
            loginView.onLoginStart(call);
            call.enqueue(new DefaultToastCallback<Result.Login>(activity){
                @Override
                public boolean onResultOk(Response<Result.Login> response, Result.Login result) {
                    return loginView.onLoginResultOk(accessToken,result);
                }

                @Override
                public boolean onResultError(Response<Result.Login> response, Result.Error error) {
                    return loginView.onLoginResultErrorAuth(error);
                }

                @Override
                public void onFinish() {
                    loginView.LoginFinish();
                }
            });
        }
    }
}
