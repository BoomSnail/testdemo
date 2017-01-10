package com.xiaoshihua.thinkpad.democnode.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.AttributeSet;

import com.melnykov.fab.FloatingActionButton;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.ui.listener.ImageJavascriptInterface;
import com.xiaoshihua.thinkpad.democnode.ui.listener.TopicJavaScriptInterface;
import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;

/**
 * Created by ThinkPad on 2016/8/9.
 */
public class TopicWebView extends CNodeWebView implements IBackToContentTopView{

    private static final String LIGHT_THEME_PATH = "file:///android_asset/topic_light.html";
    private static final String DARK_THEME_PATH = "file:///android_asset/topic_dark.html";

    private FloatingActionButton fabReply;

    private boolean pageLoaded = false;
    private TopicWithReply topicWithReply;
    private String userId = null;

    public TopicWebView(Context context) {
        super(context);
    }

    public TopicWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopicWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setFabReply(FloatingActionButton fabReply) {
        this.fabReply = fabReply;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (fabReply != null) {
            if (t - oldt > 4) {
                fabReply.hide();
            } else if (t - oldl < -4) {
                fabReply.show();
            }
        }
    }

    @SuppressLint("JavascriptInterface")
    public void setBrigeAndLoadPage(TopicJavaScriptInterface topicJavaScriptInterface){
        addJavascriptInterface(ImageJavascriptInterface.getSingleton(getContext()),
                ImageJavascriptInterface.NAME);
        addJavascriptInterface(topicJavaScriptInterface,TopicJavaScriptInterface.NAME);
        loadUrl(isDarkTheme() ? DARK_THEME_PATH:LIGHT_THEME_PATH);
    }


    @Override
    public void onPageFinished(String url) {
        pageLoaded = true;
        if (topicWithReply != null) {
            updateTopicAndUserId(topicWithReply,userId);
            topicWithReply = null;
            userId = null;
        }
    }

    public void updateTopicAndUserId(TopicWithReply topicWithReply, String userId) {
        if (pageLoaded) {
            topicWithReply.getContentHtml();
            for (Reply reply : topicWithReply.getReplyList()){
                reply.getContentHtml();
            }
            loadUrl("" +
                    "javascript:\n" +
                    "updateTopicAndUserId(" + EntityUtils.gson.toJson(topicWithReply) + ", '" + userId + "');"
            );
        }else {
            this.topicWithReply = topicWithReply;
            this.userId = userId;
        }
    }

    public void updateTopicCollect(boolean isCollect){

            if (pageLoaded) {
                loadUrl("" +
                        "javascript:\n" +
                        "updateTopicCollect(" + isCollect + ");"
                );
            }
    }

    public void updateReply(Reply reply){
        if (pageLoaded) {
            reply.getContentHtml(); // 确保Html渲染
            loadUrl("" +
                    "javascript:\n" +
                    "updateReply(" + EntityUtils.gson.toJson(reply) + ");"
            );
        }
    }

    public void appendReply(Reply reply) {
        if (pageLoaded) {
            reply.getContentHtml(); // 确保Html渲染
            loadUrl("" +
                    "javascript:\n" +
                    "appendReply(" + EntityUtils.gson.toJson(reply) + ");\n" +
                    "setTimeout(function () {\n" +
                    "    window.scrollTo(0, document.body.clientHeight);\n" +
                    "}, 100);"
            );
        }
    }

    @Override
    public void backToContentTop() {
        scrollTo(0,0);
    }


}
