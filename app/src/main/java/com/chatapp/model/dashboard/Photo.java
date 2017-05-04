
package com.chatapp.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("user_photos_id")
    @Expose
    private String userPhotosId;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("profile_photo")
    @Expose
    private String profilePhoto;

    public String getUserPhotosId() {
        return userPhotosId;
    }

    public void setUserPhotosId(String userPhotosId) {
        this.userPhotosId = userPhotosId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
