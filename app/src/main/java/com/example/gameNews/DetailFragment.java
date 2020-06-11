package com.example.gameNews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    //fields
    private TextView title,news;
    private String imageurl;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Return this view later in fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        //signed to each view
        title = view.findViewById(R.id.D_title);
        imageView = view.findViewById(R.id.imageView);
        news = view.findViewById(R.id.NewsText);
        //get passed value from home fragment
        title.setText(getArguments().getString("title"));
        news.setText(getArguments().getString("news"));
        imageurl = getArguments().getString("image");
        //use picasso to load the image and display it
        Picasso.get().load(imageurl).into(imageView);
        return view;
    }
}

