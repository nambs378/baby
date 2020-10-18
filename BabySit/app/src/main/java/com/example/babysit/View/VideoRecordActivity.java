package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysit.Adapter.AudioAdapter;
import com.example.babysit.Adapter.VideoAdapter;
import com.example.babysit.Model.VideoClip;
import com.example.babysit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class VideoRecordActivity extends AppCompatActivity {

    FloatingActionButton btCallVideo;
    RecyclerView recyclerView;
    VideoAdapter videoAdapter;

    ImageView tvBack;

    Uri videoUri;
    int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 101;

    final String MEDIA_PATH = "/sdcard/babyvideo/";
    private ArrayList<VideoClip> songsList = new ArrayList<VideoClip>();



    private String mp3Pattern = ".mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        btCallVideo = findViewById(R.id.record_video_floatbt);
        recyclerView = findViewById(R.id.record_video_rv);
        tvBack = findViewById(R.id.record_video_back);


        File mydir = new File(Environment.getExternalStorageDirectory() + "/babyvideo/");
        if(!mydir.exists())
            mydir.mkdirs();
        else
            Log.d("error", "dir. already exists");


        btCallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
            }
        });

        getPlayList();

        videoAdapter = new VideoAdapter(songsList, VideoRecordActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoAdapter);



    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // Check that the SDCard is mounted
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() +"/");
        // Create the storage directory(MyCameraVideo) if it does not exist
        if (! mediaStorageDir.exists()){

            if (! mediaStorageDir.mkdirs()){

                Log.d("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }

        // Create a media file name
        // For unique file name appending current timeStamp with file name
        java.util.Date date= new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date.getTime());

        File mediaFile;
        if(type == MEDIA_TYPE_VIDEO) {

            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "babyvideo/VID_"+ timeStamp + ".mp4");

        } else {
            return null;
        }

        return mediaFile;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // After camera screen this code will execute
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE ) {

            if (resultCode == RESULT_OK) {
                try {
                    videoUri = data.getData();

                } catch (Exception e){

                }
                // Video captured and saved to fileUri specified in the Intent
                //  Toast.makeText(getActivity(), "Video saved to: " +data.getData(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {

                // User cancelled the video capture
                Toast.makeText(getApplicationContext(), "User cancelled the video capture.", Toast.LENGTH_LONG).show();

            } else {
                // Video capture failed, advise user
                Toast.makeText(getApplicationContext(), "Video capture failed.", Toast.LENGTH_LONG).show();
            }
        }
    }


    public ArrayList<VideoClip> getPlayList() {
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

            VideoClip videoClip = new VideoClip(song.getName().substring(0, (song.getName().length() - 4)),song.getPath() );
            songsList.add(videoClip);
        }
    }


}
