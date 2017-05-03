package com.chatapp.webservice;

import com.chatapp.DatingApp;
import com.chatapp.model.Example;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by indianic on 03/05/17.
 */

public class WSLogin {
    private String message = "";
    private int success = 0;


    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public Example executeWebservice(String email, String password) {

        final String url = Constants.BASEURL + "=login";
        try {
            return parseJSONResponse(WebService.POSTRAWDATA(url, generateJson(email, password).toString()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject generateJson(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""));
            jsonObject.put("fb_id", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_ID, ""));
            jsonObject.put("fb_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""));
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("lat", "" + DatingApp.getsInstance().getCurrentLocation().getLatitude());
            jsonObject.put("long", "" + DatingApp.getsInstance().getCurrentLocation().getLongitude());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    public Example parseJSONResponse(final String response, boolean isFirstTime) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }


}
