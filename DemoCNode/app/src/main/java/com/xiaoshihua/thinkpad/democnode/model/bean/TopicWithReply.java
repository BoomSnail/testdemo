package com.xiaoshihua.thinkpad.democnode.model.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;

import java.util.List;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class TopicWithReply extends Topic {
    @SerializedName("is_collect")
    private boolean isCollect;//是否收藏该话题

    @SerializedName("replies")
    private List<Reply> replyList;

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
