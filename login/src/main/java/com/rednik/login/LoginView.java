package com.rednik.login;

import com.rednik.blacky.views.AppCommonView;
import com.rednik.hound.views.LogView;

/**
 * Created by mauricio on 27/11/17.
 */

public interface LoginView extends LogView, AppCommonView {

    void onUserIdentifiedResult(boolean result);

    void onLoginSuccess();

    void onLoginFailed();
}
