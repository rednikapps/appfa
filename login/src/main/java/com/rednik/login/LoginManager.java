package com.rednik.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.rednik.hound.log.Log;
import com.rednik.login.dto.UserDTO;

import org.json.JSONException;
import org.json.JSONObject;

import static com.rednik.login.AccountTypes.ACC_FACEBOOK;
import static com.rednik.login.AccountTypes.ACC_GOOGLE;
import static com.rednik.login.facebook.FacebookKeys.EMAIL;
import static com.rednik.login.facebook.FacebookKeys.FIRST_NAME;
import static com.rednik.login.facebook.FacebookKeys.LAST_NAME;

/**
 * Created by mauricio on 26/11/17.
 */

public class LoginManager {
    private static LoginManager ourInstance;
    private static final String TAG = getInstance().getClass().getName();

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new LoginManager();
        }
        return ourInstance;
    }

    public UserDTO executeGoogleLogin(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        UserDTO userDTO = null;
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            userDTO = getUserDTO(account);
        } catch (ApiException e) {
            // TODO USE LDK check how to use GoogleSignInStatusCodes googleSignInStatusCodes = new GoogleSignInStatusCodes()
            Log.error(TAG, e);
        }
        return userDTO;
    }

    public void executeFacebookResponse(@NonNull final FacebookResponseListener callback, @NonNull LoginResult loginResult) {
        String accessToken = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                callback.onFacebookLoginFinished(getUserDTO(object));
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", getFacebookParametersChain()); // params asked from facebook
        request.setParameters(parameters);
        request.executeAsync();
    }

    private String getFacebookParametersChain() {
        return "id, first_name, last_name, email, gender, birthday, location";
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
            userDTO = new UserDTO(id, name, lastName, email, uri, ACC_FACEBOOK);
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
            userDTO = new UserDTO(account.getId(), account.getDisplayName(), account.getFamilyName(), account.getEmail(), account.getPhotoUrl(), ACC_GOOGLE);
        }
        return userDTO;
    }

    public interface FacebookResponseListener {
        void onFacebookLoginFinished(@Nullable UserDTO userDTO);
    }
}
