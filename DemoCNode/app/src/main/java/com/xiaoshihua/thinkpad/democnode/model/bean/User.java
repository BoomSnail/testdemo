package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class User {
    @SerializedName("loginname")
    private String loginName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private String githubUsrname;

    @SerializedName("creat_at")
    private DateTime createAt;

    private int score;

    @SerializedName("recent_topics")
    private List<TopicSimple> recentTopicList;

    @SerializedName("recent_replies")
    private List<TopicSimple> recentReplyList;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGithubUsrname() {
        return githubUsrname;
    }

    public void setGithubUsrname(String githubUsrname) {
        this.githubUsrname = githubUsrname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public DateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(DateTime createAt) {
        this.createAt = createAt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<TopicSimple> getRecentTopicList() {
        return recentTopicList;
    }

    public void setRecentTopicList(List<TopicSimple> recentTopicList) {
        this.recentTopicList = recentTopicList;
    }

    public List<TopicSimple> getRecentReplyList() {
        return recentReplyList;
    }

    public void setRecentReplyList(List<TopicSimple> recentReplyList) {
        this.recentReplyList = recentReplyList;
    }
}
