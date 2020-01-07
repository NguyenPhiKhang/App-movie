package com.khangse.appmoviepopular.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.MoviesApdapter;
import com.khangse.appmoviepopular.data.FavoriteDbHelper;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private TextView textView;
    private RecyclerView recyclerView;
    private List<Movie> movieList;
    private MoviesApdapter adapter;
    private Context mContext;
    private FavoriteDbHelper favoriteDbHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_list_favorites, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_favorite);

        movieList = new ArrayList<>();

        adapter = new MoviesApdapter(mContext, movieList);

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        favoriteDbHelper = new FavoriteDbHelper(mContext);

        getAllFavorite();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllFavorite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                movieList.clear();
                movieList.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
                //pd.dismiss();
            }
        }.execute();
    }
}
