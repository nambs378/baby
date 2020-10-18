package com.example.babysit.Model;

public class ServiceState {
    int timeCd;
    String nameRoute;
    String nameChild;

    public ServiceState(int timeCd, String nameRoute, String nameChild) {
        this.timeCd = timeCd;
        this.nameRoute = nameRoute;
        this.nameChild = nameChild;
    }

    public ServiceState() {
    }

    public int getTimeCd() {
        return timeCd;
    }

    public void setTimeCd(int timeCd) {
        this.timeCd = timeCd;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public String getNameChild() {
        return nameChild;
    }

    public void setNameChild(String nameChild) {
        this.nameChild = nameChild;
    }
}
