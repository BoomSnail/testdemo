package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.dialog.TopicReplyDialog;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DoubleClickBackToContentTopListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.TopicJavaScriptInterface;
import com.xiaoshihua.thinkpad.democnode.ui.widget.TopicWebView;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;
import com.xiaoshihua.thinkpad.democnode.utils.RefreshUtils;
import com.xiaoshihua.thinkpad.democnode.view.ITopHeaderView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicItemReplyView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.contract.ITopHeaderPresenter;
import presenter.contract.ITopicItemReplyPresenter;
import presenter.contract.ITopicPresenter;
import presenter.implement.TopicHeaderPresender;
import presenter.implement.TopicItemReplyPresenter;
import presenter.implement.TopicPresenter;

public class TopicCompatActvity extends StatusBarActivity implements ITopicView,
        ITopHeaderView, ITopicItemReplyView,
        SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.web_topic)
    TopicWebView webTopic;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;
    private Topic topic;

    private ITopicReplyView iTopicReplyView;

    private ITopicPresenter iTopicPresenter;
    private ITopHeaderPresenter iTopHeaderPresenter;
    private ITopicItemReplyPresenter iTopicItemReplyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_compat_actvity);
        ButterKnife.bind(this);

        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(webTopic));

        iTopicPresenter = new TopicPresenter(this, this);
        iTopHeaderPresenter = new TopicHeaderPresender(this, this);
        iTopicItemReplyPresenter = new TopicItemReplyPresenter(this, this);

        iTopicReplyView = TopicReplyDialog.createWithAutoTheme(this, topicId, this);

        webTopic.setFabReply(fabReply);
        webTopic.setBrigeAndLoadPage(new TopicJavaScriptInterface(this, iTopicReplyView,
                iTopHeaderPresenter, iTopicItemReplyPresenter));

        RefreshUtils.initOnCreate(refreshLayout, this);
        RefreshUtils.refreshOnCreate(refreshLayout, this);
    }

    @Override
    public boolean onCollectTopicResultOk(Result result) {
        if (ActivityUtil.isAlive(this)) {
            webTopic.updateTopicCollect(true);
            return false;
        }
        return true;
    }

    @Override
    public boolean onDecollectTopicResultOk(Result result) {
        if (ActivityUtil.isAlive(this)) {
            webTopic.updateTopicCollect(false);
            return false;
        }
        return true;
    }

    @Override
    public boolean onUpReplyResultOk(Reply reply) {
        if (ActivityUtil.isAlive(this)) {
            webTopic.updateReply(reply);
            return false;
        }
        return true;
    }

    @Override
    public boolean onGetTopicResultOk(Result.Data<TopicWithReply> result) {
        if (ActivityUtil.isAlive(this)) {
            topic = result.getData();
            webTopic.updateTopicAndUserId(result.getData(), LoginShared.getId(this));
            iconNoData.setVisibility(View.GONE);
            return false;
        }
        return true;
    }

    @Override
    public void onGetTopicFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void appendReplyAndUpdateViews(Reply reply) {
        webTopic.appendReply(reply);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (topic != null) {
                    Navigator.openShare(this, "《" + topic.getTitle() + "》\n" + ApiDefine.TOPIC_LINK_URL_PREFIX + topicId + "\n—— 来自CNode社区");
                }
                return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {

        iTopicPresenter.getTopicAsyncTask(topicId);
    }

    @OnClick(R.id.fab_reply)
    public void onClick() {
        if (topic != null && LoginActivity.startForResultWithAccessTokenCheck(this)) {
            iTopicReplyView.showReplyWindow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_LOGIN &&
                resultCode == RESULT_OK) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }
}
