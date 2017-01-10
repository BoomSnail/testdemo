package com.xiaoshihua.thinkpad.democnode.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.Reply;
import com.xiaoshihua.thinkpad.democnode.model.storage.LoginShared;
import com.xiaoshihua.thinkpad.democnode.ui.widget.ContentWebView;
import com.xiaoshihua.thinkpad.democnode.utils.ActivityUtil;
import com.xiaoshihua.thinkpad.democnode.utils.FormatUtils;
import com.xiaoshihua.thinkpad.democnode.view.ITopicItemReplyView;
import com.xiaoshihua.thinkpad.democnode.view.ITopicReplyView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import presenter.contract.ITopicItemReplyPresenter;
import presenter.implement.TopicItemReplyPresenter;

/**
 *
 * Created by ThinkPad on 2016/8/8.
 */
public class TopicAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater mInflate;
    private List<Reply> replies;
    private Map<String, Integer> positionMap;
    private ITopicReplyView iTopicReplyView;

    public TopicAdapter(Activity activity, List<Reply> replies, Map<String, Integer> positionMap, ITopicReplyView iTopicReplyView) {
        this.activity = activity;
        this.replies = replies;
        this.positionMap = positionMap;
        this.iTopicReplyView = iTopicReplyView;
        mInflate = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return replies.size();
    }

    @Override
    public Object getItem(int i) {
        return replies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TopicItemHolder topicItemHolder;
        if (view == null) {
            view = mInflate.inflate(R.layout.item_topic_reply, viewGroup, false);
            topicItemHolder = new TopicItemHolder(view);
            view.setTag(topicItemHolder);
        } else {
            topicItemHolder = (TopicItemHolder) view.getTag();
        }
        topicItemHolder.update(i);
        return view;
    }

    class TopicItemHolder implements ITopicItemReplyView {

        @BindView(R.id.img_avatar)
        CircleImageView imgAvatar;
        @BindView(R.id.tv_login_name)
        TextView tvLoginName;
        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.btn_ups)
        TextView btnUps;
        @BindView(R.id.tv_target_position)
        TextView tvTargetPosition;
        @BindView(R.id.web_content)
        ContentWebView webContent;

        @BindView(R.id.icon_deep_line)
        View iconDeepLine;

        @BindView(R.id.icon_shadow_gap)
        View iconShadowGap;

        private ITopicItemReplyPresenter iTopicItemReplyPresenter;

        private Reply reply;

        public TopicItemHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            iTopicItemReplyPresenter = new TopicItemReplyPresenter( activity,this);
        }


        public void update(int i) {
            reply = replies.get(i);
            updateReplyViews(reply,i,positionMap.get(reply.getReplyId()));
        }
        private void updateReplyViews(Reply reply, int i, Integer targetPosition) {

            Glide.with(activity).load(reply.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .dontAnimate()
                    .into(imgAvatar);

            tvLoginName.setText(reply.getAuthor().getLoginName());
            tvCreateTime.setText(FormatUtils.getRecentlyTimeText(reply.getCreateAt()));

            tvIndex.setText(i + 1 + "楼");

            updateViews(reply);
            if (targetPosition == null) {
                tvTargetPosition.setVisibility(View.GONE);
            }else {
                tvTargetPosition.setVisibility(View.VISIBLE);
                tvTargetPosition.setText("回复：" + (targetPosition + 1) + "楼");
            }

            webContent.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webContent.loadRenderedContent(reply.getContentHtml());

            iconDeepLine.setVisibility(i == replies.size() -1 ?View.GONE: View.VISIBLE);
            iconShadowGap.setVisibility(i == replies.size() - 1? View.VISIBLE: View.GONE);
        }

        private void updateViews(Reply reply) {

            btnUps.setText(String.valueOf(reply.getUpList().size()));
            btnUps.setCompoundDrawablesWithIntrinsicBounds(reply.getUpList().contains(
                    LoginShared.getId(activity)) ? R.drawable.ic_thumb_up_theme_24dp :
                            R.drawable.ic_thumb_up_grey600_24dp,0,0,0
            );
        }

        @Override
        public boolean onUpReplyResultOk(Reply reply) {
            if (ActivityUtil.isAlive(activity)) {
                if (TextUtils.equals(reply.getId(),this.reply.getId())) {
                    updateViews(reply);
                }
                return false;
            }
            return true;
        }
    }



}