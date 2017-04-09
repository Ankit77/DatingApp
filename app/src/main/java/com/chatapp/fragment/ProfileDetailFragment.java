package com.chatapp.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatapp.R;
import com.chatapp.adapter.ProfileImageAdapter;
import com.chatapp.model.UserModel;
import com.chatapp.util.Utils;
import com.chatapp.webservice.WSGetProfileImage;
import com.chatapp.webservice.WSGetUserHistory;
import com.facebook.AccessToken;

import java.util.ArrayList;

/**
 * Created by ANKIT on 4/9/2017.
 */

public class ProfileDetailFragment extends Fragment {
    private ProfileImageAdapter profileImageAdapter;
    private ViewPager viewPager;
    private ArrayList<String> imageList;
    private View view;
    private AsyncLoadImage asyncLoadImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profiledetail, null);
        init(view);
        return view;
    }

    private void init(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.pager);
//        if (Utils.isNetworkAvailable(getActivity())) {
//        asyncLoadImage = new AsyncLoadImage();
//        asyncLoadImage.execute();

        new AsyncLoadBasic().execute();
//        }
    }

    private void loadImage() {
        profileImageAdapter = new ProfileImageAdapter(getActivity(), imageList);
        viewPager.setAdapter(profileImageAdapter);
    }

    private class AsyncLoadImage extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            WSGetProfileImage wsGetProfileImage = new WSGetProfileImage();
            return wsGetProfileImage.executeWebservice(AccessToken.getCurrentAccessToken().getToken());
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (!isCancelled()) {
                if (strings != null && strings.size() > 0) {
                    imageList = new ArrayList<>();
                    imageList = strings;
                    loadImage();
                }
            }
        }
    }

    private class AsyncLoadBasic extends AsyncTask<Void, Void, UserModel> {
        @Override
        protected UserModel doInBackground(Void... voids) {
            WSGetUserHistory wsGetUserHistory = new WSGetUserHistory();
            return wsGetUserHistory.executeWebservice(AccessToken.getCurrentAccessToken().getToken());
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            super.onPostExecute(userModel);
            if (!isCancelled()) {

            }
        }
    }

}
