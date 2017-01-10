package com.xiaoshihua.thinkpad.democnode.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.ui.activities.NotificationCompatActivity;
import com.xiaoshihua.thinkpad.democnode.ui.activities.NotifycationActivity;
import com.xiaoshihua.thinkpad.democnode.ui.activities.TopicActivity;
import com.xiaoshihua.thinkpad.democnode.ui.activities.TopicCompatActvity;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class Navigator {

    public static void openInMarket(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.getInstance(context).show(R.string.no_market_install_in_system);
        }
    }

    public static void openInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.getInstance(context).show(R.string.no_browser_install_in_system);
        }
    }

    public static void openEmail(Context context, String email, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + email));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(intent);
        } else {
            ToastUtils.getInstance(context).show(R.string.no_email_client_install_in_system);
        }
    }

    public static void openShare(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static void openStandardLink(Context context, String url) {
//        if () {
//        }
    }


    public static final class TopicWithAutoCompat {
        public static final String EXTRA_TOPIC_ID = "topicId";
        public static final String EXTRA_TOPIC = "topic";

        private static Class<?> getTagetClass(Context context) {
            return SettingShared.isReallyEnableTopicRenderCompat(context)?
                    TopicCompatActvity.class : TopicActivity.class;
        }

        public static void start(Activity activity, Topic topic){
            Intent intent = new Intent(activity,getTagetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID,topic.getId());
            intent.putExtra(EXTRA_TOPIC, EntityUtils.gson.toJson(topic));
            activity.startActivity(intent);
        }

        public static void start(Activity activity,String topicId){
            Intent intent = new Intent(activity,getTagetClass(activity));
            intent.putExtra(EXTRA_TOPIC_ID,topicId);
            activity.startActivity(intent);
        }

        public static void start(Context context, String topicId) {

        }
    }
}