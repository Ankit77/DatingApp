
package com.chatapp.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseDashboardModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("user_detail")
    @Expose
    private List<UserDetailModel> userDetail = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserDetailModel> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(List<UserDetailModel> userDetail) {
        this.userDetail = userDetail;
    }

}
