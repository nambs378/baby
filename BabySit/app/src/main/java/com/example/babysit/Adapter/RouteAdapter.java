package com.example.babysit.Adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Database.CheckServiceRunning;
import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.Route;
import com.example.babysit.Model.ServiceState;
import com.example.babysit.R;
import com.example.babysit.Service.AlarmSongService;
import com.example.babysit.View.AddChildActivity;
import com.example.babysit.View.CountdownActivity;
import com.example.babysit.View.DetailRouteActivity;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteHolder> {

    public CountDownTimer mCountDownTimer;

    BroadcastReceiver mRegistrationBroadcastReceiver;

    List<Route> routeList;
    Context context;
    public static String timeitem;

    SharedPreferences mpref ;
    SharedPreferences.Editor mEditor;
    ServiceState serviceState;

    public RouteAdapter(List<Route> routeList, Context context) {
        this.routeList = routeList;
        this.context = context;
    }

    public class RouteHolder extends RecyclerView.ViewHolder{
        TextView tvNameRoute, tvNameChild, tvTime;
        ImageView ivDot, ivIcon;
        View contestxxx;
        LinearLayout linerGoCd;
        public RouteHolder(@NonNull View itemView) {
            super(itemView);
            tvNameRoute = itemView.findViewById(R.id.item_nameRoute);
            tvNameChild = itemView.findViewById(R.id.item_childName);
            tvTime = itemView.findViewById(R.id.item_home_time);
            ivDot = itemView.findViewById(R.id.item_dot);
            ivIcon = itemView.findViewById(R.id.item_icon);
            linerGoCd = itemView.findViewById(R.id.home_goCD);
            contestxxx = itemView;
        }
    }

    @NonNull
    @Override
    public RouteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);

        return new RouteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteHolder holder, int position) {
        final Route route = routeList.get(position);

        int time= Integer.parseInt(route.getTime());

        mpref = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mpref.edit();



        Gson gson = new Gson();

        if (CheckServiceRunning.isMyServiceRunning(AlarmSongService.class,context)==true){


            if (mpref.getString("ServiceStatus", "")!=null){
                String json = mpref.getString("ServiceStatus", "");
                serviceState = gson.fromJson(json, ServiceState.class);

                if (serviceState.getNameRoute().equals(route.getNameRoute())){
                    holder.tvTime.setTextColor(context.getResources().getColor(R.color.Cyan));
                    holder.tvTime.setText(timeitem+" min");

                } else {
                    if (time>59){
                        long int_timer = TimeUnit.MINUTES.toMillis(time);long diffMinutes2 = int_timer / (60 * 1000) % 60;
                        long diffHours2 = int_timer / (60 * 60 * 1000) % 24;
                        holder.tvTime.setTextColor(context.getResources().getColor(R.color.gray));
                        holder.tvTime.setText(String.format("%02d:%02d min", diffHours2,diffMinutes2));

//                        if (diffHours2<10  ){
//
//                        }
                    } else {
                        holder.tvTime.setTextColor(context.getResources().getColor(R.color.gray));
                        holder.tvTime.setText(String.format("00:%02d min", time));

                    }

                }


            }
        } else {
            if (time>59){
                long int_timer = TimeUnit.MINUTES.toMillis(time);
                long diffSeconds2 = int_timer / 1000 % 60;
                long diffMinutes2 = int_timer / (60 * 1000) % 60;
                long diffHours2 = int_timer / (60 * 60 * 1000) % 24;
                holder.tvTime.setTextColor(context.getResources().getColor(R.color.gray));
                holder.tvTime.setText(String.format("%02d:%02d min", diffHours2,diffMinutes2));

                if (diffHours2<10  ){

                }
            } else {
                holder.tvTime.setTextColor(context.getResources().getColor(R.color.gray));
                holder.tvTime.setText(String.format("00:%02d min", time));

            }
        }


        try{
            holder.ivIcon.setImageBitmap(ByteToBitmap.ByteToBitmapConvert(route.getIcon()));

        }catch (Exception e) {

        }



        holder.tvNameRoute.setText(route.getNameRoute());
        holder.tvNameChild.setText(route.getChildlist());



        holder.contestxxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailRouteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("route",route);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        holder.linerGoCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, CountdownActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Router",route);
                it.putExtras(bundle);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);



            }
        });


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public String getRouteAt (int position) {
        return routeList.get(position).getNameRoute();
    }

}
