package com.chatapp.model;

/**
 * Created by ANKIT on 4/1/2017.
 */

public class SuggetionModel {
    private String name;
    private String age;
    private String location;
    private String profession;
    private String distance;
    private int like;
    private String imageUrl;

    public SuggetionModel(String name, String age, String location, String profession, String distance, int like, String imageUrl) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.profession = profession;
        this.distance = distance;
        this.like = like;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
