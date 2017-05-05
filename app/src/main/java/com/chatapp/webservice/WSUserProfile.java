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

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by indianic on 05/05/17.
 */

public class WSUserProfile {
    private String message = "";
    private int success = 0;


    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public UserDetailModel executeWebservice() {

        final String url = Constants.BASEURL + "=userprofile";
        try {
            Bundle bundle = new Bundle();
            bundle.putString("data", generateJson().toString());
            return parseJSONResponse(WebService.POST(url, generateRequest(bundle)));
//            return parseJSONResponse(WebService.POSTRAWDATA(url, generateJson(email, password).toString()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject generateJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", DatingApp.getsInstance().getSharedPreferences().getString(PREF.PREF_USERID, ""));
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

    public UserDetailModel parseJSONResponse(final String response) {
        try {
            if (!TextUtils.isEmpty(response)) {
               BaseDashboardModel baseDashboardModel = WebService.getGsonInstance().fromJson(response, BaseDashboardModel.class);
                if (baseDashboardModel != null) {
                    if (Integer.parseInt(baseDashboardModel.getSuccess()) == 1) {
                        success = Constants.RESPONSE_SUCCESS;
                    } else {
                        success = Constants.RESPONSE_FAIL;
                        message = baseDashboardModel.getMessage();
                        return null;
                    }
                }
                return baseDashboardModel.getUserDetail().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }

}
