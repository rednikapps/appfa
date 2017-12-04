package com.rednik.android.sdk.data;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.rednik.boo.DataManager;

/**
 * Created by mauricio on 27/11/17.
 */

public class SdkDataManager {
    private static SdkDataManager instance;

    private SdkDataManager() {

    }

    public static SdkDataManager getInstance() {
        if (instance == null) {
            instance = new SdkDataManager();
        }
        return instance;
    }

    public void saveObjectInPreferences(@NonNull Activity context, @NonNull String key, @NonNull Object value) {
        DataManager.getInstance().saveObjectInPreferences(context, key, value);
    }

    public Object getObjectInPreferences(@NonNull Activity context, @NonNull String key) {
        return DataManager.getInstance().getObjectInPreferences(context, key);
    }
}
