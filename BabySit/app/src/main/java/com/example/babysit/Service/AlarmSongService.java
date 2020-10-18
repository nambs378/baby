package com.example.babysit.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.babysit.Adapter.RouteAdapter;
import com.example.babysit.Model.ServiceState;
import com.example.babysit.R;
import com.example.babysit.View.AlarmActivity;
import com.example.babysit.View.CountdownActivity;
import com.example.babysit.View.HomeActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.media.ToneGenerator.MAX_VOLUME;

public class AlarmSongService extends Service {

    public static CountDownTimer mCountDownTimer;
    public static MediaPlayer mediaPlayer;
    public static String str_receiver = "com.example.babysit.Service";
    ServiceState serviceState;

    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;
//    int int_hours;
    long timesave ;

    Intent intent;
    int resIDsong = 2131558400;
    public static Vibrator vibrator;

    Boolean rung;
    int volumeSpre;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TIME", "onCreate");
        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();
        mediaPlayer = new MediaPlayer();

        String idSong = mpref.getString("resID",2131558400+"");

        rung = mpref.getBoolean("rung",false);
        volumeSpre = mpref.getInt("volume",50);

        if ( mpref.getInt("kindAlarm",1) ==1 ){
            mediaPlayer =MediaPlayer.create(getApplicationContext(), Integer.parseInt(idSong));

        } else if (mpref.getInt("kindAlarm",1) ==2){
            mediaPlayer =MediaPlayer.create(getApplicationContext(), Uri.parse(idSong));
        } else if (mpref.getInt("kindAlarm",1)==3){

        }

        Gson gson = new Gson();
        String json = mpref.getString("ServiceStatus", "");
        serviceState = gson.fromJson(json, ServiceState.class);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Log.e("timetick","   "+ "" + serviceState.getTimeCd());
        playCdown();


    }


    private void playCdown() {
        try {
            Log.d("timetick"," "+serviceState.getTimeCd());
            mCountDownTimer =  new CountDownTimer(serviceState.getTimeCd(), 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("timetick"," "+millisUntilFinished / 1000);
                    timesave = millisUntilFinished / 1000;
                    Log.e("zxc", timesave+"");

                    fn_update(millisUntilFinished / 1000);
                    mEditor.putInt("timenow", (int) timesave).commit();
                    mEditor.putString("namenow",  serviceState.getNameRoute()).commit();
                    mEditor.putString("nameC",  serviceState.getNameChild()).commit();
                }
                public void onFinish() {
                    Log.d("timetick"," "+"done");
                    if (rung==true){
                        vibrator.vibrate(50000);
                    }
                    final float volume1 = (float) (1 - (Math.log(MAX_VOLUME - volumeSpre) / Math.log(MAX_VOLUME)));
                    mediaPlayer.setVolume(volume1, volume1);

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                                vibrator.cancel();
                        }
                    });

                    mediaPlayer.start();
                    Intent dialogIntent = new Intent(getApplicationContext(), AlarmActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mEditor.putBoolean("pauseState",false).commit();

                    startActivity(dialogIntent);
                    stopSelf();
                }
            }.start();
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
//        mEditor.putBoolean("pauseState",true).commit();

        super.onDestroy();
        Log.e("Service finish","Finish");

    }

    private void fn_update(long str_time){
        long int_timer = TimeUnit.SECONDS.toMillis(str_time);
        long diffSeconds2 = int_timer / 1000 % 60;
        long diffMinutes2 = int_timer / (60 * 1000) % 60;
        long diffHours2 = int_timer / (60 * 60 * 1000) % 24;
        intent = new Intent(str_receiver);
//        intent.putExtra("time",diffHours2+":"+diffMinutes2+":"+diffSeconds2);
        intent.putExtra("time",String.format("%02d:%02d:%02d", diffHours2,diffMinutes2,diffSeconds2));

        sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        int timezxc = mpref.getInt("timenow",20000)*1000;
        String nameezxc = mpref.getString("namenow","");
        String nameChildlist = mpref.getString("nameC","");
        Log.e("timetick"," "+"timenowwww" +timezxc);
        mEditor.putInt("hours", (int) timezxc).commit();
        ServiceState serviceState1 = new ServiceState(timezxc,nameezxc,nameChildlist);
        Gson gson = new Gson();
        String json = gson.toJson(serviceState1);
        mEditor.putString("ServiceStatus", json);
        mEditor.commit();
        Log.e("zxcz"," "+"onTaskRemove");

        super.onTaskRemoved(rootIntent);
    }

}


