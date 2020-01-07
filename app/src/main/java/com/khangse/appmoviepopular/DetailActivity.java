package com.khangse.appmoviepopular;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.khangse.appmoviepopular.adapter.DetailPageAdapter;
import com.khangse.appmoviepopular.adapter.TrailerAdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.data.FavoriteContract;
import com.khangse.appmoviepopular.data.FavoriteDbHelper;
import com.khangse.appmoviepopular.fragment.DepthPageTransformer;
import com.khangse.appmoviepopular.model.Credits;
import com.khangse.appmoviepopular.model.Genre;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.model.TrailerResponse;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageBackdrop, imgPlay;
    TextView tvRuntime, tvGenre;
    ProgressBar pbGenre;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    private MovieDetails movieDetails;
    private Movie movie;
    private Credits credits;

    private int movie_id;

    private MultiSnapRecyclerView recyclerView;

    private FavoriteDbHelper favoriteDbHelper;
    private SQLiteDatabase mDb;
    private Movie movie_favorites;

    private final AppCompatActivity activity = DetailActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTabLayout.setupWithViewPager(mViewPager);


        //TODO
        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        imgPlay = (ImageView) findViewById(R.id.iv_play_circle);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayVideo();
            }
        });
        imageBackdrop = (ImageView) findViewById(R.id.image_movie_backdrop);
        tvRuntime = (TextView) findViewById(R.id.tv_runtime);
        tvGenre = (TextView) findViewById(R.id.tv_genre);
        releaseDate = (TextView) findViewById(R.id.tv_release_year);
        nameOfMovie = (TextView) findViewById(R.id.tv_detail_title);
        pbGenre = (ProgressBar) findViewById(R.id.pb_detail_loading_indicator);
        pbGenre.setVisibility(View.VISIBLE);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movies")) {

            movie = getIntent().getParcelableExtra("movies");
            nameOfMovie.setText(movie.getTitle().toString());
            releaseDate.setText((movie.getReleaseDate().equals("")) ? "" : (movie.getReleaseDate().split("-"))[0]);
            movie_id = movie.getId();

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getBackdropPath())
                    .placeholder(R.drawable.load)
                    .into(imageBackdrop);

            LoadDetailMovie();

            //TODO
            initCollapsingToolbar();
        } else {
            Toast.makeText(this, "No Data API", Toast.LENGTH_SHORT).show();
        }

        MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_button);

        if (Exists(nameOfMovie.getText().toString())) {
            favoriteButton.setFavorite(true);
            favoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
                                favoriteDbHelper.deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });


        } else {
            favoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void LoadDetailMovie() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please get your API Key", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);
                Call<MovieDetails> call = apiService.getDetails(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN, "credits");

                call.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                movieDetails = response.body();
                                tvRuntime.setText(movieDetails.getRuntime() + " min");
                                String genres = "";
                                for (Genre genre : movieDetails.getGenres()) {
                                    genres += genre.getGenreName() + ", ";
                                }
                                tvGenre.setText(genres);
                                credits = movieDetails.getCredits();
                                pbGenre.setVisibility(View.GONE);
                                movieDetails.setMovie(movie);
                                setupViewPager();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                    }
                });
            }

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, "unable to fetch data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager() {
        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(this, getSupportFragmentManager(), movieDetails, mViewPager);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(detailPageAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public boolean Exists(String searchItem) {
        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_RELEASE_DATE,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_OVERVIEW,
                FavoriteContract.FavoriteEntry.COLUMN_ORIGINAL_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_COUNT,
                FavoriteContract.FavoriteEntry.COLUMN_VOTE_AVERAGE
        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(" ");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(nameOfMovie.getText());
                    isShow = true;
                } else {
                    if (isShow) {
                        collapsingToolbarLayout.setTitle(" ");
                        isShow = false;
                    }
                }
            }
        });
    }

    //
//    //TODO
//    private void loadReview() {
//        try {
//            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
//                Toast.makeText(getApplicationContext(), "Please get your API Key", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                Client Client = new Client();
//                Service apiService = Client.getClient().create(Service.class);
//                Call<Review> call = apiService.getReview(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
//
//                call.enqueue(new Callback<Review>() {
//                    @Override
//                    public void onResponse(Call<Review> call, Response<Review> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body() != null) {
//                                List<ReviewResult> reviewResults = response.body().getResults();
//                                MultiSnapRecyclerView recyclerView2 = (MultiSnapRecyclerView) findViewById(R.id.review_recyclerview);
//                                LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                                recyclerView2.setLayoutManager(firstManager);
//                                recyclerView2.setAdapter(new ReviewAdapter(getApplicationContext(), reviewResults));
//                                recyclerView2.smoothScrollToPosition(0);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Review> call, Throwable t) {
//
//                    }
//                });
//            }
//
//        } catch (Exception e) {
//            Log.d("Error", e.getMessage());
//            Toast.makeText(this, "unable to fetch data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
    // private void initViews() {
//        trailerList = new ArrayList<>();
//        trailerAdapter = new TrailerAdapter(this, trailerList);
//
//        recyclerView = (MultiSnapRecyclerView) findViewById(R.id.recycler_view1);
//        MultiSnapRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(trailerAdapter);
//        trailerAdapter.notifyDataSetChanged();
//
//        LoadJSON();
//        loadReview();
    //}
//
//    public void LoadJSON(){
//        //int movie_id = getIntent().getExtras().getInt("id");
//
//    }
//
    public void saveFavorite() {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        movie_favorites = new Movie();
        //int movie_id = getIntent().getExtras().getInt("id");

        movie_favorites.setId(movie_id);
        movie_favorites.setTitle(nameOfMovie.getText().toString());
        movie_favorites.setBackdropPath(movie.getBackdropPath());
        movie_favorites.setReleaseDate(movie.getReleaseDate());
        movie_favorites.setPosterPath(movie.getPosterPath());
        movie_favorites.setVoteAverage(movie.getVoteAverage());
        movie_favorites.setOriginalTitle(movie.getOriginalTitle());
        movie_favorites.setOverview(movie.getOverview());
        movie_favorites.setVoteCount(movie.getVoteCount());

        favoriteDbHelper.addFavorite(movie_favorites);
    }

    public void deleteFavorite(int id) {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        favoriteDbHelper.deleteFavorite(id);
    }

    public void PlayVideo() {
        Client Client = new Client();
        Service apiService = Client.getClient().create(Service.class);
        Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Trailer> trailer = response.body().getResults();
                        if (trailer.size() > 0) {
                            Intent intent = new Intent(DetailActivity.this, PlayVideoActivity.class);
                            intent.putExtra("keyVideo", trailer.get(0).getmKey());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Don't have video to play!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error fetching trailer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
