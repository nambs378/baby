package com.example.babysit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Database.DbHelper;
import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.Model.Child;
import com.example.babysit.Model.IChild;
import com.example.babysit.R;
import com.example.babysit.View.AddChildActivity;
import com.example.babysit.View.DetailRouteActivity;
import com.example.babysit.View.HomeActivity;
import com.example.babysit.View.PassengerActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildHolder> {

    List<Child> childList;
    Context context;
    int state;
    DbHelper dbHelper;
    public static String listnameChild;

    public ChildAdapter(List<Child> childList, Context context, int state) {
        this.childList = childList;
        this.context = context;
        this.state = state;
    }

    public class ChildHolder extends RecyclerView.ViewHolder{
        TextView tvNameChild;
        ImageView ivChildCheck;
        CircleImageView circleImageView;
        Button btDelete;
        View contextsss;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            tvNameChild = itemView.findViewById(R.id.item_child_Name);
            ivChildCheck = itemView.findViewById(R.id.item_child_check);
            circleImageView = itemView.findViewById(R.id.profile_image);
            btDelete = itemView.findViewById(R.id.item_child_delete);
            contextsss = itemView;
        }
    }

    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child,parent,false);

        return new ChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChildHolder holder, final int position) {
        final Child child = childList.get(position);
        dbHelper = new DbHelper(context);


        holder.tvNameChild.setText(child.getNameChild());

        try {
            holder.circleImageView.setImageBitmap(ByteToBitmap.ByteToBitmapConvert(child.getImageChild()));

        } catch (Exception e){

        }

        if (state==1){
            holder.btDelete.setVisibility(View.VISIBLE);
            holder.ivChildCheck.setVisibility(View.INVISIBLE);
        } else if (state==2){
            holder.btDelete.setVisibility(View.INVISIBLE);
            holder.ivChildCheck.setVisibility(View.VISIBLE);
        }

        if (listnameChild!=null){
            String[] outputName = listnameChild.split(",");

            for (int i= 0;i<outputName.length;i++){
                if (outputName[i].equals(child.getNameChild())){
                    holder.ivChildCheck.setImageResource(R.mipmap.checked);
                    holder.circleImageView.setBorderColor(context.getResources().getColor(R.color.Cyan));
                    DetailRouteActivity.mapChildState.put(position,child.getNameChild());
                }
            }
        }

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Baby");
                builder.setMessage("Do you want remove this Child?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dbHelper.deleteChild(child.getNameChild());
                        PassengerActivity.iSetDataToRecyclerview.setDataToRecycler();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



        holder.ivChildCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DetailRouteActivity.mapChildState.containsValue(child.getNameChild())==true) {
                    DetailRouteActivity.mapChildState.values().remove(child.getNameChild());
                    holder.ivChildCheck.setImageResource(R.mipmap.uncheck);
                    holder.circleImageView.setBorderColor(context.getResources().getColor(R.color.white));
                } else {
                    DetailRouteActivity.mapChildState.put(position,child.getNameChild());
                    holder.ivChildCheck.setImageResource(R.mipmap.checked);
                    holder.circleImageView.setBorderColor(context.getResources().getColor(R.color.Cyan));
                }
            }
        });

        holder.contextsss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddChildActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("chill",child);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }



}
