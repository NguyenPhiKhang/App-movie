package com.khangse.appmoviepopular.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.CastAdapter;
import com.khangse.appmoviepopular.model.Cast;
import com.khangse.appmoviepopular.model.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class CastFragment extends Fragment {
    Context mContext;
    private RecyclerView recyclerView;
    private List<Cast> castList;
    private CastAdapter castAdapter;
    private MovieDetails mMovieDetails;

    public CastFragment(){}

    public static CastFragment newInstance(int page, String title, Bundle movieDetails) {
        CastFragment castFragment = new CastFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        castFragment.setArguments(args);
        castFragment.setArguments(movieDetails);
        return castFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cast, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_cast);

        mMovieDetails = getArguments().getParcelable("movies");
        castList = new ArrayList<>(mMovieDetails.getCredits().getCast());
        castAdapter = new CastAdapter(mContext, castList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(castAdapter);
        recyclerView.smoothScrollToPosition(0);
        castAdapter.notifyDataSetChanged();


        return rootView;
    }
}
