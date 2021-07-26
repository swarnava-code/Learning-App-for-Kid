package com.delgrade.socialstoryonline;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Random;

public class LearnMathActivity extends AppCompatActivity {

    Button btn_nxt;
    ImageView img, imgBg;
    TextView title, mathTV, crossHelperTV, resultTv, addHelperTV, desc;
    ProgressBar progressBar;
    CardView cardView;
    ImageButton btn_close;
    LinearLayout cardViewImgbg;
    private int currentIndex = 0, learnNumbersLimit = 100, mul_table_const=0;

    boolean firstLoad = false;
    TextToSpeech tts;
    private String[] imgTitle = new String[5];
    LinearLayout linearLayout;
    String buttonText;

    String [] foodSymbol = {"\uD83C\uDF4E", "\uD83C\uDF4C", "\uD83C\uDF47", "\uD83C\uDF6C", "\uD83C\uDF6B" };
    String [] foodTitle = {"Apple", "Banana", "Grapes", "Candy", "Chocolate" };
    //String [] foodBgColor = {"FFC107", "009688", "FFEB3B", "9C27B0", "8BC34A" };
    int[] img_bg = {R.drawable.yellow_bg, R.drawable.cyan_bg, R.drawable.yellow_bg, R.drawable.violet_bg, R.drawable.red_bg };

    int questionHardnessLevel = 5;
    Animation fade_in, shake, slide_in;
    int delayShowFirst = 200, wrongDelay = 1000, delayResult=2000, delayNext = 3500;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_math);

        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");

        //buttonText = "birthday";

        //int resId = getResources().getIdentifier("birthday", "array", getPackageName());
        //imgTitle = getResources().getStringArray(resId);


        btn_nxt = findViewById(R.id.btn_nxt);
        img = findViewById(R.id.img);
        imgBg = findViewById(R.id.imgBg);
        title = findViewById(R.id.title);
        resultTv = findViewById(R.id.resultTv);
        addHelperTV = findViewById(R.id.addHelperTV);
        desc = findViewById(R.id.desc);
        cardViewImgbg = findViewById(R.id.cardViewImgbg);


        btn_close = findViewById(R.id.btn_close);
        progressBar = findViewById(R.id.circularSpinner);
        cardView = findViewById(R.id.cardView);

        linearLayout = findViewById(R.id.test);

        mathTV = findViewById(R.id.mathTV);
        crossHelperTV = findViewById(R.id.crossHelperTV);

        listView = findViewById(R.id.listview);


        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

        //imgBg.setBackgroundResource(R.drawable.banana);
        //BlurImage.with(getApplicationContext()).load(R.drawable.bg2).intensity(20).Async(true).into(imgBg);

        // Initial Load of imageview
        if (!firstLoad) {
            loadAtFirst();
        }

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_nxt.setText("Next");

                generateMathStudyText();
                //loadAllResources();
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
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                linearLayout.getLayoutParams().height = (height/2);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,500);
    }




    void generateMathStudyText(){
        Random rand = new Random();
        mathTV.setText("");
        String concatSymbol = "";
        int result=0, div_result=1;
        float result2=0;
        int chooseFood = rand.nextInt(foodSymbol.length);
        int no1 = rand.nextInt(questionHardnessLevel)+1;
        int no2 = rand.nextInt(questionHardnessLevel)+1;
        String operationSpoken="";
        resultTv.setVisibility(View.GONE);
        mathTV.setVisibility(View.GONE);
        addHelperTV.setVisibility(View.GONE);
        btn_nxt.setEnabled(false);
        //cardView.setCardBackgroundColor(Color.parseColor("#"+foodBgColor[chooseFood]));
        cardViewImgbg.setBackgroundResource(img_bg[chooseFood]);



        if(buttonText.equals("multiplication")){
            desc.setText("Multiplication is also known as repetative addition");
            operationSpoken = "cross";
            result = no1*no2;

            int max, min;
            max = (no2>no1)?no2:no1; //getting max
            min = (no2<no1)?no2:no1; //getting max

            no2 = max;
            no1 = min;



            for(int j=0; j<no2; j++){
                for(int k=0; k<no1; k++)
                    concatSymbol += foodSymbol[chooseFood] + " ";
                concatSymbol+="\n";
            }



            String concatSymbol2 = "(";
            for(int i=0; i<no2; i++)
                concatSymbol2 += " "+no1+" +";
            concatSymbol2 = (concatSymbol2.substring(0, concatSymbol2.length()-1))+")";//delete last + symbol
            addHelperTV.setText(concatSymbol2);

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    addHelperTV.setVisibility(View.VISIBLE);
                    addHelperTV.startAnimation(shake);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, wrongDelay );

            //concatSymbol+="\n\n"+no1+" + "+no2+" = "+result;
            resultTv.setText(no1+" X "+no2+" = "+result);


            /*
            operationSpoken = "cross";
            result = no1*no2;


            String [] array = new String[no1];
            String [] array2 = new String[no1];

            String contents="";

            //for(int i=0; i<no2; i++){
                for(int j=0; j<no2; j++){
                    //concatSymbol += foodSymbol[chooseFood] + " ";
                    contents += foodSymbol[chooseFood] + " ";
                }
                //concatSymbol+="\n";
            //}
            //concatSymbol+=no1+" X "+no2+" = "+result+"\n";
            resultTv.setText(no1+" X "+no2+" = "+result);



            Arrays.fill(array, " ");
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.list_elements, array);


            Arrays.fill(array2, contents);
            ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                    R.layout.list_elements, array2);



            Runnable r = new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter);
                    listView.setAnimation(shake);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, delayShowFirst);

            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter2);
                }
            };
            Handler h2 = new Handler();
            h2.postDelayed(r2, wrongDelay );

             */





        }

        else if(buttonText.equals("addition")){
            desc.setText("The addition of two whole numbers results in the total amount or sum of those values combined.");
            operationSpoken = "+";
            result = no1+no2;

            for(int j=0; j<no1; j++)
                concatSymbol += foodSymbol[chooseFood] + " ";
            concatSymbol+="\n+";

            String concatSymbol2="";
            for(int i=0; i<no2; i++)
                concatSymbol2 += foodSymbol[chooseFood] + " ";
            addHelperTV.setText(concatSymbol2);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    addHelperTV.setVisibility(View.VISIBLE);
                    addHelperTV.startAnimation(shake);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, wrongDelay );

            //concatSymbol+="\n\n"+no1+" + "+no2+" = "+result;
            resultTv.setText(no1+" + "+no2+" = "+result);
        }

        else if(buttonText.equals("subtraction")){
            desc.setText("Subtract means to take away from a group.");
            crossHelperTV.setText("");
            crossHelperTV.setVisibility(View.GONE);
            operationSpoken = " minus ";
            int largeNo = (no1>no2)?no1:no2;
            int smallNo = (no1<no2)?no1:no2;
            result = largeNo-smallNo;
            no1 = largeNo;
            no2 = smallNo;

            for(int j=0; j<largeNo-smallNo; j++)
                concatSymbol += foodSymbol[chooseFood] + " ";

            String wrong = "";
            concatSymbol += "\n";
            for(int i=0; i<smallNo; i++){
                concatSymbol += foodSymbol[chooseFood] + " ";
                wrong += "❌" + " ";
            }

            crossHelperTV.setText(wrong);
            
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    crossHelperTV.setVisibility(View.VISIBLE);
                    crossHelperTV.startAnimation(shake);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, wrongDelay );

            resultTv.setText(largeNo+" - "+smallNo+" = "+ result);


        }

        else if(buttonText.equals("numbers")) {             //numbers
            cardViewImgbg.setBackgroundResource(R.drawable.cyan_bg);
            desc.setText("Number is a symbol that indicates a quantity. Count the Apple");
            resultTv.setVisibility(View.VISIBLE);
            mathTV.setVisibility(View.VISIBLE);
            btn_nxt.setEnabled(true);
            chooseFood = 0;

//learnNumbersLimit
            if(resultTv.getText().toString().equals(""))
                resultTv.setText("0");

            else{

                result = Integer.parseInt((resultTv.getText().toString()));
                if(result==learnNumbersLimit)
                    result = 0;
                else{
                    //mathTV.setTextSize(Math.abs(100-(2*result)));

                    switch (result){
                        case 0:
                            mathTV.setTextSize(40);
                            break;
                        case 10:
                            mathTV.setTextSize(35);
                            break;
                        case 20:
                            mathTV.setTextSize(30);
                            break;
                        case 30:
                            mathTV.setTextSize(25);
                            break;
                        case 40:
                            mathTV.setTextSize(20);
                            break;
                        case 50:
                            mathTV.setTextSize(19);
                            break;
                        case 60:
                            mathTV.setTextSize(18);
                            break;
                        case 70:
                            mathTV.setTextSize(17);
                            break;
                        case 80:
                            mathTV.setTextSize(16);
                            break;
                        case 90:
                            mathTV.setTextSize(15);
                            break;
                        case 100:
                            mathTV.setTextSize(14);
                            break;

                    }

                    //if(result<10)

                    result+=1;
                }

                resultTv.setText(result+"");
            }


            for(int j=0; j<result; j++)
                concatSymbol += foodSymbol[chooseFood] + " ";

        }
        else if(buttonText.equals("division")) {


            String personName[] = {"You", "Rahim", "Riya", "Puja"}, msg="";
            String personFace[] = {" \uD83D\uDC66\uD83C\uDFFB ", " \u200D \uD83D\uDC68\uD83C\uDFFB\u200D ", "\uD83D\uDC69\uD83C\uDFFB", "\uD83D\uDC67\uD83C\uDFFB"};

            int     dividend[] = {3,6,10,3,4,2,8,9,8,4},
                    divisor[] = {3,3,2,1,2,2,2,3,4,4};

            desc.setText("Division means sharing something between friends\uD83E\uDD1E\uD83C\uDFFB");
            int randNo = rand.nextInt(10);
            no1 = dividend[randNo];
            no2 = divisor[randNo];


            operationSpoken = "divide";
            //result2 = (float)((float)no1/(float)no2);
            div_result = (int) ((float)Math.round(((float)no1/(float)no2) * 100.0) / 100.0);

            for(int i=0; i<no2; i++){
                concatSymbol += personFace[i]+"  ";
                msg += personName[i] + " get "+ div_result+" "+foodSymbol[chooseFood]+". ";
                for(int j=0; j<div_result; j++){
                    concatSymbol += foodSymbol[chooseFood]+" ";
                }
                concatSymbol += "\n";
            }

            String finalmsg = msg;
            Runnable lateR = new Runnable() {
                @Override
                public void run() {
                    ConvertTextToSpeech(finalmsg);
                }
            };
            Handler lateH = new Handler();
            lateH.postDelayed(lateR,3000);




            /*




            delay{
            From 4 candy
                Ram get 2 candy
                Syam get 2 candy
            }


            listView.setAnimation(shake);

             */



            String [] array = new String[no2];
            String [] array2 = new String[no2];

            /*
            String contents="";

            //for(int i=0; i<no2; i++){
            for(int j=0; j<no2; j++){
                //concatSymbol += foodSymbol[chooseFood] + " ";
                contents += foodSymbol[chooseFood] + " ";
            }
            //concatSymbol+="\n";
            //}
            //concatSymbol+=no1+" X "+no2+" = "+result+"\n";

             */
            resultTv.setText(no1+" ÷ "+no2+" = "+div_result);



/*
            Arrays.fill(array, " ");
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.list_elements, array);


            Arrays.fill(array2, div_result+" "+foodSymbol[chooseFood]);
            ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                    R.layout.list_elements, array2);



            Runnable r = new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter);
                    listView.setAnimation(shake);
                }
            };
            Handler h = new Handler();
            h.postDelayed(r, delayShowFirst);

            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter2);
                }
            };
            Handler h2 = new Handler();
            h2.postDelayed(r2, wrongDelay );

 */


        }

        else if(buttonText.equals("multiplication table")) {
            mul_table_const++;
            String table="";
            for(int i=1; i<=10; i++)
                concatSymbol += mul_table_const + " X " + i +" = "+(mul_table_const*i)+"\n";

            mathTV.setText(table);
            addHelperTV.setText(table);
            mathTV.setTextSize(18);

            //Toast.makeText(getApplicationContext(), ":"+table, Toast.LENGTH_LONG).show();
        }



        else{
            Toast.makeText(getApplicationContext(), "Invalid operation", Toast.LENGTH_LONG).show();
        }

        mathTV.setText(concatSymbol);


        if(buttonText.equals("multiplication")){
            ConvertTextToSpeech(no1+operationSpoken+no2+"="+result+foodTitle[chooseFood]);
            resultAnim();
        }
        else if(buttonText.equals("division")){
            ConvertTextToSpeech(no1+operationSpoken+no2+"="+div_result+foodTitle[chooseFood]);
            resultAnim();
        }
        else if(buttonText.equals("numbers")){
            ConvertTextToSpeech(result+foodTitle[chooseFood]);
        }
        //else if(buttonText.equals("multiplication table")){
           // ConvertTextToSpeech("next");result+foodTitle[chooseFood]
        //}
        else{
            if(!buttonText.equals("multiplication table"))
                ConvertTextToSpeech(no1+foodTitle[chooseFood]+operationSpoken+no2+foodTitle[chooseFood]+"="+result+foodTitle[chooseFood]);
            resultAnim();
        }



    }

    void resultAnim(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mathTV.setVisibility(View.VISIBLE);
                mathTV.setAnimation(shake);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, delayShowFirst);

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                resultTv.setVisibility(View.VISIBLE);
                resultTv.startAnimation(fade_in);

            }
        };
        Handler h2 = new Handler();
        h2.postDelayed(r2, delayResult);

        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                btn_nxt.setEnabled(true);
            }
        };
        Handler h3 = new Handler();
        h3.postDelayed(r3, delayNext);
    }


    void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        //String text = "Hello my name is doggy";
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        tts.stop();
        if (text == null || "".equals(text)) {
            Toast.makeText(LearnMathActivity.this, "Content not available", Toast.LENGTH_LONG).show();
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
                btn_nxt.setEnabled(status == TextToSpeech.SUCCESS);
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                        Toast.makeText(LearnMathActivity.this, "textToSpeech lang not support", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(LearnMathActivity.this, " textToSpeech init fail", Toast.LENGTH_LONG).show();
            }
        });
        loadImage();
        ConvertTextToSpeech(buttonText);
        firstLoad = true;
    }

    void loadImage() {
        startLoading();
        if (firstLoad) {
            int id = getResources().getIdentifier((buttonText+currentIndex).toLowerCase().replaceAll("\\s", ""), "drawable", getPackageName());
            setImg(id);
        } else {
            btn_nxt.setText("Start now");

            //"https://m.media-amazon.com/images/M/MV5BMTc3NTUzNTI4MV5BMl5BanBnXkFtZTgwNjU0NjU5NzE@._V1_.jpg

            //setImg(R.drawable.logo1);

            //Picasso.get().load()
                    //.into(img, new com.squareup.picasso.Callback() {
                        //@Override
                        //public void onSuccess() {
                            stopLoading();
                            //final Handler handler = new Handler();
                            //handler.postDelayed(new Runnable() {
                                //@Override
                                //public void run() {
                                    ConvertTextToSpeech("Hello");
                                //}
                            //}, 500);
                        //}

                        //@Override
                        //public void onError(Exception e) {
                            //Toast.makeText(LearnMathActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    //});
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
        Picasso.get().load(id)
                .into(img, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        stopLoading();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(LearnMathActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void play(View view) {

        Bundle b = new Bundle();
        b.putString("key", buttonText);

        Intent i=new Intent(LearnMathActivity.this, PlayMath.class);
        i.putExtras(b);
        startActivity(i);


        //Toast.makeText(getApplicationContext(), "Play window not developed yet", Toast.LENGTH_LONG).show();
    }
}