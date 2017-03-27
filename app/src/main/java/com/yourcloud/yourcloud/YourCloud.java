package com.yourcloud.yourcloud;

import android.app.Application;
import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiBucket;
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

    @Override
    public void onCreate() {
        super.onCreate();
        Kii.initialize(getApplicationContext(),Constant.APP_ID , Constant.APP_KEY, Kii.Site.CN3, true);


    }



}
