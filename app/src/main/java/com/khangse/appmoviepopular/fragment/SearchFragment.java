package com.khangse.appmoviepopular.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.BuildConfig;
import com.khangse.appmoviepopular.ListMoviesActivity;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.MoviesApdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.MoviesResponse;
import com.khangse.appmoviepopular.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private Context mContext;
    private RecyclerView recyclerView;
    private MoviesApdapter moviesApdapter;
    private List<Movie> movieList;
    private TextView tvNoFound;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_search);
        tvNoFound = (TextView) view.findViewById(R.id.tv_no_find);

        movieList = new ArrayList<>();
        moviesApdapter = new MoviesApdapter(mContext, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesApdapter);
        moviesApdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.menuItem_Search);
        menuItem.expandActionView();
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LoadJSON(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void LoadJSON(String query) {
        try {
            if (Constant.API_KEY.isEmpty()) {
                Toast.makeText(mContext, "Please obtain API key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }

            Client _client = new Client();
            Service apiService = _client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getSearchMovie(Constant.API_KEY, query);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    if (movies.size() > 0) {
                        recyclerView.setAdapter(new MoviesApdapter(mContext, movies));
                        recyclerView.smoothScrollToPosition(0);
                        tvNoFound.setVisibility(View.INVISIBLE);
                    } else {
                        tvNoFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(mContext, "Error fetching data!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
