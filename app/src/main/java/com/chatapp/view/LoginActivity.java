package com.chatapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.dialog.CustomProgressDialog;
import com.chatapp.model.UserBasicInfoModel;
import com.chatapp.model.UserModel;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.webservice.WSGetInterest;
import com.chatapp.webservice.WSGetProfileImage;
import com.chatapp.webservice.WSGetUserHistory;
import com.chatapp.webservice.WSLogin;
import com.chatapp.webservice.WSRegister;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

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
    private CustomProgressDialog customProgressDialog;
    private AsyncLogin asyncLogin;
    private Snackbar snackbar = null;
    private AysncRegisterUser aysncRegisterUser;


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
        etEmail.setText("adkhatri12@gmail.com");
        etPassword.setText("ankit11112");

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
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_FB_ID, AccessToken.getCurrentAccessToken().getApplicationId()).commit();
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_FB_TOKEN, AccessToken.getCurrentAccessToken().getToken()).commit();
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

            if (TextUtils.isEmpty(etEmail.getText().toString()) && Utils.isValidEmail(etEmail.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.login_layout_root), "Please enter valid email address", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                                finish();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                snackbar.show();
            } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.login_layout_root), "Please enter password", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                                finish();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                snackbar.show();
            } else {
                if (Utils.isNetworkAvailable(LoginActivity.this)) {
                    asyncLogin = new AsyncLogin(true, false);
                    asyncLogin.execute(etEmail.getText().toString(), etPassword.getText().toString());

                } else {
                    snackbar = Snackbar
                            .make(findViewById(R.id.login_layout_root), R.string.alert_internet, Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    finish();
                                }
                            });
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                    snackbar.show();
                }
            }
        } else if (view == btnSignUp) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);

        } else if (view == rlFacebook) {
            handleFacebookLogin();
        }
    }

    private class AsyncLoadUserData extends AsyncTask<String, Void, UserModel> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog = new CustomProgressDialog(LoginActivity.this);
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customProgressDialog.show();
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

            if (!isCancelled()) {


//                Intent intent = null;
//                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
//                    intent = new Intent(LoginActivity.this, TutorialActivity.class);
//
//                } else {
//                    intent = new Intent(LoginActivity.this, HomeActivity.class);
//
//                }
//                startActivity(intent);
//                finish();

                if (userModel != null) {
                    aysncRegisterUser = new AysncRegisterUser(userModel);
                    aysncRegisterUser.execute();
                } else {
                    if (customProgressDialog != null && customProgressDialog.isShowing()) {
                        customProgressDialog.dismiss();
                    }
                }
            }
        }
    }


    private class AsyncLogin extends AsyncTask<String, Void, UserBasicInfoModel> {


        private boolean isShowProgress;
        private boolean isLoginwthFB;
        private WSLogin wsLogin;

        public AsyncLogin(boolean isShowProgress, boolean isLoginwithFB) {
            this.isShowProgress = isShowProgress;
            this.isLoginwthFB = isLoginwithFB;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isShowProgress) {
                customProgressDialog = new CustomProgressDialog(LoginActivity.this);
                customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customProgressDialog.show();
            }
        }

        @Override
        protected UserBasicInfoModel doInBackground(String... params) {
            wsLogin = new WSLogin();
            return wsLogin.executeWebservice(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(UserBasicInfoModel example) {
            super.onPostExecute(example);
            if (!isCancelled()) {
                if (customProgressDialog != null && customProgressDialog.isShowing()) {
                    customProgressDialog.dismiss();
                }
                if (wsLogin.getSuccess() == Constants.RESPONSE_SUCCESS) {
                    SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
                    editor.putString(PREF.PREF_USERID, example.getId());
                    editor.putString(PREF.PREF_USERNAME, example.getUsername());
                    editor.putBoolean(PREF.PREF_IS_LOGGED_IN, true);
                    if (isLoginwthFB) {
                        editor.putString(PREF.PREF_FB_ID, AccessToken.getCurrentAccessToken().getApplicationId());
                    }
                    editor.putInt(PREF.PREF_INTEREST_IN, Integer.parseInt(example.getInterestedIn()));
                    editor.putString(PREF.PREF_PLACE_NAME, example.getLocation());
                    editor.putString(PREF.PREF_PLACE_LAT, example.getLat());
                    editor.putString(PREF.PREF_PLACE_LONG, example.getLong());
                    editor.putString(PREF.PREF_IS_CURRENT_LOCATION, example.getShowCurrentLocation());
                    if (!TextUtils.isEmpty(example.getRadius()))
                        editor.putInt(PREF.PREF_MATCH_DISTANCE, Integer.parseInt(example.getRadius()));
                    if (!TextUtils.isEmpty(example.getMinage()))
                        editor.putInt(PREF.PREF_MIN_AGE, Integer.parseInt(example.getMinage()));
                    if (!TextUtils.isEmpty(example.getMaxage()))
                        editor.putInt(PREF.PREF_MAX_AGE, Integer.parseInt(example.getMaxage()));
                    if (example.getDistanceUnit().equalsIgnoreCase(Constants.UNIT_KM)) {
                        editor.putBoolean(PREF.PREF_DISTANCE_UNIT_KM, true);
                    } else {
                        editor.putBoolean(PREF.PREF_DISTANCE_UNIT_KM, false);
                    }

                    if (example.getEnableNewMatch().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_NEW_MATCH, true);
                    } else {
                        editor.putBoolean(PREF.PREF_NEW_MATCH, false);
                    }

                    if (example.getEnableMessageLike().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE_LIKE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE_LIKE, false);
                    }

                    if (example.getEnableSuperlikes().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, false);
                    }


                    if (example.getEnableMessage().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE, false);
                    }

                    if (example.getShowAgeEnabled().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_SHOW_AGE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_SHOW_AGE, false);
                    }
                    if (example.getShowLocationEnabled().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_SHOW_DISTANCE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_SHOW_DISTANCE, false);
                    }

                    if (Integer.parseInt(example.getGender()) == Constants.GENDER_MALE) {
                        editor.putInt(PREF.PREF_GENDER, Constants.GENDER_MALE);
                    } else {
                        editor.putInt(PREF.PREF_GENDER, Constants.GENDER_FEMALE);
                    }

                    if (!TextUtils.isEmpty(example.getName()))
                        editor.putString(PREF.PREF_NAME, example.getName());

                    if (!TextUtils.isEmpty(example.getLocation()))
                        editor.putString(PREF.PREF_USER_CURRENT_LOCATION, example.getLocation());

                    if (!TextUtils.isEmpty(example.getAge()))
                        editor.putString(PREF.PREF_AGE, example.getAge());
                    if (!TextUtils.isEmpty(example.getProfilePhoto()))
                        editor.putString(PREF.PREF_PROFILE, example.getProfilePhoto());

                    editor.commit();
                    Intent intent = null;
                    if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                        intent = new Intent(LoginActivity.this, TutorialActivity.class);

                    } else {
                        intent = new Intent(LoginActivity.this, HomeActivity.class);

                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    finish();
                } else {
                    snackbar = Snackbar
                            .make(findViewById(R.id.login_layout_root), wsLogin.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    finish();
                                }
                            });
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                    snackbar.show();
                }
            }
        }
    }

    private class AysncRegisterUser extends AsyncTask<String, Void, UserBasicInfoModel> {
        WSRegister wsRegister;
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(LoginActivity.this);
        private UserModel userModel;

        public AysncRegisterUser(UserModel userModel) {
            this.userModel = userModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected UserBasicInfoModel doInBackground(String... strings) {

            try {
                JSONObject bundle = new JSONObject();
                if (!TextUtils.isEmpty(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""))) {
                    bundle.put("device_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""));
                }
                bundle.put("device_type", "mobile");
                bundle.put("email", userModel.getEmail());
                bundle.put("password", "");
                bundle.put("fb_id", AccessToken.getCurrentAccessToken().getApplicationId());
                bundle.put("fb_token", AccessToken.getCurrentAccessToken().getToken());
                bundle.put("name", userModel.getUserName());
                bundle.put("dob", Utils.changeFbDateFormat(userModel.getBirthday()));
                if (userModel.getGender().equalsIgnoreCase("male")) {
                    bundle.put("gender", "" + Constants.GENDER_MALE);
                    bundle.put("interested_in", "" + Constants.INTEREST_WOMEN);
                } else {
                    bundle.put("gender", "" + Constants.GENDER_FEMALE);
                    bundle.put("interested_in", "" + Constants.INTEREST_MEN);
                }

                bundle.put("lat", "" + DatingApp.getsInstance().getCurrentLocation().getLatitude());
                bundle.put("long", "" + DatingApp.getsInstance().getCurrentLocation().getLongitude());
                if (!TextUtils.isEmpty(userModel.getLocation()))
                    bundle.put("location", userModel.getLocation());
                bundle.put("radius", "" + Constants.DEFULT_DISTANCE);
                bundle.put("age", Utils.getAge(Utils.changeFbDateFormat(userModel.getBirthday())));
                bundle.put("minage", "" + Constants.MIN_AGE);
                bundle.put("maxage", "" + Constants.MAX_AGE);
                bundle.put("os_type", "Android");
                bundle.put("os_version", "" + Build.VERSION.SDK_INT);

                if (userModel.getInterest() != null && userModel.getInterest().size() > 0) {
                    String interest = "";
                    for (int i = 0; i < userModel.getInterest().size(); i++) {
                        if (i == 0) {
                            interest = userModel.getInterest().get(i);
                        } else {
                            interest = interest + "," + userModel.getInterest().get(i);
                        }
                    }
                    bundle.put("interest", interest);
                }

                Bundle bundle1 = new Bundle();
                bundle1.putString("data", bundle.toString());
                wsRegister = new WSRegister();
                return wsRegister.executeWebservice(bundle1);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserBasicInfoModel example) {
            super.onPostExecute(example);

            if (!isCancelled()) {

                if (wsRegister.getSuccess() == Constants.RESPONSE_SUCCESS) {
                    if (customProgressDialog != null && customProgressDialog.isShowing()) {
                        customProgressDialog.dismiss();
                    }
                    SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
                    editor.putString(PREF.PREF_USERID, example.getId());
                    editor.putString(PREF.PREF_USERNAME, example.getUsername());
                    editor.putInt(PREF.PREF_INTEREST_IN, Integer.parseInt(example.getInterestedIn()));
                    editor.putBoolean(PREF.PREF_IS_LOGGED_IN, true);
                    editor.putString(PREF.PREF_PLACE_NAME, example.getLocation());
                    editor.putString(PREF.PREF_PLACE_LAT, example.getLat());
                    editor.putString(PREF.PREF_PLACE_LONG, example.getLong());
                    editor.putString(PREF.PREF_IS_CURRENT_LOCATION, example.getShowCurrentLocation());
                    if (!TextUtils.isEmpty(example.getRadius()))
                        editor.putInt(PREF.PREF_MATCH_DISTANCE, Integer.parseInt(example.getRadius()));
                    if (!TextUtils.isEmpty(example.getMinage()))
                        editor.putInt(PREF.PREF_MIN_AGE, Integer.parseInt(example.getMinage()));
                    if (!TextUtils.isEmpty(example.getMaxage()))
                        editor.putInt(PREF.PREF_MAX_AGE, Integer.parseInt(example.getMaxage()));
                    if (example.getDistanceUnit().equalsIgnoreCase(Constants.UNIT_KM)) {
                        editor.putBoolean(PREF.PREF_DISTANCE_UNIT_KM, true);
                    } else {
                        editor.putBoolean(PREF.PREF_DISTANCE_UNIT_KM, false);
                    }

                    if (example.getEnableNewMatch().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_NEW_MATCH, true);
                    } else {
                        editor.putBoolean(PREF.PREF_NEW_MATCH, false);
                    }

                    if (example.getEnableMessageLike().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE_LIKE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE_LIKE, false);
                    }

                    if (example.getEnableSuperlikes().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, false);
                    }

                    editor.putInt(PREF.PREF_INTEREST_IN, Integer.parseInt(example.getInterestedIn()));


                    if (example.getEnableMessage().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_MESSAGE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_MESSAGE, false);
                    }

                    if (example.getShowAgeEnabled().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_SHOW_AGE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_SHOW_AGE, false);
                    }
                    if (example.getShowLocationEnabled().equalsIgnoreCase(Constants.ENABLE)) {
                        editor.putBoolean(PREF.PREF_SHOW_DISTANCE, true);
                    } else {
                        editor.putBoolean(PREF.PREF_SHOW_DISTANCE, false);
                    }

                    if (Integer.parseInt(example.getGender()) == Constants.GENDER_MALE) {
                        editor.putInt(PREF.PREF_GENDER, Constants.GENDER_MALE);
                    } else {
                        editor.putInt(PREF.PREF_GENDER, Constants.GENDER_FEMALE);
                    }

                    if (!TextUtils.isEmpty(example.getName()))
                        editor.putString(PREF.PREF_NAME, example.getName());

                    if (!TextUtils.isEmpty(example.getLocation()))
                        editor.putString(PREF.PREF_USER_CURRENT_LOCATION, example.getLocation());

                    if (!TextUtils.isEmpty(example.getAge()))
                        editor.putString(PREF.PREF_AGE, example.getAge());
                    if (!TextUtils.isEmpty(example.getProfilePhoto()))
                        editor.putString(PREF.PREF_PROFILE, example.getProfilePhoto());

                    editor.commit();
                    Intent intent = null;
                    if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_SHOW_TUTORIAL, true)) {
                        intent = new Intent(LoginActivity.this, TutorialActivity.class);

                    } else {
                        intent = new Intent(LoginActivity.this, HomeActivity.class);

                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    finish();
                } else {
                    if (wsRegister.getMessage().equalsIgnoreCase(Constants.USER_EXIST)) {
                        asyncLogin = new AsyncLogin(false, true);
                        asyncLogin.execute(userModel.getEmail(), "");
                    } else {
                        if (customProgressDialog != null && customProgressDialog.isShowing()) {
                            customProgressDialog.dismiss();
                        }
                        snackbar = Snackbar
                                .make(findViewById(R.id.login_layout_root), wsRegister.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        snackbar.dismiss();
                                        finish();
                                    }
                                });
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
                        snackbar.show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (asyncLogin != null && asyncLogin.getStatus() == AsyncTask.Status.RUNNING) {
            asyncLogin.cancel(true);
        }
    }
}
