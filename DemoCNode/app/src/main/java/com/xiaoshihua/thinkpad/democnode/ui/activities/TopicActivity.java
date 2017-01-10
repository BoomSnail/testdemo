package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicWithReply;
import com.xiaoshihua.thinkpad.democnode.model.modelUtil.EntityUtils;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.TopicAdapter;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.TopicHeadViewHolder;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DoubleClickBackToContentTopListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.AlertDialogUtils;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;
import com.xiaoshihua.thinkpad.democnode.utils.RefreshUtils;
import com.xiaoshihua.thinkpad.democnode.ui.dialog.TopicReplyDialog;
import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import presenter.contract.ITopicPresenter;
import presenter.implement.TopicPresenter;

public class TopicActivity extends AppCompatActivity implements ITopicView,
        IBackToContentTopView, SwipeRefreshLayout.OnRefreshListener,
        Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;
    private Topic topic;

    private final List<Reply> replies = new ArrayList<>();
    private final Map<String, Integer> positionMap = new HashMap<>();

    private ITopicPresenter iTopicPresenter;
    private ITopicReplyView iTopicReplyView;
    private TopicAdapter topicAdapter;

    private TopicHeadViewHolder topicHeadViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

//        if (SettingShared.isShowTopicRenderCompatTip(this)) {
//            SettingShared.markShowTopicRenderCompatTip(this);
//            AlertDialogUtils.createBuilderWithAutoTheme(this)
//                    .setMessage(R.string.topic_render_compat_tip)
//                    .setPositiveButton(R.string.ok, null)
//                    .show();
//        }
        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID))) {
            topic = EntityUtils.gson.fromJson(getIntent().getStringExtra(Navigator
                    .TopicWithAutoCompat.EXTRA_TOPIC), Topic.class);
        }

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        iTopicReplyView = TopicReplyDialog.createWithAutoTheme(this, topicId, this);

        topicHeadViewHolder = new TopicHeadViewHolder(this, listView);
        topicHeadViewHolder.updateViews(topic, false, 0);

        topicAdapter = new TopicAdapter(this, replies, positionMap, iTopicReplyView);
        listView.setAdapter(topicAdapter);

        iconNoData.setVisibility(topic == null ? View.VISIBLE : View.GONE);

        fabReply.attachToListView(listView);

        iTopicPresenter = new TopicPresenter(this, this);

        RefreshUtils.initOnCreate(refreshLayout, this);
        RefreshUtils.refreshOnCreate(refreshLayout, this);

    }

    @Override
    public void backToContentTop() {
        listView.setSelection(0);
    }

    @Override
    public boolean onGetTopicResultOk(Result.Data<TopicWithReply> result) {
        if (ActivityUtil.isAlive(this)) {
            topic = result.getData();
            topicHeadViewHolder.updateViews(result.getData());
            replies.clear();
            replies.addAll(result.getData().getReplyList());
            positionMap.clear();
            for (int i = 0; i < replies.size(); i++) {
                Reply reply = replies.get(i);
                positionMap.put(reply.getId(), i);
            }
            topicAdapter.notifyDataSetChanged();
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
        replies.add(reply);
        positionMap.put(reply.getId(), replies.size() - 1);
        topicHeadViewHolder.updateReplyCount(replies.size());
        topicAdapter.notifyDataSetChanged();
        listView.smoothScrollToPosition(replies.size());
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
}
