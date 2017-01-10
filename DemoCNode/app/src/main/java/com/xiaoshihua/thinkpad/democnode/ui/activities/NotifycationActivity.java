package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Message;
import com.xiaoshihua.thinkpad.democnode.model.bean.Notification;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.NotificationAdapter;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DoubleClickBackToContentTopListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.RefreshUtils;
import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;
import com.xiaoshihua.thinkpad.democnode.view.INotificationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenter.contract.INotificationPresenter;
import presenter.implement.NotificationPresenter;

public class NotifycationActivity extends StatusBarActivity implements
        Toolbar.OnMenuItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        IBackToContentTopView,
        INotificationView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    List<Message> messageList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    private INotificationPresenter iNotificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifycation);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.notification);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(this, messageList);
        recyclerView.setAdapter(notificationAdapter);

        iNotificationPresenter = new NotificationPresenter(this, this);

        RefreshUtils.initOnCreate(refreshLayout,this);
        RefreshUtils.refreshOnCreate(refreshLayout,this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done_all:
                iNotificationPresenter.markAllMessageReadAsyncTask();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {
        iNotificationPresenter.getMessagesAsyncTask();
    }

    @Override
    public void backToContentTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public boolean onGetMessagesResultOk(Result.Data<Notification> result) {
        if (ActivityUtil.isAlive(this)) {
            messageList.clear();
            messageList.addAll(result.getData().getHasNotReadMessageList());
            messageList.addAll(result.getData().getHasReadMessageList());
            notifyDataSetchanged();
            return false;
        }
        return true;
    }

    private void notifyDataSetchanged() {
        notificationAdapter.notifyDataSetChanged();
        iconNoData.setVisibility(messageList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetMessagesFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onMarkAllMessageReadResultOk() {
        if (ActivityUtil.isAlive(this)) {
            for (Message message : messageList) {
                message.setRead(true);
            }
            notifyDataSetchanged();
            return false;
        }
        return true;
    }
}
