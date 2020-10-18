package com.example.babysit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Model.Child;
import com.example.babysit.Model.Song;
import com.example.babysit.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    List<Song> listSong;
    Context context;
    MediaPlayer mediaPlayer;

    int click = 0;
    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;


    public SongAdapter(List<Song> listSong, Context context) {
        this.listSong = listSong;
        this.context = context;
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView tvNameSong;
        ImageView ivSongCheck;
        View contextss;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            tvNameSong = itemView.findViewById(R.id.item_song_name);
            ivSongCheck = itemView.findViewById(R.id.item_song_check);
            contextss = itemView;
        }

    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent,false);

        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, final int position) {

        mediaPlayer = new MediaPlayer();

        final Song song = listSong.get(position);

        holder.tvNameSong.setText(song.getNameSong());

        mpref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mpref.edit();


        holder.contextss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()==true){
                    mediaPlayer.stop();
                } else {
                    if (mediaPlayer!=null){
                        mediaPlayer = MediaPlayer.create(context, song.getResId());
                    }
                    mediaPlayer.start();
                }
                Log.e("idsong",song.getResId()+"" );

            }
        });


        if (mpref.getString("resID","").equals(String.valueOf(song.getResId())) ){
            holder.ivSongCheck.setImageResource(R.mipmap.checked);
        } else {
            holder.ivSongCheck.setImageResource(R.mipmap.uncheck);

        }

        holder.ivSongCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = position;
                mEditor.putInt("kindAlarm",1).commit();
                mEditor.putString("resID", String.valueOf(song.getResId())).commit();
                notifyDataSetChanged();

//                Log.e("ggwp",mpref.getInt("resID",1)+"");
            }
        });




    }

    @Override
    public int getItemCount() {
        return listSong.size();
    }



}
