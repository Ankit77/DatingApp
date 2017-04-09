package com.chatapp.webservice;

import android.content.Context;
import android.text.TextUtils;

import com.chatapp.model.InterestModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;

/**
 * Created by ANKIT on 4/9/2017.
 */

public class WSGetProfileImage {
    private String message = "";
    private int success = 0;

    public WSGetProfileImage() {

    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public ArrayList<String> executeWebservice(String accessToken) {
        final String url = "https://graph.facebook.com/v2.8/me?fields=albums.fields(id,name,photos.fields(id,icon,picture,source,name,height,width),count,cover_photo)&access_token=" + accessToken;
        try {
            return parseJSONResponse(WebService.GET(HttpUrl.parse(url)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> parseJSONResponse(final String response) {
        ArrayList<String> imageList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(response)) {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject_albums = jsonObject.getJSONObject("albums");
                JSONArray jsonArray_data = jsonObject_albums.getJSONArray("data");
                if (jsonArray_data != null && jsonArray_data.length() > 0) {
                    for (int i = 0; i < jsonArray_data.length(); i++) {
                        JSONObject jsonObject_data = jsonArray_data.getJSONObject(i);
                        if (jsonObject_data.getString("name").equalsIgnoreCase("Cover Photos") || jsonObject_data.getString("name").equalsIgnoreCase("Profile Pictures")) {
                            JSONObject jsonObject_photos = jsonObject_data.getJSONObject("photos");
                            JSONArray jsonArray_data1 = jsonObject_photos.getJSONArray("data");
                            if (jsonArray_data1 != null && jsonArray_data1.length() > 0) {
                                for (int j = 0; j < jsonArray_data1.length(); j++) {
                                    JSONObject jsonObject_image = jsonArray_data1.getJSONObject(j);
                                    imageList.add(jsonObject_image.getString("source"));
                                }
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return imageList;
    }

}
