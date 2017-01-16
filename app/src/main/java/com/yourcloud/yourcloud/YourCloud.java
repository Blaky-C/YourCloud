package com.yourcloud.yourcloud;

import android.app.Application;

import com.kii.cloud.storage.Kii;

/**
 * Created by ritchie-huang on 17-1-16.
 */
public class YourCloud extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Kii.initialize(getApplicationContext(), "64d50f0c", "9807f0e1f17ef6876097ce8373443894", Kii.Site.CN3, true);


    }
}
