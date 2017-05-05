package com.chatapp.view;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.dialog.CustomProgressDialog;
import com.chatapp.model.UserBasicInfoModel;
import com.chatapp.tutorial.activity.TutorialActivity;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.webservice.WSRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBack;
    private EditText etName;
    private EditText etPass;
    private EditText etEmail;
    private EditText etDOB;
    private RadioGroup rgGender;
    private Button btnRegister;
    private Calendar myCalendar = Calendar.getInstance();
    private String DOB;
    private AysncRegisterUser aysncRegisterUser;
    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }

    private void init() {
        imgBack = (ImageView) findViewById(R.id.activity_register_img_back);
        etName = (EditText) findViewById(R.id.activity_register_et_name);
        etEmail = (EditText) findViewById(R.id.activity_register_et_email);
        etPass = (EditText) findViewById(R.id.activity_register_et_password);
        etDOB = (EditText) findViewById(R.id.activity_register_et_dob);
        btnRegister = (Button) findViewById(R.id.activity_register_btn_signup);
        rgGender = (RadioGroup) findViewById(R.id.activity_register_rg_gender);
        btnRegister.setOnClickListener(this);
        etDOB.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {

            if (TextUtils.isEmpty(etEmail.getText().toString()) && Utils.isValidEmail(etEmail.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter valid email address", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorAccent));
                snackbar.show();
            } else if (TextUtils.isEmpty(etName.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter name", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorAccent));
                snackbar.show();
            } else if (TextUtils.isEmpty(etPass.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter Password", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorAccent));
                snackbar.show();
            } else if (TextUtils.isEmpty(etDOB.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter DOB", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorAccent));
                snackbar.show();

            } else {
                int id = rgGender.getCheckedRadioButtonId();
                int gender = -1;
                if (id == R.id.activity_register_rb_male) {
                    gender = Constants.GENDER_MALE;
                } else {
                    gender = Constants.GENDER_FEMALE;
                }

                aysncRegisterUser = new AysncRegisterUser();
                aysncRegisterUser.execute(etEmail.getText().toString(), etPass.getText().toString(), etName.getText().toString(), "" + gender);

            }

        } else if (view == etDOB) {
            new DatePickerDialog(RegisterActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == imgBack) {
            finish();
            overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DISPLAY_DATEFORMAT, Locale.US);
            SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.API_DATEFORMAT, Locale.US);
            DOB = sdf1.format(myCalendar.getTime());
            etDOB.setText(sdf.format(myCalendar.getTime()));
        }

    };

    private class AysncRegisterUser extends AsyncTask<String, Void, UserBasicInfoModel> {
        WSRegister wsRegister;
        CustomProgressDialog customProgressDialog = new CustomProgressDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customProgressDialog.show();

        }

        @Override
        protected UserBasicInfoModel doInBackground(String... strings) {
            String email = strings[0];
            String pass = strings[1];
            String name = strings[2];
            int gender = Integer.parseInt(strings[3]);

            try {
                JSONObject bundle = new JSONObject();
                if (!TextUtils.isEmpty(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""))) {
                    bundle.put("device_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""));

                }
                bundle.put("device_type", "mobile");
                bundle.put("email", email);
                bundle.put("password", pass);
                bundle.put("name", name);
                bundle.put("dob", DOB);
                bundle.put("gender", "" + gender);
                bundle.put("lat", "" + DatingApp.getsInstance().getCurrentLocation().getLatitude());
                bundle.put("long", "" + DatingApp.getsInstance().getCurrentLocation().getLongitude());
                String location = Utils.getLocationFromLatLong(DatingApp.getsInstance().getCurrentLocation(), RegisterActivity.this);
                if (!TextUtils.isEmpty(location))
                    bundle.put("location", location);
                if (gender == Constants.GENDER_MALE) {
                    bundle.put("interested_in", "" + Constants.INTEREST_WOMEN);
                } else {
                    bundle.put("interested_in", "" + Constants.INTEREST_MEN);
                }
                bundle.put("radius", "" + Constants.DEFULT_DISTANCE);
                bundle.put("age", Utils.getAge(DOB));
                bundle.put("minage", "" + Constants.MIN_AGE);
                bundle.put("maxage", "" + Constants.MAX_AGE);
                bundle.put("os_type", "Android");
                bundle.put("os_version", "" + Build.VERSION.SDK_INT);

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
                if (customProgressDialog != null && customProgressDialog.isShowing()) {
                    customProgressDialog.dismiss();
                }
                if (wsRegister.getSuccess() == Constants.RESPONSE_SUCCESS) {
                    SharedPreferences.Editor editor = DatingApp.getsInstance().getSharedPreferences().edit();
                    editor.putString(PREF.PREF_USERID, example.getId());
                    editor.putString(PREF.PREF_USERNAME, example.getUsername());
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
                        intent = new Intent(RegisterActivity.this, TutorialActivity.class);

                    } else {
                        intent = new Intent(RegisterActivity.this, HomeActivity.class);

                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    finish();
                } else {
                    snackbar = Snackbar
                            .make(findViewById(R.id.layout_root), wsRegister.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    finish();
                                }
                            });
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorAccent));
                    snackbar.show();
                }
            }
        }
    }
}
