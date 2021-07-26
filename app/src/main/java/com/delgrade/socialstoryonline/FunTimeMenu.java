package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunTimeMenu extends AppCompatActivity {

    Button piano, drawing, puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_time_menu);

        piano = findViewById(R.id.piano);
        puzzle = findViewById(R.id.puzzle);
        drawing = findViewById(R.id.drawing);

        piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FunTimeMenu.this, PianoActivity.class);
                startActivity(i);
            }
        });

        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId = getResources().getIdentifier("animal", "array", getPackageName());
                String imgTitle[] = getResources().getStringArray(resId);
                Bundle b = new Bundle();
                b.putStringArray("key", imgTitle);

                Intent i=new Intent(FunTimeMenu.this, PuzzleActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

        drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FunTimeMenu.this, PaintActivity.class);
                startActivity(i);
            }
        });

    }

    public void back(View view) {
        finish();
    }
}