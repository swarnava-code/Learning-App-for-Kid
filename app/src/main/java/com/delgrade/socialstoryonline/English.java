package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class English extends AppCompatActivity {
    Animation slide_left_out, slide_right_out, slide_right_in, slide_up, slide_down;
    int delay = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);

        //anim conn
        slide_left_out = AnimationUtils.loadAnimation(English.this, R.anim.slide_left_out);
        slide_right_out = AnimationUtils.loadAnimation(English.this, R.anim.slide_right_out);
        slide_right_in = AnimationUtils.loadAnimation(English.this, R.anim.slide_in_right);
        slide_up = AnimationUtils.loadAnimation(English.this, R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(English.this, R.anim.slide_down);


        int [] tbRowId = {R.id.eng1, R.id.eng2, R.id.eng3, R.id.eng4, R.id.eng5, R.id.eng6, R.id.eng7, R.id.eng8, R.id.eng9, R.id.eng10, R.id.eng11,
                R.id.eng12, R.id.eng13, R.id.eng14, R.id.eng15, R.id.eng16};
        TableRow tableRow;
        int i, len = tbRowId.length;
        for(i=0; i<len; i++){
            tableRow = findViewById(tbRowId[i]);
            tableRow.setVisibility(View.GONE);
            //tableRow.startAnimation(slide_right_out);
        }



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

                //int resId = getResources().getIdentifier(buttonText, "array", getPackageName());
                //String imgTitle[] = getResources().getStringArray(resId);
                Bundle b = new Bundle();
                b.putString("key", buttonText);

                Intent i=new Intent(English.this, LearnWord.class);
                i.putExtras(b);
                startActivity(i);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, delay);
    }


    boolean visibleBasic = false;
    public void showAllBasic(View view) {
        int [] tbRowId = { R.id.eng1, R.id.eng2};
        TableRow tableRow;
        int i, len = tbRowId.length;
        TextView tv_arrow = findViewById(R.id.basic_arrow);


        if(visibleBasic==false){
            visibleBasic = true;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.setVisibility(View.VISIBLE);
                tableRow.startAnimation(slide_down);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        }
        else{
            visibleBasic = false;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.startAnimation(slide_up);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));

                TableRow finalTableRow = tableRow;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        finalTableRow.setVisibility(View.GONE);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r,500);

            }
        }
    }

    boolean visibleGrammar = false;
    public void shoAllGrammar(View view) {
        int [] tbRowId = {R.id.eng3, R.id.eng4, R.id.eng5, R.id.eng6, R.id.eng7};
        TableRow tableRow;
        int i, len = tbRowId.length;
        TextView tv_arrow = findViewById(R.id.grammar_arrow);


        if(visibleGrammar==false){
            visibleGrammar = true;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.setVisibility(View.VISIBLE);
                tableRow.startAnimation(slide_down);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        }
        else{
            visibleGrammar = false;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.startAnimation(slide_up);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));



                TableRow finalTableRow = tableRow;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        finalTableRow.setVisibility(View.GONE);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r,500);
            }
        }
    }

    boolean visibleEncyclopedia = false;
    public void showAllEncyclopedia(View view) {
        int [] tbRowId = { R.id.eng8, R.id.eng9, R.id.eng10, R.id.eng11,
                R.id.eng12, R.id.eng13, R.id.eng14, R.id.eng15, R.id.eng16};
        TableRow tableRow;
        int i, len = tbRowId.length;
        TextView tv_arrow = findViewById(R.id.encyclopedia_arrow);


        if(visibleEncyclopedia==false){
            visibleEncyclopedia = true;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.setVisibility(View.VISIBLE);
                tableRow.startAnimation(slide_down);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
            }
        }
        else{
            visibleEncyclopedia = false;
            for(i=0; i<len; i++){
                tableRow = findViewById(tbRowId[i]);
                tableRow.startAnimation(slide_up);
                tv_arrow.setBackground(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));

                TableRow finalTableRow = tableRow;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        finalTableRow.setVisibility(View.GONE);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r,500);

            }
        }
    }

    public void back(View view) {
        finish();
    }

    public void addNewByUser(View view) {
        Toast.makeText(this, "Add new words by User. (Family photo)", Toast.LENGTH_LONG).show();
    }

    public void bodypart(View view) {
        Intent i=new Intent(English.this, BodyPart.class);
        startActivity(i);
    }
}