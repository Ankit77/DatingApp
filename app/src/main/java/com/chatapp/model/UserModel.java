package com.chatapp.model;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by ANKIT on 4/10/2017.
 */

public class UserModel {
    private String userName;
    private  String Birthday;
    private ArrayList<String> educationHistory;
    private WorkExperianceModel workHistory;

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

    public ArrayList<String> getEducationHistory() {
        return educationHistory;
    }

    public void setEducationHistory(ArrayList<String> educationHistory) {
        this.educationHistory = educationHistory;
    }

    public WorkExperianceModel getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(WorkExperianceModel workHistory) {
        this.workHistory = workHistory;
    }
}
