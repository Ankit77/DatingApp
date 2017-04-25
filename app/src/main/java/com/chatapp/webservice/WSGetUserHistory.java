package com.chatapp.webservice;

import android.text.TextUtils;

import com.chatapp.model.UserModel;
import com.chatapp.model.WorkExperianceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;

/**
 * Created by ANKIT on 4/10/2017.
 */

public class WSGetUserHistory {
    private String message = "";
    private int success = 0;

    public WSGetUserHistory() {

    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public UserModel executeWebservice(String accessToken) {
        final String url = "https://graph.facebook.com/v2.8/me?fields=id,name,work,birthday,email,gender,location&access_token=" + accessToken;
        try {
            return parseJSONResponse(WebService.GET(HttpUrl.parse(url)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public UserModel parseJSONResponse(final String response) {
        UserModel userModel = new UserModel();
        try {
            if (!TextUtils.isEmpty(response)) {
                JSONObject jsonObject = new JSONObject(response);
                userModel.setUserName(jsonObject.optString("name"));
                userModel.setBirthday(jsonObject.optString("birthday"));
                JSONObject jsonObjec_location = jsonObject.getJSONObject("location");
                if (jsonObjec_location != null) {
                    userModel.setLocation(jsonObjec_location.getString("name"));
                }
                userModel.setGender(jsonObject.getString("gender"));
                userModel.setEmail(jsonObject.getString("email"));
                JSONArray jsonArray_work = jsonObject.getJSONArray("work");
                if (jsonArray_work != null && jsonArray_work.length() > 0) {
                    WorkExperianceModel workExperianceModel = new WorkExperianceModel();
                    JSONObject jsonObject_work = jsonArray_work.getJSONObject(0);
                    workExperianceModel.setEmployer(jsonObject_work.getJSONObject("employer").optString("name"));
                    workExperianceModel.setLocation(jsonObject_work.getJSONObject("location").optString("name"));
                    workExperianceModel.setPosition(jsonObject_work.getJSONObject("position").optString("name"));
                    userModel.setWorkHistory(workExperianceModel);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return userModel;
    }
}
