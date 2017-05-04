
package com.chatapp.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDetailModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("fb_token")
    @Expose
    private String fbToken;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("auth_type")
    @Expose
    private String authType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("user_detail_id")
    @Expose
    private String userDetailId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("distance_unit")
    @Expose
    private String distanceUnit;
    @SerializedName("enable_new_match")
    @Expose
    private String enableNewMatch;
    @SerializedName("show_current_location")
    @Expose
    private String showCurrentLocation;
    @SerializedName("enable_message")
    @Expose
    private String enableMessage;
    @SerializedName("enable_message_like")
    @Expose
    private String enableMessageLike;
    @SerializedName("enable_superlikes")
    @Expose
    private String enableSuperlikes;
    @SerializedName("show_age_enabled")
    @Expose
    private String showAgeEnabled;
    @SerializedName("show_location_enabled")
    @Expose
    private String showLocationEnabled;
    @SerializedName("interested_in")
    @Expose
    private String interestedIn;
    @SerializedName("radius")
    @Expose
    private String radius;
    @SerializedName("minage")
    @Expose
    private String minage;
    @SerializedName("maxage")
    @Expose
    private String maxage;
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("relationship")
    @Expose
    private String relationship;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("current_work")
    @Expose
    private String currentWork;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("faith")
    @Expose
    private String faith;
    @SerializedName("location")
    @Expose
    private String location;
//    @SerializedName("photos")
//    @Expose
//    private List<Photo> photos = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(String userDetailId) {
        this.userDetailId = userDetailId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getEnableNewMatch() {
        return enableNewMatch;
    }

    public void setEnableNewMatch(String enableNewMatch) {
        this.enableNewMatch = enableNewMatch;
    }

    public String getShowCurrentLocation() {
        return showCurrentLocation;
    }

    public void setShowCurrentLocation(String showCurrentLocation) {
        this.showCurrentLocation = showCurrentLocation;
    }

    public String getEnableMessage() {
        return enableMessage;
    }

    public void setEnableMessage(String enableMessage) {
        this.enableMessage = enableMessage;
    }

    public String getEnableMessageLike() {
        return enableMessageLike;
    }

    public void setEnableMessageLike(String enableMessageLike) {
        this.enableMessageLike = enableMessageLike;
    }

    public String getEnableSuperlikes() {
        return enableSuperlikes;
    }

    public void setEnableSuperlikes(String enableSuperlikes) {
        this.enableSuperlikes = enableSuperlikes;
    }

    public String getShowAgeEnabled() {
        return showAgeEnabled;
    }

    public void setShowAgeEnabled(String showAgeEnabled) {
        this.showAgeEnabled = showAgeEnabled;
    }

    public String getShowLocationEnabled() {
        return showLocationEnabled;
    }

    public void setShowLocationEnabled(String showLocationEnabled) {
        this.showLocationEnabled = showLocationEnabled;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getMinage() {
        return minage;
    }

    public void setMinage(String minage) {
        this.minage = minage;
    }

    public String getMaxage() {
        return maxage;
    }

    public void setMaxage(String maxage) {
        this.maxage = maxage;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCurrentWork() {
        return currentWork;
    }

    public void setCurrentWork(String currentWork) {
        this.currentWork = currentWork;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFaith() {
        return faith;
    }

    public void setFaith(String faith) {
        this.faith = faith;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public List<Photo> getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(List<Photo> photos) {
//        this.photos = photos;
//    }

}
