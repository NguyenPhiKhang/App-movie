package com.khangse.appmoviepopular;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.model.Movie;
import com.khangse.appmoviepopular.model.Review;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.model.TrailerResponse;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;

    private MultiSnapRecyclerView recyclerView;
    //private TrailerAdapter trailerAdapter;
    //private List<Trailer> trailerList;

    //private FavoriteDbHelper favoriteDbHelper;
//    private Movie favorite;
//    private final AppCompatActivity activity = DetailActivity.this;
//    Movie movie;
//    String thumbnail, movieName, synopsis, rating, dateOfRelease;
//    int movie_id;
//
//    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initCollapsingToolbar();
//
//        //TODO
//        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
//        mDb = dbHelper.getWritableDatabase();
//
//        imageView= (ImageView)findViewById(R.id.thumbnail_image_header);
//        //nameOfMovie =(TextView)findViewById(R.id.title);
//        plotSynopsis = (TextView)findViewById(R.id.plotsynopsis);
//        userRating = (TextView)findViewById(R.id.userrating);
//        releaseDate =(TextView)findViewById(R.id.releasedate);
//
//        Intent intentThatStartedThisActivity = getIntent();
//        if (intentThatStartedThisActivity.hasExtra("movies")){
//
//            movie = getIntent().getParcelableExtra("movies");
//
//            thumbnail = movie.getPosterPath();
//            movieName = movie.getOriginalTitle();
//            synopsis = movie.getOverview();
//            rating = Double.toString(movie.getVoteAverage());
//            dateOfRelease = movie.getReleaseDate();
//            movie_id = movie.getId();
////            String thumbnail = "https://image.tmdb.org/t/p/w500"+getIntent().getExtras().getString("poster_path");
////            String movieName = getIntent().getExtras().getString("original_title");
////            String synopsis = getIntent().getExtras().getString("overview");
////            Double rating = getIntent().getExtras().getDouble("vote_average");
////            String releaseOfDate = getIntent().getExtras().getString("release_date");
//
//            Glide.with(this)
//                    .load("https://image.tmdb.org/t/p/w500"+thumbnail)
//                    .placeholder(R.drawable.load)
//                    .into(imageView);
//
//            //nameOfMovie.setText(movieName);
//            plotSynopsis.setText(synopsis);
//            userRating.setText(rating);
//            releaseDate.setText(dateOfRelease);
//
//            //TODO
//            ((CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar)).setTitle(movieName);
//        }
//        else{
//            Toast.makeText(this, "No Data API", Toast.LENGTH_SHORT).show();
//        }
//
//        MaterialFavoriteButton favoriteButton =(MaterialFavoriteButton)findViewById(R.id.favorite_button);
//
////        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////
////        favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
////            @Override
////            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
////                SharedPreferences.Editor editor = getSharedPreferences("com.khangse.movieapp.DetailActivity", MODE_PRIVATE).edit();
////                if(favorite){
////                    editor.putBoolean("Favorite Added", true);
////                    editor.commit();
////                    saveFavorite();
////                    Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
////                }else{
////                    deleteFavorite(movie_id);
////                    editor.putBoolean("Favorite Removed", true);
////                    editor.commit();
////                    Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
////                }
////            }
////        });
//
//
//        if (Exists(movieName)){
//            favoriteButton.setFavorite(true);
//            favoriteButton.setOnFavoriteChangeListener(
//                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
//                        @Override
//                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
//                            if (favorite == true) {
//                                saveFavorite();
//                                Snackbar.make(buttonView, "Added to Favorite",
//                                        Snackbar.LENGTH_SHORT).show();
//                            } else {
//                                favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
//                                favoriteDbHelper.deleteFavorite(movie_id);
//                                Snackbar.make(buttonView, "Removed from Favorite",
//                                        Snackbar.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//
//        }else {
//            favoriteButton.setOnFavoriteChangeListener(
//                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
//                        @Override
//                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
//                            if (favorite == true) {
//                                saveFavorite();
//                                Snackbar.make(buttonView, "Added to Favorite",
//                                        Snackbar.LENGTH_SHORT).show();
//                            } else {
//                               deleteFavorite(movie_id);
//                                Snackbar.make(buttonView, "Removed from Favorite",
//                                        Snackbar.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//
//        }



        //initViews();
    }


//    public boolean Exists(String searchItem) {
//
//        String[] projection = {
//                FavoriteContract.FavoriteEntry._ID,
//                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
//                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
//                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
//                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
//                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS
//
//        };
//        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
//        String[] selectionArgs = { searchItem };
//        String limit = "1";
//
//        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
//        boolean exists = (cursor.getCount() > 0);
//        cursor.close();
//        return exists;
//    }
//
////    private void initCollapsingToolbar(){
////        final CollapsingToolbarLayout collapsingToolbarLayout=
////                (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
////        collapsingToolbarLayout.setTitle(" ");
////
////        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
////        appBarLayout.setExpanded(true);
////
////        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
////            boolean isShow = false;
////            int scrollRange = -1;
////
////            @Override
////            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
////                if(scrollRange==-1){
////                    scrollRange = appBarLayout.getTotalScrollRange();
////                }
////                if(scrollRange+verticalOffset==0){
////                    collapsingToolbarLayout.setTitle(getString(R.string.details_movie));
////                    isShow = true;
////                }else{
////                    if(isShow){
////                        collapsingToolbarLayout.setTitle(" ");
////                        isShow = false;
////                    }
////                }
////            }
////        });
////    }
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
//        private void initViews(){
//        trailerList = new ArrayList<>();
//        trailerAdapter = new TrailerAdapter(this, trailerList);
//
//        recyclerView = (MultiSnapRecyclerView)findViewById(R.id.recycler_view1);
//        MultiSnapRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(trailerAdapter);
//        trailerAdapter.notifyDataSetChanged();
//
//        LoadJSON();
//        loadReview();
//    }
//
//    public void LoadJSON(){
//        //int movie_id = getIntent().getExtras().getInt("id");
//
//        try{
//            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
//                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Client Client = new Client();
//            Service apiService = Client.getClient().create(Service.class);
//            Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
//            call.enqueue(new Callback<TrailerResponse>() {
//                @Override
//                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            List<Trailer> trailer = response.body().getResults();
//                            recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailer));
//                            recyclerView.smoothScrollToPosition(0);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TrailerResponse> call, Throwable t) {
//                    Log.d("Error", t.getMessage());
//                    Toast.makeText(DetailActivity.this, "Error fetching trailer", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }catch(Exception e){
//            Log.d("Error", e.getMessage());
//            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void saveFavorite(){
//        favoriteDbHelper = new FavoriteDbHelper(activity);
//        favorite = new Movie();
//        //int movie_id = getIntent().getExtras().getInt("id");
//
//        favorite.setId(movie_id);
//        favorite.setOriginalTitle(movieName);
//        favorite.setPosterPath(thumbnail);
//        favorite.setOverview(plotSynopsis.getText().toString().trim());
//        favorite.setVoteAverage(Double.parseDouble(userRating.getText().toString().trim()));
//
//        favoriteDbHelper.addFavorite(favorite);
//    }
//
//    public void deleteFavorite(int id){
//        favoriteDbHelper = new FavoriteDbHelper(activity);
//        favoriteDbHelper.deleteFavorite(id);
//    }
}
