package com.example.babysit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.R;

import java.util.List;

public class IconAdapter extends BaseAdapter {

    List<Integer> listIcon;
    Context context;

    public IconAdapter(List<Integer> listIcon, Context context) {
        this.listIcon = listIcon;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listIcon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_icon, null);
        ImageView imageView = view.findViewById(R.id.item_icon_iv);
        imageView.setImageResource(listIcon.get(i));
        return view;
    }
}


