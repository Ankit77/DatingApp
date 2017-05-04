package com.chatapp.webservice;

import android.os.Bundle;
import android.text.TextUtils;

import com.chatapp.DatingApp;
import com.chatapp.model.dashboard.BaseDashboardModel;
import com.chatapp.model.dashboard.UserDetailModel;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by ANKIT on 5/5/2017.
 */

public class WSSetting {
    private String message = "";
    private int success = 0;


    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public boolean executeWebservice(String location, double lat, double lang, int radius, String interested_in, int minage, int maxage, String distance_unit, int enable_notification, int enable_new_match, int enable_message, int enable_message_like, int enable_superlikes) {

        final String url = Constants.BASEURL + "=editusersettings";
        try {
            Bundle bundle = new Bundle();
            bundle.putString("data", generateJson(location, lat, lang, radius, interested_in, minage, maxage, distance_unit, enable_notification, enable_new_match, enable_message, enable_message_like, enable_superlikes).toString());
            return parseJSONResponse(WebService.POST(url, generateRequest(bundle)));
//            return parseJSONResponse(WebService.POSTRAWDATA(url, generateJson(email, password).toString()), true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private JSONObject generateJson(String location, double lat, double lang, int radius, String interested_in, int minage, int maxage, String distance_unit, int enable_notification, int enable_new_match, int enable_message, int enable_message_like, int enable_superlikes) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_USERID, ""));
            jsonObject.put("show_current_location", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_IS_CURRENT_LOCATION, ""));
            jsonObject.put("location", location);
            jsonObject.put("lat", location);
            jsonObject.put("long", location);
            jsonObject.put("interested_in", interested_in);
            jsonObject.put("radius", radius);
            jsonObject.put("minage", minage);
            jsonObject.put("maxage", maxage);
            jsonObject.put("distance_unit", distance_unit);
            jsonObject.put("enable_notification", enable_notification);
            jsonObject.put("enable_new_match", enable_new_match);
            jsonObject.put("enable_message", enable_message);
            jsonObject.put("enable_message_like", enable_message_like);
            jsonObject.put("enable_superlikes", enable_superlikes);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    private RequestBody generateRequest(Bundle bundle) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                if (bundle.get(key) != null && !TextUtils.isEmpty(bundle.get(key).toString())) {
                    formBodyBuilder.add(key, bundle.get(key).toString());
                }
            }
        }
        return formBodyBuilder.build();
    }

    public boolean parseJSONResponse(final String response) {
        try {
//            if (!TextUtils.isEmpty(response)) {
//                BaseDashboardModel baseDashboardModel = WebService.getGsonInstance().fromJson(response, BaseDashboardModel.class);
//                if (baseDashboardModel != null) {
//                    if (Integer.parseInt(baseDashboardModel.getSuccess()) == 1) {
//                        success = Constants.RESPONSE_SUCCESS;
//                        userlist.clear();
//                        userlist.addAll(baseDashboardModel.getUserDetail());
//                    } else {
//                        success = Constants.RESPONSE_FAIL;
//                        message = baseDashboardModel.getMessage();
//                    }
//                }
//                return userlist;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;

        }
        return true;
    }
}
