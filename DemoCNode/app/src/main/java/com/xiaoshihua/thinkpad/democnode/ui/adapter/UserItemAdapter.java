package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicSimple;
import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ThinkPad on 2016/8/8.
 */
public class UserItemAdapter extends RecyclerView.Adapter {

    private final Activity activity;
    private final LayoutInflater mInflate;
    private final List<TopicSimple> topicSimples;

    public UserItemAdapter(Activity activity, List<TopicSimple> topicSimples) {
        this.activity = activity;
        mInflate = LayoutInflater.from(activity);
        this.topicSimples = topicSimples;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflate.inflate(R.layout.item_user_detail, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserItemViewHolder userItemViewHolder = (UserItemViewHolder) holder;
        userItemViewHolder.update(position);
    }

    @Override
    public int getItemCount() {
        return topicSimples.size();
    }


    class UserItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_login_name)
        TextView tvLoginName;
        @BindView(R.id.tv_last_reply_time)
        TextView tvLastReplyTime;

        TopicSimple topicSimple;
        public UserItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick({R.id.img_avatar, R.id.btn_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_avatar:
                    UserDetailActivity.startWithTransitionAnimation(activity,topicSimple.getAuthor().getLoginName(),
                            imgAvatar,topicSimple.getAuthor().getAvatarUrl());
                    break;
                case R.id.btn_item:
                    Navigator.TopicWithAutoCompat.start(activity, topicSimple.getId());
                    break;
            }
        }

        public void update(int position) {
             topicSimple = topicSimples.get(position);

            tvTitle.setText(topicSimple.getTitle());
            Glide.with(activity).load(topicSimple.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate()
                    .into(imgAvatar);
            tvLoginName.setText(topicSimple.getAuthor().getLoginName());
            tvLastReplyTime.setText(FormatUtils.getRecentlyTimeText(topicSimple.getLastReplyAt()));
        }
    }
}
