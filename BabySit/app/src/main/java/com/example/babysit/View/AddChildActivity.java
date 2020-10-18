package com.example.babysit.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babysit.Database.DbHelper;
import com.example.babysit.Factory.ByteToBitmap;
import com.example.babysit.Model.Child;
import com.example.babysit.Presenter.GetChildData;
import com.example.babysit.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddChildActivity extends AppCompatActivity {
    CircleImageView circleImageView;

    ImageView ivAdd, ivFinish,ivBack;
    EditText etNameChild;

    TextView tvUpdate;

    String nameChild;

    Button btDelete;
    int PICK_IMAGE_REQUEST = 100;
    Bitmap selectedImage;
    byte[] data;
    DbHelper dbHelper;
    Bitmap resized;
    Child child1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        AnhXa();
        dbHelper = new DbHelper(getApplicationContext());

        try{
            Bundle bundle = getIntent().getExtras();
            child1 = (Child) bundle.getSerializable("chill");
            etNameChild.setText(child1.getNameChild());
            if (child1.getImageChild()!=null){
                circleImageView.setImageBitmap(ByteToBitmap.ByteToBitmapConvert(child1.getImageChild()));
                ivAdd.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e){

        }

        if (child1==null){
            tvUpdate.setVisibility(View.INVISIBLE);
            ivFinish.setVisibility(View.VISIBLE);
        } else if (child1!=null){
            tvUpdate.setVisibility(View.VISIBLE);
            ivFinish.setVisibility(View.INVISIBLE);
        }

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new AddChildAsync().execute();
                    Toast.makeText(AddChildActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();

            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.updateChild(child1.getNameChild(),new Child(etNameChild.getText().toString(),child1.getImageChild()));
                Toast.makeText(AddChildActivity.this, "Sửa thanh cong", Toast.LENGTH_SHORT).show();
                finish();

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
        circleImageView = findViewById(R.id.addchild_photo);
        ivAdd = findViewById(R.id.passenger_iv_add);
        ivFinish = findViewById(R.id.passenger_bt_finish);
        etNameChild = findViewById(R.id.passenger_et_namechild);
        btDelete = findViewById(R.id.passenger_bt_delete);
        tvUpdate = findViewById(R.id.add_child_update);
        ivBack = findViewById(R.id.add_child_back);
    }

    private void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                resized = Bitmap.createScaledBitmap(selectedImage,(int)(selectedImage.getWidth()*0.6), (int)(selectedImage.getHeight()*0.6), true);
                circleImageView.setImageBitmap(resized);
                ivAdd.setVisibility(View.INVISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AddChildActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(AddChildActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    private class AddChildAsync extends AsyncTask<Void , Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                data = getBitmapAsByteArray(resized);
            }catch (Exception e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            addChild();

        }

    }

    public void addChild(){
        nameChild = etNameChild.getText().toString();
        Child child = new Child(nameChild, data);
        boolean childEmpty = child.isValidDataChild();

        if (childEmpty==true){
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            etNameChild.setError("Please enter name child!");
        }

        dbHelper.addChild(child);
    }

}
