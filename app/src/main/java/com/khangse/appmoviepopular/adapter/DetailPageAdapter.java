package com.khangse.appmoviepopular.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.khangse.appmoviepopular.fragment.CastFragment;
import com.khangse.appmoviepopular.fragment.InformationFragment;
import com.khangse.appmoviepopular.fragment.ReviewFragment;
import com.khangse.appmoviepopular.fragment.TrailerFragment;
import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.utils.Constant;

import static com.khangse.appmoviepopular.utils.Constant.CAST;
import static com.khangse.appmoviepopular.utils.Constant.INFORMATION;
import static com.khangse.appmoviepopular.utils.Constant.REVIEWS;
import static com.khangse.appmoviepopular.utils.Constant.TAP_TITLE;
import static com.khangse.appmoviepopular.utils.Constant.TRAILERS;

public class DetailPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private MovieDetails movie;
    private ViewPager mViewPager;

    public DetailPageAdapter(Context context, FragmentManager fm, MovieDetails _movie, ViewPager viewPager){
        super(fm);
        mContext = context;
        movie = _movie;
        mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movies", movie);
        switch (position) {
            case INFORMATION:
                return InformationFragment.newInstance(position, TAP_TITLE[position].toUpperCase(), bundle, mViewPager);
            case TRAILERS:
                return TrailerFragment.newInstance(position, TAP_TITLE[position].toUpperCase(), bundle);
            case CAST:
                return CastFragment.newInstance(position, TAP_TITLE[position].toUpperCase(), bundle);
            case REVIEWS:
                return ReviewFragment.newInstance(position, TAP_TITLE[position].toUpperCase(), bundle);
        }
        return null;
    }

    @Override
    public int getCount() {
        return Constant.PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAP_TITLE[position % Constant.PAGE_COUNT].toUpperCase();
    }
}
