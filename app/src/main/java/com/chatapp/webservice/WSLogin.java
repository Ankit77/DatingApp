package com.chatapp.webservice;

import android.os.Bundle;
import android.text.TextUtils;

import com.chatapp.DatingApp;
import com.chatapp.model.Example;
import com.chatapp.util.Constants;
import com.chatapp.util.PREF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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
            Bundle bundle = new Bundle();
            bundle.putString("data", generateJson(email, password).toString());
            return parseJSONResponse(WebService.POST(url, generateRequest(bundle)));
//            return parseJSONResponse(WebService.POSTRAWDATA(url, generateJson(email, password).toString()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject generateJson(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_TOKEN, ""));
            if (!TextUtils.isEmpty(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_ID, ""))) {
                jsonObject.put("fb_id", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_ID, ""));
            }
            if (!TextUtils.isEmpty(DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""))) {
                jsonObject.put("fb_token", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_FB_TOKEN, ""));
            }
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

    public Example parseJSONResponse(final String response) {
        try {
            if (TextUtils.isEmpty(response)) {
                Example example = WebService.getGsonInstance().fromJson(response, Example.class);
                return example;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }


}
