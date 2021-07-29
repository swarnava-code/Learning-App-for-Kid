package com.delgrade.socialstoryonline.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.delgrade.socialstoryonline.English;
import com.delgrade.socialstoryonline.FunTimeMenu;
import com.delgrade.socialstoryonline.Mathematics;
import com.delgrade.socialstoryonline.R;
import com.delgrade.socialstoryonline.SocialStoryMenuActivity;

import java.util.Random;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView btn_math, btn_english,btn_social_story,btn_funtime;
    //final Animation translateScale = AnimationUtils.loadAnimation(HomeFragment.this, R.anim.translate_scale);
   // int[] img_bg = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.e };
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        btn_math = root.findViewById(R.id.play_math);
        btn_english = root.findViewById(R.id.play_english);
        btn_social_story = root.findViewById(R.id.play_social_story);
        btn_funtime = root.findViewById(R.id.funtime);


        btn_math.setOnClickListener(this);
        btn_english.setOnClickListener(this);
        btn_social_story.setOnClickListener(this);
        btn_funtime.setOnClickListener(this);


        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //getActivity().getWindowManager().getDefaultDisplay().systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        loadImage();

        root.findViewById(R.id.background).setBackgroundResource(R.drawable.gradient_start);


        //anim = (AnimationDrawable) root.findViewById(R.id.background).getBackground();
        //((AnimationDrawable) root.findViewById(R.id.background).getBackground()).start();


        return root;
    }


    void loadImage() {
        ImageView img=(ImageView) root.findViewById(R.id.img);
        Random rand = new Random();
        //int max = img_bg.length;
        //int randomNum = rand.nextInt(max);
        //Glide.with(requireContext()).load(img_bg[randomNum]).into(img);
    }



    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        loadImage();
        switch (view.getId()) {
            case R.id.play_math:
                //switchFragment(HelpFragment.TAG);
                startActivity(new Intent(getActivity(), Mathematics.class));
                break;
            case R.id.play_english:
                startActivity(new Intent(getActivity(), English.class));
                break;
            case R.id.play_social_story:
                startActivity(new Intent(getActivity(), SocialStoryMenuActivity.class));
                break;
            case R.id.funtime:
                startActivity(new Intent(getActivity(), FunTimeMenu.class));
                break;
        }
    }
}