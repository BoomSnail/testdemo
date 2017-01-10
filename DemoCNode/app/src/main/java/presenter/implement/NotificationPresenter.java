package presenter.implement;

import android.app.Activity;

import com.xiaoshihua.thinkpad.democnode.api.DefaultToastCallback;
import com.xiaoshihua.thinkpad.democnode.api.ApiClient;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.model.bean.Notification;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.view.INotificationView;

import presenter.contract.INotificationPresenter;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * Created by ThinkPad on 2016/8/7.
 */
public class NotificationPresenter implements INotificationPresenter {

    private final Activity activity;
    private final INotificationView iNotificationView;

    public NotificationPresenter(Activity activity, INotificationView iNotificationView) {
        this.activity = activity;
        this.iNotificationView = iNotificationView;
    }

    @Override
    public void getMessagesAsyncTask() {
        Call<Result.Data<Notification>> call = ApiClient.service.getMessages(
                LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER
        );
        call.enqueue(new DefaultToastCallback<Result.Data<Notification>>(activity){
            @Override
            public boolean onResultOk(Response<Result.Data<Notification>> response, Result.Data<Notification> result) {
                return iNotificationView.onGetMessagesResultOk(result);
            }

            @Override
            public void onFinish() {
                iNotificationView.onGetMessagesFinish();
            }
        });
    }

    @Override
    public void markAllMessageReadAsyncTask() {

        Call<Result> call = ApiClient.service.markAllMessageRead(
                LoginShared.getAccessToken(activity)
        );
        call.enqueue(new DefaultToastCallback<Result>(activity){
            @Override
            public boolean onResultOk(Response<Result> response, Result result) {
                return iNotificationView.onMarkAllMessageReadResultOk();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
