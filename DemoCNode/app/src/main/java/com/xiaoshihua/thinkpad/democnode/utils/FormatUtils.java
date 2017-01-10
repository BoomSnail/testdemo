package com.xiaoshihua.thinkpad.democnode.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class FormatUtils {

    private static final long MINUTE = 60 * 1000;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long WEEK = 7 * DAY;
    private static final long MONTH = 31 * DAY;
    private static final long YEAR = 12 * MONTH;


    public static String getRecentlyTimeText(DateTime dateTime) {
        long offset = new DateTime().getMillis() - dateTime.getMillis();
        if (offset > YEAR) {
            return (offset / YEAR) + "年前";
        } else if (offset > MONTH) {
            return (offset / MONTH) + "个月前";
        } else if (offset > WEEK) {
            return (offset / WEEK) + "周前";
        } else if (offset > DAY) {
            return (offset / DAY) + "天前";
        } else if (offset > HOUR) {
            return (offset / HOUR) + "小时前";
        } else if (offset > MINUTE) {
            return (offset / MINUTE) + "分钟前";
        } else {
            return "刚刚";
        }
    }
    /**
     * 检测是否是用户accessToken
     */
    public static boolean isAccessToken(String accessToken){
        if (TextUtils.isEmpty(accessToken)){
            return false;
        }else {
            try {
                UUID.fromString(accessToken);
                return true;
            }catch (Exception e){
                return false;
            }
        }

    }

    /**
     * 生成一个不重复的requestCode
     */
    private static int requestCodeSeed = 0;
    public  synchronized  static int createRequestCode(){
        requestCodeSeed ++;
        return requestCodeSeed;
    }

    /**
     * 获取菜单导航消息数目字符串
     */
    public static String getNavigationDisplayCountText(int count){
        if (count>90){
            return "99+";
        } else if (count<= 0) {
            return "";
        }else {
            return String.valueOf(count);
        }

    }


    /**
     * 修复头像地址的历史遗留问题
     */
    public static String getCompatAvatarUrl(String avatarUrl){
        if (!TextUtils.isEmpty(avatarUrl) && avatarUrl.startsWith("//gravatar.com/avatar/")) {
            return "http" + avatarUrl;
        }else {
            return avatarUrl;
        }
    }

    public static String handleHtml(String html) {
        String Html;
        //保证html不为空
        Html = TextUtils.isEmpty(html) ? "":html;
        //过滤css这里会自动补全用户的来凝结地址和骑牛图片网址，但是会清除class和style属性


        return Html;

    }

    public static String renderMarkdown(String content) {
        return content;
    }


//    /**
//     * 标准Url检测
//     */
//    public static boolean isUserLinkUrl(@Nullable String userUrl){
//        return !TextUtils.isEmpty(userUrl) && userUrl.startsWith();
//    }
//
//    public static boolean isTopicLinkUrl(@Nullable String url) {
//        return !TextUtils.isEmpty(url) && url.startsWith(ApiDefine.TOPIC_LINK_URL_PREFIX);
//    }

}
