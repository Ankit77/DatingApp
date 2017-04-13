package com.chatapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chatapp.R;
import com.chatapp.util.Utils;
import com.chatapp.view.HomeActivity;

/**
 * Created by indianic on 11/04/17.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout llOtherLocation;
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, null);
        homeActivity = (HomeActivity) getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        homeActivity.setActionBarTitle("Settings");
        homeActivity.isBackEnable(true);
        llOtherLocation = (LinearLayout) view.findViewById(R.id.fragment_setting_ll_otherlocation);
        llOtherLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == llOtherLocation) {
            SelectLocationFragment selectLocationFragment = new SelectLocationFragment();
            Utils.addNextFragment(R.id.activity_home_container, getActivity(), selectLocationFragment, SettingFragment.this, false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}
