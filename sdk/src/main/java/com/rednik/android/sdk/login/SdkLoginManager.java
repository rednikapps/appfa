package com.rednik.android.sdk.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.rednik.android.sdk.data.SdkDataManager;
import com.rednik.hound.log.Log;
import com.rednik.login.LoginManager;
import com.rednik.login.LoginView;
import com.rednik.login.dto.UserDTO;

import static com.rednik.login.DataTypes.USER_DTO_DATA;

/**
 * Created by mauricio on 27/11/17.
 */

public class SdkLoginManager {
    private static SdkLoginManager ourInstance;

    private LoginView loginView;

    private SdkLoginManager() {
    }

    public static SdkLoginManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new SdkLoginManager();
        }
        return ourInstance;
    }

    public void executeLogin(@NonNull Intent data, @NonNull LoginView callback) {
        UserDTO userDTO = LoginManager.getInstance().executeGoogleLogin(data);
        postResult(userDTO, callback);
    }

    public void postResult(@Nullable UserDTO userDTO, @NonNull LoginView callback) {
        if (userDTO == null) {
            callback.onLoginSuccess();
        } else {
            callback.onLoginFailed();
        }
    }

    public void executeLogin(@NonNull CallbackManager callbackManager, @NonNull final LoginView callback) {
        com.facebook.login.LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.debug(callback.getTag(), "Facebook Login Success: " + loginResult.toString());
                LoginManager.FacebookResponseListener listener = new LoginManager.FacebookResponseListener() {
                    @Override
                    public void onFacebookLoginFinished(@Nullable UserDTO userDTO) {
                        postResult(userDTO, callback);
                    }
                };
                LoginManager.getInstance().executeFacebookResponse(listener, loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                callback.onLoginFailed();
                Log.error(callback.getTag(), error);
            }
        });
    }

    public void userCanBeIdentified(@NonNull Activity activity, @NonNull LoginView callback) {
        UserDTO userDTO = (UserDTO) SdkDataManager.getInstance().getObjectInPreferences(activity, USER_DTO_DATA);
        callback.onUserIdentifiedResult(userDTO != null);
    }
}
