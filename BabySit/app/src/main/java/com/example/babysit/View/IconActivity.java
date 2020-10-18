package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.babysit.Adapter.IconAdapter;
import com.example.babysit.R;

import java.util.ArrayList;
import java.util.List;

public class IconActivity extends AppCompatActivity {
    List<Integer> listicon;

    GridView gridView;
    IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);

        gridView = findViewById(R.id.simpleGridView);
        listicon = new ArrayList<>();
        listicon.add(R.mipmap.home);
        listicon.add(R.mipmap.ballon);
        listicon.add(R.mipmap.maps);
        listicon.add(R.mipmap.search);
        listicon.add(R.mipmap.ironman);
        listicon.add(R.mipmap.supermanpng);
        listicon.add(R.mipmap.sonic);

        iconAdapter = new IconAdapter(listicon,this);
        gridView.setAdapter(iconAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("imageIcon", listicon.get(position));
                setResult(Activity.RESULT_OK, resultIntent);
                Toast.makeText(IconActivity.this, ""+listicon.get(position), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
