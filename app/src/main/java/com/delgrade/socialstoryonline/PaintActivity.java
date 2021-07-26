package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.tolunaykan.drawinglibrary.PaintView;

public class PaintActivity extends AppCompatActivity {

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);




        paintView = findViewById(R.id.drawingView);
        paintView.setBackgroundColor(Color.WHITE);
        paintView.setBrushColor(Color.BLACK);


    }

    public void onBrushColor(View view){
        paintView.setBrushColor(Color.WHITE);
    }

    public void onBrush(View view){
        paintView.disableEraser();
    }

    public void onBrushSize(View view){
        paintView.setBrushSize(40);
    }

    public void onUndo(View view){
        paintView.undoDrawing();
    }

    public void onEraser(View view){
        paintView.enableEraser();
    }

    public void onRedo(View view){
        paintView.redoDrawing();
    }
}
