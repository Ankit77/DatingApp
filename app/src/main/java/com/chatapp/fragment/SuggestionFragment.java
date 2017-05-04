package com.chatapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.adapter.SuggetionAdapter;
import com.chatapp.model.SuggetionModel;
import com.chatapp.model.dashboard.UserDetailModel;
import com.chatapp.util.Utils;
import com.chatapp.util.WriteLog;
import com.chatapp.view.HomeActivity;
import com.chatapp.webservice.WSDashboard;
import com.chatapp.wenchao.cardstack.CardAnimator;
import com.chatapp.wenchao.cardstack.CardStack;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;


/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggestionFragment extends Fragment implements CardStack.CardEventListener {
    private View view;
    private SuggetionAdapter suggetionAdapter;
    private HomeActivity homeActivity;
    private RippleBackground rippleBackground;
    private CardStack cardStack;
    private boolean isBackEnable = false;
    private AsyncGetSuggetion asyncGetSuggetion;
    private ArrayList<UserDetailModel> suggetionList;
    private TextView tvNoData;

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
        view = inflater.inflate(R.layout.fragment_suggetion, null);
        tvNoData = (TextView) view.findViewById(R.id.fragment_suggetion_tv_nodata);
        homeActivity = (HomeActivity) getActivity();
        cardStack = (CardStack) view.findViewById(R.id.container);
        cardStack.setStackGravity(CardAnimator.TOP);
        init(view);
        return view;
    }

    private void init(View view) {
        rippleBackground = (RippleBackground) view.findViewById(R.id.fragment_suggestion_radarview);
        loadData();
        homeActivity.setActionBarTitle("Matches");
        homeActivity.isBackEnable(isBackEnable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_chat, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.quickblox.q_municate");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                Toast.makeText(getActivity(), "Chat is called", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    private void loadData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rippleBackground.startRippleAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        asyncGetSuggetion = new AsyncGetSuggetion();
                        asyncGetSuggetion.execute();

                    }
                }, 5000);
            }
        }, 1000);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeActivity.setActionBarTitle("Matches");
            homeActivity.isBackEnable(isBackEnable);
        }
    }

    @Override
    public boolean swipeEnd(int section, float distance) {
        if (section == 0) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Top Left");
        } else if (section == 1) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Top Right");
        } else if (section == 2) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Bottom Left");
        } else if (section == 3) {
            WriteLog.E(SuggestionFragment.class.getSimpleName(), "Bottom Right");
        }
        return (distance > 300) ? true : false;


    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {
        WriteLog.E(SuggestionFragment.class.getSimpleName(), "Index is" + mIndex);
    }

    @Override
    public void topCardTapped(int mIndex) {
        ProfileDetailFragment profileDetailFragment = new ProfileDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.key_isloggedin_user), false);
        profileDetailFragment.setArguments(bundle);
        Utils.addNextFragment(R.id.activity_home_container, homeActivity, profileDetailFragment, SuggestionFragment.this, false);
        WriteLog.E(SuggestionFragment.class.getSimpleName(), suggetionList.get(mIndex).getName());
    }


    private class AsyncGetSuggetion extends AsyncTask<Void, Void, ArrayList<UserDetailModel>> {
        private WSDashboard wsDashboard;

        @Override
        protected ArrayList<UserDetailModel> doInBackground(Void... voids) {
            wsDashboard = new WSDashboard();
            return wsDashboard.executeWebservice();
        }

        @Override
        protected void onPostExecute(ArrayList<UserDetailModel> userDetailModels) {
            super.onPostExecute(userDetailModels);
            if (!isCancelled()) {
                rippleBackground.stopRippleAnimation();
                if (userDetailModels != null && userDetailModels.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                    rippleBackground.setVisibility(View.GONE);
                    cardStack.setVisibility(View.VISIBLE);
                    suggetionList = userDetailModels;
                    suggetionAdapter = new SuggetionAdapter(getActivity(), suggetionList);
                    cardStack.setAdapter(suggetionAdapter);
                    cardStack.setStackGravity(cardStack.getStackGravity() == CardAnimator.TOP ? CardAnimator.BOTTOM : CardAnimator.TOP);
                    cardStack.reset(true);
                    cardStack.setListener(SuggestionFragment.this);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    cardStack.setVisibility(View.GONE);
                    rippleBackground.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (asyncGetSuggetion != null & asyncGetSuggetion.getStatus() == AsyncTask.Status.RUNNING) {
            asyncGetSuggetion.cancel(true);
        }
    }
}

