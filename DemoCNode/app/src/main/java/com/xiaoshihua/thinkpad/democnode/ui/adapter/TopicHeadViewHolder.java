package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.ui.activities.LoginActivity;
import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.ui.widget.ContentWebView;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.view.ITopHeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.contract.ITopHeaderPresenter;
import presenter.implement.TopicHeaderPresender;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class TopicHeadViewHolder implements ITopHeaderView {

    @BindView(R.id.layout_content)
    protected ViewGroup layoutContent;

    @BindView(R.id.icon_good)
    protected View iconGood;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    @BindView(R.id.img_avatar)
    protected ImageView imgAvatar;

    @BindView(R.id.ctv_tab)
    protected CheckedTextView ctvTab;

    @BindView(R.id.tv_login_name)
    protected TextView tvLoginName;

    @BindView(R.id.tv_create_time)
    protected TextView tvCreateTime;

    @BindView(R.id.tv_visit_count)
    protected TextView tvVisitCount;

    @BindView(R.id.btn_favorite)
    protected ImageView btnFavorite;

    @BindView(R.id.web_content)
    protected ContentWebView webContent;

    @BindView(R.id.layout_no_reply)
    protected ViewGroup layoutNoReply;

    @BindView(R.id.layout_reply_count)
    protected ViewGroup layoutReplyCount;

    @BindView(R.id.tv_reply_count)
    protected TextView tvReplyCount;

    private Activity activity;
    private Topic topic;
    private boolean isCollect;

    private ITopHeaderPresenter iTopHeaderPresenter;

    public TopicHeadViewHolder(Activity activity, ListView listView) {

        this.activity = activity;

        LayoutInflater inflater = LayoutInflater.from(activity);
        View headerView = inflater.inflate(R.layout.item_topic_header, listView, false);
        ButterKnife.bind(this, headerView);

        listView.addHeaderView(headerView, null, false);

        this.iTopHeaderPresenter = new TopicHeaderPresender(activity, this);
    }


    @Override
    public boolean onCollectTopicResultOk(Result result) {
        if (ActivityUtil.isAlive(activity)) {
            isCollect = true;
            btnFavorite.setImageResource(R.drawable.ic_favorite_theme_24dp);
            return false;
        }
        return true;
    }

    @Override
    public boolean onDecollectTopicResultOk(Result result) {
        if (ActivityUtil.isAlive(activity)) {
            isCollect = false;
            btnFavorite.setImageResource(R.drawable.ic_favorite_outline_grey600_24dp);
            return false;
        }
        return true;
    }

    @OnClick({R.id.img_avatar, R.id.btn_favorite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                UserDetailActivity.startWithTransitionAnimation(activity, topic.getAuthor().getLoginName(),
                        imgAvatar, topic.getAuthor().getAvatarUrl());
                break;
            case R.id.btn_favorite:
                if (topic != null) {
                    if (LoginActivity.startForResultWithAccessTokenCheck(activity)) {
                        if (isCollect) {
                            iTopHeaderPresenter.decollectTopicAsyncTask(topic.getId());
                        } else {
                            iTopHeaderPresenter.collectTopicAsyncTask(topic.getId());
                        }
                    }
                }
                break;
        }
    }

    public void updateViews(Topic topic, boolean isCollect, int replyCount) {
        this.topic = topic;
        this.isCollect = isCollect;
        if (topic != null) {

            layoutContent.setVisibility(View.VISIBLE);
            iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);

            tvTitle.setText(topic.getTitle());
            Glide.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).dontAnimate().into(imgAvatar);
            //  ctvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
            ctvTab.setChecked(topic.isTop());
            tvLoginName.setText(topic.getAuthor().getLoginName());
            tvCreateTime.setText(FormatUtils.getRecentlyTimeText(topic.getCreateAt()) + "创建");
            tvVisitCount.setText(topic.getVisitCount() + "次浏览");
            btnFavorite.setImageResource(isCollect ? R.drawable.ic_favorite_theme_24dp : R.drawable.ic_favorite_outline_grey600_24dp);

            webContent.loadRenderedContent(topic.getContentHtml());

            updateReplyCount(replyCount);

        } else {
            layoutContent.setVisibility(View.GONE);
            iconGood.setVisibility(View.GONE);
        }
    }

    public void updateReplyCount(int replyCount) {
        layoutNoReply.setVisibility(replyCount > 0 ? View.GONE : View.VISIBLE);
        layoutReplyCount.setVisibility(replyCount > 0 ? View.VISIBLE : View.GONE);
        tvReplyCount.setText(replyCount + "条回复");
    }

    public void updateViews(TopicWithReply data) {
        updateViews(data, data.isCollect(), data.getReplyList().size());
    }
}
