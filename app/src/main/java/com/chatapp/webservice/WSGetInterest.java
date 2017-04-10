package com.chatapp.webservice;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;

/**
 * Created by ANKIT on 3/4/2017.
 */

public class WSGetInterest {

    private String message = "";
    private int success = 0;
    private Context context;
    private int total;
    private boolean isNextPageAvail = false;
    private String isNextPageUrl = "";
    ArrayList<String> interestModelArrayList = new ArrayList<>();

    public WSGetInterest(Context context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public ArrayList<String> executeWebservice(String accessToken) {
        final String url = "https://graph.facebook.com/v2.8/me?fields=context&access_token=" + accessToken;
        try {
            return parseJSONResponse(WebService.GET(HttpUrl.parse(url)), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> parseJSONResponse(final String response, boolean isFirstTime) {
        try {
            if (isFirstTime) {
                if (!TextUtils.isEmpty(response)) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject_context = jsonObject.getJSONObject("context");
                    JSONObject jsonObject_like = jsonObject_context.getJSONObject("mutual_likes");
                    return parseDataResponse(jsonObject_like);
                }
            } else {
                JSONObject jsonObject = new JSONObject(response);
                return parseDataResponse(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }


    public ArrayList<String> parseDataResponse(JSONObject jsonObject_data) {

        try {
            JSONObject jsonObject_summery = jsonObject_data.getJSONObject("summary");
            total = jsonObject_summery.getInt("total_count");

            JSONArray jsonArray_likes = jsonObject_data.getJSONArray("data");
            if (jsonArray_likes != null && jsonArray_likes.length() > 0) {
                ArrayList<String> interestdata = new ArrayList<>();
                for (int i = 0; i < jsonArray_likes.length(); i++) {
                    JSONObject jsonObject_index = jsonArray_likes.getJSONObject(i);
//                    InterestModel interestModel = new InterestModel();
//                    interestModel.setId(jsonObject_index.optString("id"));
//                    interestModel.setName(jsonObject_index.optString("name"));
                    interestdata.add(jsonObject_index.optString("name"));
                }
                interestModelArrayList.addAll(interestdata);
                JSONObject jsonObject_page = jsonObject_data.getJSONObject("paging");
                if (jsonObject_page.has("next")) {
                    isNextPageUrl = jsonObject_page.optString("next");
                    isNextPageAvail = true;
                    isNextPageUrl = jsonObject_page.optString("next");
                    parseJSONResponse(WebService.GET(HttpUrl.parse(isNextPageUrl)), false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return interestModelArrayList;
    }

}
