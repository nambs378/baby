package com.example.babysit.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.babysit.Database.DbContract.*;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BabySit.db";
    private  static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    private Resources mResources;



    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mResources = context.getResources();
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        final String CREATE_SQLITE_ROUTE =  "CREATE TABLE " +
                RouteTable.TABLE_NAME_ROUTE  + "(" +
                RouteTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RouteTable.COLUMN_NAMEROUTE + " TEXT," +
                RouteTable.COLUMN_CHILDROUTE + " TEXT, " +
                RouteTable.COLUMN_ICON + " BLOB, " +
                RouteTable.COLUMN_TIME + " TEXT " +
                ")";
        db.execSQL(CREATE_SQLITE_ROUTE);

        final String CREATE_SQLITE_CHILD =  "CREATE TABLE " +
                RouteTable.TABLE_NAME_CHILD  + "(" +
                RouteTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RouteTable.COLUMN_NAMECHILD + " TEXT," +
                RouteTable.COLUMN_IMAGECHILD + " BLOB " +
                ")";

        db.execSQL(CREATE_SQLITE_CHILD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + RouteTable.TABLE_NAME_ROUTE);
        db.execSQL("DROP TABLE IF EXISTS " + RouteTable.TABLE_NAME_CHILD);
        onCreate(db);

    }


    public void addChild(Child child) {
        ContentValues cv = new ContentValues();
        cv.put(RouteTable.COLUMN_NAMECHILD, child.getNameChild());
        cv.put(RouteTable.COLUMN_IMAGECHILD, child.getImageChild());
        db.insert(RouteTable.TABLE_NAME_CHILD  ,null,cv);
    }


    public List<Child> getAllChild(){
        List<Child> childList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ RouteTable.TABLE_NAME_CHILD,null);
        if (c.moveToFirst()){
            do {
                Child child = new Child();
                child.setNameChild(c.getString(c.getColumnIndex(RouteTable.COLUMN_NAMECHILD)));
                child.setImageChild(c.getBlob(c.getColumnIndex(RouteTable.COLUMN_IMAGECHILD)));
                childList.add(child);
            }while (c.moveToNext());
        }
        c.close();
        return childList;
    }

    public int deleteChild(String nameChild){
        return db.delete(RouteTable.TABLE_NAME_CHILD, RouteTable.COLUMN_NAMECHILD+"=?", new String[]{nameChild});

    }

    public void addRoute(Route route) {
        ContentValues cv = new ContentValues();
        cv.put(RouteTable.COLUMN_NAMEROUTE, route.getNameRoute());
        cv.put(RouteTable.COLUMN_CHILDROUTE, route.getChildlist());
        cv.put(RouteTable.COLUMN_ICON, route.getIcon());
        cv.put(RouteTable.COLUMN_TIME, route.getTime());
        db.insert(RouteTable.TABLE_NAME_ROUTE  ,null,cv);
    }

    public List<Route> getAllRoute(){
        List<Route> routeList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ RouteTable.TABLE_NAME_ROUTE,null);
        if (c.moveToFirst()){
            do {
                Route route = new Route();
                route.setNameRoute(c.getString(c.getColumnIndex(RouteTable.COLUMN_NAMEROUTE)));
                route.setChildlist(c.getString(c.getColumnIndex(RouteTable.COLUMN_CHILDROUTE)));
                route.setIcon(c.getBlob(c.getColumnIndex(RouteTable.COLUMN_ICON)));
                route.setTime(c.getString(c.getColumnIndex(RouteTable.COLUMN_TIME)));

                routeList.add(route);
            }while (c.moveToNext());
        }
        c.close();
        return routeList;
    }


    public List<Child> getCdChild(String namechild){

        String[] outputName = namechild.split(", ");
        List<Child> childList = new ArrayList<>();
        db = getReadableDatabase();

        for (int i= 0;i<outputName.length;i++){
            Cursor c = db.rawQuery("SELECT * FROM "+ RouteTable.TABLE_NAME_CHILD+
                    " WHERE " + RouteTable.COLUMN_NAMECHILD + " = ?",new String[] {outputName[i]});
            if (c.moveToFirst()){
                do {
                    Child child = new Child();
                    child.setNameChild(c.getString(c.getColumnIndex(RouteTable.COLUMN_NAMECHILD)));
                    child.setImageChild(c.getBlob(c.getColumnIndex(RouteTable.COLUMN_IMAGECHILD)));
                    childList.add(child);
                }while (c.moveToNext());
            }
            c.close();
        }
        return childList;

    }


    public int updateChild (String oldName,Child child) {
        db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(RouteTable.COLUMN_NAMECHILD, child.getNameChild());
        contentValues.put(RouteTable.COLUMN_IMAGECHILD, child.getImageChild());
        return db.update(RouteTable.TABLE_NAME_CHILD,contentValues,RouteTable.COLUMN_NAMECHILD +
                " = ?",new String[]{oldName});
    }

    public int updateRoute (String oldName,Route route) {
        db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(RouteTable.COLUMN_NAMEROUTE, route.getNameRoute());
        contentValues.put(RouteTable.COLUMN_CHILDROUTE, route.getChildlist());
        contentValues.put(RouteTable.COLUMN_ICON, route.getIcon());
        contentValues.put(RouteTable.COLUMN_TIME, route.getTime());
        return db.update(RouteTable.TABLE_NAME_ROUTE,contentValues,RouteTable.COLUMN_NAMEROUTE +
                " = ?",new String[]{oldName});
    }

    public int deleteRoute(String nameRoute) {
        return db.delete(RouteTable.TABLE_NAME_ROUTE, RouteTable.COLUMN_NAMEROUTE+"=?", new String[]{nameRoute});
    }



}
