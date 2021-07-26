package com.delgrade.socialstoryonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.delgrade.socialstoryonline.ui.home.HomeFragment;

public class SocialStoryMenuActivity extends AppCompatActivity implements StoryAdapter.ItemClickListener {
    Animation slide_left_out, slide_right_out;
    int delay = 300;
    StoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_story_menu);

        String[] data = {"Angry", "Ask for Help", "Birthday", "Brushing", "Covid Story", "Hide and Seek", "Hitting Others", "Hygiene", "Making Noise", "Personal Space", "Playing Games", "School Time", "School Work", "Sharing", "Summer Vacation", "Taking Turns"};
        String title = "Social Story";

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvstories);
        int numberOfColumns = Utility.calculateNoOfColumns(getApplicationContext(),180);
        //int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new StoryAdapter(this, data, title.replaceAll("\\s+",""));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        //anim conn
        slide_left_out = AnimationUtils.loadAnimation(SocialStoryMenuActivity.this, R.anim.slide_left_out);
        slide_right_out = AnimationUtils.loadAnimation(SocialStoryMenuActivity.this, R.anim.slide_right_out);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(position%2 != 0)
        {
            view.startAnimation(slide_right_out);
        }
        else
        {
            view.startAnimation(slide_left_out);
        }
        commonClick(adapter.getItem(position));
        //Toast.makeText(this, "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
    }

    public void buttonClickLeft(View view) {
        CardView b = (CardView)view;
        //TextView tv=findViewById(R.id.textname);
        //String buttonText = tv.getText().toString().trim();
        view.startAnimation(slide_left_out);
        //commonClick(buttonText);
    }

    public void buttonClickRight(View view) {
        CardView b = (CardView)view;
        //TextView tv=findViewById(R.id.textname);
        //String buttonText = tv.getText().toString().trim();
        view.startAnimation(slide_right_out);
        //commonClick(buttonText);
    }

    private void commonClick(String buttonText){

        Runnable r = new Runnable() {
            @Override
            public void run() {

                Bundle b = new Bundle();
                b.putString("key", buttonText);

                Intent i=new Intent(SocialStoryMenuActivity.this, SocialStoryActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, delay);
    }

    public void btn_back(View view) {
        ImageView b= (ImageView) view;
        finish();
    }

    public void addNewByUser(View view) {
        Toast.makeText(this, "Create Your Own Social Story", Toast.LENGTH_LONG).show();
    }

    public static class Utility {
        public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            return (int) (screenWidthDp / columnWidthDp + 0.5);
        }
    }
}