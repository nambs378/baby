package com.example.babysit.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysit.Adapter.CdAdapter;
import com.example.babysit.Database.CheckServiceRunning;
import com.example.babysit.Database.DbHelper;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;
import com.example.babysit.Model.ServiceState;
import com.example.babysit.R;
import com.example.babysit.Service.AlarmSongService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountdownActivity extends AppCompatActivity {
    public static ProgressBar progressBarCircle;

    RecyclerView recyclerView;
    CdAdapter cdAdapter;
    TextView cdName, tvCdTime;
    List<Child> listch;
    DbHelper dbHelper;
    ImageView ivStartPause;
    TextView tvCancel, tvEdit;

    Route route;
    SharedPreferences mpref;
    SharedPreferences.Editor mEditor;
    int totalSecond;
    String nameRou;

    int timeState = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        getRoute();
        AnhXa();
        setChildToAdapter();
        mEditor.putBoolean("pauseState",false).commit();


        ivStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ( CheckServiceRunning.isMyServiceRunning(AlarmSongService.class,CountdownActivity.this)==true ){
                    Toast.makeText(CountdownActivity.this, "Already time running!", Toast.LENGTH_SHORT).show();
                } else {
                    setTime();
                }

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if ( CheckServiceRunning.isMyServiceRunning(AlarmSongService.class,CountdownActivity.this)==true){

                   AlertDialog.Builder builder = new AlertDialog.Builder(CountdownActivity.this);
                   builder.setTitle("Baby");
                   builder.setMessage("Do you want cancel?");
                   builder.setCancelable(false);
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           mEditor.remove("ServiceStatus").apply();
                           Intent intent = new Intent(CountdownActivity.this, AlarmSongService.class);
                           stopService(intent);
                           try{
                               AlarmSongService.mCountDownTimer.cancel();
                           }catch (Exception e){

                           }
                           finish();
                       }
                   });
                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();

                       }
                   });
                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();


               }
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(CountdownActivity.this, AlarmSongService.class);
                    stopService(intent);
                } catch (Exception e){

                }
                Intent it2 = new Intent(CountdownActivity.this,DetailRouteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("route",route);
                it2.putExtras(bundle);
                finish();
                startActivity(it2);
            }
        });






    }

    private void getRoute() {
        route= new Route();
        Bundle bundle = this.getIntent().getExtras();
        route = (Route) bundle.getSerializable("Router");
    }

    private void setChildToAdapter() {
        listch = dbHelper.getCdChild(route.getChildlist());
        cdAdapter = new CdAdapter(listch,CountdownActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cdAdapter);
    }



    private void AnhXa() {
        listch= new ArrayList<>();
        dbHelper = new DbHelper(this);
        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();
        recyclerView = findViewById(R.id.cd_rv);
        cdName = findViewById(R.id.cd_nameroute);
        tvCdTime = findViewById(R.id.cd_tv_time);
        ivStartPause = findViewById(R.id.cd_start_pause);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        tvCancel = findViewById(R.id.cd_cancel);
        tvEdit = findViewById(R.id.cd_edit);

        nameRou = route.getNameRoute();
        cdName.setText("Active Route: " + nameRou);

        int gettime = Integer.parseInt(route.getTime()) ;
        if (gettime>59){
            long int_timer = TimeUnit.MINUTES.toMillis(gettime);
            long diffSeconds2 = int_timer / 1000 % 60;
            long diffMinutes2 = int_timer / (60 * 1000) % 60;
            long diffHours2 = int_timer / (60 * 60 * 1000) % 24;
//            tvCdTime.setText(diffHours2+":"+diffMinutes2+":"+diffSeconds2);
            tvCdTime.setText(String.format("%02d:%02d:%02d", diffHours2,diffMinutes2,diffSeconds2));

        } else {
            tvCdTime.setText(String.format("00:%02d:00",gettime));
//            tvCdTime.setText("00:"+gettime+":00");
        }
        setProgressBarValues(Integer.parseInt(route.getTime())*60);
    }


    private void setTime() {
        int timerou = Integer.parseInt(route.getTime());
        totalSecond = (timerou*60000);
        mEditor.putInt("hours", totalSecond).commit();
        Intent intent_service = new Intent(getApplicationContext(), AlarmSongService.class);
        ServiceState serviceState = new ServiceState(totalSecond,nameRou,route.getChildlist());
        Gson gson = new Gson();
        String json = gson.toJson(serviceState);
        mEditor.putString("ServiceStatus", json);
        mEditor.commit();
        startService(intent_service);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str_time = intent.getStringExtra("time");
            tvCdTime.setText(str_time);
            progressBarCircle.setProgress(timeState++);
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


    public static void setProgressBarValues(int tol) {
        progressBarCircle.setMax( tol );
        progressBarCircle.setProgress( 1);
    }


}
