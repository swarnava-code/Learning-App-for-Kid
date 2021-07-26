package com.delgrade.socialstoryonline;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SocialStoryActivity extends AppCompatActivity {

    TextView speakBtn;
    ImageView imgBg, img;
    TextView title;
    ProgressBar progressBar;
    CardView cardView;

    ImageButton btn_close;
    int currentIndex = 0;

    boolean firstLoad = false;
    TextToSpeech tts;
    String[] imgTitle = new String[20];
    LinearLayout linearLayout;
    int cmd = 2; // 0-right, 1-left, 2-just speak, checking left or right , flag
    String buttonText;
    ProgressBar statusprogressbar;

    ProgressDialog progressDialog;

    int imgTitleCount;
    CardView buttonNext,buttonReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_story);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..."); // Setting Message
        //progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

        //btn_nxt = findViewById(R.id.btn_nxt);
        img = findViewById(R.id.img);
        imgBg = findViewById(R.id.imgBg);
        title = findViewById(R.id.title);
        statusprogressbar = findViewById(R.id.statusprogressbar);

        buttonNext = (CardView)findViewById(R.id.buttonNext);
        //buttonReplay = (CardView)findViewById(R.id.buttonReplay);


        //prevBtn = findViewById(R.id.prevBtn);
        //nextBtn = findViewById(R.id.nextBtn);
        speakBtn = findViewById(R.id.speak_btn);
        progressBar = findViewById(R.id.circularSpinner);
        cardView = findViewById(R.id.cardView);

        linearLayout = findViewById(R.id.test);

        buttonNext.setVisibility(View.INVISIBLE);
        //buttonReplay.setVisibility(View.INVISIBLE);


        //getting from prev page
        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");
        title.setText(buttonText);
        buttonText = buttonText.toLowerCase();
        int resId = getResources().getIdentifier(buttonText.toLowerCase().replaceAll("\\s", ""), "array", getPackageName());
        imgTitle = getResources().getStringArray(resId);

        imgTitleCount = imgTitle.length;




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
                try {
                    loadAllResources();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
/*
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

 */


        img.setOnTouchListener(new OnSwipeTouchListener(SocialStoryActivity.this) {
            public void onSwipeTop() throws InterruptedException {
                //Toast.makeText(LearnWord.this, "top", Toast.LENGTH_SHORT).show();
                cmd = 2;
                loadAllResources();
            }
            public void onSwipeRight() throws InterruptedException {
                //Toast.makeText(LearnWord.this, "right", Toast.LENGTH_SHORT).show();
                cmd = 0;
                loadAllResources();
            }
            public void onSwipeLeft() throws InterruptedException {
                //Toast.makeText(LearnWord.this, "left", Toast.LENGTH_SHORT).show();
                cmd = 1;
                loadAllResources();
            }
            @SuppressLint("ClickableViewAccessibility")
            public void onSwipeBottom() throws InterruptedException {
                //Toast.makeText(LearnWord.this, "bottom", Toast.LENGTH_SHORT).show();
                cmd = 2;
                loadAllResources();
            }

        });



        //loading the 1st image
        //currentIndex++;
        loadResources();


        progressDialog.dismiss();




    }

    public void didTapButton(View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        buttonNext.startAnimation(myAnim);
        String[] arr = {"Hygiene", "Birthday", "Angry", "Ask For Help", "Brushing", "School Time", "Hide and Seek", "Hitting Others", "Making Noise", "Personal Space", "Playing Games", "Sharing", "Summer Vacation", "Taking Turns", "School Work"};
        Random random = new Random();
        int rand = random.nextInt(arr.length);

        Bundle b = new Bundle();
        b.putString("key", arr[rand]);
        Intent i = new Intent(getApplicationContext(), SocialStoryActivity.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    public void replayButton(View v) throws InterruptedException {
        Toast.makeText(SocialStoryActivity.this, v+"", Toast.LENGTH_LONG).show();
        final Animation myAnima = AnimationUtils.loadAnimation(this, R.anim.bounce);
        buttonReplay.startAnimation(myAnima);

        //Toast.makeText(SocialStoryActivity.this, currentIndex+"", Toast.LENGTH_LONG).show();
/*        currentIndex=-1;

        updateButtons();
        loadAllResources();*/
    }

    public void btn_back(View view) {
        ImageView b= (ImageView) view;
        onBackPressed();
    }

    void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        //String text = "Hello my name is doggy";
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        tts.stop();
        if (text == null || "".equals(text)) {
            speakBtn.setText("Content not available");
            //Toast.makeText(SocialStoryActivity.this, "Content not available", Toast.LENGTH_LONG).show();
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
                        //Toast.makeText(SocialStoryActivity.this, "textToSpeech lang not support", Toast.LENGTH_LONG).show();

                    }
                } else
                   Toast.makeText(SocialStoryActivity.this, " textToSpeech init fail", Toast.LENGTH_LONG).show();
            }
        });
        loadImage();
        firstLoad = true;
    }

    public void loadAllResources() throws InterruptedException {
        if(cmd==1 && (currentIndex!=(imgTitle.length-1))){
            currentIndex++;
            //currentIndex = currentIndex % imgTitle.length;
        }
        else if(cmd==0 && currentIndex!=0){
            currentIndex--;
            //currentIndex = currentIndex % imgTitle.length;
        }
        else if(cmd==2) {
            ConvertTextToSpeech(imgTitle[currentIndex]);
        }
        loadResources();
    }

    private void loadResources(){
        loadImage();
        float div = ((float)currentIndex/(float)(imgTitle.length-1));
        int percentage = (int)(div*100);
        statusprogressbar.setProgress(percentage);
    }

    void loadImage() {
        startLoading();
        try {
            setImageByPicasso();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int id = getResources().getIdentifier((buttonText+currentIndex).toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
 /*       if (firstLoad) {
            int id = getResources().getIdentifier((buttonText+currentIndex).toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
            setImg(id);
        } else {*/

        //setImageByPicasso(id);


            /*Picasso.get().load(R.drawable.logo)
                    .into(img, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            stopLoading();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ConvertTextToSpeech(imgTitle[currentIndex]);
                                }
                            }, 500);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(SocialStoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });*/
        //}
    }

/*    void setImg(int id){
        setByPicasso(id);
        //setBlurImg(id);
    }*/
    /*
    void setBlurImg(int id){
        BlurImage.with(getApplicationContext()).load(id).intensity(30).Async(true).into(imgBg);

    }

     */

    void updateButtons() throws InterruptedException {
        int temp=imgTitleCount-1;

        if(temp == currentIndex)
        {
            //buttonReplay.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        }
        else
        {
            buttonNext.setVisibility(View.INVISIBLE);
            //buttonReplay.setVisibility(View.INVISIBLE);
        }
    }

    void setImageByPicasso() throws InterruptedException {
        updateButtons();
        String name = buttonText.toLowerCase().replaceAll("\\s", "");
        String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/socialstory/"+name+"/"+name+currentIndex+".jpg";

        Picasso.get().load(id2)
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        stopLoading();
                        speakBtn.setText(imgTitle[currentIndex]);
                        ConvertTextToSpeech(imgTitle[currentIndex]);
                    }

                    @Override
                    public void onError(Exception e) {
                        //Toast.makeText(SocialStoryActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        Log.d("SocialStoryActivit", "Picasso onError: "+e.toString());
                    }
                });
    }

    public void play(View view) {
        Bundle b = new Bundle();
        b.putString("key", buttonText);
        tts.stop();
        Intent i=new Intent(SocialStoryActivity.this, SocialStoryLearnActivity.class);
        i.putExtras(b);
        startActivity(i);
        //finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tts.stop();
        //finish();
/*        ConvertTextToSpeech(".");
        tts.stop();
        finish();*/
    }

}