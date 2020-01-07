package com.khangse.appmoviepopular.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.BuildConfig;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.ReviewAdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.model.Review;
import com.khangse.appmoviepopular.model.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {
    Context mContext;
    private RecyclerView recyclerView;
    private MovieDetails movieDetails;
    private List<Review> reviewList;
    private ReviewAdapter reviewAdapter;
    private LinearLayout linearLayout;

    public ReviewFragment() {
    }

    public static ReviewFragment newInstance(int page, String title, Bundle bundle) {
        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        reviewFragment.setArguments(args);
        reviewFragment.setArguments(bundle);
        return reviewFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_review);
        linearLayout =(LinearLayout)rootView.findViewById(R.id.view_no_reviews) ;

        movieDetails = getArguments().getParcelable("movies");
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(mContext, reviewList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();

        LoadJson();

        return rootView;
    }

    private void LoadJson() {
        int movie_id = movieDetails.getMovie().getId();
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(mContext, "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<ReviewResponse> call = apiService.getReview(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Review> reviews = response.body().getReviewResults();
                            if (reviews.size() > 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(new ReviewAdapter(mContext, reviews));
                                recyclerView.smoothScrollToPosition(0);
                            } else {
                                recyclerView.setVisibility(View.INVISIBLE);
                                linearLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(mContext, "Error fetching trailer", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
