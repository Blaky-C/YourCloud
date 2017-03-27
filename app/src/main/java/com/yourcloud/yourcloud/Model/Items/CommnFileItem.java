package com.yourcloud.yourcloud.Model.Items;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;


/**
 * Created by ritchie-huang on 17-1-20.
 */

public abstract class CommnFileItem<VH extends FlexibleViewHolder>
        extends AbstractFlexibleItem<VH> {

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

    public CommnFileItem() {
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
        if (o instanceof CommnFileItem) {
            CommnFileItem item = (CommnFileItem) o;
            return this.id.equals(item.id);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
