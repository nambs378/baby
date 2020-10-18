package com.example.babysit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Database.DbHelper;
import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.Model.Child;
import com.example.babysit.R;
import com.example.babysit.View.DetailRouteActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CdAdapter extends RecyclerView.Adapter<CdAdapter.ChildHolder>  {


    List<Child> childList;
    Context context;

    public CdAdapter(List<Child> childList, Context context) {
        this.childList = childList;
        this.context = context;
    }



    public class ChildHolder extends RecyclerView.ViewHolder{
        TextView tvNameChild;
        CircleImageView circleImageView;
        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            tvNameChild = itemView.findViewById(R.id.item_cd_name);
            circleImageView = itemView.findViewById(R.id.item_cd_avatar);
        }


    }

    @NonNull
    @Override
    public ChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cd_child,parent,false);


        return new ChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildHolder holder, int position) {
        final Child child = childList.get(position);

        holder.tvNameChild.setText(child.getNameChild());
        holder.circleImageView.setImageBitmap(ByteToBitmap.ByteToBitmapConvert(child.getImageChild()));

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

}
