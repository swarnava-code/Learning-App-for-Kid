package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class PlayMath extends AppCompatActivity {
    String [] foodSymbol = {"\uD83C\uDF4E", "\uD83C\uDF4C", "\uD83C\uDF47", "\uD83C\uDF6C", "\uD83C\uDF6B" };
    //String [] foodTitle = {"Apple", "Banana", "Grapes", "Candy", "Chocolate" };
    int[] img_bg = {R.drawable.yellow_bg, R.drawable.cyan_bg, R.drawable.yellow_bg, R.drawable.violet_bg, R.drawable.red_bg };

    TextView[] tv = new TextView[4];
    TextView tv_question;
    String buttonText;
    Random rand = new Random();
    MediaPlayer blurp, boing;
    ImageButton nextBtn;
    int a = 4, randFoodChoose; //answer
    String q1 = "", q2 = "";
    int i1, i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_math);
        tv = new TextView[] { (TextView)findViewById(R.id.answerText1),
                (TextView)findViewById(R.id.answerText2),
                (TextView)findViewById(R.id.answerText3),
                (TextView)findViewById(R.id.answerText4)};
        tv_question = (TextView) findViewById(R.id.tv_question);
        blurp = MediaPlayer.create(PlayMath.this, R.raw.blurp);
        boing = MediaPlayer.create(PlayMath.this, R.raw.boing);
        nextBtn = findViewById(R.id.nextBtn);

        Bundle b = this.getIntent().getExtras();
        buttonText = b.getString("key");

        tv[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClick(0);
            }
        });
        tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClick(1);
            }
        });
        tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClick(2);
            }
        });
        tv[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClick(3);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setVisibility(View.INVISIBLE);
                tv[0].setBackgroundResource(R.drawable.red_bg);
                tv[1].setBackgroundResource(R.drawable.red_bg);
                tv[2].setBackgroundResource(R.drawable.red_bg);
                tv[3].setBackgroundResource(R.drawable.red_bg);
                loadGame();
                tvEnabled(true);
            }
        });

        loadGame();


    }

    void tvClick(int no){
        //grid2.setEnabled(false);
        //grid2.setVisibility(View.GONE);
        if(tv[no].getText().toString().equals(a+"")){
            blurp.start();
            tv[no].setBackgroundResource(R.drawable.correct);
        }
        else{
            boing.start();
            tv[no].setBackgroundResource(R.drawable.wrong);

            for(int i=0; i<4; i++){
                if(tv[i].getText().toString().equals(a+"")){
                    tv[i].setBackgroundResource(R.drawable.correct);
                    break;
                }
            }
        }
        nextBtn.setVisibility(View.VISIBLE);
        tvEnabled(false);

        if(buttonText.equals("division")){
            int dividend=i1, divisor=i2;
            if(i1<i2){
                dividend = i2;
                divisor = i1;
            }

            tv_question.setText(tv_question.getText().toString()+(dividend/divisor)+"\n"+dividend+"\n———\nX");
        }

    }
    void tvEnabled(boolean bool){
        for(int i=0; i<4; i++)
            tv[i].setEnabled(bool);
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

    void loadGame(){

        randFoodChoose = rand.nextInt(foodSymbol.length);
        //tv_question.setBackgroundResource(img_bg[randFoodChoose]);

        if(buttonText.equals("numbers")){
            a = rand.nextInt(10);
            while(a==0)
                a = rand.nextInt(10);

            String q = "";
            for(int i=0; i<a; i++)
                q += foodSymbol[randFoodChoose]+" ";

            tv_question.setText(q);

            int randArr[] = {a, a-1, a+1, a+2 };
            shuffleanarray(randArr);

            for(int i=0; i<4; i++)
                tv[i].setText(randArr[i]+"");

        }
        else if(buttonText.equals("addition")){
            i1 = rand.nextInt(10);
            i2 = rand.nextInt(10);
            while(i1==0)
                i1 = rand.nextInt(10);
            while(i2==0)
                i2 = rand.nextInt(10);

            a = i1+i2;



            q1 = ""; q2 = "";
            if(i1<=5)
                for(int i=0; i<i1; i++)
                    q1 += foodSymbol[randFoodChoose]+" ";
            else
                q1 += i1;

            if(i2<=5)
                for(int i=0; i<i2; i++)
                    q2 += foodSymbol[randFoodChoose]+" ";
            else
                q2 += i2;



            tv_question.setText(q1+"\n+\n"+q2);



            int randArr[] = {a, a-1, a+1, a+2 };
            shuffleanarray(randArr);

            for(int i=0; i<4; i++){
                tv[i].setText(randArr[i]+"");
            }
        }

        else if(buttonText.equals("subtraction")){

            i1 = rand.nextInt(10);
            i2 = rand.nextInt(10);
            while(i1==0)
                i1 = rand.nextInt(10);
            while(i2==0)
                i2 = rand.nextInt(10);

            a = Math.abs(i1-i2) ;



            q1 = ""; q2 = "";
            /*
            if(i1<=5)
                for(int i=0; i<i1; i++)
                    q1 += foodSymbol[randFoodChoose]+" ";
            else

             */
                q1 += i1;

                /*
            if(i2<=5)
                for(int i=0; i<i2; i++)
                    q2 += foodSymbol[randFoodChoose]+" ";
            else

                 */
                q2 += i2;

                if(i1>i2)
            tv_question.setText(q1+" - "+q2);
                else
                    tv_question.setText(q2+" - "+q1);


            int randArr[] = {a, a-1, a+1, a+2 };
            shuffleanarray(randArr);

            for(int i=0; i<4; i++){
                tv[i].setText(randArr[i]+"");
            }
        }

        else if(buttonText.equals("multiplication")||buttonText.equals("multiplication table")){

            i1 = rand.nextInt(5);
            i2 = rand.nextInt(4);
            while(i1==0)
                i1 = rand.nextInt(10);
            while(i2==0)
                i2 = rand.nextInt(10);

            a = i1*i2 ;

            q1 = ""; q2 = "";
            q1 += i1;
            q2 += i2;
            tv_question.setText(q1+" X "+q2);

            int randArr[] = {a, a-1, a+1, a+2 };
            shuffleanarray(randArr);

            for(int i=0; i<4; i++)
                tv[i].setText(randArr[i]+"");

        }

        else if(buttonText.equals("division")){

            int     dividend[] = {3,6,10,3,4,2,8,9,8,4},
                    divisor[] = {3,3,2,1,2,2,2,3,4,4};
            int randNo = rand.nextInt(10);
            i1 = dividend[randNo];
            i2 = divisor[randNo];

            while(i1==0)
                i1 = rand.nextInt(10);
            while(i2==0)
                i2 = rand.nextInt(10);


            if(i1>i2)
                a = i1/i2 ;
            else
                a = i2/i1 ;

            q1 = ""; q2 = "";
            q1 += i1;
            q2 += i2;
            if(i1>i2){
                tv_question.setText(q1+" ÷ "+q2+"\n"+q2+")"+q1+"(");
            }

            else{
                tv_question.setText(q2+" ÷ "+q1+"\n"+q1+")"+q2+"(");
            }


            int randArr[] = {a, a-1, a+1, a+2 };
            shuffleanarray(randArr);

            for(int i=0; i<4; i++)
                tv[i].setText(randArr[i]+"");

        }



    }

}