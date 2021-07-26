package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class Mathematics extends AppCompatActivity {
    Animation slide_left_out, slide_right_out;
    int delay = 300;
    //ImageButton btn_close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematics);

        //btn_close = findViewById(R.id.btn_close);


        //anim conn
        slide_left_out = AnimationUtils.loadAnimation(Mathematics.this, R.anim.slide_left_out);
        slide_right_out = AnimationUtils.loadAnimation(Mathematics.this, R.anim.slide_right_out);
    }

    public void buttonClickLeft(View view) {
        Button b = (Button)view;
        String buttonText = b.getText().toString().toLowerCase();
        view.startAnimation(slide_left_out);
        commonClick(buttonText);
    }

    public void buttonClickRight(View view) {
        view.startAnimation(slide_right_out);
        Button b = (Button)view;
        String buttonText = b.getText().toString().toLowerCase();
        commonClick(buttonText);
    }

    private void commonClick(String buttonText){

        Runnable r = new Runnable() {
            @Override
            public void run() {

                Bundle b = new Bundle();
                b.putString("key", buttonText);

                Intent i=new Intent(Mathematics.this, LearnMathActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, delay);
    }

    public void back(View view) {
        finish();
    }

    public void addNewByUser(View view) {
        Toast.makeText(this, "Create Your Own formula", Toast.LENGTH_LONG).show();
    }

    public void back_to_main_menu(View view) {
        finish();
    }
}