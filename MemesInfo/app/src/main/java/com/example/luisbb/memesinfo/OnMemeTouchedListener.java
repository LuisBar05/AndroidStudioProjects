package com.example.luisbb.memesinfo;

import android.graphics.drawable.Drawable;

public interface OnMemeTouchedListener {
    void onMemeTouched(String name, Drawable image, String description, String URL);
}
