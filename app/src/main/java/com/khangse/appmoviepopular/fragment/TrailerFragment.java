package com.khangse.appmoviepopular.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khangse.appmoviepopular.BuildConfig;
import com.khangse.appmoviepopular.R;
import com.khangse.appmoviepopular.adapter.TrailerAdapter;
import com.khangse.appmoviepopular.api.Client;
import com.khangse.appmoviepopular.api.Service;
import com.khangse.appmoviepopular.model.MovieDetails;
import com.khangse.appmoviepopular.model.Trailer;
import com.khangse.appmoviepopular.model.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerFragment extends Fragment {
    Context mContext;
    private RecyclerView recyclerView;
    private MovieDetails mMovieDetails;
    private List<Trailer> trailerList;
    private TrailerAdapter trailerAdapter;

    public TrailerFragment() {
    }

    public static TrailerFragment newInstance(int page, String title, Bundle movie) {
        TrailerFragment trailerFragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        trailerFragment.setArguments(args);
        trailerFragment.setArguments(movie);
        return trailerFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);

        mMovieDetails = getArguments().getParcelable("movies");

        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_trailer);
        trailerList=new ArrayList<>();
        trailerAdapter = new TrailerAdapter(mContext, trailerList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(trailerAdapter);
        trailerAdapter.notifyDataSetChanged();


        LoadJson();
        return rootView;
    }

    private void LoadJson() {
        int movie_id = mMovieDetails.getMovie().getId();
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(mContext, "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Trailer> trailer = response.body().getResults();
                            recyclerView.setAdapter(new TrailerAdapter(mContext, trailer));
                            recyclerView.smoothScrollToPosition(0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
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
