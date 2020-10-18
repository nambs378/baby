package com.example.babysit.Factory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ByteToBitmap {

    public static Bitmap ByteToBitmapConvert(byte[] bytes){
//        Bitmap bitmap = BitmapFactory.decodeFile("/path/images/image.jpg");
//        ByteArrayOutputStream blob = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
//        bytes = blob.toByteArray();

        return  BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }


}
