package com.example.luisbb.fingerpainting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private final int SET_COLOR_REQUEST=1000;
    public static final String SET_RED_DATA="resultRed";
    public static final String SET_GREEN_DATA="resultGreen";
    public static final String SET_BLUE_DATA="resultBlue";
    static int myRed=0;
    static int myGreen=0;
    static int myBlue=0;
    private DrawingView dv;
    private LinearLayout myDrawPad;
    private float rotateDegree=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dv=new DrawingView(this);
        myDrawPad=findViewById(R.id.viewDraw);
        myDrawPad.addView(dv);

        Toolbar myToolbar=findViewById(R.id.toolbar);
        if(myToolbar==null)
            return;

        myToolbar.setTitle("Finger Painting!");
        setSupportActionBar(myToolbar);

        final ImageView myImage=findViewById(R.id.ivDrawing);
        BitmapDrawable originalBmpDraw= (BitmapDrawable) myImage.getDrawable();
        final Bitmap originalBmp=originalBmpDraw.getBitmap();


        myImage.setOnClickListener(v -> {
            rotateDegree+=60;
            Bitmap rotateBmp=Bitmap.createBitmap(originalBmp.getWidth(), originalBmp.getHeight(),
                    originalBmp.getConfig());
            Canvas rotateCanvas=new Canvas(rotateBmp);
            Matrix rotateMatrix=new Matrix();
            rotateMatrix.setRotate(rotateDegree, originalBmp.getWidth()/2, originalBmp.getHeight()/2);
            Paint paint=new Paint();
            rotateCanvas.drawBitmap(originalBmp, rotateMatrix, paint);
            myImage.setImageBitmap(rotateBmp);

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
                myDrawPad.addView(dv);
                Toast.makeText(this, "Paint allowed!", Toast.LENGTH_LONG).show();
                break;
            case R.id.mnu_item_two:
                myDrawPad.removeView(dv);
                Toast.makeText(this, "Paint not allowed!", Toast.LENGTH_SHORT).show();
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
