package com.xiaoshihua.thinkpad.democnode.view;

import com.xiaoshihua.thinkpad.democnode.model.bean.Notification;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public interface INotificationView {
    //获取数据成功后调用
    boolean onGetMessagesResultOk(Result.Data<Notification> result);
    //获取数据完成后调用
    void onGetMessagesFinish();


    boolean onMarkAllMessageReadResultOk();

}
