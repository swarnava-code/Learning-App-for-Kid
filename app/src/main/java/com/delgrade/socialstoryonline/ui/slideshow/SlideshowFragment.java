package com.delgrade.socialstoryonline.ui.slideshow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.delgrade.socialstoryonline.R;

import java.util.Random;

public class SlideshowFragment extends Fragment {

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        ImageView img=(ImageView) root.findViewById(R.id.img);
        Glide.with(requireContext()).load(R.drawable.feedback).into(img);

        return root;
    }
}