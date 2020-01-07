package com.khangse.appmoviepopular;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY = "AIzaSyCcOyWQSopvDoCn14pPCpy8Icv-PM03qeM";

    YouTubePlayerView youTubePlayerView;

    String keyVideo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.myoututbe);

        youTubePlayerView.initialize(API_KEY, PlayVideoActivity.this);

        keyVideo = getIntent().getExtras().getString("keyVideo");

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.cueVideo(keyVideo);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this, 123);
        }
        else{
            Toast.makeText(this, "Errrorrrr", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==123){
            youTubePlayerView.initialize(API_KEY,PlayVideoActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
