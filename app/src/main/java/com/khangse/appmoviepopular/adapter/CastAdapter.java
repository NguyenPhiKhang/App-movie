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
import com.khangse.appmoviepopular.model.Cast;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.utils.Constant;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {
    private Context mContext;
    private List<Cast> mCastList;

    public CastAdapter(Context mContext, List<Cast> castList){
        this.mContext = mContext;
        this.mCastList = castList;

    }

    @NonNull
    @Override
    public CastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.MyViewHolder holder, int position) {
        holder.tvCastName.setText(mCastList.get(position).getName());
        holder.tvCastCharacter.setText(mCastList.get(position).getCharacter());
        Glide.with(mContext)
                .load(Constant.IMAGE_BASE_URL+Constant.BACKDROP_FILE_SIZE+mCastList.get(position).getProfilePath())
                .placeholder(R.drawable.person)
                .into(holder.ivCast);
    }

    @Override
    public int getItemCount() {
        return mCastList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvCastName, tvCastCharacter;
        public ImageView ivCast;

        public MyViewHolder(View view){
            super(view);

            tvCastName = (TextView)view.findViewById(R.id.tv_cast_name);
            tvCastCharacter=(TextView)view.findViewById(R.id.tv_cast_character);
            ivCast = (ImageView)view.findViewById(R.id.iv_cast);
        }
    }
}
