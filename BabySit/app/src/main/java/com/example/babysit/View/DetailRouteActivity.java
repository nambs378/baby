package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysit.Adapter.ChildAdapter;
import com.example.babysit.Database.CheckServiceRunning;
import com.example.babysit.Database.DbHelper;
import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.Fragment.ListTimeFragment;
import com.example.babysit.Fragment.TimePickerFragment;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;
import com.example.babysit.Presenter.ISetDataToRecyclerview;
import com.example.babysit.Presenter.SetDataToRecyclerview;
import com.example.babysit.R;
import com.example.babysit.Service.AlarmSongService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DetailRouteActivity extends AppCompatActivity implements IPassengetActivity {
    //
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int UPDATE_CHILD_LIST = 113;

    Button btChangeTimeList , btChangeTimePick;
    EditText nameRoute;
    ImageView ivStart, ivIcon;
    LinearLayout linearLayoutAdd;
    //
    public static int tabSelect = 2;

    Route route1;
    TextView tvCancel, tvSave, tvSetIcon;

    RecyclerView recyclerView ;
    //
    public static ChildAdapter childAdapter;
    public static ISetDataToRecyclerview iSetDataToRecyclerview;

    DbHelper dbHelper;
    int state = 2;
    //
    public static int mincurrent;

    String nameR;

    //
    public static String nameChild;
    byte[] iconIm ;

    //
    public static String timeRoute;
    int min;

    //
    public static HashMap<Integer , String> mapChildState = new HashMap<Integer, String>();

    int imageIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);
        AnhXa();
        checkTab();

        dbHelper = new DbHelper(this);

        TimePickerFragment timePickerFragment = (TimePickerFragment) getSupportFragmentManager().findFragmentById(R.id.timepickFragment);

        iSetDataToRecyclerview = new SetDataToRecyclerview(this);
        iSetDataToRecyclerview.setDataToRecycler();


        try{
            Bundle bundle = getIntent().getExtras();
            route1 = (Route) bundle.getSerializable("route");
            if (route1!=null){
                tvSetIcon.setVisibility(View.INVISIBLE);
            }
            tabSelect =2;
            nameRoute.setText(route1.getNameRoute());
            ChildAdapter.listnameChild = route1.getChildlist();
            iconIm =route1.getIcon();
            ivIcon.setImageBitmap(ByteToBitmap.ByteToBitmapConvert(iconIm));

            getTimeToFrag();
        } catch (Exception e){

        }







        btChangeTimeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSelect=1;
                checkTab();
            }
        });

        btChangeTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSelect=2;
                checkTab();
            }
        });

        linearLayoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRouteActivity.this, AddChildActivity.class);
                startActivityForResult(intent, UPDATE_CHILD_LIST);

            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (route1==null){
                    nameR = nameRoute.getText().toString();
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int key: mapChildState.keySet()) {
                        if (mapChildState.size()== (key)){
                            stringBuilder.append(mapChildState.get(key));
                        } else {
                            stringBuilder.append(mapChildState.get(key)+ ",");
                        }
                    }
                    nameChild = stringBuilder.toString();
                    Log.d("testnamechild", nameChild);
                    if (tabSelect==1){
                        timeRoute = ListTimeFragment.minList+"";
                    } else if (tabSelect==2) {
                        min = TimePickerFragment.min;
                        timeRoute = min+"";
                    }

                    Log.d("testtimechild", timeRoute);
                    Route route2= new Route(nameR,nameChild,iconIm,timeRoute);

                    boolean etempty = route2.isValidDataRoute();

                    if (etempty==true){
                        dbHelper.addRoute(route2);
                        HomeActivity.iSetRouteToRecyclerview.setRouteToRecycler();
                        HomeActivity.routeAdapter.notifyDataSetChanged();
                    } else {
                        nameRoute.setError("Please enter name route!");
                    }



                } else {
                    nameR = nameRoute.getText().toString();
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int key: mapChildState.keySet()) {
                        if (mapChildState.size()== (key)){
                            stringBuilder.append(mapChildState.get(key));
                        } else {
                            stringBuilder.append(mapChildState.get(key)+ ",");
                        }
                    }
                    nameChild = stringBuilder.toString();
                    Log.d("testnamechild", nameChild);
                    if (tabSelect==1){
                        timeRoute = ListTimeFragment.minList+"";
                    } else if (tabSelect==2) {
                        min = TimePickerFragment.min;
                        timeRoute = min+"";
                    }

                    Log.d("testtimechild", timeRoute);
                    Route route4= new Route(nameR,nameChild,iconIm,timeRoute);
                    dbHelper.updateRoute(route1.getNameRoute(),route4);
                    HomeActivity.iSetRouteToRecyclerview.setRouteToRecycler();
                    HomeActivity.routeAdapter.notifyDataSetChanged();
                }

                finish();
            }
        });

        ivStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {








                if ( CheckServiceRunning.isMyServiceRunning(AlarmSongService.class,DetailRouteActivity.this)==true) {
                    Toast.makeText(DetailRouteActivity.this, "Another service is running!", Toast.LENGTH_SHORT).show();
                } else {
                    if (route1==null){
                        nameR = nameRoute.getText().toString();
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int key: mapChildState.keySet()) {
                            if (mapChildState.size()== (key)){
                                stringBuilder.append(mapChildState.get(key));
                            } else {
                                stringBuilder.append(mapChildState.get(key)+ ", ");
                            }
                        }
                        nameChild = stringBuilder.toString();
                        Log.d("testnamechild", nameChild);
                        if (tabSelect==1){
                            timeRoute = ListTimeFragment.minList+"";
                        } else if (tabSelect==2) {
                            min = TimePickerFragment.min;
                            timeRoute = min+"";
                        }

                        Log.d("testtimechild", timeRoute);
                        Route route= new Route(nameR,nameChild,iconIm,timeRoute);

                        boolean etempty = route.isValidDataRoute();

                        if (etempty ==true){
                            dbHelper.addRoute(route);
                            HomeActivity.iSetRouteToRecyclerview.setRouteToRecycler();
                            Toast.makeText(DetailRouteActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailRouteActivity.this, CountdownActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Router", route);
                            intent.putExtras(bundle);
                            finish();
                            startActivity(intent);
                        } else {
                            nameRoute.setError("Please enter name route!");
                        }



                    } else if(route1!=null){
                        Intent intent = new Intent(DetailRouteActivity.this, CountdownActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Router", route1);
                        intent.putExtras(bundle);
                        finish();
                        startActivity(intent);
                    }

                }



            }
        });

        tvSetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRouteActivity.this, IconActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void getTimeToFrag() {
        Log.e("czx", route1.getTime());
        int ttime = Integer.parseInt(route1.getTime());
        try{
            if (ttime >59){
                long int_timer = TimeUnit.MINUTES.toMillis(ttime);
//                long diffSeconds2 = int_timer / 1000 % 60;
                long diffMinutes2 = int_timer / (60 * 1000) % 60;
                long diffHours2 = int_timer / (60 * 60 * 1000) % 24;
                Log.e("czx", diffMinutes2+"  "+diffHours2);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    TimePickerFragment.picker.setHour((int) diffHours2);
                    TimePickerFragment.picker.setMinute((int) diffMinutes2);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    TimePickerFragment.picker.setHour(0);
                    TimePickerFragment.picker.setMinute(ttime);
                }

            }
        } catch (Exception e){

        }




    }

    private void checkTab() {
        if (tabSelect==1){
            btChangeTimeList.setTextColor(getResources().getColor(R.color.Cyan));
            btChangeTimePick.setTextColor(getResources().getColor(R.color.gray));
            hidePickFragment();
            showListFragment();
        } else if (tabSelect==2){
            btChangeTimePick.setTextColor(getResources().getColor(R.color.Cyan));
            btChangeTimeList.setTextColor(getResources().getColor(R.color.gray));
            showPickFragment();
            hideListFragment();
        }
    }

    private void AnhXa() {
        btChangeTimeList = findViewById(R.id.detail_change_listtime);
        btChangeTimePick = findViewById(R.id.detail_change_timepick);
        recyclerView = findViewById(R.id.detail_recy_child);
        nameRoute = findViewById(R.id.detail_etName);
        linearLayoutAdd = findViewById(R.id.detail_add_child);
        ivStart = findViewById(R.id.detail_start);
        tvCancel = findViewById(R.id.detail_cancel);
        tvSave = findViewById(R.id.detail_save);
        tvSetIcon = findViewById(R.id.detail_setIcon);
        ivIcon = findViewById(R.id.detail_iv_icon);
    }

    public void showPickFragment( ){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment bottomFragment = manager.findFragmentById(R.id.timepickFragment);
        ft.show(bottomFragment);
        ft.commit();
    }

    public void hidePickFragment( ){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment bottomFragment = manager.findFragmentById(R.id.timepickFragment);
        ft.hide(bottomFragment);
        ft.commit();
    }

    public void showListFragment( ){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment bottomFragment = manager.findFragmentById(R.id.listTimeFragment);
        ft.show(bottomFragment);
        ft.commit();
    }

    public void hideListFragment( ){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment bottomFragment = manager.findFragmentById(R.id.listTimeFragment);
        ft.hide(bottomFragment);
        ft.commit();
    }

    @Override
    public void setDataToRecycler(List<Child> childList) {
        childAdapter = new ChildAdapter(childList,getApplicationContext(),state);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(childAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                imageIcon = data.getIntExtra("imageIcon", 0);
                Log.e("imgae", imageIcon + "");
                tvSetIcon.setVisibility(View.INVISIBLE);
                ivIcon.setImageResource(imageIcon);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageIcon);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                iconIm = stream.toByteArray();

            }
        }

        if (requestCode == UPDATE_CHILD_LIST) {
            if (resultCode == RESULT_OK) {
                iSetDataToRecyclerview.setDataToRecycler();
            }
        }
    }


}
