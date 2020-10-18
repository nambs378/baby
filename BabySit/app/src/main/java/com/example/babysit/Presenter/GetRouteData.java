package com.example.babysit.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.babysit.Database.DbHelper;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;
import com.example.babysit.View.IHome;
import com.example.babysit.View.IPassengetActivity;

import java.util.List;

public class GetRouteData implements IGetRouteData {

    DbHelper dbHelper;


    public GetRouteData(IHome iHome){
        dbHelper =new DbHelper((Context) iHome);
    }

    @Override
    public List<Route> getListRoute() {
        List<Route> routeList = dbHelper.getAllRoute();
        Log.d("listchild", routeList.toString()+"zxc");
        return routeList;
    }


}
