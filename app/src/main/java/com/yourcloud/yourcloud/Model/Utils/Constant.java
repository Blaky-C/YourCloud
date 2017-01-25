package com.yourcloud.yourcloud.Model.Utils;

import com.yourcloud.yourcloud.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by ritchie-huang on 17-1-21.
 */

public class Constant {
    public final static Map<String, Integer> mMap = new HashMap<>();

    public static Constant mInstance;

    public static List<AbstractFlexibleItem> localFileList;

    static {
        mMap.put("A", R.color.material_color_amber_300);
        mMap.put("B", R.color.material_color_blue_300);
        mMap.put("C", R.color.material_color_cyan_300);
        mMap.put("D", R.color.material_color_deep_orange_300);
        mMap.put("E", R.color.material_color_blue_grey_300);
        mMap.put("F", R.color.material_color_brown_300);
        mMap.put("G", R.color.material_color_deep_purple_300);
        mMap.put("H", R.color.material_color_green_300);
        mMap.put("I", R.color.material_color_grey_300);
        mMap.put("J", R.color.material_color_blue_grey_300);
        mMap.put("K", R.color.material_color_indigo_300);
        mMap.put("L", R.color.material_color_light_green_300);
        mMap.put("M", R.color.material_color_lime_300);
        mMap.put("N", R.color.material_color_orange_300);
        mMap.put("O", R.color.material_color_pink_300);
        mMap.put("P", R.color.material_color_purple_300);
        mMap.put("Q", R.color.material_color_red_300);
        mMap.put("R", R.color.material_color_yellow_300);
        mMap.put("S", R.color.material_color_teal_300);
        mMap.put("T", R.color.colorAccent);
        mMap.put("U", R.color.colorBlue);
        mMap.put("V", R.color.colorGray);
        mMap.put("W", R.color.colorGreen);
        mMap.put("X", R.color.colorBlue);
        mMap.put("Y", R.color.colorOrange);
        mMap.put("Z", R.color.colorPurpleDark);
    }

    public static Constant getInstance() {
        if (mInstance == null) {
            mInstance = new Constant();
        }
        return mInstance;
    }

    public static List<AbstractFlexibleItem> getLocalFileList() {
        return localFileList;
    }


    public void removeItem(IFlexible item) {
        localFileList.remove(item);
    }
}