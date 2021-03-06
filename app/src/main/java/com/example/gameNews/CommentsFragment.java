package com.example.gameNews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CommentsFragment extends Fragment {

    //fields
    private RecyclerView mRecyclerview;
    private CommentsAdapter mAdapter;
    private DatabaseReference mDatabseref;
    private List<uploadModel> muploads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // return this view later
        View v =inflater.inflate(R.layout.fragment_comments, container, false);

        //Adjust recyclerview
        mRecyclerview = v.findViewById(R.id.Comments_recycler);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Create a new List and contain items
        muploads = new ArrayList<>();

        //set the database reference with correct path on firebase
        mDatabseref = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // for loop to add each items
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    uploadModel mupload = postSnapshot.getValue(uploadModel.class);
                    muploads.add(mupload);
                }
                mAdapter = new CommentsAdapter(getActivity(),muploads);
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


}
