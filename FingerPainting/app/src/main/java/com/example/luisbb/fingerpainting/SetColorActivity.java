package com.example.luisbb.fingerpainting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SetColorActivity extends AppCompatActivity {
    private static SeekBar redSeekbar;
    private static SeekBar greenSeekbar;
    private static SeekBar blueSeekbar;
    private static TextView redText;
    private static TextView greenText;
    private static TextView blueText;
    private static ImageView referencePanel;
    private static int red=0, green=0, blue=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_color);
        redSeekbarListener();
        greenSeekbarListener();
        blueSeekbarListener();
    }

    public void setSchemeColor(){
        referencePanel=findViewById(R.id.whiteSquare);
        referencePanel.setColorFilter(Color.rgb(red, green, blue));
    }

    public void redSeekbarListener(){
        redSeekbar=findViewById(R.id.redSeekbar);
        redText=findViewById(R.id.redProgress);

        redSeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        red=progress;
                        redText.setText("RED ("+progress+"/"+redSeekbar.getMax()+")");
                        setSchemeColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                }
        );
    }

    public void greenSeekbarListener(){
        greenSeekbar=findViewById(R.id.greenSeekbar);
        greenText=findViewById(R.id.greenProgress);

        greenSeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        green=progress;
                        greenText.setText("GREEN ("+progress+"/"+greenSeekbar.getMax()+")");
                        setSchemeColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                }
        );
    }

    public void blueSeekbarListener(){
        blueSeekbar=findViewById(R.id.blueSeekbar);
        blueText=findViewById(R.id.blueProgress);

        blueSeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        blue=progress;
                        blueText.setText("BLUE ("+progress+"/"+blueSeekbar.getMax()+")");
                        setSchemeColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                }
        );
    }
}
