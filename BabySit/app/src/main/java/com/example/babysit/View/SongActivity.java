package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.example.babysit.Adapter.SongAdapter;
import com.example.babysit.Model.Song;
import com.example.babysit.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {

    List<Song> nameSongList;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        nameSongList = new ArrayList<>();
        listRaw();
        mediaPlayer = new MediaPlayer();
        recyclerView = findViewById(R.id.song_rv);

        songAdapter = new SongAdapter(nameSongList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(songAdapter);

    }


    public void listRaw(){
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            try {
                int resourceID=fields[count].getInt(fields[count]);
                String nameSong = fields[count].getName();
                Song song = new Song(resourceID,nameSong);
                nameSongList.add(song);
                Log.i("Raw Asset: ", resourceID+" ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }


}
