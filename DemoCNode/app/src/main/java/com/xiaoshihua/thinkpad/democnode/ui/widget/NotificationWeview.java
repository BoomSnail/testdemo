package com.xiaoshihua.thinkpad.democnode.ui.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.xiaoshihua.thinkpad.democnode.model.bean.Message;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.ui.listener.ImageJavascriptInterface;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NotificationJavascriptInterface;
import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public class NotificationWeview extends CNodeWebView implements IBackToContentTopView {

    private static final String LIGHT_THEME_PATH = "file:///android_asset/notification_light.html";
    private static final String DARK_THEME_PATH = "file:///android_asset/notification_dark.html";

    private boolean pageLoad = false;
    private List<Message> messageList = null;

    public NotificationWeview(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public NotificationWeview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public NotificationWeview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NotificationWeview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("AddJavascriptInterface")
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        addJavascriptInterface(ImageJavascriptInterface.getSingleton(context), ImageJavascriptInterface.NAME);
        addJavascriptInterface(NotificationJavascriptInterface.with(context), NotificationJavascriptInterface.NAME);
        loadUrl(isDarkTheme() ? DARK_THEME_PATH : LIGHT_THEME_PATH);
    }

    @Override
    public void onPageFinished(String url) {
        pageLoad = true;
        if (messageList != null) {
            updateMessageList(messageList);
            messageList = null;
        }
    }

    public void updateMessageList( List<Message> messageList) {
        if (pageLoad) {
            for (Message message : messageList) {
                message.getReply().getContentHtml(); // 确保Html渲染
            }
            loadUrl("" +
                    "javascript:\n" +
                    "updateMessages(" + EntityUtils.gson.toJson(messageList) + ");"
            );
        } else {
            this.messageList = messageList;
        }
    }

    public void markAllMessageRead() {
        if (pageLoad) {
            loadUrl("" +
                    "javascript:\n" +
                    "markAllMessageRead();"
            );
        }
    }

    @Override
    public void backToContentTop() {
        scrollTo(0, 0);
    }
}
