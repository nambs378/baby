package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.babysit.Adapter.AudioAdapter;
import com.example.babysit.Model.Audio;
import com.example.babysit.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordVoiceActivity extends AppCompatActivity {

    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
    private static Button stopButton;
    private static Button recordButton;

    EditText etNameAudio;

    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;

    private boolean isRecording = false;

    final String MEDIA_PATH = "/sdcard/babyrecorder/";
//    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private ArrayList<Audio> songsList = new ArrayList<Audio>();

    private String mp3Pattern = ".mp3";

    RecyclerView recordAudioRv;
    AudioAdapter audioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_voice);

        audioSetup();
        recordAudioRv = findViewById(R.id.record_audio_rv);
        getPlayList();



        audioAdapter = new AudioAdapter(songsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recordAudioRv.setLayoutManager(layoutManager);
        recordAudioRv.setAdapter(audioAdapter);



        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNameAudio.getText().toString())){
                    etNameAudio.setError("Please enter name audio first!");
                } else {
                    recordAudio(view,etNameAudio.getText().toString());
                }
                
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAudio(view);
                audioAdapter.notifyDataSetChanged();
                getPlayList();

            }
        });






    }

    private void audioSetup()
    {
        recordButton =
                (Button) findViewById(R.id.record_audio_bt_record);
        stopButton = (Button) findViewById(R.id.record_audio_bt_stop);
        etNameAudio = findViewById(R.id.record_audio_et_name);
        if (!hasMicrophone())
        {
            stopButton.setEnabled(false);
            recordButton.setEnabled(false);
        } else {
            stopButton.setEnabled(false);
        }

        File mydir = new File(Environment.getExternalStorageDirectory() + "/babyrecorder/");
        if(!mydir.exists())
            mydir.mkdirs();
        else
            Log.d("error", "dir. already exists");
    }


    public void recordAudio (View view,String nameAudio){
        isRecording = true;
        stopButton.setEnabled(true);
        recordButton.setEnabled(false);
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(
                    MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile("/sdcard/babyrecorder/" + nameAudio + "_video.mp3");
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    public void stopAudio (View view)
    {

        stopButton.setEnabled(false);

        if (isRecording)
        {
            recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            recordButton.setEnabled(true);
        }
    }

    public void playAudio (View view) throws IOException
    {
        recordButton.setEnabled(false);
        stopButton.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    recordButton.setEnabled(false);

                    Toast.makeText(this,
                            "Record permission required",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            STORAGE_REQUEST_CODE);
                }
                return;
            }
            case STORAGE_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    recordButton.setEnabled(false);
                    Toast.makeText(this,
                            "External Storage permission required",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public ArrayList<Audio> getPlayList() {
        System.out.println(MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();

            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern)) {
            Audio audio = new Audio(song.getName().substring(0, (song.getName().length() - 4)),song.getPath() );

            songsList.add(audio);
        }
    }






}
