package com.khangse.appmoviepopular.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.khangse.appmoviepopular.DetailActivity;
import com.khangse.appmoviepopular.ListMoviesActivity;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.MoviesApdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.MoviesResponse;
import com.khangse.appmoviepopular.utils.Constant;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    Context mContext;
    CarouselView customCarouselView;
    private ProgressBar progressBar_Popular;
    private ProgressBar progressBar_Toprated;
    private MultiSnapRecyclerView recyclerViewPopular;
    private MultiSnapRecyclerView recyclerViewTopRated;
    private MoviesApdapter adapterPopular;
    private MoviesApdapter adapterTopRated;
    private List<Movie> moviePopularList;
    private List<Movie> movieTopRatedList;
    private List<Movie> movieNowPlayingList;
    private ImageView imgPopular;
    private ImageView imgTopRated;
    ProgressDialog pd;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        customCarouselView = (CarouselView) view.findViewById(R.id.customCarouselView);
        recyclerViewPopular = (MultiSnapRecyclerView) view.findViewById(R.id.recyclerview_popular);
        recyclerViewTopRated = (MultiSnapRecyclerView) view.findViewById(R.id.rv_top_rated);

        progressBar_Popular = (ProgressBar)view.findViewById(R.id.probar_popular);
        progressBar_Popular.setVisibility(View.VISIBLE);
        progressBar_Toprated=(ProgressBar)view.findViewById(R.id.probar_toprated);
        progressBar_Toprated.setVisibility(View.VISIBLE);

        imgPopular = (ImageView)view.findViewById(R.id.btn_popular_all);
        imgTopRated = (ImageView)view.findViewById(R.id.btn_toprated_all);
        imgPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoListMovies("Best Popular");
            }
        });
        imgTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoListMovies("Top Rated");
            }
        });

        moviePopularList = new ArrayList<>();
        movieTopRatedList = new ArrayList<>();
        movieNowPlayingList = new ArrayList<>();
        adapterPopular = new MoviesApdapter(mContext, moviePopularList);
        adapterTopRated = new MoviesApdapter(mContext, movieTopRatedList);

        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        //recyclerViewPopular.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPopular.setAdapter(adapterPopular);
        adapterPopular.notifyDataSetChanged();

        //recyclerViewTopRated.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTopRated.setAdapter(adapterTopRated);
        adapterTopRated.notifyDataSetChanged();

        customCarouselView.setPageCount(0);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);

        LoadJson();

        return view;
    }

    private void gotoListMovies(String nameList){
        Intent intent = new Intent(mContext, ListMoviesActivity.class);
        intent.putExtra("title", nameList);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        Toast.makeText(mContext, "You clicked category: "+ nameList, Toast.LENGTH_SHORT).show();
    }

    private void LoadJson() {
        try {
            if (Constant.API_KEY.isEmpty()) {
                Toast.makeText(mContext, "Please obtain API key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            Client _client = new Client();
//            Cache cache = new Cache(getCacheDir(), cacheSize);
//
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .cache(cache)
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public okhttp3.Response intercept(Interceptor.Chain chain)
//                                throws IOException {
//                            Request request = chain.request();
//                            if (!isNetworkAvailable()) {
//                                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
//                                request = request
//                                        .newBuilder()
//                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                        .build();
//                            }
//                            return chain.proceed(request);
//                        }
//                    })
//                    .build();
//
//            Retrofit.Builder builder = new Retrofit.Builder()
//                    .baseUrl("http://api.themoviedb.org/3/")
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//            Retrofit retrofit = builder.build();
            Service apiService = _client.getClient().create(Service.class);
            Call<MoviesResponse> callPopular = apiService.getPopularMovies(Constant.API_KEY);
            callPopular.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    moviePopularList.clear();
                    moviePopularList.addAll(movies);
                    //Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL);
                    recyclerViewPopular.setAdapter(new MoviesApdapter(mContext, moviePopularList));
                    //recyclerViewPopular.smoothScrollToPosition(0);
                    adapterPopular.notifyDataSetChanged();
                    //pd.dismiss();
                    progressBar_Popular.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(mContext, "Error fetching data Popular!", Toast.LENGTH_SHORT).show();
                }
            });

            Call<MoviesResponse> callTopRated = apiService.getTopRatedMovies(Constant.API_KEY);
            callTopRated.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    movieTopRatedList.clear();
                    movieTopRatedList.addAll(movies);
                    //Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL);
                    recyclerViewTopRated.setAdapter(new MoviesApdapter(mContext, movieTopRatedList));
                    //recyclerViewTopRated.smoothScrollToPosition(0);
                    adapterTopRated.notifyDataSetChanged();
                    //pd.dismiss();
                    progressBar_Popular.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(mContext, "Error fetching data Top Rated!", Toast.LENGTH_SHORT).show();
                }
            });

            Call<MoviesResponse> callNowplaying = apiService.getNowPlayingMovies(Constant.API_KEY);
            callNowplaying.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL);
                    movieNowPlayingList.clear();
                    movieNowPlayingList.addAll(movies);
                    if (movieNowPlayingList.size() > 0)
                    {
                        customCarouselView.setPageCount(6);
                        customCarouselView.setSlideInterval(4000);
                        customCarouselView.setViewListener(viewListener);
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(mContext, "Error fetching data Top Rated!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.slide_item, null);

            TextView labelTextView = (TextView) customView.findViewById(R.id.slide_title);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.slide_img);

            if(movieNowPlayingList.size()>0) {

                String backdropPath = "https://image.tmdb.org/t/p/w500" + movieNowPlayingList.get(position).getBackdropPath();

                Glide.with(mContext)
                        .load(backdropPath)
                        .placeholder(R.drawable.load)
                        .into(fruitImageView);

                //fruitImageView.setImageResource(sampleImages[position]);
                labelTextView.setText(movieNowPlayingList.get(position).getTitle());

                customView.setOnClickListener(clickCarousel);
            }

            return customView;
        }
    };

    View.OnClickListener clickCarousel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = customCarouselView.getCurrentItem();
            Movie clickedDataItem = movieNowPlayingList.get(pos);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("movies", clickedDataItem);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            Toast.makeText(mContext, "Click Image = " + movieNowPlayingList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

}
