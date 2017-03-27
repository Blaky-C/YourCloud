package com.yourcloud.yourcloud.Presentor.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by ritchie-huang on 17-1-22.
 */

public class MyFlexibleAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

    private static final String TAG = MyFlexibleAdapter.class.getSimpleName();

    private List<AbstractFlexibleItem> dataList = new ArrayList<>();

    private Context mContext;

    public MyFlexibleAdapter(@Nullable List<AbstractFlexibleItem> items, @Nullable Object listeners) {
        super(items, listeners);
        this.dataList = items;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    public List<AbstractFlexibleItem> getDataList() {
        return dataList;
    }

}
