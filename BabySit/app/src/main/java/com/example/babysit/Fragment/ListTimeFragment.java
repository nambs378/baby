package com.example.babysit.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babysit.Adapter.TimeAdapter;
import com.example.babysit.R;

import java.util.ArrayList;
import java.util.List;

public class ListTimeFragment extends Fragment {

    RecyclerView timeRV;
    TimeAdapter timeAdapter;
    List<Integer> listmin;

    public static int minList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_listtime, container, false);

        timeRV = rootView.findViewById(R.id.fragment_rv);

        listmin = new ArrayList<>();

        listmin.add(5);
        listmin.add(10);
        listmin.add(15);
        listmin.add(20);
        listmin.add(25);
        listmin.add(45);
        listmin.add(95);

        timeAdapter = new TimeAdapter(listmin, rootView.getContext());
        timeRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        timeRV.setAdapter(timeAdapter);

        return rootView;

    }
}
