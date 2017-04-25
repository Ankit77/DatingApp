package com.chatapp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.model.UserModel;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.webservice.WSGetInterest;
import com.chatapp.webservice.WSGetProfileImage;
import com.chatapp.webservice.WSGetUserHistory;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;

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
    private AsyncLoadUserData asyncLoadUserData;


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
                SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
                editor.putBoolean(PREF.PREF_IS_LOGGED_IN, true);
                editor.putBoolean(PREF.PREF_FB_LOGIN, true);
                editor.putString(PREF.PREF_FB_TOKEN, AccessToken.getCurrentAccessToken().getToken());
                editor.commit();
                asyncLoadUserData = new AsyncLoadUserData();
                asyncLoadUserData.execute(AccessToken.getCurrentAccessToken().getToken());
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
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
            mLoginManager.logInWithReadPermissions(LoginActivity.this, Utils.getFacebookPermision());
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

    private class AsyncLoadUserData extends AsyncTask<String, Void, UserModel> {

        private UserModel userModel;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = Utils.displayProgressDialog(LoginActivity.this);
        }

        @Override
        protected UserModel doInBackground(String... params) {
            String accesstoken = params[0];
            WSGetUserHistory wsGetUserHistory = new WSGetUserHistory();
            UserModel userModel = wsGetUserHistory.executeWebservice(accesstoken);
            WSGetInterest wsGetInterest = new WSGetInterest();
            ArrayList<String> interestlist = wsGetInterest.executeWebservice(accesstoken);
            if (interestlist != null) {
                userModel.setInterest(interestlist);
            }
            WSGetProfileImage wsGetProfileImage = new WSGetProfileImage();
            ArrayList<String> imagelist = wsGetProfileImage.executeWebservice(accesstoken);
            if (imagelist != null) {
                userModel.setImages(imagelist);
            }
            return userModel;
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            super.onPostExecute(userModel);
            Utils.dismissProgressDialog(progressDialog);
            if (!isCancelled()) {
                Intent intent = null;
                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                    intent = new Intent(LoginActivity.this, TutorialActivity.class);

                } else {
                    intent = new Intent(LoginActivity.this, HomeActivity.class);

                }
                startActivity(intent);
                finish();
            }
        }
    }


}
