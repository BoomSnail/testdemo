package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Message;
import com.xiaoshihua.thinkpad.democnode.model.bean.Notification;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DoubleClickBackToContentTopListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.ui.widget.NotificationWeview;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.RefreshUtils;
import com.xiaoshihua.thinkpad.democnode.view.INotificationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenter.contract.INotificationPresenter;
import presenter.implement.NotificationPresenter;

public class NotificationCompatActivity extends StatusBarActivity implements
        INotificationView, Toolbar.OnMenuItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.web_notification)
    NotificationWeview webNotification;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private INotificationPresenter iNotificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_compat);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.notification);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(webNotification));

        iNotificationPresenter = new NotificationPresenter(this,this);

        RefreshUtils.initOnCreate(refreshLayout, this);
        RefreshUtils.refreshOnCreate(refreshLayout, this);

    }

    @Override
    public boolean onGetMessagesResultOk(Result.Data<Notification> result) {
        if (ActivityUtil.isAlive(this)) {
            List<Message> messageList = new ArrayList<>();
            messageList.addAll(result.getData().getHasReadMessageList());
            messageList.addAll(result.getData().getHasNotReadMessageList());

            webNotification.updateMessageList(messageList);
            iconNoData.setVisibility(messageList.size() == 0? View.VISIBLE:View.GONE);
            return false;
        }
        return true;
    }

    @Override
    public void onGetMessagesFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onMarkAllMessageReadResultOk() {
        if (ActivityUtil.isAlive(this)) {
            webNotification.markAllMessageRead();
            return false;
        }
        return true;
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
}
