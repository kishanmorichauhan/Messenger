package com.example.chatapp.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String uid, name,phoneNumber,profileImage;
    boolean isSelected;
    ArrayList<String> broadCastId;

    public User(){
        
    }

    public User(String uid, String name, String phoneNumber, String profileImage) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public User(String id, String name, ArrayList<String> broadCastId) {
        this.uid = id;
        this.name = name;
        this.broadCastId = broadCastId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ArrayList<String> getBroadCastId() {
        return broadCastId;
    }

    public void setBroadCastId(ArrayList<String> broadCastId) {
        this.broadCastId = broadCastId;
    }
}
