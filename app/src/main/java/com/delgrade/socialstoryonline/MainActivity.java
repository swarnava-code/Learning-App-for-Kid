package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int delayTime = 2000;
    private boolean isFirstAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);


        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);

        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(getString(R.string.app_name));
                textView2.setText(getString(R.string.developer));
                textView.setAnimation(fade_in);
                if (!isFirstAnimation) {
                    imageView.clearAnimation();
/*                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    finish();*/

                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, Home.class));
                            finish();
                        }
                    };
                    Handler h = new Handler();
                    h.postDelayed(r, delayTime);
                }
                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(hold);
    }

}