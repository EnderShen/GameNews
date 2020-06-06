package com.example.gamesale;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mview;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

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

    public void setDetail(Context context,String title,String image){

        TextView mtitle = mview.findViewById(R.id.rTitleText);
        ImageView mimage = mview.findViewById(R.id.rImageView);

        mtitle.setText(title);

        Picasso.get().load(image).into(mimage);
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
