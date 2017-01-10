package com.xiaoshihua.thinkpad.democnode.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * recycleView上啦加载
 * Created by ThinkPad on 2016/8/6.
 */
public class RecycleViewLoadMoreListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager linearLayoutManager;
    private final OnLoadMoreListener onLoadMoreListener;
    private final int limit;

    public RecycleViewLoadMoreListener(LinearLayoutManager linearLayoutManager, OnLoadMoreListener onLoadMoreListener, int limit) {
        super();
        this.linearLayoutManager = linearLayoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
        this.limit = limit;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        if (linearLayoutManager.getItemCount() >= limit &&
                linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager
                        .getItemCount() - 1) {
            onLoadMoreListener.onLoadMore();
        }
    }

//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        if (linearLayoutManager.getItemCount() > limit &&
//                linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager
//                        .getItemCount() - 1) {
//            onLoadMoreListener.onLoadMore();
//        }
//    }

    public interface OnLoadMoreListener {

        void onLoadMore();

    }
}
