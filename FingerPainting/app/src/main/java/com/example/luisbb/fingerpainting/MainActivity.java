package com.example.luisbb.fingerpainting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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
                startActivity(myIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
