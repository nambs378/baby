package com.example.babysit.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.example.babysit.Adapter.RouteAdapter;
import com.example.babysit.Database.DbHelper;
import com.example.babysit.Model.Route;
import com.example.babysit.Presenter.ISetRouteToRecyclerview;
import com.example.babysit.Presenter.SetDataToRecyclerview;
import com.example.babysit.Presenter.SetRouteToRecyclerview;
import com.example.babysit.R;
import com.example.babysit.Service.AlarmSongService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements IHome{
    FloatingActionButton floatingActionButton;
    TextView tvGosetting;
    RecyclerView recyclerView;
    public static RouteAdapter routeAdapter;
    DbHelper dbHelper;
    public static ISetRouteToRecyclerview iSetRouteToRecyclerview;

    public static String timeitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        AnhXa();

        dbHelper = new DbHelper(this);
        iSetRouteToRecyclerview = new SetRouteToRecyclerview(this);
        iSetRouteToRecyclerview.setRouteToRecycler();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DetailRouteActivity.class);
                startActivity(intent);
            }
        });

        tvGosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



        //Swipe out delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Baby");
                builder.setMessage("Do you want remove this Route?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbHelper.deleteRoute(routeAdapter.getRouteAt(viewHolder.getAdapterPosition()));
//                notiVocaList.clear();
//                notiVocaList.addAll(quizDBHelper.getAllNoti());
                        iSetRouteToRecyclerview.setRouteToRecycler();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        iSetRouteToRecyclerview.setRouteToRecycler();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();





            }
        }).attachToRecyclerView(recyclerView);





    }

    private void AnhXa() {
        floatingActionButton = findViewById(R.id.home_floatbt);
        tvGosetting = findViewById(R.id.home_gosettings);
        recyclerView = findViewById(R.id.home_rv);
    }


    @Override
    public void setDataToRecycler(List<Route> routeList) {
        routeAdapter = new RouteAdapter(routeList,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(routeAdapter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RouteAdapter.timeitem = intent.getStringExtra("time");
            Log.e("broadcast",timeitem+"");
//            tvCdTime.setText(str_time);
//            setProgressBarValues();

            routeAdapter.notifyDataSetChanged();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(AlarmSongService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
