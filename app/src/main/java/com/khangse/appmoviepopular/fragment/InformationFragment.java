package com.khangse.appmoviepopular.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.model.Cast;
import com.khangse.appmoviepopular.model.Crew;
import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.utils.FormatUtils;

import org.w3c.dom.Text;

import static com.khangse.appmoviepopular.utils.Constant.CAST;

public class InformationFragment extends Fragment {
    Context mContext;
    private TextView tvOverview, tvRated, tvVoteCount, tvDirector, tvCast, tvOriginalTitle,
            tvReleseDate, tvStatus, tvBudget, tvRevenue, tvViewAll;
    private MovieDetails movie;
    private ViewPager mViewPager;

    public InformationFragment(ViewPager viewPager) {
        mViewPager=viewPager;
    }

    public static InformationFragment newInstance(int page, String title, Bundle bundle, ViewPager viewPager) {
        InformationFragment infoFragment = new InformationFragment(viewPager);
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        infoFragment.setArguments(args);
        infoFragment.setArguments(bundle);
        return infoFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        tvOverview = (TextView)rootView.findViewById(R.id.tv_overview);
        tvRated =(TextView)rootView.findViewById(R.id.tv_vote_average);
        tvVoteCount =(TextView)rootView.findViewById(R.id.tv_vote_count);
        tvDirector =(TextView)rootView.findViewById(R.id.tv_director);
        tvCast =(TextView)rootView.findViewById(R.id.tv_cast);
        tvOriginalTitle=(TextView)rootView.findViewById(R.id.tv_original_title);
        tvReleseDate=(TextView)rootView.findViewById(R.id.tv_release_date);
        tvStatus =(TextView)rootView.findViewById(R.id.tv_status);
        tvBudget=(TextView)rootView.findViewById(R.id.tv_budget);
        tvRevenue=(TextView)rootView.findViewById(R.id.tv_revenue);
        tvViewAll=(TextView)rootView.findViewById(R.id.tv_view_all);
        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(CAST);
            }
        });

        String list_cast="";

        if(getArguments()!=null){
           movie = getArguments().getParcelable("movies");
           tvOverview.setText(movie.getMovie().getOverview());
           tvRated.setText(String.format("%.1f", movie.getMovie().getVoteAverage()));
           tvVoteCount.setText(FormatUtils.formatNumber(movie.getMovie().getVoteCount()));
           for(Crew crew: movie.getCredits().getCrew()){
               if(crew.getJob().equals(getString(R.string.director))){
                   tvDirector.setText(crew.getName());
                   break;
               }
           }
           for(Cast cast: movie.getCredits().getCast()){
               list_cast+=cast.getName()+", ";
           }
           tvCast.setText(list_cast);
           tvOriginalTitle.setText(movie.getMovie().getOriginalTitle());
           tvReleseDate.setText((movie.getMovie().getReleaseDate().equals(""))?"":FormatUtils.formatDate(movie.getMovie().getReleaseDate()));
           tvStatus.setText(movie.getStatus());
           tvBudget.setText(FormatUtils.formatCurrency(movie.getBudget()));
           tvRevenue.setText(FormatUtils.formatCurrency(movie.getRevenue()));
        }

        return rootView;
    }
}
