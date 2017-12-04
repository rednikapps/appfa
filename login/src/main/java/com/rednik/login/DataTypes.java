package com.rednik.login;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.rednik.login.DataTypes.USER_DTO_DATA;


/**
 * Created by mauricio on 04/12/17.
 */
@Retention(value = RetentionPolicy.SOURCE)
@StringDef(value = {USER_DTO_DATA})
public @interface DataTypes {
    String USER_DTO_DATA = "userDTO";
}
