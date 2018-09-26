package com.example.luisbb.memesinfo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements  OnMemeTouchedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MemesFragment myFragment= new MemesFragment();
        fragmentTransaction.add(R.id.myContainer, myFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMemeTouched (String name, Drawable image, String description, String URL) {
        Intent myIntent=new Intent(this, InfoMemeActivity.class);
        myIntent.putExtra("MemeNameString", name);
        myIntent.putExtra("MemeDescriptionString", description);
        myIntent.putExtra("MemeUrlString", URL);
        startActivity(myIntent);
    }
}
