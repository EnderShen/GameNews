package com.example.gameNews;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamesale.R;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClicklistener.onItemClick(view,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mClicklistener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetail(Context context,String title,String image,String news){

        TextView mTitle = mView.findViewById(R.id.rTitleText);
        TextView mNews = mView.findViewById(R.id.rNews);
        ImageView mImage = mView.findViewById(R.id.rImageView);


        mNews.setText(news);
        mTitle.setText(title);
        Picasso.get().load(image).into(mImage);
    }

    private  ViewHolder.ClickListener mClicklistener;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){

        mClicklistener = clickListener;
    }
}
