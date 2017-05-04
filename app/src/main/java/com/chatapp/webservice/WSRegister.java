package com.chatapp.webservice;

import android.os.Bundle;
import android.text.TextUtils;

import com.chatapp.model.BaseResponseModel;
import com.chatapp.model.UserBasicInfoModel;
import com.chatapp.util.Constants;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by ANKIT on 5/4/2017.
 */

public class WSRegister {
    private String message = "";
    private int success = 0;


    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public UserBasicInfoModel executeWebservice(Bundle bundle) {

        final String url = Constants.BASEURL + "=registration";
        try {

            return parseJSONResponse(WebService.POST(url, generateRequest(bundle)));
//            return parseJSONResponse(WebService.POSTRAWDATA(url, generateJson(email, password).toString()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
