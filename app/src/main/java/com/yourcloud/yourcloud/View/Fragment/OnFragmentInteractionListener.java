package com.yourcloud.yourcloud.View.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import eu.davidea.flexibleadapter.SelectableAdapter;


public interface OnFragmentInteractionListener {

    void onFragmentChange(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,
                          @SelectableAdapter.Mode int mode);

}