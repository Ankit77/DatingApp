package com.chatapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.text.TextUtils;

import com.chatapp.util.PREF;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by indianic on 08/04/17.
 */

public class DatingApp extends Application {
    private SharedPreferences sharedPreferences;
    private static DatingApp sInstance;
    private Location currentLocation;
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        sInstance = this;
        token = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            sharedPreferences.edit().putString(PREF.PREF_TOKEN, token).commit();
        }
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public static DatingApp getsInstance() {
        return sInstance;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

}
