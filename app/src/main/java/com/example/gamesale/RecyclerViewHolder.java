package com.example.gamesale;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    View view;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;

    }

    public void setDetails(Context context,String title,String image){
        TextView mTitle = view.findViewById(R.id.rTitleText);
        ImageView mImage = view.findViewById(R.id.rImageView);

        mTitle.setText(title);
        Picasso.get().load(image).into(mImage);


    }
}
