package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.babysit.Adapter.CdAdapter;
import com.example.babysit.Database.DbHelper;
import com.example.babysit.Factory.SlideToUnlock;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.ServiceState;
import com.example.babysit.R;
import com.example.babysit.Service.AlarmSongService;
import com.google.gson.Gson;

import java.util.List;

import in.shadowfax.proswipebutton.ProSwipeButton;

public class AlarmActivity extends AppCompatActivity {
    TextView tvNameRoute;
    RecyclerView recyclerView;
    ImageView circleAnimation;

    VideoView alarmVideoview;

    CdAdapter cdAdapter;
    List<Child> listch;
    DbHelper dbHelper;
    ProSwipeButton proSwipeBtn;
    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;
    ServiceState serviceState;
    Vibrator vibrator;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initView();
        mediaPlayer = new MediaPlayer();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        try{
            tvNameRoute.setText("Active route: "+serviceState.getNameRoute());

        } catch (Exception e){

        }



        if (mpref.getInt("kindAlarm",1)==3){
            alarmVideoview.setVisibility(View.VISIBLE);
            String urlmp4 = mpref.getString("resID","");
            try{
                alarmVideoview.setVideoPath(urlmp4);
                alarmVideoview.start();
            }catch (Exception e){

            }

        }

        setChildToAdapter();

        proSwipeBtn = (ProSwipeButton) findViewById(R.id.awesome_btn);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        proSwipeBtn.showResultIcon(false); // false if task failed

                        try{
                            AlarmSongService.mediaPlayer.stop();
                            AlarmSongService.vibrator.cancel();
                            HomeActivity.iSetRouteToRecyclerview.setRouteToRecycler();
                        }catch (Exception e){

                        }

                        Intent intent = new Intent(AlarmActivity.this,AlarmSongService.class);
                        stopService(intent);

                        finish();
                    }
                }, 1000);
            }
        });

        Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);


        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                circleAnimation.setAnimation(zoomout);

            }

            public void onFinish() {
            }
        }.start();



    }

    private void initView() {
        tvNameRoute = findViewById(R.id.alarm_nameroute);
        recyclerView = findViewById(R.id.alarm_rv);
        dbHelper = new DbHelper(this);
        alarmVideoview = findViewById(R.id.alarm_videoview);
        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();
        Gson gson = new Gson();
        String json = mpref.getString("ServiceStatus", "");
        serviceState = gson.fromJson(json, ServiceState.class);
        circleAnimation = findViewById(R.id.alarm_circle_opacity);


    }


    private void setChildToAdapter() {
        listch = dbHelper.getCdChild(serviceState.getNameChild());
        cdAdapter = new CdAdapter(listch,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cdAdapter);
    }





}

