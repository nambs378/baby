package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.babysit.Adapter.ChildAdapter;
import com.example.babysit.Database.DbHelper;
import com.example.babysit.Model.Child;
import com.example.babysit.Presenter.GetChildData;
import com.example.babysit.Presenter.IGetChildData;
import com.example.babysit.Presenter.ISetDataToRecyclerview;
import com.example.babysit.Presenter.SetDataToRecyclerview;
import com.example.babysit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PassengerActivity extends AppCompatActivity implements IPassengetActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    public static ChildAdapter childAdapter;
    public static ISetDataToRecyclerview iSetDataToRecyclerview;
    DbHelper dbHelper;
    int state;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        AnhXa();
        Intent intent = this.getIntent();
        state = intent.getIntExtra("state",0);



        dbHelper = new DbHelper(this);
        iSetDataToRecyclerview = new SetDataToRecyclerview(this);
        iSetDataToRecyclerview.setDataToRecycler();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerActivity.this,AddChildActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void AnhXa() {
        recyclerView = findViewById(R.id.passenger_rv);
        floatingActionButton = findViewById(R.id.passenger_floating);
        ivBack = findViewById(R.id.passenger_back);
    }


    @Override
    public void setDataToRecycler(List<Child> childList) {
        childAdapter = new ChildAdapter(childList,getApplicationContext(),state);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(childAdapter);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                iSetDataToRecyclerview.setDataToRecycler();


            }
        }
    }



}
