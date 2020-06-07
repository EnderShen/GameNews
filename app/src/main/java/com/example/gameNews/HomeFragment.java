package com.example.gameNews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.gamesale.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private DatabaseReference reference;

    private FirebaseRecyclerAdapter<RecyclerViewModel,ViewHolder> mFireBaseRecyclerAdapter;
    private FirebaseRecyclerOptions<RecyclerViewModel> options;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = v.findViewById(R.id.Home_RecyclerView);

        FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
        reference = mfirebaseDatabase.getReference("data");


        showRecyclerViewData();
        imageSlipper(v);
        return v;
    }

    public void showRecyclerViewData(){
        options = new FirebaseRecyclerOptions.Builder<RecyclerViewModel>().setQuery(reference, RecyclerViewModel.class).build();

        mFireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<RecyclerViewModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull RecyclerViewModel model) {
                viewHolder.setDetail(getActivity().getApplicationContext(),model.getTitle(),model.getImage(),model.getNews());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle detail = new Bundle();
                        detail.putString("title",mFireBaseRecyclerAdapter.getItem(position).getTitle());
                        detail.putString("image",mFireBaseRecyclerAdapter.getItem(position).getImage());
                        detail.putString("news",mFireBaseRecyclerAdapter.getItem(position).getNews());
                        detailFragment.setArguments(detail);
                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,detailFragment).addToBackStack(null).commit();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "122221", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFireBaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(mFireBaseRecyclerAdapter);
    }
    public void onStart(){
        super.onStart();

        if(mFireBaseRecyclerAdapter != null){
            mFireBaseRecyclerAdapter.startListening();
        }
    }
    public void onStop(){
        super.onStop();
        mFireBaseRecyclerAdapter.stopListening();
    }

    private void imageSlipper(View v){
        ImageSlider imageSlider =v.findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.ign,"IGN: #1 destination for all video game news"));
        slideModels.add(new SlideModel(R.drawable.game,"See game release dates"));
        slideModels.add(new SlideModel(R.drawable.watchdog));

        imageSlider.setImageList(slideModels,true);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                if(i==0){
                    Intent Browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ign.com/au"));
                    startActivity(Browser);
                }
                if(i==1){
                    Intent Browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mcvuk.com/game-release-dates/"));
                    startActivity(Browser);
                }
            }
        });
    }

}
