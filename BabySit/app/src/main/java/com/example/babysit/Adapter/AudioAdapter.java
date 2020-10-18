package com.example.babysit.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Model.Audio;
import com.example.babysit.Model.Song;
import com.example.babysit.R;

import java.util.List;

public class AudioAdapter extends  RecyclerView.Adapter<AudioAdapter.AudioHolder> {
    List<Audio> listAudio;
    Context context;
    MediaPlayer mediaPlayer;

    int click = 0;
    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;

    public AudioAdapter(List<Audio> listAudio, Context context) {
        this.listAudio = listAudio;
        this.context = context;
    }

    public class AudioHolder extends RecyclerView.ViewHolder {
        TextView tvNameAudio;
        ImageView ivAudioCheck;
        View contextss;
        public AudioHolder(@NonNull View itemView) {
            super(itemView);
            tvNameAudio = itemView.findViewById(R.id.item_audio_name);
            ivAudioCheck = itemView.findViewById(R.id.item_audio_check);
            contextss = itemView;
        }
    }

    @NonNull
    @Override
    public AudioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio,parent,false);

        return new AudioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioHolder holder, final int position) {
        final Audio audio = listAudio.get(position);
        mediaPlayer = new MediaPlayer();
        mpref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mpref.edit();

        holder.tvNameAudio.setText(audio.getNameAudio());

        holder.contextss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()==true){
                    mediaPlayer.stop();
                } else {
                    if (mediaPlayer!=null){
                        mediaPlayer = MediaPlayer.create(context, Uri.parse(audio.getPathAudio()));
                    }
                    mediaPlayer.start();
                }
//                Log.e("idsong",song.getResId()+"" );

            }
        });

        if (mpref.getString("resID","").equals( audio.getPathAudio()) ){
            holder.ivAudioCheck.setImageResource(R.mipmap.checked);
        } else {
            holder.ivAudioCheck.setImageResource(R.mipmap.uncheck);

        }

        holder.ivAudioCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = position;
                mEditor.putInt("kindAlarm",2).commit();
                mEditor.putString("resID", audio.getPathAudio()).commit();
                notifyDataSetChanged();

//                Log.e("ggwp",mpref.getInt("resID",1)+"");
            }
        });






    }

    @Override
    public int getItemCount() {
        return listAudio.size();
    }


}
