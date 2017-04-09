package com.chatapp.view;

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
import com.chatapp.model.InterestModel;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.webservice.WSGetInterest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
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
//                new GraphRequest(
//                        AccessToken.getCurrentAccessToken(),
//                        "...?fields={fieldname_of_type_CoverPhoto}",
//                        null,
//                        HttpMethod.GET,
//                        new GraphRequest.Callback() {
//                            public void onCompleted(GraphResponse response) {
//                                WriteLog.E(TAG, response.getRawResponse().toString());
//            /* handle the result */
//                            }
//                        }
//                ).executeAsync();
//                new AsyncGetInterest().execute();
                SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
                editor.putBoolean(PREF.PREF_IS_LOGGED_IN, true);
                editor.commit();
                Intent intent_home = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent_home);
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                finish();

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

    private class AsyncGetInterest extends AsyncTask<String, Void, ArrayList<InterestModel>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<InterestModel> doInBackground(String... strings) {
            WSGetInterest wsGetInterest = new WSGetInterest(LoginActivity.this);
            return wsGetInterest.executeWebservice(AccessToken.getCurrentAccessToken().getToken());
        }

        @Override
        protected void onPostExecute(ArrayList<InterestModel> interestModels) {
            super.onPostExecute(interestModels);
            if (interestModels != null && interestModels.size() > 0) {

            }
        }
    }
}
