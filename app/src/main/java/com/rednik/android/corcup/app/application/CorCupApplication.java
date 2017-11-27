package com.rednik.android.corcup.app.application;

import android.app.Application;

import com.rednik.android.corcup.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mauricio on 24/11/17.
 */

public class CorCupApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initCalligraphy();
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
