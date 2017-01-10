package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Topic;
import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * Created by ThinkPad on 2016/8/6.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_LOAD_MORE = 1;

    private Activity activity;
    private LayoutInflater mInflater;
    private List<Topic> topics;

    private boolean loading = false;

    @Override
    public int getItemViewType(int position) {
        if (topics.size() >= 20 && position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean canLoadMore() {
        return topics.size() >= 20 && !loading;
    }

    public MainAdapter(Activity activity, List<Topic> topics) {
        this.activity = activity;
        this.topics = topics;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOAD_MORE:
                return new LoadMoreViewHolder(mInflater.inflate(R.layout.item_load_more, parent, false));
            default:
                return new NormalViewHolder(mInflater.inflate(R.layout.item_main, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return topics.size() >= 20 ? topics.size() + 1 : topics.size();
    }



    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);
        }

        protected void update(int position) {
        }

    }

    class NormalViewHolder extends ViewHolder {

        @BindView(R.id.ctv_tab)
        CheckedTextView ctvTab;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_avatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_reply_count)
        TextView tvReplyCount;
        @BindView(R.id.icon_slash)
        TextView iconSlash;
        @BindView(R.id.tv_visit_count)
        TextView tvVisitCount;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_last_reply_time)
        TextView tvLastReplyTime;
        @BindView(R.id.btn_item)
        LinearLayout btnItem;
        @BindView(R.id.icon_good)
        ImageView iconGood;

        private Topic topic;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            topic = topics.get(position);

            tvTitle.setText(topic.getTitle());
            ctvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
            //ctvTab.setText(R.string.tab_top);
            ctvTab.setChecked(topic.isTop());

            Glide.with(activity).load(topic.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate().into(imgAvatar);

            tvAuthor.setText(topic.getAuthor().getLoginName());

            tvCreateTime.setText(activity.getString(R.string.create_at_$));
            tvReplyCount.setText(String.valueOf(topic.getReplyCount()));
            tvVisitCount.setText(String.valueOf(topic.getVisitCount()));

            tvLastReplyTime.setText(FormatUtils.getRecentlyTimeText(topic.getLastReplyAt()));
            iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);
        }

        @OnClick({R.id.img_avatar, R.id.btn_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_avatar:
                    UserDetailActivity.startWithTransitionAnimation(activity,topic.getAuthor().getLoginName(),
                            imgAvatar,topic.getAuthor().getAvatarUrl());
                    break;
                case R.id.btn_item:
                    Navigator.TopicWithAutoCompat.start(activity,topic);
                    break;
            }
        }
    }

    class LoadMoreViewHolder extends ViewHolder {

        @BindView(R.id.icon_loading)
        ProgressWheel iconLoading;

        @BindView(R.id.icon_finish)
        TextView iconFinish;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(int position) {
            iconLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
            iconFinish.setVisibility(loading ? View.GONE : View.VISIBLE);
        }
    }
}
