package com.example.gamesale;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private StorageReference storageReference;
    private RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference rcyclerReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = v.findViewById(R.id.Home_RecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseDatabase = firebaseDatabase.getInstance();
        rcyclerReference = firebaseDatabase.getReference("data");
        imageSlipper(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<MemberClass, RecyclerViewHolder>firebaseRecyclerAdapter =
         new FirebaseRecyclerAdapter<MemberClass, RecyclerViewHolder>(
                 MemberClass.class,
                 R.layout.recyclerlayout,
                 RecyclerViewHolder.class,
                 rcyclerReference
         ) {
             @Override
             protected void populateViewHolder(RecyclerViewHolder viewHolder, MemberClass memberClass, int i) {
                 viewHolder.setDetails(getActivity().getApplicationContext(),memberClass.getTitle(),memberClass.getImage());
             }
         };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public void imageSlipper(View v){
        ImageSlider imageSlider =v.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference().child("flipperImages/watchdog.jpg");

        try {
            final File localFile = File.createTempFile("watchdog","jpg");

            slideModels.add(new SlideModel(R.drawable.watchdog));
        } catch (IOException e) {
            e.printStackTrace();
        }

        slideModels.add(new SlideModel("https://p.ecopetit.cat/wpic/lpic/26-263518_tumblr-photography-wallpaper-rocks-on-earth-background.jpg","1 Image"));
        slideModels.add(new SlideModel("https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg","2 Image"));
        slideModels.add(new SlideModel("https://live.staticflickr.com/7006/6621416427_8504865e6a_z.jpg","3 Image"));
        slideModels.add(new SlideModel("https://c4.wallpaperflare.com/wallpaper/662/618/496/natur-2560x1600-sceneries-wallpaper-preview.jpg","4 Image"));
        imageSlider.setImageList(slideModels,true);
    }
}
