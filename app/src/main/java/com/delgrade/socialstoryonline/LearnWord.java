package com.delgrade.socialstoryonline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class LearnWord extends AppCompatActivity {

    ImageButton speakBtn, prevBtn, nextBtn;
    ImageView imgBg, img;
    TextView title;
    ProgressBar progressBar;
    CardView cardView;

    ImageButton btn_close;
    private int currentIndex = 0;

    boolean firstLoad = false, alphabet = false;
    TextToSpeech tts;
    private String[] imgTitle = new String[20];
    LinearLayout linearLayout;
    int cmd = 2; // 0-right, 1-left, 2-just speak, checking left or right , flag
    String buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_word);


        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");
        int resId = getResources().getIdentifier(buttonText.toLowerCase().replaceAll("\\s", ""), "array", getPackageName());
        imgTitle = getResources().getStringArray(resId);

        //Bundle b = this.getIntent().getExtras();
        //imgTitle = b.getStringArray("key");

        if(imgTitle[0].equals("Apple")&&imgTitle[1].equals("Ball"))
            alphabet = true;


        //btn_nxt = findViewById(R.id.btn_nxt);
        img = findViewById(R.id.img);
        imgBg = findViewById(R.id.imgBg);
        title = findViewById(R.id.title);

        btn_close = findViewById(R.id.btn_close);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        speakBtn = findViewById(R.id.speak_btn);
        progressBar = findViewById(R.id.circularSpinner);
        cardView = findViewById(R.id.cardView);

        linearLayout = findViewById(R.id.test);

        //imgBg.setBackgroundResource(R.drawable.banana);
        //BlurImage.with(getApplicationContext()).load(R.drawable.bg2).intensity(20).Async(true).into(imgBg);

        // Initial Load of imageview
        if (!firstLoad) {
            loadAtFirst();
        }

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmd = 2;
                loadAllResources();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //set height of image
        Runnable r = new Runnable() {
            @Override
            public void run() {
                linearLayout.getLayoutParams().height=cardView.getWidth();
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,500);

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                speakBtn.setEnabled(true);
            }
        };
        Handler h1 = new Handler();
        h1.postDelayed(r1,6000);

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmd = 0;
                loadAllResources();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmd = 1;
                loadAllResources();
            }
        });


        img.setOnTouchListener(new OnSwipeTouchListener(LearnWord.this) {
            public void onSwipeTop() {
                //Toast.makeText(LearnWord.this, "top", Toast.LENGTH_SHORT).show();
                cmd = 2;
                loadAllResources();
            }
            public void onSwipeRight() {
                //Toast.makeText(LearnWord.this, "right", Toast.LENGTH_SHORT).show();
                cmd = 0;
                loadAllResources();
            }
            public void onSwipeLeft() {
                //Toast.makeText(LearnWord.this, "left", Toast.LENGTH_SHORT).show();
                cmd = 1;
                loadAllResources();
            }
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeBottom() {
                //Toast.makeText(LearnWord.this, "bottom", Toast.LENGTH_SHORT).show();
                cmd = 2;
                loadAllResources();
            }

        });



        //loading the 1st image
        //currentIndex++;
        loadResources();



    }

    void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        //String text = "Hello my name is doggy";
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        tts.stop();
        if (text == null || "".equals(text)) {
            Toast.makeText(LearnWord.this, "Content not available", Toast.LENGTH_LONG).show();
            text = "Content not available";
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }


    void startLoading() {
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.INVISIBLE);
    }

    void stopLoading() {
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
    }

    public void loadAtFirst() {
        // Loading TTS
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speakBtn.setEnabled(status == TextToSpeech.SUCCESS);
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                        Toast.makeText(LearnWord.this, "textToSpeech lang not support", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(LearnWord.this, " textToSpeech init fail", Toast.LENGTH_LONG).show();
            }
        });
        loadImage();
        firstLoad = true;
    }

    public void loadAllResources() {

        if(cmd==1 && currentIndex!=imgTitle.length-1){
            currentIndex++;

            if(currentIndex == imgTitle.length-1){
                nextBtn.setVisibility(View.INVISIBLE);
            }

            loadResources();


            prevBtn.setVisibility(View.VISIBLE);


        }
        else if(cmd==0 && currentIndex!=0){
            currentIndex--;

            if(currentIndex == 0){
                prevBtn.setVisibility(View.INVISIBLE);
            }

            loadResources();


            nextBtn.setVisibility(View.VISIBLE);

        }
        else if(cmd==2)
            ConvertTextToSpeech(imgTitle[currentIndex]);
    }

    private void loadResources(){
        loadImage();
    }

    void loadImage() {
        startLoading();
        if (firstLoad) {
            //int id = getResources().getIdentifier(imgTitle[currentIndex].toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
            setImg(R.drawable.background);
        } else {


            //"https://m.media-amazon.com/images/M/MV5BMTc3NTUzNTI4MV5BMl5BanBnXkFtZTgwNjU0NjU5NzE@._V1_.jpg

            setImg(R.drawable.logo);
            Picasso.get().load(R.drawable.logo)
                    .into(img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            stopLoading();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    if(alphabet){
                                        title.setText(imgTitle[currentIndex].charAt(0)+" - "+imgTitle[currentIndex].toLowerCase().charAt(0)+" for "+imgTitle[currentIndex]);
                                        ConvertTextToSpeech(imgTitle[currentIndex].charAt(0)+" for "+imgTitle[currentIndex]);
                                    }
                                    else{
                                        ConvertTextToSpeech(imgTitle[currentIndex]);
                                    }
                                }
                            }, 500);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(LearnWord.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    void setImg(int id){
        setByPicasso(id);
        //setBlurImg(id);
    }
    /*
    void setBlurImg(int id){
        BlurImage.with(getApplicationContext()).load(id).intensity(30).Async(true).into(imgBg);

    }

     */

    void setByPicasso(int id){
        String name = imgTitle[currentIndex].toLowerCase().replaceAll("\\s", "");
        String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/english/"+buttonText+"/"+name+".jpg";

        startLoading();
        Picasso.get().load(id2)
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        title.setText(imgTitle[currentIndex]);
                        ConvertTextToSpeech(imgTitle[currentIndex]);
                        stopLoading();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(LearnWord.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void play(View view) {
        Bundle b = new Bundle();
        b.putString("key", buttonText);

        Intent i=new Intent(LearnWord.this, PlayWord.class);
        i.putExtras(b);
        startActivity(i);
    }
}