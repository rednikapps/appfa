package com.rednik.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rednik.hound.log.Log;
import com.rednik.login.dto.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import static com.rednik.login.facebook.FacebookKeys.BIRTHDAY;
import static com.rednik.login.facebook.FacebookKeys.EMAIL;
import static com.rednik.login.facebook.FacebookKeys.FIRST_NAME;
import static com.rednik.login.facebook.FacebookKeys.GENDER;
import static com.rednik.login.facebook.FacebookKeys.ID;
import static com.rednik.login.facebook.FacebookKeys.LAST_NAME;
import static com.rednik.login.facebook.FacebookKeys.LOCATION;

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

    public void executeGoogleLogin(Intent data, LoginView callback) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            UserDTO userDTO = getUserDTO(account);
            callback.onLoginSuccess(userDTO);
        } catch (ApiException e) {
            // TODO check how to use GoogleSignInStatusCodes googleSignInStatusCodes = new GoogleSignInStatusCodes()
            callback.onLoginFailed();
            Log.error(callback.getTag(), e);
        }
    }

    public void setUpFacebookLogin(@NonNull CallbackManager callbackManager, @NonNull LoginButton loginButton, @NonNull final LoginView callback) {
        loginButton.setReadPermissions(EMAIL);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.debug(callback.getTag(), "Facebook Login Success: " + loginResult.toString());
                executeFacebookResponse(callback, loginResult);
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

    public void userCanBeIdentified(@NonNull LoginView callback) {
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(callback.getContext());
        UserDTO userDTO = getUserDTO(account);
        callback.onUserIdentifiedResult(userDTO);
    }

    private void executeFacebookResponse(@NonNull final LoginView callback, @NonNull LoginResult loginResult) {
        String accessToken = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Get facebook data from login
                callback.onLoginSuccess(getUserDTO(object));
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", getFacebookParametersChain()); // params asked from facebook
        request.setParameters(parameters);
        request.executeAsync();
    }

    private String getFacebookParametersChain() {
        return ID + "," + FIRST_NAME + "," + LAST_NAME + "," + EMAIL + "," + GENDER + "," + BIRTHDAY + "," + LOCATION;
    }

    /**
     * get user from FACEBOOK login data
     *
     * @param object json to read
     * @return userdto
     */
    private UserDTO getUserDTO(JSONObject object) {
        UserDTO userDTO = null;
        try {
            String id = object.getString("id");
            Uri uri = Uri.parse("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            String name = object.has(FIRST_NAME) ? object.getString(FIRST_NAME) : "";
            String lastName = object.has(LAST_NAME) ? object.getString(LAST_NAME) : "";
            String email = object.has(EMAIL) ? object.getString(EMAIL) : "";
            //bundle.putString("location", object.getJSONObject("location").getString("name"));
            userDTO = new UserDTO(id, name, lastName, email, uri);
        } catch (JSONException e) {
            // TODO Log.d(TAG,"Error parsing JSON");
        }
        return userDTO;
    }

    /**
     * get user from GOOGLE login data
     *
     * @param account google login object
     * @return userdto
     */
    private UserDTO getUserDTO(GoogleSignInAccount account) {
        UserDTO userDTO = null;
        if (account != null) {
            userDTO = new UserDTO(account.getId(), account.getDisplayName(), account.getFamilyName(), account.getEmail(), account.getPhotoUrl());
        }
        return userDTO;
    }
}
