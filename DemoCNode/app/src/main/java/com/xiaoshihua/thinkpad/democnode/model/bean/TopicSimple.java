package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class TopicSimple {

    private String id;
    private Author author;//作者
    private String title;//标题

    @SerializedName("last_reply_at")
    private DateTime lastReplyAt;//最新回复时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public DateTime getLastReplyAt() {
        return lastReplyAt;
    }

    public void setLastReplyAt(DateTime lastREplyAt) {
        this.lastReplyAt = lastREplyAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
