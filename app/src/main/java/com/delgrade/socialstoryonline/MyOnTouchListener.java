package com.delgrade.socialstoryonline;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

public class MyOnTouchListener implements View.OnTouchListener {
    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            ClipData data= ClipData.newPlainText("","");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v,0);
            return true;
        }
        else {
            return false;
        }
    }
}
