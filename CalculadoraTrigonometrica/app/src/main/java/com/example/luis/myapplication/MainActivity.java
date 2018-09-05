package com.example.luis.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView myImage;
    TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view){
        boolean checked= ((RadioButton) view).isChecked();
        myImage= findViewById(R.id.anglePicture);
        myText= findViewById(R.id.theResult);

        switch(view.getId()){
            case R.id.firstGrade:
                myImage.setImageResource(R.drawable.grade45);
                break;
            case R.id.secondGrade:
                myImage.setImageResource(R.drawable.grade90);
                break;
            case R.id.thirdGrade:
                myImage.setImageResource(R.drawable.grade180);
                break;
            case R.id.firstFunction:
                if(((RadioButton) findViewById(R.id.firstGrade)).isChecked())
                    myText.setText("Resultado= ".concat(String.valueOf(Math.sin(Math.toRadians(45)))));
                else{
                    if(((RadioButton) findViewById(R.id.secondGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.sin(Math.toRadians(90)))));
                    else if(((RadioButton) findViewById(R.id.thirdGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.sin(Math.toRadians(180)))));
                }
                break;
            case R.id.secondFunction:
                if(((RadioButton) findViewById(R.id.firstGrade)).isChecked())
                    myText.setText("Resultado= ".concat(String.valueOf(Math.cos(Math.toRadians(45)))));
                else{
                    if(((RadioButton) findViewById(R.id.secondGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.cos(Math.toRadians(90)))));
                    else if(((RadioButton) findViewById(R.id.thirdGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.cos(Math.toRadians(180)))));
                }
                break;
            case R.id.thirdFunction:
                if(((RadioButton) findViewById(R.id.firstGrade)).isChecked())
                    myText.setText("Resultado= ".concat(String.valueOf(Math.tan(Math.toRadians(45)))));
                else{
                    if(((RadioButton) findViewById(R.id.secondGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.tan(Math.toRadians(90)))));
                    else if(((RadioButton) findViewById(R.id.thirdGrade)).isChecked())
                        myText.setText("Resultado= ".concat(String.valueOf(Math.tan(Math.toRadians(180)))));
                }
                break;
        }
    }

}
