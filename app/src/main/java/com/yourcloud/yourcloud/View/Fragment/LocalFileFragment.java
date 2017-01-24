package com.yourcloud.yourcloud.View.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yourcloud.yourcloud.Model.Utils.DividerItemDecoration;
import com.yourcloud.yourcloud.Model.Utils.FindDocFile;
import com.yourcloud.yourcloud.Model.Utils.Utils;
import com.yourcloud.yourcloud.Presentor.Adapter.MyFlexibleAdapter;
import com.yourcloud.yourcloud.R;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.fastscroller.FastScroller;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;


public class LocalFileFragment extends Fragment {

//    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    MyFlexibleAdapter mAdapter;

    public List<AbstractFlexibleItem> list = new ArrayList<>();

    public FindDocFile mDocFile;

    public static LocalFileFragment newInstance() {
        LocalFileFragment fragment = new LocalFileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDocFile = new FindDocFile(getActivity(),
                new String[]{".doc", ".docx",".pdf"});

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_file, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
//        ButterKnife.bind(view);

        initData();
        initView(view);
        return view;

    }

    private void initData() {
        list = mDocFile.getDataList();

    }

    private void initView(View view) {

        mAdapter = new MyFlexibleAdapter(list, getActivity());
        mAdapter.setNotifyChangeOfUnfilteredItems(true)
                .setMode(SelectableAdapter.MODE_SINGLE);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setFastScroller((FastScroller) view.findViewById(R.id.fast_scroller),
                Utils.getColorAccent(getActivity()));


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }


}