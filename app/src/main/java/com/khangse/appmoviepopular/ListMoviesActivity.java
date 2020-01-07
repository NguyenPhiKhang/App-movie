package com.khangse.appmoviepopular;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.adapter.MoviesApdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.MoviesResponse;
import com.khangse.appmoviepopular.utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMoviesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private String nameList;
    private ProgressBar progressBar;
    private List<Movie> movieList;
    private MoviesApdapter moviesApdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);

        toolbar = (Toolbar)findViewById(R.id.toolbar_list_movies);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_movies);
        progressBar = (ProgressBar)findViewById(R.id.pb_list_movies);

        progressBar.setVisibility(View.VISIBLE);

        nameList = getIntent().getExtras().getString("title");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nameList);
        toolbar.setBackgroundResource(R.color.md_grey_900);

        movieList = new ArrayList<>();
        moviesApdapter = new MoviesApdapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesApdapter);
        moviesApdapter.notifyDataSetChanged();


        LoadJson();
    }

    private void LoadJson() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(ListMoviesActivity.this, "Please obtain API key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }

            Client _client = new Client();
            Service apiService = _client.getClient().create(Service.class);
            Call<MoviesResponse> call;
            if(nameList.equals("Best Popular"))
                call = apiService.getPopularMovies(Constant.API_KEY);
            else call = apiService.getTopRatedMovies(Constant.API_KEY);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesApdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(ListMoviesActivity.this, "Error fetching data!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            Toast.makeText(ListMoviesActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
