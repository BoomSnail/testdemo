package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Result;

import retrofit2.Call;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public interface ILoginView  {

    void onAccessTokenFormatError();

    void onLoginStart(Call<Result.Login> call);

    boolean onLoginResultOk(String accessToken,Result.Login login);

    boolean onLoginResultErrorAuth(Result.Error error);

    void LoginFinish();

}
