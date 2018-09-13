package com.excel.livechannels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.List;

public class PlayVideoActivity extends YouTubeBaseActivity {

    final static String TAG = "PlayVideoActivity";
    Context context = this;
    YouTubePlayerView youtubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    YouTubePlayer.PlaybackEventListener mOnPlaybackEventListener;
    YouTubePlayer.PlayerStateChangeListener mOnPlayerStateChangeListener;
    YouTubePlayer mainPlayer;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_play_video );

        youtubePlayerView = (YouTubePlayerView) findViewById( R.id.playVideo );
        Intent in = getIntent();
        final String videoID = in.getStringExtra( "videoID" );


        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                this, YouTubeConfig.getApiKey(), videoID, 0, true, false );

        mOnPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {

            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onStopped() {
                Log.e( TAG, "Video stopped !" );

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        };

        mOnPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener(){

            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {
                Log.e( TAG, "Video ended !" );
                finish();
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess( YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b ) {

                Log.i( TAG, "onInitializationSuccess()" );
                youTubePlayer.setPlaybackEventListener( mOnPlaybackEventListener );
                youTubePlayer.setPlayerStateChangeListener( mOnPlayerStateChangeListener );
                youTubePlayer.setFullscreen( true );
                youTubePlayer.loadVideo( videoID );
                //youTubePlayer.play();
                //youTubePlayer.
            }

            @Override
            public void onInitializationFailure( YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult ) {
            }

        };

        youtubePlayerView.initialize( YouTubeConfig.getApiKey(), mOnInitializedListener );
        //youtubePlayerView.
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d( TAG, "onPause()" );
        finish();
    }
}
