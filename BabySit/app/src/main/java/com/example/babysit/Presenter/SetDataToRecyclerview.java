package com.example.babysit.Presenter;

import android.content.Context;

import com.example.babysit.Model.Child;
import com.example.babysit.View.IPassengetActivity;
import com.example.babysit.View.PassengerActivity;

import java.util.List;

public class SetDataToRecyclerview implements ISetDataToRecyclerview {

    IGetChildData iGetChildData;
    IPassengetActivity iPassengetActivity ;

    public SetDataToRecyclerview (IPassengetActivity iPassengetActivity){
        this.iPassengetActivity = iPassengetActivity;
        iGetChildData = new GetChildData(iPassengetActivity);
    }

    @Override
    public void setDataToRecycler() {
        List<Child> childList1 = iGetChildData.getListChild();
        iPassengetActivity.setDataToRecycler(childList1);
    }


}
