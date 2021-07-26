package com.delgrade.socialstoryonline;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    String title;

    int[] img_bg = {R.drawable.hygiene, R.drawable.covidstory, R.drawable.c, R.drawable.e };

    String url ="https://stcetcse2021.delgradecorporation.in/ss-v1/assets/images/";

    // data is passed into the constructor
    StoryAdapter(Context context, String[] data, String title) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.title =title;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(mData[position]);
        String str=mData[position].replaceAll("\\s+","").toLowerCase().trim();
        String fURL=url+title.toLowerCase()+"/"+str+"/"+str+".png";
        Picasso.get()
                .load(fURL)
                .resize(100, 100)
                .centerCrop()
                .into(holder.myImageView);
        /*Picasso.get()
                .load(fURL)
                .fit()
                .into(holder.myImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //do smth when picture is loaded successfully
                        Toast.makeText(mInflater.getContext(), position +"is loaded!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Exception ex) {
                        //do smth when there is picture loading error
                        Log.e("myTag", ex.toString());
                    }
                });*/
        Log.e("myTag", url+title.toLowerCase()+"/"+str+"/"+str+".png");
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView);
            myImageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
