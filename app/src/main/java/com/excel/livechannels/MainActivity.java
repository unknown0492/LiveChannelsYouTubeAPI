package com.excel.livechannels;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView youtubePlayerView;
    Button playVideo;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    YouTubePlayer.PlaybackEventListener mOnPlaybackEventListener;
    Context context = this;
    ImageView iv1, iv2, iv3;
    private static final int REQ_START_STANDALONE_PLAYER = 1;
    final static String TAG = "MainActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //youtubePlayerView = (YouTubePlayerView) findViewById( R.id.youTubeVideo );
        //playVideo = (Button) findViewById( R.id.playVideo );
        iv1 = (ImageView) findViewById( R.id.iv1 );
        iv2 = (ImageView) findViewById( R.id.iv2 );
        iv3 = (ImageView) findViewById( R.id.iv3 );

        //iv1.requestFocus();
        iv1.setFocusable( true );
        //iv2.requestFocus();
        iv2.setFocusable( true );
        //iv3.requestFocus();
        iv3.setFocusable( true );

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

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess( YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b ) {
                youTubePlayer.loadVideo( "sSTH5sBWcVQ" );
            }

            @Override
            public void onInitializationFailure( YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult ) {
            }

        };

        iv1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( !hasFocus ){
                    ObjectAnimator.ofFloat( iv1, "scaleX", 1.0f, 0.9f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv1, "scaleY", 1.0f, 0.9f ).setDuration( 500 ).start();;
                }
                else{
                    ObjectAnimator.ofFloat( iv1, "scaleX", 0.9f, 1.0f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv1, "scaleY", 0.9f, 1.0f ).setDuration( 500 ).start();;
                }
            }
        });

        iv2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( !hasFocus ){
                    ObjectAnimator.ofFloat( iv2, "scaleX", 1.0f, 0.9f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv2, "scaleY", 1.0f, 0.9f ).setDuration( 500 ).start();;
                }
                else{
                    ObjectAnimator.ofFloat( iv2, "scaleX", 0.9f, 1.0f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv2, "scaleY", 0.9f, 1.0f ).setDuration( 500 ).start();;
                }
            }
        });

        iv3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( !hasFocus ){
                    ObjectAnimator.ofFloat( iv3, "scaleX", 1.0f, 0.9f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv3, "scaleY", 1.0f, 0.9f ).setDuration( 500 ).start();;
                }
                else{
                    ObjectAnimator.ofFloat( iv3, "scaleX", 0.9f, 1.0f ).setDuration( 500 ).start();
                    ObjectAnimator.ofFloat( iv3, "scaleY", 0.9f, 1.0f ).setDuration( 500 ).start();;
                }
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                /*Intent in = YouTubeStandalonePlayer.createVideoIntent(MainActivity.this, YouTubeConfig.getApiKey(), "1MeK6RUO28Y", 0, true, false );
                startActivityForResult( in, REQ_START_STANDALONE_PLAYER);*/
                Intent in = new Intent( context, PlayVideoActivity.class );
                in.putExtra( "videoID", "1MeK6RUO28Y" );
                startActivity( in );
            }

        });

        iv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent( context, PlayVideoActivity.class );
                in.putExtra( "videoID", "sSTH5sBWcVQ" );
                startActivity( in );
                /*Intent in = YouTubeStandalonePlayer.createVideoIntent(MainActivity.this, YouTubeConfig.getApiKey(), "sSTH5sBWcVQ", 0, true, false );
                startActivityForResult( in, REQ_START_STANDALONE_PLAYER);*/
            }

        });

        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent( context, PlayVideoActivity.class );
                in.putExtra( "videoID", "rUWxSEwctFU" );
                startActivity( in );
                /*Intent in = YouTubeStandalonePlayer.createVideoIntent(MainActivity.this, YouTubeConfig.getApiKey(), "gPYB4HFpGNQ", 0, true, false );
                startActivityForResult( in, REQ_START_STANDALONE_PLAYER);*/
            }

        });

        Picasso.get()
                .load("http://img.youtube.com/vi/1MeK6RUO28Y/0.jpg")
                .into( iv1 );

        Picasso.get()
                .load("http://img.youtube.com/vi/sSTH5sBWcVQ/0.jpg")
                .into( iv2 );

        Picasso.get()
                .load("http://img.youtube.com/vi/rUWxSEwctFU/0.jpg")
                .into( iv3 );

        //youtubePlayerView.initialize( YouTubeConfig.getApiKey(), mOnInitializedListener );

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQ_START_STANDALONE_PLAYER && resultCode != RESULT_OK ) {
            YouTubeInitializationResult errorReason =
                    YouTubeStandalonePlayer.getReturnedInitializationResult( data );


            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, 0).show();
            } else {
                *//*String errorMessage =
                        String.format(getString(R.string.error_player), errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();*//*
            }
        }
    }*/
}
