package com.xiaoshihua.thinkpad.democnode.utils;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.xiaoshihua.thinkpad.democnode.R;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class RefreshUtils {

    public static void initOnCreate(SwipeRefreshLayout swipeRefreshLayout,
                                    SwipeRefreshLayout.OnRefreshListener onRefreshListener){
        swipeRefreshLayout.setColorSchemeResources(R.color.red_light,R.color.blue_light,R.color.orange_light,R.color.green_light);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }
    /**
     * TODO refreshLayout无法直接在onCreate中设置刷新状态
     */
    public static void refreshOnCreate(final SwipeRefreshLayout refreshLayout,
                                       final SwipeRefreshLayout.OnRefreshListener refreshListener){
        HandlerUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
               refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }
        },100);
    }

}
