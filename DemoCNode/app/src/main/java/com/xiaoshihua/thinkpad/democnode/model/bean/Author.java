package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class Author {

    @SerializedName("loginname")//登陆名
    private String loginName;

    @SerializedName("avatar_url")//头像url
    private String avatarUrl;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
