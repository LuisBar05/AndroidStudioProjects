package com.example.luisbb.fingerpainting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private double rotateDegree=0, x1, x2, y1, y2, a1, a2, b1, b2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dv=new DrawingView(this);
        myDrawPad=findViewById(R.id.viewDraw);
//        myDrawPad.addView(dv);

        Toolbar myToolbar=findViewById(R.id.toolbar);
        if(myToolbar==null)
            return;

        myToolbar.setTitle("Finger Painting!");
        setSupportActionBar(myToolbar);

        final ImageView myImage=findViewById(R.id.ivDrawing);
        BitmapDrawable originalBmpDraw= (BitmapDrawable) myImage.getDrawable();
        final Bitmap originalBmp= originalBmpDraw.getBitmap();
        Bitmap imageCopy=originalBmp.copy(Bitmap.Config.ARGB_8888, true);
        Canvas myCanvas=new Canvas(imageCopy);
        myImage.setImageBitmap(imageCopy);
        Paint myPaint=new Paint();
        myPaint.setColor(Color.CYAN);
        myPaint.setStrokeWidth(10.0f);
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        myImage.setOnTouchListener ((view, motionEvent) -> {
            switch(motionEvent.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    x1=motionEvent.getX();
                    y1=motionEvent.getY();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    x2=motionEvent.getX(motionEvent.getActionIndex());
                    y2=motionEvent.getY(motionEvent.getActionIndex());
                    myCanvas.drawLine((float) x1, (float) y1, (float) x2, (float) y2, myPaint);
                    myImage.invalidate();
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    a1=motionEvent.getX(motionEvent.getActionIndex());
                    b1=motionEvent.getY(motionEvent.getActionIndex());
                    break;
                case MotionEvent.ACTION_UP:
                    a2=motionEvent.getX();
                    b2=motionEvent.getY();
                    myCanvas.drawLine((float) a1, (float) b1, (float) a2, (float) b2, myPaint);
                    myImage.invalidate();
                    rotateDegree=angleBetweenLines(x1, x2, y1, y2, a1, a2, b1, b2);
                    TextView showDegrees=findViewById(R.id.myDegree);
                    showDegrees.setText(String.valueOf(rotateDegree));

                    Bitmap rotateBmp=Bitmap.createBitmap(originalBmp.getWidth(), originalBmp.getHeight(), originalBmp.getConfig());
                    Canvas rotateCanvas=new Canvas(rotateBmp);
                    Matrix rotateMatrix=new Matrix();
                    rotateMatrix.setRotate((float) rotateDegree, originalBmp.getWidth()/2, originalBmp.getHeight()/2);
                    Paint paint=new Paint();
                    rotateCanvas.drawBitmap(originalBmp, rotateMatrix, paint);
                    myImage.setImageBitmap(rotateBmp);
                    break;
            }

            return true;
        });

//        myImage.setOnClickListener(v -> {
//            rotateDegree+=60;
//            Bitmap rotateBmp=Bitmap.createBitmap(originalBmp.getWidth(), originalBmp.getHeight(),
//                    originalBmp.getConfig());
//            Canvas rotateCanvas=new Canvas(rotateBmp);
//            Matrix rotateMatrix=new Matrix();
//            rotateMatrix.setRotate(rotateDegree, originalBmp.getWidth()/2, originalBmp.getHeight()/2);
//            Paint paint=new Paint();
//            rotateCanvas.drawBitmap(originalBmp, rotateMatrix, paint);
//            myImage.setImageBitmap(rotateBmp);
//
//        });

    }

    public static double angleBetweenLines(double x1, double x2, double y1, double y2, double a1, double a2,
                                           double b1, double b2){
        double angleOne=Math.atan2(y2-y1, x1-x2);
        double angleTwo=Math.atan2(b2-b1, a1-a2);
        double calculatedAngle=Math.toDegrees(angleOne-angleTwo);
        if(calculatedAngle<0)
            calculatedAngle+=360;
        return calculatedAngle;
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
