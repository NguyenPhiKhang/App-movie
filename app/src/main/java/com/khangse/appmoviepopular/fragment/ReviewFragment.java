package com.khangse.appmoviepopular.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.khangse.appmoviepopular.R;

public class ReviewFragment extends Fragment {
    Context mContext;

    public ReviewFragment(){}

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
        return rootView;
    }
}
