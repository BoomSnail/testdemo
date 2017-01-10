package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Message;
import com.xiaoshihua.thinkpad.democnode.ui.activities.UserDetailActivity;
import com.xiaoshihua.thinkpad.democnode.ui.widget.ContentWebView;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.utils.Navigator;
import com.xiaoshihua.thinkpad.democnode.utils.ResUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ThinkPad on 2016/8/7.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Activity activity;
    private LayoutInflater mInflate;
    private List<Message> messages;

    public NotificationAdapter(Activity activity, List<Message> messages) {
        this.activity = activity;
        this.messages = messages;

        mInflate = LayoutInflater.from(activity);
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NotificationViewHolder(mInflate.inflate(R.layout.item_notification,
                parent, false));
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.update(position);
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar)
        protected ImageView imgAvatar;

        @BindView(R.id.tv_from)
        protected TextView tvFrom;

        @BindView(R.id.tv_time)
        protected TextView tvTime;

        @BindView(R.id.tv_action)
        protected TextView tvAction;

        @BindView(R.id.badge_read)
        protected View badgeRead;

        @BindView(R.id.web_content)
        protected ContentWebView webContent;

        @BindView(R.id.tv_topic_title)
        protected TextView tvTopicTitle;

        private Message message;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void update(int position) {
            message = messages.get(position);

            //System.out.println("肖仕华" + message.getAuthor().getAvatarUrl());
            Glide.with(activity).load(message.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate()
                    .into(imgAvatar);
            tvFrom.setText(message.getAuthor().getLoginName());
            tvTime.setText(FormatUtils.getRecentlyTimeText(message.getCreateAt()));
            tvTime.setTextColor(ResUtils.getThemeAttrColor(activity, message.isRead() ?
                    android.R.attr.textColorSecondary : R.attr.colorAccent));
            badgeRead.setVisibility(message.isRead() ? View.GONE : View.VISIBLE);
            tvTopicTitle.setText("话题”：" + message.getTopic().getTitle());

            /**
             * 判断通知类型
             */
            if (message.getType() == Message.Type.at) {
                if (message.getReply() == null || TextUtils.isEmpty(message.getReply().getId())) {
                    tvAction.setText("在话题中@了您");
                    webContent.setVisibility(View.GONE);
                } else {
                    tvAction.setText("在回复中@了您");
                    webContent.setVisibility(View.VISIBLE);
                    webContent.setWebViewClient(new WebViewClient(){
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }
                    });
                    webContent.loadRenderedContent(message.getReply().getContentHtml());
                }
            } else {
                tvAction.setText("回复了您的话题");
                webContent.setVisibility(View.VISIBLE);
                webContent.loadRenderedContent(message.getReply().getContentHtml());
            }
        }

        @OnClick({R.id.img_avatar, R.id.btn_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_avatar:
                    UserDetailActivity.startWithTransitionAnimation(activity,message.getAuthor().getLoginName(),
                            imgAvatar,message.getAuthor().getAvatarUrl());
                    break;
                case R.id.btn_item:
                    Navigator.TopicWithAutoCompat.start(activity,message.getTopic().getId());
                    break;
            }
        }
    }
}
