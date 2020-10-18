package com.example.babysit.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.babysit.R;
import com.example.babysit.View.DetailRouteActivity;
import com.example.babysit.View.HomeActivity;

import java.util.concurrent.TimeUnit;

public class TimePickerFragment extends Fragment {

    int hour;
    public static int min;
    int timeGet;
    public static TimePicker picker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_timepicker, container, false);
        picker=rootView.findViewById(R.id.datePicker1);
        picker.setIs24HourView(true);
        Log.e("min",DetailRouteActivity.mincurrent+"");

        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    if (hour==0){
                        min = timePicker.getMinute();
                    } else {
                        min = timePicker.getMinute() + (hour*60);
                    }
                    Log.d("timepick", hour+" "+min);
                }
            }
        });
        return rootView;

    }
}
