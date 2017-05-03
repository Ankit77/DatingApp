package com.chatapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.text.TextUtils;

import com.chatapp.util.PREF;
import com.google.firebase.iid.FirebaseInstanceId;
import com.quickblox.chat.QBChatService;

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
        initChat();
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

    private void initChat() {
        QBChatService.setDebugEnabled(true); // enable chat logging

        QBChatService.setDefaultPacketReplyTimeout(10000);//set reply timeout in milliseconds for connection's packet.
//        Can be used for events like login, join to dialog to increase waiting response time from server if network is slow.
        QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
        chatServiceConfigurationBuilder.setSocketTimeout(60); //Sets chat socket's read timeout in seconds
        chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
        chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
        QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);

    }

}
