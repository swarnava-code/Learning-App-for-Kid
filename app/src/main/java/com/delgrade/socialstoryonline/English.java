package com.delgrade.socialstoryonline;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URL;

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



        //changeImage(NAME);

        try {
            new English.asyncTask().execute();
            //setLogoByPicasso();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //ImageView imageView = findViewById(this.getResources().getIdentifier(NAME, "id", this.getPackageName()));
    }


    private class asyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            String NAME = "";
            int resId = getResources().getIdentifier("english", "array", getPackageName());
            String imgTitle[] = getResources().getStringArray(resId);

            for(int i=0; i<imgTitle.length; i++){
                NAME = imgTitle[i];
                String name = NAME.toLowerCase().replaceAll("\\s", "");
                String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/english/"+name+"/"+name+".png";
                Bitmap bitmapMain = null;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    URL url = new URL(id2);
                    bitmapMain = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch(IOException e) {
                    System.out.println(e);
                }
                int imageId = 0;
                imageId = getResources().getIdentifier(NAME, "id", getPackageName());
                Button button = findViewById(imageId);
                Drawable top = new BitmapDrawable(getResources(), bitmapMain);;//getResources().getDrawable(R.drawable.angry);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
                    }
                });

            }




            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //for update
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //execute this after backgroud task completed
            // we can interact with frontend(GUI) here
        }

    }


    void setLogoByPicasso() throws InterruptedException {
        String NAME = "";
        int resId = getResources().getIdentifier("english", "array", getPackageName());
        String imgTitle[] = getResources().getStringArray(resId);

        for(int i=0; i<imgTitle.length; i++){
            NAME = imgTitle[i];
            String name = NAME.toLowerCase().replaceAll("\\s", "");
            String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/english/"+name+"/"+name+".png";
            Bitmap bitmapMain = null;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                URL url = new URL(id2);
                bitmapMain = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }
            int imageId = 0;
            imageId = this.getResources().getIdentifier(NAME, "id", this.getPackageName());
            Button button = findViewById(imageId);
            Drawable top = new BitmapDrawable(getResources(), bitmapMain);;//getResources().getDrawable(R.drawable.angry);
            button.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
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