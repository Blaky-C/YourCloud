package com.yourcloud.yourcloud.Model.Utils;

import com.yourcloud.yourcloud.R;

import java.util.ArrayList;
import java.util.Collections;
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
    public final static String APP_ID = "64d50f0c";
    public final static String APP_KEY = "9807f0e1f17ef6876097ce8373443894";
    public final static int CLOUD_OBJECT = 0x001;
    public final static int UPLOAD_PROCESS = 0x000EEF;
    public final static int DOWNLOAD_PROCESS = 0xEF000;


    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>() {{
        put("1", "文档");
        put("2", "音乐");
        put("3", "图片");
        put("4", "视频");
        put("5", "压缩文件");
    }};


    public static Constant mInstance;


    public static List<AbstractFlexibleItem> cloudFileList;


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


    public static List<AbstractFlexibleItem> getCloudFileList() {
        return cloudFileList;
    }


}