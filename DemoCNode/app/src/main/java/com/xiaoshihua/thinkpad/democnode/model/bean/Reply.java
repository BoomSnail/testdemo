package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class Reply {

    public enum UpAction{
        up,down
    }

    private String id;

    private Author author;

    private String content;

    @SerializedName("ups")
    private List<String> upList;

    @SerializedName("reply_id")
    private String replyId;

    @SerializedName("create_at")
    private DateTime createAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        content = null;//清楚已经处理的Html渲染缓存
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getUpList() {
        return upList;
    }

    public void setUpList(List<String> upList) {
        this.upList = upList;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * html渲染缓存
     */
    @SerializedName("content_html")
    private String contentHtml;

    public String getContentHtml() {
        if (contentHtml == null){

        }
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {

        this.contentHtml = contentHtml;
    }
}
