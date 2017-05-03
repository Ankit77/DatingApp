package com.chatapp.view;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.chatapp.model.Example;
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
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {

            if (TextUtils.isEmpty(etEmail.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter email address", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });

                snackbar.show();
            } else if (TextUtils.isEmpty(etName.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter name", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });

                snackbar.show();
            } else if (TextUtils.isEmpty(etPass.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter Password", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });

                snackbar.show();
            } else if (TextUtils.isEmpty(etDOB.getText().toString())) {
                snackbar = Snackbar
                        .make(findViewById(R.id.layout_root), "Please enter DOB", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        });

                snackbar.show();

            } else {
                int id = rgGender.getCheckedRadioButtonId();
                int gender = -1;
                if (id == R.id.activity_register_rb_male) {
                    gender = Constants.GENDER_MALE;
                } else {
                    gender = Constants.GENDER_FEMALE;
                }
                try {
                    JSONObject bundle = new JSONObject();
                    if (!TextUtils.isEmpty(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""))) {
                        bundle.put("device_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""));

                    }
                    bundle.put("device_type", "mobile");
                    bundle.put("email", etEmail.getText().toString());
                    bundle.put("password", etPass.getText().toString());
                    bundle.put("name", etName.getText().toString());
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
                    aysncRegisterUser = new AysncRegisterUser(bundle1);
                    aysncRegisterUser.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else if (view == etDOB) {
            new DatePickerDialog(RegisterActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

    private class AysncRegisterUser extends AsyncTask<String, Void, Example> {
        private Bundle bundle;

        public AysncRegisterUser(Bundle bundle) {
            this.bundle = bundle;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Example doInBackground(String... strings) {

            WSRegister wsRegister = new WSRegister();
            return wsRegister.executeWebservice(bundle);
        }

        @Override
        protected void onPostExecute(Example example) {
            super.onPostExecute(example);
            if (!isCancelled()) {

            }
        }
    }
}
