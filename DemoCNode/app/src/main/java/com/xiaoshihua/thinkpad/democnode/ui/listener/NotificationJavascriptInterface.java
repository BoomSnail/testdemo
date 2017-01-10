package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public class NotificationJavascriptInterface {
    private volatile static NotificationJavascriptInterface singleton;

    public static NotificationJavascriptInterface with( Context context) {
        if (singleton == null) {
            synchronized (NotificationJavascriptInterface.class) {
                if (singleton == null) {
                    singleton = new NotificationJavascriptInterface(context);
                }
            }
        }
        return singleton;
    }

    public static final String NAME = "notificationBridge";

    private final Context context;

    private NotificationJavascriptInterface( Context context) {
        this.context = context.getApplicationContext();
    }

    @JavascriptInterface
    public void openTopic(String topicId) {
        Navigator.TopicWithAutoCompat.start(context, topicId);
    }

    @JavascriptInterface
    public void openUser(String loginName) {
        UserDetailActivity.start(context, loginName);
    }
}
