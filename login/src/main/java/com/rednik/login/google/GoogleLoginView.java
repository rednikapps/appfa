package com.rednik.login.google;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rednik.blacky.views.AppCommonView;
import com.rednik.hound.views.LogView;
import com.rednik.login.dto.UserDTO;

/**
 * Created by mauricio on 26/11/17.
 */

public interface GoogleLoginView extends LogView, AppCommonView {

    /**
     * Prepare activity o use Google Sign In Button
     */
    void setUpGoogleButton();

    /**
     * called in click event
     */
    void onGoogleButtonPressed();

    /**
     * Receive GoogleSign In Event Result
     */
    void onGoogleLoginSuccess(@NonNull UserDTO userDTO);

    void onGoogleLoginFailed();

    void onUserIdentifiedResult(@Nullable UserDTO userDTO);
}
