package com.example.babysit.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.babysit.Database.DbHelper;
import com.example.babysit.Model.Child;
import com.example.babysit.View.IPassengetActivity;

import java.util.List;

public class GetChildData implements IGetChildData{

    DbHelper dbHelper;


    public GetChildData(IPassengetActivity iPassengetActivity){
        dbHelper =new DbHelper((Context) iPassengetActivity);
    }



    @Override
    public List<Child> getListChild() {
        List<Child> childList = dbHelper.getAllChild();
        Log.d("listchild", childList.toString()+"zxc");
        return childList;
    }

}
