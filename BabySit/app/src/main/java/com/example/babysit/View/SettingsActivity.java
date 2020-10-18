package com.example.babysit.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.babysit.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {




    RelativeLayout relativeLayout, goSong, openRecordAudio , openRecordVideo;
    ImageView ivBack;
    Switch settingSwitch;
    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;
    SeekBar seekBarAudio;
    Boolean rung;
    int volumeSpre;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AnhXa();



        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,  PassengerActivity.class);
                intent.putExtra("state",1);
                startActivity(intent);
            }
        });

        goSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,SongActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        volumeSpre = mpref.getInt("volume",50);


        seekBarAudio.setMax(100);
        seekBarAudio.setProgress(volumeSpre);
        seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    mEditor.putInt("volume",i).commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        openRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this,RecordVoiceActivity.class);
                startActivity(intent);

            }
        });

        openRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingsActivity.this,VideoRecordActivity.class);
                startActivity(intent);
            }
        });


    }



    private void AnhXa() {
        relativeLayout = findViewById(R.id.settings_goPassenger);
        goSong = findViewById(R.id.settings_goSong);
        ivBack = findViewById(R.id.settings_back);
        settingSwitch = findViewById(R.id.settings_switch);
        seekBarAudio = findViewById(R.id.settings_seekbar_audio);
        openRecordAudio = findViewById(R.id.settings_openRecord_audio);
        openRecordVideo = findViewById(R.id.settings_goVideo);



        mpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mpref.edit();

        rung =  mpref.getBoolean("rung",false);
        Log.e("switch",rung.toString());
        if (rung==false){
            settingSwitch.setChecked(false);
        } else {
            settingSwitch.setChecked(true);
        }

        settingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    mEditor.putBoolean("rung",true).commit();
                } else {
                    mEditor.putBoolean("rung",false).commit();
                }
            }
        });


    }




}
