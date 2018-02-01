package com.babuwyt.siji.entity;

import java.util.ArrayList;

/**
 * Created by lenovo on 2018/1/24.
 */

public class ActionPicEntity {
    private String name;
    private ArrayList<PicEntity> picEntities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PicEntity> getPicEntities() {
        return picEntities;
    }

    public void setPicEntities(ArrayList<PicEntity> picEntities) {
        this.picEntities = picEntities;
    }
}
