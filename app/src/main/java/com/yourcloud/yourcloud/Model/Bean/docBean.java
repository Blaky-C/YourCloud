package com.yourcloud.yourcloud.Model.Bean;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourcloud.yourcloud.Model.Utils.Constant;
import com.yourcloud.yourcloud.Model.Utils.FlipViewUtil;
import com.yourcloud.yourcloud.R;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flipview.FlipView;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * Created by ritchie-huang on 17-1-20.
 */

public class docBean extends AbstractFlexibleItem<docBean.ViewHolder> {
    public FlipViewUtil mFlipViewUtil;

    String id;
    String path;
    String size;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public docBean() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.recycler_expandable_item;
    }

    @Override
    public ViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {

        return new ViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {
        mFlipViewUtil = new FlipViewUtil();
        mFlipViewUtil.setFirstLetter(getName());
        String frontText = mFlipViewUtil.getFirstLetter();
        mFlipViewUtil.setFlipColor(Constant.mMap);
        Log.d("test", frontText);
//        holder.flipView.setFront
        holder.flipView.setFrontText(frontText);
        holder.title.setText(getName());
        holder.subTitle.setText(getPath());

    }

    public class ViewHolder extends FlexibleViewHolder {

        FlipView flipView;
        TextView title;
        TextView subTitle;

        public ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            flipView = (FlipView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            subTitle = (TextView) view.findViewById(R.id.subtitle);

        }
    }
}
