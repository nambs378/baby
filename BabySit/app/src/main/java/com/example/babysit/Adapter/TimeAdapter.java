package com.example.babysit.Adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Fragment.ListTimeFragment;
import com.example.babysit.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {

    List<Integer> list;
    Context context;

    int click = 0;

    public TimeAdapter(List<Integer> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class TimeHolder extends RecyclerView.ViewHolder{
        TextView tvMin;
        RelativeLayout relativeLayout;
        public View contextsss ;

        public TimeHolder(@NonNull View itemView) {
            super(itemView);
            tvMin = itemView.findViewById(R.id.item_time_min);
            relativeLayout = itemView.findViewById(R.id.item_time_to_border);
            contextsss =itemView;

        }

    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time,parent,false);

        return new TimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeHolder holder, final int position) {
        int min = list.get(position);
        holder.tvMin.setText(min+" min");

        if (click==0){
            ListTimeFragment.minList = list.get(click);
        }

        if (click == position){
            holder.relativeLayout.setBackgroundResource(R.drawable.borderr_time);
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.unborder_time);
        }


        holder.contextsss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = position;
                Toast.makeText(context, "zxc: "+click, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                ListTimeFragment.minList = list.get(click);
            }
        });





    }


    @Override
    public int getItemCount() {
        return list.size();
    }




}
