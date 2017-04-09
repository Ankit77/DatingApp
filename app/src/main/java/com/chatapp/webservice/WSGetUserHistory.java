package com.chatapp.webservice;

import android.text.TextUtils;

import com.chatapp.model.UserModel;
import com.chatapp.model.WorkExperianceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
        final String url = "https://graph.facebook.com/v2.8/me?fields=id,name,work,education,birthday&access_token=" + accessToken;
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
                JSONArray jsonArray_edu = jsonObject.getJSONArray("education");
                if (jsonArray_edu != null && jsonArray_edu.length() > 0) {
                    ArrayList<String> eduList = new ArrayList<>();
                    for (int i = 0; i < jsonArray_edu.length(); i++) {
                        JSONObject jsonObject_school = jsonArray_edu.getJSONObject(i);
                        eduList.add(jsonObject_school.getJSONObject("school").optString("name"));
                    }
                    userModel.setEducationHistory(eduList);
                }
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
