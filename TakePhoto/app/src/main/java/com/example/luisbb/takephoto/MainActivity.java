package com.example.luisbb.takephoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_REQUEST_CODE=1000;
    private final int REQUEST_IMAGE_CAPTURE=1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar =findViewById(R.id.toolbar);
        myToolBar.setTitle("Taking a Photo!");
        setSupportActionBar(myToolBar);
    }

    public void onButtonClicked(View view){
        if(ContextCompat.checkSelfPermission (this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions (this,
                    new String [] {Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);

            return;
        }

        takePicture();
    }

    private void takePicture(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager())!=null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CAMERA_REQUEST_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_DENIED)
                takePicture();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras=data.getExtras();
            ImageView myImage=findViewById(R.id.imgCamera);
            myImage.setImageBitmap((Bitmap) extras.get("data"));

        }
    }
}
