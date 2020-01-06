package com.khangse.appmoviepopular.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.utils.Constant;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {
    private Context mContext;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context mContext, List<Trailer> trailerList){
        this.mContext = mContext;
        this.trailerList = trailerList;

    }

    @NonNull
    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.MyViewHolder holder, int position) {
        holder.nameTrailer.setText(trailerList.get(position).getmName());
        Glide.with(mContext)
                .load(Constant.YOUTUBE_THUMBNAIL_BASE_URL+trailerList.get(position).getmKey()+ Constant.YOUTUBE_THUMBNAIL_URL_JPG)
                .placeholder(R.drawable.loading_video)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTrailer;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);

            nameTrailer = (TextView)view.findViewById(R.id.tv_trailer_name);
            thumbnail = (ImageView)view.findViewById(R.id.iv_trailer_thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        Trailer clickDataItem = trailerList.get(pos);
                        String videoId = clickDataItem.getmKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.YOUTUBE_BASE_URL+videoId));
                        intent.putExtra("VIDEO_ID", videoId);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You click "+ clickDataItem.getmName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
