package com.example.babysit.Presenter;

import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;
import com.example.babysit.View.IHome;
import com.example.babysit.View.IPassengetActivity;

import java.util.List;

public class SetRouteToRecyclerview implements ISetRouteToRecyclerview {

    IGetRouteData iGetRouteData;
    IHome iHome ;

    public SetRouteToRecyclerview (IHome iHome){
        this.iHome = iHome;
        iGetRouteData = new GetRouteData(iHome);
    }

    @Override
    public void setRouteToRecycler() {
        List<Route> routeList = iGetRouteData.getListRoute();
        iHome.setDataToRecycler(routeList);
    }

}
