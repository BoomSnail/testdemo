package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.TabType;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.model.storage.SettingShared;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.MainAdapter;
import com.xiaoshihua.thinkpad.democnode.ui.base.FullLayoutActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.DoubleClickBackToContentTopListener;
import com.xiaoshihua.thinkpad.democnode.ui.listener.RecycleViewLoadMoreListener;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.AlertDialogUtils;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.utils.HandlerUtils;
import com.xiaoshihua.thinkpad.democnode.utils.RefreshUtils;
import com.xiaoshihua.thinkpad.democnode.utils.ThemeUtils;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;
import com.xiaoshihua.thinkpad.democnode.view.IBackToContentTopView;
import com.xiaoshihua.thinkpad.democnode.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import presenter.implement.MainPresenter;

public class MainActivity extends FullLayoutActivity implements IMainView,
        IBackToContentTopView, SwipeRefreshLayout.OnRefreshListener, RecycleViewLoadMoreListener.OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.icon_no_data)
    TextView iconNoData;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fab_create_topic)
    FloatingActionButton fabCreateTopic;

    @BindView(R.id.img_nav_top_background)
    ImageView imgNavTopBackground;
    //状态栏
    @BindView(R.id.nav_adapt_status_bar)
    View navAdaptStatusBar;
    //导航部分个人信息
    @BindView(R.id.image_avatar)
    CircleImageView imageAvatar;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.layout_info)
    LinearLayout layoutInfo;
    @BindView(R.id.btn_logout)
    TextView btnLogout;
    @BindView(R.id.btn_theme_dark)
    ImageView btnThemeDark;

    @BindView(R.id.btn_nav_all)
    CheckedTextView btnNavAll;
    @BindView(R.id.btn_nav_good)
    CheckedTextView btnNavGood;
    @BindView(R.id.btn_nav_share)
    CheckedTextView btnNavShare;
    @BindView(R.id.btn_nav_ask)
    CheckedTextView btnNavAsk;
    @BindView(R.id.btn_nav_job)
    CheckedTextView btnNavJob;

    @BindView(R.id.badge_nav_notification)
    TextView badgeNavNotification;

    @BindView(R.id.btn_nav_setting)
    TextView btnNavSetting;
    @BindView(R.id.btn_nav_about)
    TextView btnNavAbout;
    //抽屉导航布局

    @BindView(R.id.center_adapt_status_bar)
    View centerAdaptStatusBar;

    @BindView(R.id.main_drawerLayout)
    DrawerLayout mainDrawerLayout;

//    @BindViews({
//            R.id.btn_nav_all,
//            R.id.btn_nav_good,
//            R.id.btn_nav_share,
//            R.id.btn_nav_ask,
//            R.id.btn_nav_job})

//    @BindViews({
//            iv
//    })

    //当前板块
    private TabType currentTab = TabType.all;

    private int currentPage = 0;//从未加载

    private List<Topic> topics = new ArrayList<>();

    private ArrayList<CheckedTextView> navMainItemList;

    private MainPresenter mainPresenter;

    // 首次按下返回键时间戳
    private long firstBackPressedTime = 0;

    // 是否启用夜间模式
    private boolean enableThemeDark;

    private MainAdapter adapter;

    private static final int PAGE_LIMIT = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        enableThemeDark = ThemeUtils.configThemeBeforeOnCreate(this,R.style.AppThemeLight_FitsStatusBar,
                R.style.AppThemeDark_FitsStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        navMainItemList = new ArrayList<>();
        navMainItemList.add(btnNavAll);
        navMainItemList.add(btnNavGood);
        navMainItemList.add(btnNavShare);
        navMainItemList.add(btnNavAsk);
        navMainItemList.add(btnNavJob);

        // 设置一个简单的左边或者右边的影子
        mainDrawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);
        //设置监听器
        mainDrawerLayout.addDrawerListener(drawableListener);
        //返回结束activity
//        toolbar.setNavigationOnClickListener(new NavigationOpenClickListener(mainDrawerLayout));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter(this, topics);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecycleViewLoadMoreListener(layoutManager, this, PAGE_LIMIT));

        fabCreateTopic.attachToRecyclerView(recyclerView);

        mainPresenter = new MainPresenter(this, this);

        //更新个人信息
        updateUserInfoViews();

        btnThemeDark.setImageResource(enableThemeDark?R.drawable.ic_wb_sunny_white_24dp:
                R.drawable.ic_brightness_3_white_24dp);
        imgNavTopBackground.setVisibility(enableThemeDark?View.INVISIBLE:View.VISIBLE);

        RefreshUtils.initOnCreate(refreshLayout, this);
        RefreshUtils.refreshOnCreate(refreshLayout, this);

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(false);
//                refreshLayout.setOnRefreshListener(MainActivity.this);
//            }
//        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //获取消息数目
        mainPresenter.getMessageCountAsyncTask();
        //判断是否需要切换主题

        if (SettingShared.isEnableThemeDark(this) != enableThemeDark) {
            ThemeUtils.notifyThemeApply(this,true);
        }
    }

    private final DrawerLayout.DrawerListener drawableListener = new DrawerLayout.SimpleDrawerListener() {

        @Override
        public void onDrawerOpened(View drawerView) {
            //打开侧边栏时，更新个人信息，获取个人，获取消息数目
            updateUserInfoViews();
            mainPresenter.getUserAsyncTask();
            mainPresenter.getMessageCountAsyncTask();
        }

        //关闭侧边栏，根据id选择
        @Override
        public void onDrawerClosed(View drawerView) {

            TabType newTab = TabType.all;

            for (CheckedTextView navItem : navMainItemList) {
                if (navItem.isChecked()) {
                    switch (navItem.getId()) {
                        case R.id.btn_nav_all:
                            newTab = TabType.all;
                            break;
                        case R.id.btn_nav_good:
                            newTab = TabType.good;
                            break;
                        case R.id.btn_nav_share:
                            newTab = TabType.share;
                            break;
                        case R.id.btn_nav_ask:
                            newTab = TabType.ask;
                            break;
                        case R.id.btn_nav_job:
                            newTab = TabType.job;
                            break;
                        default:
                            newTab = TabType.all;
                            break;
                    }
                    break;
                }
            }
            if (newTab != currentTab) {
                currentTab = newTab;
                currentPage = 0;
                toolbar.setTitle(currentTab.getNameId());
                topics.clear();
                notifyDataSetChanged();
                refreshLayout.setRefreshing(true);
                onRefresh();
                fabCreateTopic.show(true);
            }
        }
    };

    //刷新完成后 针对数据进行adapter绑定
    @Override
    public boolean onRefreshTopicListResultOk(TabType tab, Result.Data<List<Topic>> result) {
        if (currentTab == tab) {
            topics.clear();
            topics.addAll(result.getData());
            notifyDataSetChanged();
            currentPage = 1;
            return false;
        } else {
            return true;
        }
    }

    //失败后弹出Toast
    @Override
    public boolean onRefreshTopicListResultErrorOrCallException(TabType tab, Result.Error error) {
        if (currentTab == tab) {
            ToastUtils.getInstance(this).show(error.getErrorMessage());
            return false;
        }
        return true;
    }

    //刷新完成后refreshLayout停止刷新
    @Override
    public void onRefreshTopicListFinish() {

        refreshLayout.setRefreshing(false);
    }

    //加载成功后adapter对数据进行绑定
    @Override
    public boolean onLoadMoreTopicListResultOk(TabType tab, Integer page, Result.Data<List<Topic>> result) {
        if (currentTab == tab && currentPage == page) {
            if (result.getData().size() > 0) {
                topics.addAll(result.getData());
                adapter.setLoading(false);
                adapter.notifyItemRangeChanged(topics.size() - result.getData().size(), result.getData().size());
                currentPage++;
                return true;
            } else {
                ToastUtils.getInstance(this).show(R.string.have_no_more_data);
                return false;
            }
        }
        return true;
    }

    //加载失败后弹出消息
    @Override
    public boolean onLoadMoreTopicListResultErrorOrCallException(TabType tab, Integer page, Result.Error error) {
        if (currentTab == tab && currentPage == page) {
            ToastUtils.getInstance(this).show(error.getErrorMessage());
            return false;
        }
        return true;
    }

    //加载完成后停止刷新,适配器监听数据改变
    @Override
    public void onLoadMoreTopicListFinish() {
        adapter.setLoading(false);
        adapter.notifyItemChanged(adapter.getItemCount() - 1);
    }

    //绑定数据
    @Override
    public void updateUserInfoViews() {

        if (ActivityUtil.isAlive(this)) {

            if (TextUtils.isEmpty(LoginShared.getAccessToken(this))) {
                Glide.with(this).load(R.drawable.image_placeholder)
                        .placeholder(R.drawable.image_placeholder)
                        .dontAnimate()
                        .into(imageAvatar);
                tvLoginName.setText(R.string.click_avatar_to_login);
                tvScore.setText(null);
                btnLogout.setVisibility(View.GONE);
            } else {

                Glide.with(this).load(LoginShared.getAvatarUrl(this))
                        .placeholder(R.drawable.image_placeholder)
                        .dontAnimate()
                        .into(imageAvatar);
                tvLoginName.setText(LoginShared.getLoginName(this));
                tvScore.setText(getString(R.string.score_$) + LoginShared.getScore(this));
                btnLogout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void updateMessageCountViews(Result.Data<Integer> result) {
        if (ActivityUtil.isAlive(this)) {
            badgeNavNotification.setText(FormatUtils.getNavigationDisplayCountText(result.getData()));
        }
    }

    @Override
    public void onRefresh() {

        mainPresenter.refreshTopicListAsyncTask(currentTab, PAGE_LIMIT);
    }

    @Override
    public void onLoadMore() {
        if (adapter.canLoadMore()) {
            adapter.setLoading(true);
            adapter.notifyItemChanged(adapter.getItemCount() - 1);
            mainPresenter.loadMoreTopicListAsyncTask(currentTab, currentPage, PAGE_LIMIT);
        }
    }

    /**
     * 更新列表
     */
    private void notifyDataSetChanged() {
        if (topics.size() < PAGE_LIMIT) {
            adapter.setLoading(false);
        }
        adapter.notifyDataSetChanged();
        iconNoData.setVisibility(topics.size() == 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 主导航项单击事件
     */
    @OnClick({
            R.id.btn_nav_all,
            R.id.btn_nav_good,
            R.id.btn_nav_share,
            R.id.btn_nav_ask,
            R.id.btn_nav_job
    })
    public void onNavigationMainItemClick(CheckedTextView itemView) {
        for (CheckedTextView navItem : navMainItemList) {
            navItem.setChecked(navItem.getId() == itemView.getId());
        }
        mainDrawerLayout.closeDrawers();
    }

    /**
     * 次要菜单导航
     */

    @OnClick({
            R.id.btn_nav_notification,
            R.id.btn_nav_setting,
            R.id.btn_nav_about
    })
    public void onNavigationItemOtherClick(View itemView) {
        switch (itemView.getId()) {
            case R.id.btn_nav_notification:
//                if (LoginActivity.startForResultWithAccessTokenCheck(this)) {
//                    notificationAction.startDelayed();
//                    drawerLayout.closeDrawers();
//                }
                messageAction.startDelayed();
                mainDrawerLayout.closeDrawers();
                break;
            case R.id.btn_nav_setting:
                settingAction.startDelayed();
                mainDrawerLayout.closeDrawers();
                break;
            case R.id.btn_nav_about:
                aboutAction.startDelayed();
                mainDrawerLayout.closeDrawers();
                break;
        }
    }


    private class OtherItemAction implements Runnable {

        private Class gotoClz;

        public OtherItemAction(Class gotoClz) {
            this.gotoClz = gotoClz;
        }

        @Override
        public void run() {
            startActivity(new Intent(MainActivity.this, gotoClz));
        }

        public void startDelayed() {
            HandlerUtils.postDelayed(this, 400);
        }
    }

    private OtherItemAction messageAction = new OtherItemAction(NotifycationActivity.class);
    private OtherItemAction aboutAction = new OtherItemAction(AboutActivity.class);
    private OtherItemAction settingAction = new OtherItemAction(SettingActivity.class);

    @OnClick({R.id.fab_create_topic, R.id.layout_info, R.id.btn_logout, R.id.btn_theme_dark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_create_topic://发帖按钮
                if (LoginActivity.startForResultWithAccessTokenCheck(this)) {
                    startActivity(new Intent(this, CreateTopicActivity.class));
                }
                break;
            case R.id.layout_info://用户信息按钮
                if (TextUtils.isEmpty(LoginShared.getAccessToken(this))) {
                    LoginActivity.startForResult(this);
                } else {
                    UserDetailActivity.startWithTransitionAnimation(this, LoginShared.getLoginName(this),
                            imageAvatar, LoginShared.getAvatarUrl(this));
                }
                break;
            case R.id.btn_logout://注销
                AlertDialogUtils.createBuilderWithAutoTheme(this)
                        .setMessage(R.string.logout_tip)
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginShared.logout(MainActivity.this);
                                badgeNavNotification.setText(null);
                                updateUserInfoViews();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
                break;
            case R.id.btn_theme_dark://主题按钮

                SettingShared.setKeyEnableThemeDark(this, !enableThemeDark);
                ThemeUtils.notifyThemeApply(this, false);

                break;
        }
    }

    /**
     * 判断登陆是否成功
     * 登陆成功后，则刷新获取个人信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LoginActivity.REQUEST_LOGIN && resultCode == RESULT_OK) {
            updateUserInfoViews();
            mainPresenter.getUserAsyncTask();
        }
    }

    //关闭导航键
    @Override
    public void onBackPressed() {

        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mainDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
            long SecondBackPressedTime = System.currentTimeMillis();
            if (SecondBackPressedTime - firstBackPressedTime > 2000) {
                ToastUtils.getInstance(this).show(R.string.press_back_again_to_exit);

                firstBackPressedTime = SecondBackPressedTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * 返回键关闭导航
     */
    @Override
    public void backToContentTop() {

        recyclerView.scrollToPosition(0);
    }
}
