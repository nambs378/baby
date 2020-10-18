package com.example.babysit.Model;

import android.text.TextUtils;

import java.io.Serializable;

public class Child implements IChild, Serializable {
//    private int idChild;
    private String nameChild;
    private byte[] imageChild;

    public Child( String nameChild, byte[] imageChild) {
//        this.idChild = idChild;
        this.nameChild = nameChild;
        this.imageChild = imageChild;
    }

    public Child() {
    }



//    @Override
//    public String getNameChild() {
//        return nameChild;
//    }
//
//    @Override
//    public byte[] getImageChild() {
//        return imageChild;
//    }
//
//


    @Override
    public String getNameChild() {
        return nameChild;
    }

    @Override
    public byte[] getImageChild() {
        return imageChild;
    }

    @Override
    public void setNameChild(String nameChild) {
        this.nameChild = nameChild;
    }

    @Override
    public void setImageChild(byte[] imageChild) {
        this.imageChild = imageChild;
    }

    @Override
    public boolean isValidDataChild() {

        if (TextUtils.isEmpty(getNameChild()) ) {
            return false;
        } else {
            return true;
        }
    }


}
