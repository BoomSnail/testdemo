package com.xiaoshihua.thinkpad.democnode.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.api.ApiDefine;
import com.xiaoshihua.thinkpad.democnode.model.bean.Result;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.model.bean.User;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.UserDetailAdapter;
import com.xiaoshihua.thinkpad.democnode.ui.base.StatusBarActivity;
import com.xiaoshihua.thinkpad.democnode.ui.listener.NavigationFinishClickListener;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;
import com.xiaoshihua.thinkpad.democnode.utils.ToastUtils;
import com.xiaoshihua.thinkpad.democnode.view.IUserDetialView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import presenter.contract.IUserDetailPresenter;
import presenter.implement.UserDetailPresenter;

public class UserDetailActivity extends StatusBarActivity implements IUserDetialView,
        Toolbar.OnMenuItemClickListener {

    private static final String EXTRA_LOGIN_NAME = "loginName";
    private static final String EXTRA_AVATAR_URL = "avatarUrl";
    private static final String NAME_IMG_AVATAR = "imgAvatar";

    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_github_username)
    TextView tvGithubUsername;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout_info)
    LinearLayout layoutInfo;

    private UserDetailAdapter userDetailAdapter;

    private String loginName;

    private String gitHubUserName;

    private boolean isLoading = false;

    private UserDetailPresenter userDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.user_detail);
        toolbar.setOnMenuItemClickListener(this);

        userDetailAdapter = new UserDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(userDetailAdapter);
        viewPager.setOffscreenPageLimit(userDetailAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);

        loginName = getIntent().getStringExtra(EXTRA_LOGIN_NAME);
        tvLoginName.setText(loginName);

        String avatarUrl = getIntent().getStringExtra(EXTRA_AVATAR_URL);
        if (!TextUtils.isEmpty(avatarUrl)) {
            Glide.with(this).load(avatarUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate()
                    .into(imgAvatar);
        }

        userDetailPresenter = new UserDetailPresenter(this,this);
        userDetailPresenter.getUserAsyncter(loginName);
        userDetailPresenter.getCollectTopicListAsyncTask(loginName);

    }

    public static void startWithTransitionAnimation(Activity activity, String loginName,
                                                    View imgAvatar, String avatarUrl) {

        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        intent.putExtra(EXTRA_AVATAR_URL, avatarUrl);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, imgAvatar, NAME_IMG_AVATAR);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }

    @Override
    public void onGetUserstart() {
        isLoading = true;
        progressWheel.spin();
    }

    @Override
    public boolean onGetUserResultOk(Result.Data<User> result) {
        if (ActivityUtil.isAlive(this)) {
            updateUserInfoViews(result.getData());
            userDetailAdapter.update(result.getData());
            gitHubUserName = result.getData().getGithubUsrname();
            return false;
        }
        return true;
    }

    @Override
    public boolean onGetUserResultError(Result.Error error) {
        if (ActivityUtil.isAlive(this)) {
            ToastUtils.getInstance(this).show(error.getErrorMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean onGetUserLoadError() {
        if (ActivityUtil.isAlive(this)) {
            ToastUtils.getInstance(this).show(R.string.data_load_faild_and_click_avatar_to_reload);
            return false;
        }
        return true;
    }

    @Override
    public void onGetUserFinish() {
        progressWheel.stopSpinning();
        isLoading = false;
    }

    @Override
    public void updateUserInfoViews(User user) {
        Glide.with(this).load(user.getAvatarUrl())
                .placeholder(R.drawable.image_placeholder)
                .dontAnimate()
                .into(imgAvatar);

        tvLoginName.setText(user.getLoginName());
        if (TextUtils.isEmpty(user.getGithubUsrname())) {
            tvGithubUsername.setVisibility(View.INVISIBLE);
            tvGithubUsername.setText(null);
        } else {
            tvGithubUsername.setVisibility(View.VISIBLE);
            tvGithubUsername.setText(user.getGithubUsrname());
        }

        tvCreateTime.setText(getString(R.string.register_time_$) + user.getCreateAt());
        tvScore.setText(getString(R.string.score_$) + user.getScore());
    }

    @Override
    public boolean onGetCollectTopicListResultOk(Result.Data<List<Topic>> result) {
        if (ActivityUtil.isAlive(this)) {
            userDetailAdapter.update(result.getData());
            return false;
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_in_browser:
                Navigator.openInBrowser(this, ApiDefine.USER_LINK_URL_PREFIX);
                return true;
        }
        return false;
    }

    //activity销毁时不保存Fragment
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @OnClick({R.id.img_avatar, R.id.tv_github_username})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                if(!isLoading){
                    userDetailPresenter.getUserAsyncter(loginName);
                    userDetailPresenter.getCollectTopicListAsyncTask(loginName);
                }
                break;
            case R.id.tv_github_username:
                if (!TextUtils.isEmpty(gitHubUserName)){
                    Navigator.openInBrowser(this,"https://github.com/" + gitHubUserName);
                }
                break;
        }
    }

    public static void start(Activity activity, String loginName) {
        Intent intent = new Intent(activity,UserDetailActivity.class);
        intent.putExtra(EXTRA_LOGIN_NAME,loginName);
        activity.startActivity(intent);
    }

    public static void start(Context context, String loginName) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_LOGIN_NAME, loginName);
        context.startActivity(intent);
    }
}
