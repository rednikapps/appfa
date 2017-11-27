package com.rednik.hound.log;

import android.support.annotation.NonNull;

/**
 * Created by mauricio on 26/11/17.
 */

public class Log {

    public static void error(@NonNull String tag, @NonNull Exception e) {
        // TODO do something w/this
        android.util.Log.e(tag, e.getMessage());
    }
}
