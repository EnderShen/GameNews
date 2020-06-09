package com.example.gameNews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamesale.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ImageViewHolder> {

    private Context mContext;
    private List<uploadModel> mUploads;

    public CommentsAdapter(Context context, List<uploadModel> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_itme_comments,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        uploadModel uuploadModel = mUploads.get(position);
        holder.textViewTitle.setText(uuploadModel.getmTitle());
        holder.comments.setText(uuploadModel.getmComments());
        String imageuri =uuploadModel.getmImageUri();
        Picasso.get().load(imageuri).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewTitle;
        public TextView comments;

        public ImageViewHolder(View itemview){
            super((itemview));

            textViewTitle = itemview.findViewById(R.id.cctext_comments_title);
            imageView = itemview.findViewById(R.id.ccuploaded_image);
            comments = itemview.findViewById(R.id.ccComments);
        }
    }
}
