package com.rednik.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rednik.hound.log.Log;
import com.rednik.login.dto.UserDTO;
import com.rednik.login.google.GoogleLoginView;

/**
 * Created by mauricio on 26/11/17.
 */

public class LoginManager {
    private static LoginManager ourInstance;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new LoginManager();
        }
        return ourInstance;
    }

    public void executeGoogleLogin(Intent data, GoogleLoginView callback) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            UserDTO userDTO = getGoogleUser(account);
            callback.onGoogleLoginSuccess(userDTO);
        } catch (ApiException e) {
            // TODO check how to use GoogleSignInStatusCodes googleSignInStatusCodes = new GoogleSignInStatusCodes()
            callback.onGoogleLoginFailed();
            Log.error(callback.getTag(), e);
        }
    }

    public void userCanBeIdentified(@NonNull GoogleLoginView callback) {
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(callback.getContext());
        UserDTO userDTO = getGoogleUser(account);
        callback.onUserIdentifiedResult(userDTO);
    }

    private UserDTO getGoogleUser(GoogleSignInAccount account) {
        UserDTO userDTO = null;
        if (account != null) {
            userDTO = new UserDTO(account.getDisplayName(), account.getFamilyName(), account.getEmail(), account.getPhotoUrl());
        }
        return userDTO;
    }
}
