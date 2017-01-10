package com.xiaoshihua.thinkpad.democnode.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoshihua.thinkpad.democnode.R;
import com.xiaoshihua.thinkpad.democnode.model.bean.TopicSimple;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.UserDetailAdapter;
import com.xiaoshihua.thinkpad.democnode.ui.adapter.UserItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private UserItemAdapter userItemAdapter;
    public UserDetailFragment() {
        // Required empty public constructor
    }

    private List<TopicSimple> topicList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        initViews();

        return view;
    }

    private void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userItemAdapter = new UserItemAdapter(getActivity(),topicList);
        recyclerView.setAdapter(userItemAdapter);

    }

    public void notifyDataSetChanged(List<TopicSimple> recentReplyList) {
        topicList.clear();
        topicList.addAll(recentReplyList);
        userItemAdapter.notifyDataSetChanged();
    }
}
