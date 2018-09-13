package com.example.luisbb.fingerpainting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int SET_COLOR_REQUEST=1000;
    public static final String SET_RED_DATA="resultRed";
    public static final String SET_GREEN_DATA="resultGreen";
    public static final String SET_BLUE_DATA="resultBlue";
    public static int myRed;
    public static int myGreen;
    public static int myBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar=findViewById(R.id.toolbar);
        if(myToolbar==null)
            return;

        myToolbar.setTitle("Finger Painting!");
        setSupportActionBar(myToolbar);

        final ImageView imageView = findViewById (R.id.ivDrawing);

        imageView.setOnTouchListener ((view, motionEvent) -> {

            switch (motionEvent.getAction ()) {
                case MotionEvent.ACTION_MOVE:
                    imageView.setColorFilter(Color.rgb(myRed, myGreen, myBlue));
                    return true;
            }
            return super.onTouchEvent (motionEvent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_item_one:
                Intent myIntent=new Intent(this, SetColorActivity.class);
                startActivityForResult(myIntent, SET_COLOR_REQUEST);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SET_COLOR_REQUEST:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    myRed=data.getIntExtra(SET_RED_DATA, 0);
                    myGreen=data.getIntExtra(SET_GREEN_DATA, 0);
                    myBlue=data.getIntExtra(SET_BLUE_DATA, 0);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
