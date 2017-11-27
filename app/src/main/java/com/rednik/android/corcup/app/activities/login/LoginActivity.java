package com.rednik.android.corcup.app.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.rednik.android.corcup.R;
import com.rednik.android.sdk.base.RednikBaseActivity;
import com.rednik.login.LoginManager;
import com.rednik.login.LoginView;
import com.rednik.login.dto.UserDTO;
import com.rednik.muriel.anim.CustomAnimationsManager;
import com.rednik.muriel.widget.MulticolorTextView;

import butterknife.BindView;

public class LoginActivity extends RednikBaseActivity implements LoginView {
    private static final String TAG = LoginActivity.class.getName();

    @BindView(R.id.textview_login_title)
    MulticolorTextView titleTextView;
    @BindView(R.id.textview_login_subtitle)
    MulticolorTextView subtitleTextVIew;
    @BindView(R.id.google_button_signin)
    Button googleSignInButton;
    @BindView(R.id.facebook_button_signin)
    LoginButton facebookSignInButton;
    @BindView(R.id.button_facebook_simulator)
    Button facebookButtonSimulator;
    @BindView(R.id.progress_login)
    ProgressBar loading;
    @BindView(R.id.layout_login_button_container)
    LinearLayout loginButtonContainer;

    /*Google Sign In*/
    private GoogleSignInClient googleSignInClient;
    private static final int RC_GOOGLE_SIGN_IN = 0110;

    /*Facebook Sign In*/
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        butterKnifeBinder();
        setUpGoogleButton();
        setFacebookSignInButton();
        setUpWidgets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoginManager.getInstance().userCanBeIdentified(this);
    }

    private void setUpWidgets() {
        titleTextView.setColorizedText(getString(R.string.app_name), getResources().getIntArray(R.array.loginTitleColors));
        subtitleTextVIew.setColorizedText(getString(R.string.splash_subtitle), getResources().getIntArray(R.array.loginTitleColors));
        CustomAnimationsManager.getInstance().setProgressBarCustomAnimation(this, loading, R.drawable.loading_image_circular_animation);
    }

    private void setUpGoogleButton() {
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoogleButtonPressed();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setFacebookSignInButton() {
        facebookButtonSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookSignInButton.performClick();
            }
        });
        LoginManager.getInstance().setUpFacebookLogin(callbackManager, facebookSignInButton, this);
    }

    private void onGoogleButtonPressed() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onLoginSuccess(UserDTO user) {
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onUserIdentifiedResult(@Nullable UserDTO userDTO) {
        if (userDTO == null) {
            showLoginButton();
        } else {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            LoginManager.getInstance().executeGoogleLogin(data, this);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void showLoginButton() {
        loading.setVisibility(View.GONE);
        loginButtonContainer.setVisibility(View.VISIBLE);
    }
}
