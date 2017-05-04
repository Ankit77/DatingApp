package com.chatapp.webservice;

import android.os.Bundle;
import android.text.TextUtils;

import com.chatapp.DatingApp;
import com.chatapp.model.BaseResponseModel;
import com.chatapp.model.UserBasicInfoModel;
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

    public UserBasicInfoModel executeWebservice(String email, String password) {

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
            if (!TextUtils.isEmpty(password))
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

    public UserBasicInfoModel parseJSONResponse(final String response) {
        UserBasicInfoModel userBasicInfoModel = null;
        try {
            if (!TextUtils.isEmpty(response)) {
                BaseResponseModel baseResponseModel = WebService.parseBaseResponse(response);
                if (baseResponseModel != null) {
                    if (Integer.parseInt(baseResponseModel.getSuccess()) == 1) {
                        userBasicInfoModel = WebService.getGsonInstance().fromJson(baseResponseModel.getData().toString(), UserBasicInfoModel.class);
                        success = Constants.RESPONSE_SUCCESS;
                    } else {
                        userBasicInfoModel = null;
                        success = Constants.RESPONSE_FAIL;
                        message = baseResponseModel.getMessage();
                    }
                }
                return userBasicInfoModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }


}
