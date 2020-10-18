package com.example.babysit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Model.VideoClip;
import com.example.babysit.R;

import java.io.File;
import java.net.URLConnection;
import java.util.List;

public class VideoAdapter  extends  RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    List<VideoClip> videoClipList;
    Context context;
    MediaPlayer mediaPlayer;

    int click = 0;
    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;

    public VideoAdapter(List<VideoClip> videoClipList, Context context) {
        this.videoClipList = videoClipList;
        this.context = context;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        TextView tvNameVideo;
        ImageView ivVideoCheck, Ivthumbnail;
        View contextss;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            tvNameVideo = itemView.findViewById(R.id.item_video_name);
            ivVideoCheck = itemView.findViewById(R.id.item_video_check);
            Ivthumbnail = itemView.findViewById(R.id.item_video_iv);
            contextss = itemView;
        }
    }


    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);

        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, final int position) {
        final VideoClip videoClip = videoClipList.get(position);

        mediaPlayer = new MediaPlayer();
        mpref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mpref.edit();

        holder.tvNameVideo.setText(videoClip.getNameVideo());

        Bitmap bMap = ThumbnailUtils.createVideoThumbnail(videoClip.getPathVideo(), MediaStore.Video.Thumbnails.MICRO_KIND);
        holder.Ivthumbnail.setImageBitmap(bMap);


        holder.contextss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        if (mpref.getString("resID","").equals( videoClip.getPathVideo()) ){
            holder.ivVideoCheck.setImageResource(R.mipmap.checked);
        } else {
            holder.ivVideoCheck.setImageResource(R.mipmap.uncheck);

        }

        holder.ivVideoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = position;
                mEditor.putInt("kindAlarm",3).commit();
                mEditor.putString("resID", videoClip.getPathVideo()).commit();
                notifyDataSetChanged();
//                Log.e("ggwp",mpref.getInt("resID",1)+"");
            }
        });



    }

    @Override
    public int getItemCount() {
        return videoClipList.size();
    }




}
