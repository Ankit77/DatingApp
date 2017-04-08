package com.chatapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by indianic on 08/04/17.
 */

public class DatingApp extends Application {
    private SharedPreferences sharedPreferences;
    private static DatingApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        sInstance = this;
//        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public static DatingApp getsInstance() {
        return sInstance;
    }


}
