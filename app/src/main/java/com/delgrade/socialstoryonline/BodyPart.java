package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class BodyPart extends AppCompatActivity {

    TextView textView_desc;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_part);

        textView_desc = findViewById(R.id.textView_desc);
        loadAtFirst();

    }

    void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        //String text = "Hello my name is doggy";
        //Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        tts.stop();
        if (text == null || "".equals(text)) {
            Toast.makeText(BodyPart.this, "Content not available", Toast.LENGTH_LONG).show();
            text = "Content not available";
        }
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }


    public void loadAtFirst() {
        // Loading TTS
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //speakBtn.setEnabled(status == TextToSpeech.SUCCESS);
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                        Toast.makeText(BodyPart.this, "textToSpeech lang not support", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(BodyPart.this, " textToSpeech init fail", Toast.LENGTH_LONG).show();
            }
        });
        //loadImage();
        //firstLoad = true;
    }


    public void bodypartListener(View view) {
        TextView textView = (TextView) view;
        String string = textView.getTag().toString();
        textView_desc.setText(string);
        ConvertTextToSpeech(string);
        //Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }

    public void back(View view) {
        finish();
    }
}