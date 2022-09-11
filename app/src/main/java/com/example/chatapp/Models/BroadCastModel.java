package com.example.chatapp.Models;

import java.util.ArrayList;
import java.util.List;

public class BroadCastModel {
    
    private String id ,name;
    ArrayList<String> isList;

    public BroadCastModel(String id, String name, ArrayList<String> isList) {
        this.id = id;
        this.name = name;
        this.isList = isList;
    }

    public BroadCastModel() {
    }

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

    public ArrayList<String> getIsList() {
        return isList;
    }

    public void setIsList(ArrayList<String> isList) {
        this.isList = isList;
    }
}
