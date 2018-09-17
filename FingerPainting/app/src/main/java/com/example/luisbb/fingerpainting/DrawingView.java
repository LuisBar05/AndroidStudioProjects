package com.example.luisbb.fingerpainting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class DrawingView extends View {
    private Path drawPath;
    private Bitmap myBmp;
    private Canvas myCanvas, bmpCanvas;
    private Paint drawPaint, canvasPaint;
    private LayoutParams params;

    public DrawingView(Context context){
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawPath= new Path();
        drawPaint= new Paint();
        setColor();
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(12.0f);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        canvasPaint=new Paint(Paint.DITHER_FLAG);
        params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        myBmp=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        myCanvas=new Canvas(myBmp);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        setColor();
        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cookie_monster), 0, 0, canvasPaint);
        canvas.drawBitmap(myBmp, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX= event.getX();
        float touchY=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(){
        invalidate();
        drawPaint.setColor(Color.rgb(MainActivity.myRed, MainActivity.myGreen, MainActivity.myBlue));
    }
}
