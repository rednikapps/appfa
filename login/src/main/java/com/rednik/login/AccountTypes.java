package com.rednik.login;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rednik.login.AccountTypes.ACC_FACEBOOK;
import static com.rednik.login.AccountTypes.ACC_GOOGLE;

/**
 * Created by mauricio on 04/12/17.
 */
@Retention(value = RetentionPolicy.SOURCE)
@StringDef(value = {ACC_FACEBOOK, ACC_GOOGLE})
public @interface AccountTypes {
    String ACC_FACEBOOK = "facebook";
    String ACC_GOOGLE = "google";
}
