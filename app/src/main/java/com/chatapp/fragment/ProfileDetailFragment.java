package com.chatapp.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.adapter.ProfileImageAdapter;
import com.chatapp.chipview.ChipView;
import com.chatapp.model.UserModel;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.chatapp.view.HomeActivity;
import com.chatapp.webservice.WSGetInterest;
import com.chatapp.webservice.WSGetProfileImage;
import com.chatapp.webservice.WSGetUserHistory;
import com.nirhart.parallaxscroll.views.ParallaxScrollView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by ANKIT on 4/9/2017.
 */

public class ProfileDetailFragment extends Fragment implements View.OnClickListener {
    private ProfileImageAdapter profileImageAdapter;
    private ViewPager viewPager;
    private ArrayList<String> imageList;
    private ArrayList<String> interestList;
    private View view;
    private AsyncLoadImage asyncLoadImage;
    private LinearLayout llLike;
    private ChipView mTextChipDefault;
    private CircleIndicator indicator;
    private ProgressDialog progressDialog;
    private AsyncLoadBasic asyncLoadBasic;
    private AsyncGetInterest asyncGetInterest;
    private UserModel mUserModel;
    private HomeActivity homeActivity;
    private FloatingActionButton btnEditProifile;
    private boolean isLogedUserProfile = false;
    private boolean isBackEnable = false;
    private RelativeLayout rlProgressbar;
    private ParallaxScrollView parallaxScrollView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            isLogedUserProfile = getArguments().getBoolean(getString(R.string.key_isloggedin_user), false);
            isBackEnable = getArguments().getBoolean(getString(R.string.key_is_back_enable), true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profiledetail, null);
        homeActivity = (HomeActivity) getActivity();
        homeActivity.setActionBarTitle(getString(R.string.screen_profile));
        homeActivity.isBackEnable(isBackEnable);
        init(view);
        return view;
    }

    private void init(View view) {
        parallaxScrollView = (ParallaxScrollView) view.findViewById(R.id.fragment_profiledetail_scrollview);
        rlProgressbar = (RelativeLayout) view.findViewById(R.id.fragment_profiledetail_rl_progressbar);
        viewPager = (ViewPager) view.findViewById(R.id.fragment_profiledetail_pager);
        indicator = (CircleIndicator) view.findViewById(R.id.fragment_profiledetail_indicator);
        btnEditProifile = (FloatingActionButton) view.findViewById(R.id.fragment_profiledetail_btn_edit);
        mTextChipDefault = (ChipView) view.findViewById(R.id.fragment_profiledetail_chipview_interest);
        llLike = (LinearLayout) view.findViewById(R.id.fragment_profiledetail_ll_like);
        asyncLoadImage = new AsyncLoadImage();
        asyncLoadImage.execute();
        if (isLogedUserProfile) {
            llLike.setVisibility(View.GONE);
            btnEditProifile.setVisibility(View.VISIBLE);
        } else {
            llLike.setVisibility(View.VISIBLE);
            btnEditProifile.setVisibility(View.GONE);
        }
        btnEditProifile.setOnClickListener(this);

    }

    private void loadData() {
        profileImageAdapter = new ProfileImageAdapter(getActivity(), imageList);
        viewPager.setAdapter(profileImageAdapter);
        indicator.setViewPager(viewPager);
        mTextChipDefault.setChipList(interestList);
    }

    @Override
    public void onClick(View v) {
        if (v == btnEditProifile) {
            EditProfileFragment editProfileFragment = new EditProfileFragment();
            Utils.addNextFragment(R.id.activity_home_container, homeActivity, editProfileFragment, ProfileDetailFragment.this, false);
        }
    }

    private class AsyncLoadImage extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            parallaxScrollView.setVisibility(View.GONE);
            rlProgressbar.setVisibility(View.VISIBLE);
            btnEditProifile.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            WSGetProfileImage wsGetProfileImage = new WSGetProfileImage();
            return wsGetProfileImage.executeWebservice(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""));
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (!isCancelled()) {
                if (strings != null && strings.size() > 0) {
                    imageList = new ArrayList<>();
                    imageList = strings;
                    asyncLoadBasic = new AsyncLoadBasic();
                    asyncLoadBasic.execute();
                }
            } else {
                Utils.dismissProgressDialog(progressDialog);
            }
        }
    }

    private class AsyncLoadBasic extends AsyncTask<Void, Void, UserModel> {
        @Override
        protected UserModel doInBackground(Void... voids) {
            WSGetUserHistory wsGetUserHistory = new WSGetUserHistory();
            return wsGetUserHistory.executeWebservice(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""));
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            super.onPostExecute(userModel);
            if (!isCancelled()) {
                if (userModel != null) {
                    mUserModel = userModel;
                }
                asyncGetInterest = new AsyncGetInterest();
                asyncGetInterest.execute();
            }
        }
    }

    private class AsyncGetInterest extends AsyncTask<String, Void, ArrayList<String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            WSGetInterest wsGetInterest = new WSGetInterest();
            return wsGetInterest.executeWebservice(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""));
        }

        @Override
        protected void onPostExecute(ArrayList<String> interestModels) {
            super.onPostExecute(interestModels);
            if (!isCancelled()) {
                parallaxScrollView.setVisibility(View.VISIBLE);
                rlProgressbar.setVisibility(View.GONE);
                btnEditProifile.setVisibility(View.VISIBLE);
                if (interestModels != null && interestModels.size() > 0) {
                    interestList = interestModels;
                }
                loadData();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        homeActivity.setActionBarTitle(getString(R.string.screen_profile));
        homeActivity.isBackEnable(isBackEnable);
    }
}
