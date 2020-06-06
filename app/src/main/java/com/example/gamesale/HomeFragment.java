package com.example.gamesale;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    LinearLayoutManager mlinearlayoutManager;
    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;
    FirebaseRecyclerAdapter<Model,ViewHolder> mfirebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        imageSlipper(v);

        mlinearlayoutManager = new LinearLayoutManager(getActivity());
        mlinearlayoutManager.setReverseLayout(true);
        mlinearlayoutManager.setStackFromEnd(true);

        mrecyclerView = v.findViewById(R.id.Home_RecyclerView);

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mfirebaseDatabase.getReference("data");

        showData();
        return v;
    }

    public void showData(){
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mdatabaseReference,Model.class).build();

        mfirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {

                viewHolder.setDetail(getActivity().getApplicationContext(),model.getTitle(),model.getImage());


            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "122221", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        mrecyclerView.setLayoutManager(mlinearlayoutManager);
        mfirebaseRecyclerAdapter.startListening();
        mrecyclerView.setAdapter(mfirebaseRecyclerAdapter);
    }

    public void onStart(){
        super.onStart();

        if(mfirebaseRecyclerAdapter != null){
            mfirebaseRecyclerAdapter.startListening();
        }
    }


    public void imageSlipper(View v){
        ImageSlider imageSlider =v.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

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
