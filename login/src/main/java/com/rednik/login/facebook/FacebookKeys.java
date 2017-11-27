package com.rednik.login.facebook;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rednik.login.facebook.FacebookKeys.BIRTHDAY;
import static com.rednik.login.facebook.FacebookKeys.EMAIL;
import static com.rednik.login.facebook.FacebookKeys.FIRST_NAME;
import static com.rednik.login.facebook.FacebookKeys.GENDER;
import static com.rednik.login.facebook.FacebookKeys.ID;
import static com.rednik.login.facebook.FacebookKeys.LAST_NAME;
import static com.rednik.login.facebook.FacebookKeys.LOCATION;

/**
 * Created by mauricio on 27/11/17.
 */
@Retention(value = RetentionPolicy.SOURCE)
@StringDef(value = {ID, FIRST_NAME, LAST_NAME, EMAIL, GENDER, BIRTHDAY, LOCATION})
public @interface FacebookKeys {
    String ID = "id";
    String FIRST_NAME = "first_name";
    String LAST_NAME = "last_name";
    String EMAIL = "email";
    String GENDER = "id";
    String BIRTHDAY = "id";
    String LOCATION = "id";


}
