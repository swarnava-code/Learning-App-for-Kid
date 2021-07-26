package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.squareup.picasso.Target;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.lang.System.load;

public class PuzzleActivity extends AppCompatActivity {

    private static final String TAG = "SocialStory";
    //private String[] imgTitle = new String[20];
    ImageView drag1,drag2,drag3,drag4, drag5, drag6;
    LinearLayout g1, g2;
    Button nextButton;
    private ImageView[] options;
    int dragCount;
    Animation shake;
    ImageView[] imageView = new ImageView[6] ;
    ImageView[] answer = new ImageView[6];
    Bitmap[] bitMapImg = new Bitmap[6];
    TextView name, scoreTv;
    int imgId, score=0, randNo;
    String imgTitle;
    String[] topic = {"animal", "bird", "insects", "fruit", "flower"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        name = findViewById(R.id.name);
        scoreTv = findViewById(R.id.score);
        generateRandomSubject();

        imageView = new ImageView[] { (ImageView)findViewById(R.id.answer1),
                (ImageView)findViewById(R.id.answer2),
                (ImageView)findViewById(R.id.answer3),
                (ImageView)findViewById(R.id.answer4),
                (ImageView)findViewById(R.id.answer5),
                (ImageView)findViewById(R.id.answer6)};


        dragCount=0;

        options=new ImageView[6];

        final Random random = new Random();

        //image view  for quiz
        drag1 = (ImageView)findViewById(R.id.drag1);
        drag2 = (ImageView)findViewById(R.id.drag2);
        drag3 = (ImageView)findViewById(R.id.drag3);
        drag4 = (ImageView)findViewById(R.id.drag4);
        drag5 = (ImageView)findViewById(R.id.drag5);
        drag6 = (ImageView)findViewById(R.id.drag6);

        for (int i=0; i<6; i++) {
            answer[i] = imageView[i];
            //answerText[i] = textView[i];
        }

        shake = AnimationUtils.loadAnimation(PuzzleActivity.this, R.anim.shake);

        drag1.setMinimumHeight(drag1.getHeight());
        drag2.setMinimumHeight(drag2.getHeight());
        drag3.setMinimumHeight(drag3.getHeight());
        drag4.setMinimumHeight(drag4.getHeight());
        drag5.setMinimumHeight(drag5.getHeight());
        drag6.setMinimumHeight(drag6.getHeight());

        drag1.setMaxHeight(drag1.getHeight());
        drag2.setMaxHeight(drag2.getHeight());
        drag3.setMaxHeight(drag3.getHeight());
        drag4.setMaxHeight(drag4.getHeight());
        drag5.setMaxHeight(drag5.getHeight());
        drag6.setMaxHeight(drag6.getHeight());

        options[0] = drag1;
        options[1] = drag2;
        options[2] = drag3;
        options[3] = drag4;
        options[4] = drag5;
        options[5] = drag6;

        g1 = (LinearLayout) findViewById(R.id.grid1);
        g2 = (LinearLayout) findViewById(R.id.grid2);
        //cardView=(CardView)findViewById(R.id.buttonCard);

        drag1.setOnTouchListener(new MyOnTouchListener());
        drag2.setOnTouchListener(new MyOnTouchListener());
        drag3.setOnTouchListener(new MyOnTouchListener());
        drag4.setOnTouchListener(new MyOnTouchListener());
        drag5.setOnTouchListener(new MyOnTouchListener());
        drag6.setOnTouchListener(new MyOnTouchListener());

        answer[0].setOnDragListener(new MyDragListener());
        answer[1].setOnDragListener(new MyDragListener());
        answer[2].setOnDragListener(new MyDragListener());
        answer[3].setOnDragListener(new MyDragListener());
        answer[4].setOnDragListener(new MyDragListener());
        answer[5].setOnDragListener(new MyDragListener());

        nextButton=(Button)findViewById(R.id.nextButton);

        setPhoto();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();

            }
        });

        next();

    }

    void generateRandomSubject() {
        //String[] topic = {"animal", "bird", "insects", "fruit", "flower"};
        Random random = new Random();

        randNo = random.nextInt(topic.length);
        int resId = getResources().getIdentifier(topic[randNo], "array", getPackageName());
        String imgTitleArr[] = getResources().getStringArray(resId);

        int randNo2 = random.nextInt(imgTitleArr.length);
        imgTitle = imgTitleArr[randNo2];
        imgId = getResources().getIdentifier(imgTitle.toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
        name.setText(imgTitle);
    }

    void next(){
        generateRandomSubject();
        for (int i=0; i<6; i++) {
            answer[i] = imageView[i];
            //answerText[i] = textView[i];
        }

        g1.setVisibility(View.VISIBLE);
        g2.setVisibility(View.VISIBLE);
        drag1.setVisibility(View.VISIBLE);
        drag2.setVisibility(View.VISIBLE);
        drag3.setVisibility(View.VISIBLE);
        drag4.setVisibility(View.VISIBLE);
        drag5.setVisibility(View.VISIBLE);
        drag6.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
        name.setVisibility(View.GONE);

        answer[0].setImageDrawable(null);
        answer[1].setImageDrawable(null);
        answer[2].setImageDrawable(null);
        answer[3].setImageDrawable(null);
        answer[4].setImageDrawable(null);
        answer[5].setImageDrawable(null);

        //j=random.nextInt(3);  //choose a set from 3 set in Animal List

        setPhoto();//juggle the array element

    }



    void breakImage(){

        Bitmap bitmapMain = null;
        int width, height, partW, partH, col, row;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String name = imgTitle.toLowerCase().replaceAll("\\s", "");
        String id2 = "https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/english/"+topic[randNo]+"/"+name+".jpg";

        try {
            URL url = new URL(id2);
            bitmapMain = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }


        height = bitmapMain.getHeight();
        width = bitmapMain.getWidth();
        Bitmap cropbitmap = bitmapMain;

        if (height>width) {
            int a = ((int)(height-width)/2);
            cropbitmap = Bitmap.createBitmap(bitmapMain, 0, a, width, width); //crop
        }
        else if (height<width) {
            int a = ((int)(width-height)/2);
            cropbitmap = Bitmap.createBitmap(bitmapMain, a, 0, height, height); //crop
        }

        col = 3;
        row = 2;
        Bitmap bitmap = cropbitmap;
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        partW = width/col;
        partH = height/row;


        Bitmap cropbitmap1= Bitmap.createBitmap(bitmap, 0, 0, partW, partH);//1st part
        Bitmap cropbitmap2= Bitmap.createBitmap(bitmap,(width/3), 0, partW, partH);//2nd part
        Bitmap cropbitmap3= Bitmap.createBitmap(bitmap,(width/3)+(width/3), 0, partW, partH);//3rd part

        Bitmap cropbitmap4= Bitmap.createBitmap(bitmap,0, (height/2), partW, partH);//4th part
        Bitmap cropbitmap5= Bitmap.createBitmap(bitmap,(width/3), (height/2), partW, partH);//5th part
        Bitmap cropbitmap6= Bitmap.createBitmap(bitmap,(width/3)+(width/3), (height/2), partW, partH);//6th part

/*
        try{
            int x, y=0, k=0;
            for(int i=0; i<row; i++){
                x=0;
                for(int j=0; j<col; j++){
                    Bitmap cropbitmap123 = Bitmap.createBitmap(bitmap, x, y-2, partW, partH);
                    bitMapImg[k] = cropbitmap123;
                    x += partW;
                    k++;
                }
                y += partH;
            }
        }
        catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

 */



        bitMapImg[0] = cropbitmap1;
        bitMapImg[1] = cropbitmap2;
        bitMapImg[2] = cropbitmap3;
        bitMapImg[3] = cropbitmap4;

        bitMapImg[4] = cropbitmap5;
        bitMapImg[5] = cropbitmap6;


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
        int hint=0;
        breakImage();
        int[] rand = {0,1,2,3,4,5}; //blank array

        shuffleanarray(rand);

        for (int m=0; m<6; m++) {
            options[m].setImageBitmap(bitMapImg[rand[m]]);
            options[m].setTag(rand[m]+"");
            answer[m].setTag(m+"");
            if(rand[m]==1)
                hint = m;
        }

        //answer[Integer.parseInt(options[1].getTag().toString())].setImageBitmap(bitMapImg[1]);



        answer[1].setImageBitmap(bitMapImg[1]);
        options[hint].setVisibility(View.INVISIBLE);
        int finalHint = hint;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                options[finalHint].setVisibility(View.VISIBLE);
                answer[1].setImageBitmap(null);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,2000);


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
                        droptarget.setImageDrawable(option.getDrawable());
                        dragCount++;
                        score++;
                        if(dragCount==6){
                            nextButton.setVisibility(View.VISIBLE);
                            name.setVisibility(View.VISIBLE);
                            dragCount=0;
                        }
                        option.setVisibility(View.INVISIBLE);
                    }
                    else {
                        score--;
                        Animation shake = AnimationUtils.loadAnimation(PuzzleActivity.this, R.anim.shake);
                        droptarget.startAnimation(shake);
                        option.startAnimation(shake);
                        vibrate(500);
                        //Snackbar.make(findViewById(android.R.id.content), "Incorrect Answer! \nTry Again \uD83D\uDE0A", Snackbar.LENGTH_LONG).show();
                    }
                    scoreTv.setText("Score: "+score);
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