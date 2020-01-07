package com.khangse.appmoviepopular.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.model.Review;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.utils.Constant;

import org.w3c.dom.Text;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Review> reviewList;

    String[] arrColor={
            "#FFC107", "#A3A3A3", "#607d8b", "#E53935","#9C27B0","#1E88E5","#00ACC1","#009688","#43A047"};

    public ReviewAdapter(Context mContext, List<Review> reviews){
        this.mContext = mContext;
        this.reviewList = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        holder.tvAuthor.setText(reviewList.get(position).getAuthor());
        holder.tvContent.setText(reviewList.get(position).getContent());
        int i = (int)(Math.random()*arrColor.length);
        holder.viewAvatar.setBackgroundColor(Color.parseColor(arrColor[i]));
        holder.tvAvatar.setText((reviewList.get(position).getAuthor().charAt(0)+"").toUpperCase());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvAuthor, tvContent, tvAvatar;
        public LinearLayout viewAvatar;

        public MyViewHolder(View view){
            super(view);

            tvAuthor = (TextView)view.findViewById(R.id.text_author);
            tvContent = (TextView)view.findViewById(R.id.text_content);
            tvAvatar =(TextView)view.findViewById(R.id.tv_Avatar);
            viewAvatar=(LinearLayout)view.findViewById(R.id.view_Avatar);
        }
    }
}
