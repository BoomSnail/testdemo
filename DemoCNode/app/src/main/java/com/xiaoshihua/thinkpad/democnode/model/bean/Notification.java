package com.xiaoshihua.thinkpad.democnode.model.bean;

import android.os.*;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class Notification {
    @SerializedName("had_read_mesaage")
    private List<Message> hasReadMessageList;

    @SerializedName("hasnot_read_messages")
    private List<Message> hasNotReadMessageList;

    public List<Message> getHasReadMessageList() {
        return hasReadMessageList;
    }

    public void setHasReadMessageList(List<Message> hasReadMessageList) {
        this.hasReadMessageList = hasReadMessageList;
    }

    public List<Message> getHasNotReadMessageList() {
        return hasNotReadMessageList;
    }

    public void setHasNotReadMessageList(List<Message> hasNotReadMessageList) {
        this.hasNotReadMessageList = hasNotReadMessageList;
    }
}
