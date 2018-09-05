// Esta clase fue un intento fallido, hacer caso omiso

package com.example.luis.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


public class TrigonometryFunctions extends AppCompatActivity {
    TextView myText;
    public void CalculateFunction(ImageView myImage, int numFunction){
        myText= findViewById(R.id.theResult);

        if(myImage.equals(findViewById(R.id.firstGrade))){
            if(numFunction==0)
                myText.setText(String.valueOf(Math.sin(45)));
            else {
                if (numFunction == 1)
                    myText.setText(String.valueOf(Math.cos(45)));
                else
                    myText.setText(String.valueOf(Math.tan(45)));
            }
        }

        else{
            if(myImage.equals(findViewById(R.id.secondGrade))){
                if(numFunction==0)
                    myText.setText(String.valueOf(Math.sin(45)));
                else {
                    if (numFunction == 1)
                        myText.setText(String.valueOf(Math.cos(45)));
                    else
                        myText.setText(String.valueOf(Math.tan(45)));
                }
            }

            else{
                if(numFunction==0)
                    myText.setText(String.valueOf(Math.sin(45)));
                else {
                    if (numFunction == 1)
                        myText.setText(String.valueOf(Math.cos(45)));
                    else
                        myText.setText(String.valueOf(Math.tan(45)));
                }
            }
        }

    }
}
