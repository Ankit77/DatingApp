package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = LoginActivity.class.getSimpleName();
    private CallbackManager mFacebookCallbackManager;
    private LoginManager mLoginManager;
    private AccessTokenTracker mAccessTokenTracker;
    private EditText etEmail;
    private EditText etPassword;
    private RelativeLayout rlFacebook;
    private TextView tvForgotPassword;
    private Button btnLogin;
    private Button btnSignUp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.activity_login_btn_signin);
        btnSignUp = (Button) findViewById(R.id.activity_login_btn_signup);
        etEmail = (EditText) findViewById(R.id.activity_login_et_UserName);
        etPassword = (EditText) findViewById(R.id.activity_login_et_Password);
        etEmail = (EditText) findViewById(R.id.activity_login_et_UserName);
        rlFacebook = (RelativeLayout) findViewById(R.id.activity_login_rl_login_facebook);
        tvForgotPassword = (TextView) findViewById(R.id.activity_login_tv_forgotpassword);

        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        rlFacebook.setOnClickListener(this);
        setupFacebookStuff();
        updateFacebookButtonUI();
        Utils.getHashKey(LoginActivity.this);

    }

    private void setupFacebookStuff() {

        // This should normally be on your application class


        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                updateFacebookButtonUI();
            }
        };

        mLoginManager = LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateFacebookButtonUI();
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        Log.d(TAG, user.optString("email"));
                        Log.d(TAG, user.optString("name"));
                        Log.d(TAG, user.optString("id"));
                        new GraphRequest(
                                AccessToken.getCurrentAccessToken(),
                                "/" + user.optString("id")+"/likes",
                                null,
                                HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    public void onCompleted(GraphResponse response) {

                                        WriteLog.E(TAG, response.getRawResponse());
                                    }
                                }
                        ).executeAsync();
                    }
                }).executeAsync();

//                SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
//                editor.putBoolean(PREF.PREF_IS_LOGGED_IN, true);
//                editor.commit();
//                Intent intent_home = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent_home);
//                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
//                finish();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void updateFacebookButtonUI() {
        if (AccessToken.getCurrentAccessToken() != null) {
            WriteLog.E(LoginActivity.class.getSimpleName(), "Login Succeess");
        } else {
            WriteLog.E(LoginActivity.class.getSimpleName(), "Login Fail");
        }
    }

    private void handleFacebookLogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            mLoginManager.logOut();
        } else {
            mAccessTokenTracker.startTracking();
            mLoginManager.logInWithReadPermissions(LoginActivity.this,  Arrays.asList("user_friends", "email", "public_profile"));
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogin) {

        } else if (view == btnSignUp) {

        } else if (view == rlFacebook) {
            handleFacebookLogin();
        }
    }
}
