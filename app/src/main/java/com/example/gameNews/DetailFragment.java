package com.example.gameNews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gamesale.R;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    TextView title,news;
    String imageurl;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);


        title = view.findViewById(R.id.D_title);
        imageView = view.findViewById(R.id.imageView);
        news = view.findViewById(R.id.NewsText);

        title.setText(getArguments().getString("title"));
        imageurl = getArguments().getString("image");
        news.setText(getArguments().getString("news"));
        Picasso.get().load(imageurl).into(imageView);
        return view;
    }
}

