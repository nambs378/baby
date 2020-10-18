package com.example.babysit.Model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class Route implements IRoute, Serializable {
    private String nameRoute;
    private String childlist;
    private byte[] icon;
    private String time;

    public Route(String nameRoute, String childlist, byte[] icon, String time) {
        this.nameRoute = nameRoute;
        this.childlist = childlist;
        this.icon = icon;
        this.time = time;
    }

    public Route() {
    }


    @Override
    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    @Override
    public String getChildlist() {
        return childlist;
    }

    @Override
    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public void setChildlist(String childlist) {
        this.childlist = childlist;
    }


    @Override
    public String getTime() {
        return time;
    }

    @Override
    public boolean isValidDataRoute() {

        if (TextUtils.isEmpty(getNameRoute()) ) {
            return false;
        } else{
            return true;
        }
    }

    public void setTime(String time) {
        this.time = time;
    }



}
