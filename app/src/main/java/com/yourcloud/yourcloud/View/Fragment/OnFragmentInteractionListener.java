package com.yourcloud.yourcloud.View.Fragment;

import android.support.v7.widget.RecyclerView;

import eu.davidea.flexibleadapter.SelectableAdapter;


public interface OnFragmentInteractionListener {

    void onFragmentChange(RecyclerView recyclerView,
                          @SelectableAdapter.Mode int mode);

}