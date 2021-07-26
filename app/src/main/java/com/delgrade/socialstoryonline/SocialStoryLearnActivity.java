package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;


import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class SocialStoryLearnActivity extends AppCompatActivity {

    ImageButton btn_nxt, btn_pause;
    ImageView img, imgBg,btn_back;
    TextView title;
    ProgressBar progressBar;
    CardView cardView;

    private int currentIndex = 0;

    boolean firstLoad = false, forcestop=false, ttsError=false;

    TextToSpeech tts;
    private String[] imgTitle = new String[50];
    LinearLayout linearLayout;
    String buttonText;

    ProgressBar progressbar;
    String desc, TAG = "SocialStory";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_story_learn);

        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");

        int resId = getResources().getIdentifier(buttonText.toLowerCase().replaceAll("\\s", ""), "array", getPackageName());
        imgTitle = getResources().getStringArray(resId);


        btn_nxt = findViewById(R.id.btn_nxt);
        btn_pause = findViewById(R.id.btn_pause);
        img = findViewById(R.id.img);
        imgBg = findViewById(R.id.imgBg);
        title = findViewById(R.id.title);

        btn_back = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.circularSpinner);
        cardView = findViewById(R.id.cardView);

        linearLayout = findViewById(R.id.test);
        progressbar = findViewById(R.id.progressbar);

        // Initial Load of imageview
        if (!firstLoad) {
            loadAtFirst();
        }

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn_nxt.setText("Next");
                forcestop=false;
                if(ttsError)
                    loadAllResourcesWithoutTTS();
                else
                    loadAllResources();

                btn_pause.setVisibility(View.VISIBLE);
                btn_nxt.setVisibility(View.GONE);

                //keepScreenOn();
/*                if(ttsError)
                    loadAllResourcesWithoutTTS();
                else
                    loadAllResources();*/
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn_nxt.setText("Next");
                        forcestop=true;
                        btn_pause.setVisibility(View.GONE);
                        btn_nxt.setVisibility(View.VISIBLE);
/*                forcestop=true;
                btn_pause.setVisibility(View.GONE);
                //btn_nxt.setText("▷");
                btn_nxt.setVisibility(View.VISIBLE);*/
            }
        });



        //set height of image
        Runnable r = new Runnable() {
            @Override
            public void run() {
                linearLayout.getLayoutParams().height = cardView.getWidth();
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,500);



        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                if(!tts.isSpeaking()){
                    keepScreenOn();
                    loadAllResourcesWithoutTTS();
                }

            }
        };
        Handler h1 = new Handler();
        h1.postDelayed(r1,6000);



        title.setText("Welcome to "+buttonText.toUpperCase());

    }


    public void btn_back(View view) {
        ImageView b= (ImageView) view;
        onBackPressed();
    }

    void keepScreenOn(){
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        } else {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

         */

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }


    public static int countWordsUsingSplit(String input) { if (input == null || input.isEmpty()) { return 0; } String[] words = input.split("\\s+"); return words.length; }


    void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        //String text = "Hello my name is doggy";
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        tts.stop();
        if (text == null || "".equals(text)) {
            Log.d(TAG, "Content not available");
            //Toast.makeText(SocialStoryLearnActivity.this, "Content not available", Toast.LENGTH_LONG).show();
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

    // This method for slow device which taking time to load TTS
    void loopInitIsSpeaking(){
        // runnable for time delay
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(!forcestop){

                    if(tts.isSpeaking()){ //if initially tts spoke hello then load all resources
                        //loadAllResources();
                        loadAllResources();
                    }
                    else{
                        loopInitIsSpeaking();
                    }
                }
                else{
                    tts.stop();
                }
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,300);
    }

    public void loadAtFirst() {
        // Loading TTS
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                        ttsError = true;
                        Toast.makeText(SocialStoryLearnActivity.this, "textToSpeech lang not support", Toast.LENGTH_LONG).show();
                        loadAllResourcesWithoutTTS();
                    }
                    else{
                        keepScreenOn();
                        loopInitIsSpeaking();
                    }

                } else{
                    ttsError = true;
                    Toast.makeText(SocialStoryLearnActivity.this, " textToSpeech init fail", Toast.LENGTH_LONG).show();
                    loadAllResourcesWithoutTTS();
                }
            }

        });
        loadImage();
        firstLoad = true;

    }


    int countCommaAndFullstop(String someString){
        int commaCount = 0, fullstopCount=0;

        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == ',') {
                commaCount++;
            }
            else if (someString.charAt(i) == '.') {
                fullstopCount++;
            }
        }

        return (commaCount+fullstopCount);
    }

    void loopIsSpeaking(){
        // runnable for time delay
        Runnable r = new Runnable() {
            @Override
            public void run() {

                if(!forcestop){

                    if(tts.isSpeaking()){
                        //loadAllResources();
                        loopIsSpeaking();
                    }

                    else{

                        if(currentIndex!=0) {
                            Runnable r1 = new Runnable() {
                                @Override
                                public void run() {
                                    loadAllResources();
                                }
                            };
                            Handler h1 = new Handler();
                            h1.postDelayed(r1,2000);

                        }
                        else{
                            btn_pause.setVisibility(View.GONE);
                            //btn_nxt.setText("▷ Play Again");
                            btn_nxt.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else{
                    tts.stop();
                }

            }
        };
        Handler h = new Handler();
        h.postDelayed(r,300);


    }


    public void loadAllResourcesWithoutTTS() {
        btn_pause.setVisibility(View.VISIBLE);
        String desc = imgTitle[currentIndex];
        loadImage();
        title.setText(desc);
        ConvertTextToSpeech(desc);
        currentIndex++;
        currentIndex = currentIndex % imgTitle.length;

        float div = (float)currentIndex/(float)imgTitle.length;
        int percentage = (int)(div*100);
        if(percentage==0)
            progressbar.setProgress(100);
        else
            progressbar.setProgress(percentage);

        //loopIsSpeaking();



        int deleyTime = (countCommaAndFullstop(desc)*50)+(countWordsUsingSplit(desc)*600);


        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(!forcestop){
                    if(currentIndex!=0) {
                        loadAllResources();
                    }
                    else{
                        btn_pause.setVisibility(View.GONE);
                        //btn_nxt.setText("Start Again");
                        btn_nxt.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, deleyTime);


    }

    public void loadAllResources() {
        btn_pause.setVisibility(View.VISIBLE);
        desc = imgTitle[currentIndex];
        loadImage();
        title.setText(desc);
        //ConvertTextToSpeech(desc);
        currentIndex++;
        currentIndex = currentIndex % imgTitle.length;

        float div = (float)currentIndex/(float)imgTitle.length;
        int percentage = (int)(div*100);
        if(percentage==0)
            progressbar.setProgress(100);
        else
            progressbar.setProgress(percentage);

        loopIsSpeaking();



        //int deleyTime = (countCommaAndFullstop(desc)*50)+(countWordsUsingSplit(desc)*600);

        /*
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(forcestop==false){
                    if(currentIndex!=0) {
                        loadAllResources();
                    }
                    else{

                        btn_nxt.setText("Start Again");
                        btn_nxt.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, deleyTime);
        */

    }

    void loadImage() {
        startLoading();
        if (firstLoad) {
            int id = getResources().getIdentifier((buttonText+currentIndex).toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
            setImg(id);
        } else {
            //btn_nxt.setText("Start now");

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
                                    ConvertTextToSpeech("Hello");
                                }
                            }, 500);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(SocialStoryLearnActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
        String name = buttonText.toLowerCase().replaceAll("\\s", "");
        String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/socialstory/"+name+"/"+name+currentIndex+".jpg";

        Picasso.get().load(id2)
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        stopLoading();
                        ConvertTextToSpeech(desc);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(SocialStoryLearnActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

/*    public void play(View view) {
        Bundle b = new Bundle();
        b.putStringArray("key", imgTitle);

        Intent i=new Intent(SocialStoryLearnActivity.this, PlayWord.class);
        i.putExtras(b);
        startActivity(i);
    }*/

    @Override
    public void onBackPressed() {
        forcestop=true;
        //ConvertTextToSpeech(".");
        tts.stop();
        finish();
    }
}