package com.rednik.muriel.anim;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

/**
 * Created by mauricio on 26/11/17.
 */

public class CustomAnimationsManager {
    private static CustomAnimationsManager ourInstance;

    private CustomAnimationsManager() {
    }

    public static CustomAnimationsManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new CustomAnimationsManager();
        }
        return ourInstance;
    }

    public void setProgressBarCustomAnimation(@NonNull Context context, @NonNull ProgressBar progressBar, int animation) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(animation);
        } else {
            drawable = context.getResources().getDrawable(animation);
        }
        progressBar.setIndeterminateDrawable(drawable);
    }
}
