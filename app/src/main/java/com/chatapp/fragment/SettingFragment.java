package com.chatapp.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.view.HomeActivity;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

/**
 * Created by indianic on 11/04/17.
 */

public class SettingFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private View view;
    private HomeActivity homeActivity;
    private AppCompatCheckBox chkCurrentLocation;
    private AppCompatCheckBox chkOtherLocation;
    private LinearLayout llOtherLocation;
    private TextView tvOtherLocation;
    private ImageView imgSelectLocation;
    private SwitchCompat swtMen;
    private SwitchCompat swtWomen;
    private TextView tvMatchDistance;
    private CrystalSeekbar sbDistance;
    private CrystalRangeSeekbar rsbAge;
    private LinearLayout llOtherLocationview;
    private TextView tvAges;
    private RadioGroup rgDistanceUnit;
    private SwitchCompat swtNewMatch;
    private SwitchCompat swtMessage;
    private SwitchCompat swtMessageLike;
    private SwitchCompat swtMessageSuperLike;
    private Button btnLogout;
    private Button btnDeleteAccount;
    private boolean isBackEnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            isBackEnable = getArguments().getBoolean(getString(R.string.key_is_back_enable), true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, null);
        homeActivity = (HomeActivity) getActivity();
        init(view);
        return view;
    }


    private void init(View view) {
        homeActivity.setActionBarTitle(getString(R.string.screen_settings));
        homeActivity.isBackEnable(isBackEnable);
        llOtherLocationview = (LinearLayout) view.findViewById(R.id.fragment_setting_ll_otherlocationview);
        chkCurrentLocation = (AppCompatCheckBox) view.findViewById(R.id.fragment_setting_chk_currentlocation);
        chkOtherLocation = (AppCompatCheckBox) view.findViewById(R.id.fragment_setting_chk_otherlocation);
        llOtherLocation = (LinearLayout) view.findViewById(R.id.fragment_setting_ll_otherlocation);
        tvOtherLocation = (TextView) view.findViewById(R.id.fragment_setting_tv_otherlocation);
        imgSelectLocation = (ImageView) view.findViewById(R.id.fragment_setting_img_editlocation);
        swtMen = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_men);
        swtWomen = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_women);
        swtNewMatch = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_new_matches);
        swtMessage = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_messages);
        swtMessageLike = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_messages_likes);
        swtMessageSuperLike = (SwitchCompat) view.findViewById(R.id.fragment_setting_sb_super_likes);
        sbDistance = (CrystalSeekbar) view.findViewById(R.id.fragment_setting_sb_distance);
        tvMatchDistance = (TextView) view.findViewById(R.id.fragment_setting_tv_distance);
        rsbAge = (CrystalRangeSeekbar) view.findViewById(R.id.fragment_setting_sb_ages);
        tvAges = (TextView) view.findViewById(R.id.fragment_setting_tv_ages);
        rgDistanceUnit = (RadioGroup) view.findViewById(R.id.fragment_setting_rg_distance_unit);
        btnLogout = (Button) view.findViewById(R.id.fragment_setting_btn_logout);
        btnDeleteAccount = (Button) view.findViewById(R.id.fragment_setting_btn_deleteaccount);
        llOtherLocation.setOnClickListener(this);
        imgSelectLocation.setOnClickListener(this);
        chkOtherLocation.setOnCheckedChangeListener(this);
        chkCurrentLocation.setOnCheckedChangeListener(this);
        swtWomen.setOnCheckedChangeListener(this);
        swtMen.setOnCheckedChangeListener(this);
        swtNewMatch.setOnCheckedChangeListener(this);
        swtMessage.setOnCheckedChangeListener(this);
        swtMessageLike.setOnCheckedChangeListener(this);
        swtMessageSuperLike.setOnCheckedChangeListener(this);
        btnLogout.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);
        swtNewMatch.setChecked(DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_NEW_MATCH, true));
        swtMessage.setChecked(DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_MESSAGE, true));
        swtMessageLike.setChecked(DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_MESSAGE_LIKE, true));
        swtMessageSuperLike.setChecked(DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, true));
        if (DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_IS_CURRENT_LOCATION,Constants.SHOW_CURRENT_LOCATION).equalsIgnoreCase(Constants.SHOW_CURRENT_LOCATION)) {
            chkCurrentLocation.setChecked(true);
            chkOtherLocation.setChecked(false);
        } else {
            chkOtherLocation.setChecked(true);
            chkCurrentLocation.setChecked(false);
        }

        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_ACTIVE_SUBSCRIBE, true)) {
            llOtherLocationview.setVisibility(View.VISIBLE);
            chkCurrentLocation.setVisibility(View.VISIBLE);
        } else {
            llOtherLocationview.setVisibility(View.GONE);
            chkCurrentLocation.setVisibility(View.GONE);
        }
        tvOtherLocation.setText(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_PLACE_NAME, getString(R.string.default_selectlocation)));
        if (DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_INTEREST_IN, 0) == Constants.INTEREST_MEN) {
            swtMen.setChecked(true);
            swtWomen.setChecked(false);
        } else if (DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_INTEREST_IN, 0) == Constants.INTEREST_WOMEN) {
            swtMen.setChecked(false);
            swtWomen.setChecked(true);
        } else if (DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_INTEREST_IN, 0) == Constants.INTEREST_BOTH) {
            swtMen.setChecked(true);
            swtWomen.setChecked(true);
        } else {
            swtMen.setChecked(false);
            swtWomen.setChecked(false);
        }
        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
            tvMatchDistance.setText(String.valueOf(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MATCH_DISTANCE, Constants.DEFULT_DISTANCE)) + " " + Constants.UNIT_KM);
        } else {
            tvMatchDistance.setText(String.valueOf(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MATCH_DISTANCE, Constants.DEFULT_DISTANCE)) + " " + Constants.UNIT_MI);
        }
        //set min max value of seekbar
        sbDistance.setMinValue(Constants.MIN_DISTANCE).apply();
        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
            sbDistance.setMaxValue(Constants.MAX_DISTANCE_KM).apply();
        } else {
            sbDistance.setMaxValue(Constants.MAX_DISTANCE_MI).apply();
        }
        sbDistance.setOnSeekbarFinalValueListener(new OnSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number value) {
                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
                    tvMatchDistance.setText(String.valueOf(value.intValue()) + " " + Constants.UNIT_KM);
                } else {
                    tvMatchDistance.setText(String.valueOf(value.intValue()) + " " + Constants.UNIT_MI);
                }
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_MATCH_DISTANCE, value.intValue()).commit();
            }
        });
        sbDistance.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
                    tvMatchDistance.setText(String.valueOf(value.intValue()) + " " + Constants.UNIT_KM);
                } else {
                    tvMatchDistance.setText(String.valueOf(value.intValue()) + " " + Constants.UNIT_MI);
                }
            }
        });
        sbDistance.setMinStartValue(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MATCH_DISTANCE, Constants.DEFULT_DISTANCE)).apply();
        rgDistanceUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (group == rgDistanceUnit) {
                    int selectedId = group.getCheckedRadioButtonId();
                    if (selectedId == R.id.fragment_setting_rb_km) {

                        if (!DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
                            tvMatchDistance.setText(Utils.convertKMToMile(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MATCH_DISTANCE, Constants.DEFULT_DISTANCE)) + " " + Constants.UNIT_MI);
                        }
                        DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_DISTANCE_UNIT_KM, true).commit();
                        sbDistance.setMaxValue(Constants.MAX_DISTANCE_KM).apply();


                    } else {
                        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
                            tvMatchDistance.setText(Utils.convertMileToKM(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MATCH_DISTANCE, Constants.DEFULT_DISTANCE)) + " " + Constants.UNIT_KM);
                        }
                        DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_DISTANCE_UNIT_KM, false).commit();
                        sbDistance.setMaxValue(Constants.MAX_DISTANCE_MI).apply();
                    }

                }
            }
        });

        //Set Age
        tvAges.setText(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MIN_AGE, Constants.DEFULT_MIN_AGE) + " - " + DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MAX_AGE, Constants.DEFULT_MAX_AGE));
        rsbAge.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvAges.setText(minValue.intValue() + " - " + maxValue.intValue());
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_MIN_AGE, minValue.intValue()).commit();
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_MAX_AGE, maxValue.intValue()).commit();
            }
        });
        rsbAge.setMinStartValue(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MIN_AGE, Constants.DEFULT_MIN_AGE)).apply();
        rsbAge.setMaxStartValue(DatingApp.getsInstance().getSharedPreferences().getInt(PREF.PREF_MAX_AGE, Constants.DEFULT_MAX_AGE)).apply();
        if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, false)) {
            rgDistanceUnit.check(R.id.fragment_setting_rb_km);
        } else {
            rgDistanceUnit.check(R.id.fragment_setting_rb_mi);
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_location, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_done) {
            getFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == llOtherLocation) {
            SelectLocationFragment selectLocationFragment = new SelectLocationFragment();
            Utils.addNextFragment(R.id.activity_home_container, getActivity(), selectLocationFragment, SettingFragment.this, false);
        } else if (view == btnLogout) {

        } else if (view == btnDeleteAccount) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            tvOtherLocation.setText(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_PLACE_NAME, getString(R.string.default_selectlocation)));
            homeActivity.setActionBarTitle(getString(R.string.screen_settings));
            homeActivity.isBackEnable(isBackEnable);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == chkCurrentLocation) {
            if (isChecked) {
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_IS_CURRENT_LOCATION, Constants.SHOW_CURRENT_LOCATION).commit();
                chkOtherLocation.setOnCheckedChangeListener(null);
                llOtherLocation.setVisibility(View.GONE);
                chkOtherLocation.setChecked(false);
                chkOtherLocation.setOnCheckedChangeListener(this);
            } else {
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_IS_CURRENT_LOCATION, Constants.SHOW_OTHER_LOCATION).commit();
                chkOtherLocation.setOnCheckedChangeListener(null);
                llOtherLocation.setVisibility(View.VISIBLE);
                chkOtherLocation.setChecked(true);
                chkOtherLocation.setOnCheckedChangeListener(this);
            }
        } else if (buttonView == chkOtherLocation) {
            if (isChecked) {
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_IS_CURRENT_LOCATION, Constants.SHOW_OTHER_LOCATION).commit();
                chkCurrentLocation.setOnCheckedChangeListener(null);
                llOtherLocation.setVisibility(View.VISIBLE);
                chkCurrentLocation.setChecked(false);
                chkCurrentLocation.setOnCheckedChangeListener(this);
            } else {
                DatingApp.getsInstance().getSharedPreferences().edit().putString(PREF.PREF_IS_CURRENT_LOCATION, Constants.SHOW_CURRENT_LOCATION).commit();
                chkCurrentLocation.setOnCheckedChangeListener(null);
                llOtherLocation.setVisibility(View.GONE);
                chkCurrentLocation.setChecked(true);
                chkOtherLocation.setOnCheckedChangeListener(this);
            }
        } else if (buttonView == swtMen || buttonView == swtWomen) {
            if (swtMen.isChecked() && swtWomen.isChecked()) {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_INTEREST_IN, Constants.INTEREST_BOTH).commit();
            } else if (swtMen.isChecked() && !swtWomen.isChecked()) {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_INTEREST_IN, Constants.INTEREST_MEN).commit();
            } else if (!swtMen.isChecked() && swtWomen.isChecked()) {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_INTEREST_IN, Constants.INTEREST_WOMEN).commit();
            } else if (!swtMen.isChecked() && !swtWomen.isChecked()) {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_INTEREST_IN, Constants.INTEREST_NONE).commit();
            }
        } else if (buttonView == swtNewMatch) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_NEW_MATCH, isChecked).commit();
        } else if (buttonView == swtMessage) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_MESSAGE, isChecked).commit();
        } else if (buttonView == swtMessageLike) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_MESSAGE_LIKE, isChecked).commit();
        } else if (buttonView == swtMessageSuperLike) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_MESSAGE_SUPER_LIKE, isChecked).commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class AsyncSetting extends AsyncTask<Void,Void,Boolean>
    {

    }
}
