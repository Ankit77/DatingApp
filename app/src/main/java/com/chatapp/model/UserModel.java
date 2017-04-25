package com.chatapp.model;

import java.util.ArrayList;

/**
 * Created by ANKIT on 4/10/2017.
 */

public class UserModel {
    private String gender;
    private String location;
    private String email;
    private String userName;
    private  String Birthday;
    private WorkExperianceModel workHistory;
    private ArrayList<String> interest;
    private ArrayList<String> images;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public WorkExperianceModel getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(WorkExperianceModel workHistory) {
        this.workHistory = workHistory;
    }

    public ArrayList<String> getInterest() {
        return interest;
    }

    public void setInterest(ArrayList<String> interest) {
        this.interest = interest;
    }

    //    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getBirthday() {
//        return Birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        Birthday = birthday;
//    }
//
//    public ArrayList<String> getEducationHistory() {
//        return educationHistory;
//    }
//
//    public void setEducationHistory(ArrayList<String> educationHistory) {
//        this.educationHistory = educationHistory;
//    }
//
//    public WorkExperianceModel getWorkHistory() {
//        return workHistory;
//    }
//
//    public void setWorkHistory(WorkExperianceModel workHistory) {
//        this.workHistory = workHistory;
//    }
}
