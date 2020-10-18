package com.example.babysit.Database;

import android.provider.BaseColumns;

public class DbContract {

    private DbContract() {}

    public static class RouteTable implements BaseColumns{

        public static final String TABLE_NAME_ROUTE = "routetable";
        public static final String COLUMN_NAMEROUTE = "nameroute";
        public static final String COLUMN_CHILDROUTE = "childroute";
        public static final String COLUMN_ICON = "iconroute";
        public static final String COLUMN_TIME = "timeroute";

        public static final String TABLE_NAME_CHILD = "childtable";
        public static final String COLUMN_NAMECHILD = "namechild";
        public static final String COLUMN_IMAGECHILD = "imagechild";



    }


}
