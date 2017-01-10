package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicSimple;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.model.bean.User;
import com.xiaoshihua.thinkpad.democnode.ui.fragment.UserDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class UserDetailAdapter extends FragmentPagerAdapter {

    private List<UserDetailFragment> fragments = new ArrayList<>();
    private final String[] titles = {
            "最近回复",
            "最新发布",
            "话题收藏"
    };
    public UserDetailAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new UserDetailFragment());
        fragments.add(new UserDetailFragment());
        fragments.add(new UserDetailFragment());
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void update(User data) {
        fragments.get(0).notifyDataSetChanged(data.getRecentReplyList());
        fragments.get(1).notifyDataSetChanged(data.getRecentTopicList());
    }

    public void update(List<Topic> data) {
        List<TopicSimple> topicWithReplies = new ArrayList<>();
        for (Topic topic: data) {
            topicWithReplies.add(topic);
        }
        fragments.get(2).notifyDataSetChanged(topicWithReplies);
    }
}
