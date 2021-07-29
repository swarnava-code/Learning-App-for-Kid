
package com.delgrade.socialstoryonline;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PianoActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int sound3StreamId, MAX_STREAM = 9; //6 piano button
    int Stream[] = {R.raw.w36, R.raw.w40, R.raw.w41, R.raw.w42, R.raw.w43, R.raw.w44, R.raw.w45, R.raw.w46, R.raw.w50};
    int sound[] = new int[Stream.length];


    TextView[] textViews = new TextView[Stream.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        MAX_STREAM = Stream.length;

        for(int i=0; i<MAX_STREAM; i++){
            int j = i;
            textViews[i] = findViewById(getResources().getIdentifier("tv"+i, "id", getPackageName()));
        }


        textViews[0].setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[0], 1, 1, 0, 0, 1);

                return false;
            }
        });

        textViews[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[1], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[2], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[3], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[4], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[5].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[5], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[6].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[6], 1, 1, 0, 0, 1);
                return false;
            }
        });

        textViews[7].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound[7], 1, 1, 0, 0, 1);
                return false;
            }
        });








        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAM)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
        }

        for(int i=0; i<MAX_STREAM; i++){
            sound[i] = soundPool.load(this, Stream[i], 1);
        }
    }


/*
To pause
//soundPool.pause(sound3StreamId);
//soundPool.autoPause();
 */


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
