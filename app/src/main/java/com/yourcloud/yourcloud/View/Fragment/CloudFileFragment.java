package com.yourcloud.yourcloud.View.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourcloud.yourcloud.Model.Utils.Constant;
import com.yourcloud.yourcloud.Model.Utils.DividerItemDecoration;
import com.yourcloud.yourcloud.Model.Utils.Utils;
import com.yourcloud.yourcloud.Presentor.Adapter.MyFlexibleAdapter;
import com.yourcloud.yourcloud.R;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;


public class CloudFileFragment extends BaseFragment {


//    private final Handler mHandler = new Handler(Looper.getMainLooper(), message -> {
//        switch (message.what) {
//            case Constant.CLOUD_OBJECT:
//                list = (List<AbstractFlexibleItem>) message.obj;
//                return true;
//            default:
//                return false;
//        }
//    });

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyFlexibleAdapter mAdapter;
    private static CloudFileFragment fragment;
    private OnFragmentInteractionListener mListener;
    public List<AbstractFlexibleItem> list = new ArrayList<>();

    public static CloudFileFragment newInstance() {
        if (fragment == null) {
            fragment = new CloudFileFragment();
        }

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_file, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


        initView(view);
        return view;

    }

    private void initData() {
        list = Constant.getCloudFileList();
    }


    private void initView(View view) {

        mAdapter = new MyFlexibleAdapter(list, getActivity());
        mAdapter.setNotifyChangeOfUnfilteredItems(true)
                .setMode(SelectableAdapter.MODE_SINGLE);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setFastScroller((FastScroller) view.findViewById(R.id.fast_scroller),
                Utils.getColorAccent(getActivity()));
        mAdapter.expandItemsAtStartUp()
                .setAutoCollapseOnExpand(false)
                .setAutoScrollOnExpand(true)
                .setAnimateToLimit(Integer.MAX_VALUE)
                .setLongPressDragEnabled(true)
                .setHandleDragEnabled(true)
                .setSwipeEnabled(true)
                .setUnlinkAllItemsOnRemoveHeaders(true)
                // Show Headers at startUp, 1st call, correctly executed, no warning log message!
                .setDisplayHeadersAtStartUp(true)
                .setStickyHeaders(true)
                // Simulate developer 2nd call mistake, now it's safe, not executed, no warning log message!
                .setDisplayHeadersAtStartUp(true)
                // Simulate developer 3rd call mistake, still safe, not executed, warning log message displayed!
                .showAllHeaders();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mListener.onFragmentChange(mSwipeRefreshLayout, mRecyclerView, SelectableAdapter.MODE_SINGLE);
    }


}
