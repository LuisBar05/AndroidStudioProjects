package com.example.luisbb.memesinfo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoMemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_meme);

        Bundle myBundle=  new Bundle();
        myBundle.putString("MemeName", getIntent().getStringExtra("MemeNameString"));
        myBundle.putString("MemeDescription", getIntent().getStringExtra("MemeDescriptionString"));
        myBundle.putString("MemeUrl", getIntent().getStringExtra("MemeUrlString"));
        myBundle.putInt("MemeImage", getIntent().getIntExtra("MemeImageIndex", 0));

        ShowInfoFragment myFragment=new ShowInfoFragment();
        myFragment.setArguments(myBundle);

        getSupportFragmentManager ()
                .beginTransaction ()
                .add(R.id.myInfoContainer, myFragment)
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }
}
