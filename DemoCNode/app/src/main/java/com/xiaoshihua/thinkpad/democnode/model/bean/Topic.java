package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;

import org.joda.time.DateTime;

import java.util.Formatter;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class Topic extends TopicSimple {

    @SerializedName("author_id")
    private String authorId;//作者id

    private TabType tab;//当前页标签

    private String content;//内容

    private boolean good;//是否是精华

    private boolean top;

    @SerializedName("reply_count")
    private int replyCount;//回复的数量

    @SerializedName("visit_count")
    private int visitCount;//浏览的数量

    @SerializedName("create_at")
    private DateTime createAt;//创建时间

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        content = null;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public TabType getTab() {
        return tab;
    }

    public void setTab(TabType tab) {
        this.tab = tab;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * Html渲染缓存
     */

    @SerializedName("content_html")
    private String contentHtml;

    public String getContentHtml() {
        if (contentHtml == null) {
            if (ApiDefine.MD_RENDER) {

                contentHtml = FormatUtils.handleHtml(content);
            } else {
                contentHtml = FormatUtils.handleHtml(FormatUtils.renderMarkdown(content));
            }
        }
        return contentHtml;
    }


}
