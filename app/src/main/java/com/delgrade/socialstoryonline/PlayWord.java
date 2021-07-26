package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class PlayWord extends AppCompatActivity {

    private String[] imgTitle = new String[20];

    ImageView drag1,drag2,drag3,drag4;//, answer1,answer2,answer3,answer4;
    //TextView answerText1,answerText2,answerText3,answerText4;
    LinearLayout g1, g2;
    //CardView cardView;
    ImageButton nextButton;
    private ImageView[] options;//, answers;
    //private TextView[] answerTexts;
    int j, dragCount;
    Animation shake;


    ImageView[] imageView = new ImageView[4] ;
    ImageView[] answer = new ImageView[4];

    TextView[] textView = new TextView[4];
    TextView[] answerText = new TextView[4];


    MediaPlayer blurp, boing;
    String buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_word);

        blurp = MediaPlayer.create(PlayWord.this, R.raw.blurp);
        boing = MediaPlayer.create(PlayWord.this, R.raw.boing);



        textView = new TextView[] { (TextView)findViewById(R.id.answerText1),
                (TextView)findViewById(R.id.answerText2),
                (TextView)findViewById(R.id.answerText3),
                (TextView)findViewById(R.id.answerText4)};

        imageView = new ImageView[] { (ImageView)findViewById(R.id.answer1),
                (ImageView)findViewById(R.id.answer2),
                (ImageView)findViewById(R.id.answer3),
                (ImageView)findViewById(R.id.answer4)};



        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");
        int resId = getResources().getIdentifier(buttonText.toLowerCase().replaceAll("\\s", ""), "array", getPackageName());
        imgTitle = getResources().getStringArray(resId);

        dragCount=0;

        options=new ImageView[4];
        //answerTexts=new TextView[4];
        //answers=new ImageView[4];
        final Random random=new Random();

        //image view  for quiz
        drag1=(ImageView)findViewById(R.id.drag1);
        drag2=(ImageView)findViewById(R.id.drag2);
        drag3=(ImageView)findViewById(R.id.drag3);
        drag4=(ImageView)findViewById(R.id.drag4);

        jumble();


        shake = AnimationUtils.loadAnimation(PlayWord.this, R.anim.shake);
        drag1.setMinimumHeight(drag1.getHeight());
        drag2.setMinimumHeight(drag2.getHeight());
        drag3.setMinimumHeight(drag3.getHeight());
        drag4.setMinimumHeight(drag4.getHeight());

        options[0]=drag1;
        options[1]=drag2;
        options[2]=drag3;
        options[3]=drag4;
       

        g1=(LinearLayout) findViewById(R.id.grid1);
        g2=(LinearLayout) findViewById(R.id.grid2);
        //cardView=(CardView)findViewById(R.id.buttonCard);

        drag1.setOnTouchListener(new MyOnTouchListener());
        drag2.setOnTouchListener(new MyOnTouchListener());
        drag3.setOnTouchListener(new MyOnTouchListener());
        drag4.setOnTouchListener(new MyOnTouchListener());

        answer[0].setOnDragListener(new MyDragListener());
        answer[1].setOnDragListener(new MyDragListener());
        answer[2].setOnDragListener(new MyDragListener());
        answer[3].setOnDragListener(new MyDragListener());

        nextButton=(ImageButton)findViewById(R.id.nextButton);

        setPhoto();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jumble();
                g1.setVisibility(View.VISIBLE);
                g2.setVisibility(View.VISIBLE);
                drag1.setVisibility(View.VISIBLE);
                drag2.setVisibility(View.VISIBLE);
                drag3.setVisibility(View.VISIBLE);
                drag4.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.GONE);

                answer[0].setImageDrawable(null);
                answer[1].setImageDrawable(null);
                answer[2].setImageDrawable(null);
                answer[3].setImageDrawable(null);

                j=random.nextInt(3);  //choose a set from 3 set in Animal List
                setPhoto();//juggle the array element
            }
        });
    }

    void jumble(){  // it is for jumble image position
        int i;
        int[] jumble = new int[] { 0,1,2,3 };
        shuffleanarray(jumble);
        for (i=0; i<4; i++) {
            answer[i] = imageView[jumble[i]];
            answerText[i] = textView[jumble[i]];
        }
    }

    public static void shuffleanarray(int[] a) { // it is for jumble image position helper
        int n = a.length;
        Random random = new Random();
        // generating random number from list
        random.nextInt();
        for (int i = 0; i < n; i++) {
            // using random generated number
            int change = i + random.nextInt(n - i);

            // swapping elements to shuffle
            int holder = a[i];
            a[i] = a[change];
            a[change] = holder;
        }
    }

    // it is for jumble photos : choose random 4 photo from photo set(animal or bird or ...)
    private void setPhoto() {
        int a, b, c, d, size = imgTitle.length;
        int[] rand = {0, 5, 2, 3}; //blank array

        //generate random data
        Random random=new Random();
        while (true){
            a=random.nextInt(size);
            b=random.nextInt(size);
            c=random.nextInt(size);
            d=random.nextInt(size);
            if (a!=b && b!=c && c!=d && d!=a && d!=b  && c!=a){
                break;
            }
        }
        rand[0]=a;
        rand[1]=b;
        rand[2]=c;
        rand[3]=d;

        for (int m=0; m<4; m++) {
            //int id = getResources().getIdentifier(imgTitle[rand[m]].toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());

            String name = imgTitle[rand[m]].toLowerCase().replaceAll("\\s", "");
            String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/english/"+buttonText+"/"+name+".jpg";


            Picasso.get().load(id2)
                    .into(options[m], new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            //stopLoading();

                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(PlayWord.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


            //options[m].setImageResource(id);
            options[m].setTag(imgTitle[rand[m]]);

            answer[m].setTag(imgTitle[rand[m]]);
            answerText[m].setText(imgTitle[rand[m]]);
        }
    }


    //custom class for drag listener
    public class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent motionEvent) {
            int k=motionEvent.getAction();
            switch (k)
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View v=(View)motionEvent.getLocalState();
                    ImageView droptarget=(ImageView) view;
                    ImageView option=(ImageView)v;

                    if (droptarget.getTag().toString().equals(option.getTag().toString())) {
                        blurp.start();
                        droptarget.setImageDrawable(option.getDrawable());
                        dragCount++;
                        if(dragCount==4){
                            nextButton.setVisibility(View.VISIBLE);
                            dragCount=0;
                        }
                        option.setVisibility(View.INVISIBLE);
                    }
                    else {
                        boing.start();
                        Animation shake = AnimationUtils.loadAnimation(PlayWord.this, R.anim.shake);
                        droptarget.startAnimation(shake);
                        option.startAnimation(shake);
                        vibrate(500);
                        //Snackbar.make(findViewById(android.R.id.content), "Incorrect Answer! \nTry Again \uD83D\uDE0A", Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //Snackbar.make(findViewById(android.R.id.content), "ACTION_DRAG_ENDED! \uD83D\uDE0A", Snackbar.LENGTH_LONG).show();
                    break;
                default:break;
            }
            return true;
        }
    }

    void vibrate(int time_in_ms){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createOneShot(time_in_ms, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(time_in_ms);
    }

}