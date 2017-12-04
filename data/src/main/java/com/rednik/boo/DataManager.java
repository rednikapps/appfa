package com.rednik.boo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mauricio on 04/12/17.
 */

public class DataManager {
    private static DataManager ourInstance;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public void saveObjectInPreferences(@NonNull Activity context, @NonNull String key, @NonNull Object value) {
        SharedPreferences preferences = context.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public Object getObjectInPreferences(@NonNull Activity context, @NonNull String key) {
        SharedPreferences preferences = context.getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(key, "");
        return gson.fromJson(json, Object.class);
    }
}
