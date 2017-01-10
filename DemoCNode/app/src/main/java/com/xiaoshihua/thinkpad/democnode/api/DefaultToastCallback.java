package com.xiaoshihua.thinkpad.democnode.api;

import android.app.Activity;
import android.app.AliasActivity;
import android.content.DialogInterface;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.api.CallbackAdapter;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.ui.activities.LoginActivity;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.AlertDialogUtils;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;

import retrofit2.Response;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class DefaultToastCallback <T extends Result> extends CallbackAdapter<T> {
    private final Activity activity;

    public DefaultToastCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onResultError(Response<T> response, Result.Error error) {
       if (response.code() == 401){
           return onResultErrorAuth(response,error);
       }else {
           return onResultErrorOther(response,error);
       }
    }

    private boolean onResultErrorOther(Response<T> response, Result.Error error) {
        if (ActivityUtil.isAlive(activity)){
            ToastUtils.getInstance(activity).show(error.getErrorMessage());
        }
        return false;
    }

    private boolean onResultErrorAuth(Response<T> response, Result.Error error) {
        if (ActivityUtil.isAlive(activity)){

            AlertDialogUtils.createBuilderWithAutoTheme(activity)
                    .setMessage(R.string.access_token_out_of_date)
                    .setPositiveButton(R.string.relogin, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            LoginActivity.startForResult(activity);
                        }
                    })
                    .setNegativeButton(R.string.cancel,null)
                    .show();
        }
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, Result.Error error) {
        if (ActivityUtil.isAlive(activity)){
            ToastUtils.getInstance(activity).show(error.getErrorMessage());
        }
        return false;
    }

}
