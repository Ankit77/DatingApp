package com.chatapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.util.Constants;
import com.chatapp.util.Utils;

import static com.chatapp.fragment.HomeFragment.resideMenu;


/**
 * Base fragment for all the fragment
 * Created by ZuseDigital on 11/01/16.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private long mLastClickTime = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void initView(View view);

    protected abstract void trackScreen();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trackScreen();
        initView(view);

    }

    @Override
    public void onClick(View v) {
        /**
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {

            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (v.getId()) {
            case R.id.img_back:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_LEFT);
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                } else {
                    getActivity().onBackPressed();
                }
                break;


        }
    }

    public ResideMenu getResideMenu() {
        return resideMenu;
    }
}
