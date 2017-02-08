package com.yourcloud.yourcloud;

import android.app.Application;
import com.kii.cloud.storage.Kii;
import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Utils.Constant;
import com.yourcloud.yourcloud.Model.Utils.FindSpecificFile;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by ritchie-huang on 17-1-16.
 */
public class YourCloud extends Application {

    public final static String FILE_TYPE_OF_DOCUMENT = "1";
    public final static String FILE_TYPE_OF_PICTURE = "2";
    public final static String FILE_TYPE_OF_VIDEO = "3";
    public final static String FILE_TYPE_OF_MUSIC = "4";
    public final static String FILE_TYPE_OF_ZIP = "5";

    public FindSpecificFile mFindDocFile;
    public List<AbstractFlexibleItem> mList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        Kii.initialize(getApplicationContext(), "64d50f0c", "9807f0e1f17ef6876097ce8373443894", Kii.Site.CN3, true);
        initData();

    }

    private void initData() {
        HeaderItem headerItem = new HeaderItem(FILE_TYPE_OF_DOCUMENT);
        headerItem.setTitle("文档");
        mFindDocFile = new FindSpecificFile(getApplicationContext(), new String[]{".doc", ".docx", ".pdf"}, headerItem);

        mList = mFindDocFile.getDataList();
        Constant.localFileList = mList;
    }

}
