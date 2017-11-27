package com.rednik.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rednik.blacky.views.AppCommonView;
import com.rednik.hound.views.LogView;
import com.rednik.login.dto.UserDTO;

/**
 * Created by mauricio on 27/11/17.
 */

public interface LoginView extends LogView, AppCommonView {

    void onUserIdentifiedResult(@Nullable UserDTO userDTO);

    void onLoginSuccess(@NonNull UserDTO userDTO);

    void onLoginFailed();
}
