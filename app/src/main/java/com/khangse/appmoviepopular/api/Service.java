package com.khangse.appmoviepopular.api;


import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.model.MoviesResponse;
import com.khangse.appmoviepopular.model.ReviewResponse;
import com.khangse.appmoviepopular.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetails> getDetails(@Path("id") int id, @Query("api_key") String apiKey, @Query("append_to_response") String credits);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    //Reviews
    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReview(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
