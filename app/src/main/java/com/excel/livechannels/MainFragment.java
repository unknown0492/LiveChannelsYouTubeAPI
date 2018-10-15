/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.excel.livechannels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.excel.excelclasslibrary.UtilFile;
import com.excel.excelclasslibrary.UtilNetwork;
import com.excel.excelclasslibrary.UtilSharedPreferences;
import com.excel.excelclasslibrary.UtilURL;
import com.excel.livechannels.adapters.RecyclerViewAdapter;
import com.excel.livechannels.data.VideoCategory;
import com.excel.livechannels.data.VideoInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_DATA;
import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_KEY_INFO;
import static com.excel.livechannels.data.Constants.WEBSERVICE_URL;

public class MainFragment extends RowsFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;
    LayoutInflater layoutInflater;

    ActivityFragmentInterface activityFragmentInterface;

    VideoCategory[] videoCategories;
    VideoInfo videoInfo[], videoInfo1[];
    Context context = null;

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        Log.i( TAG, "onActivityCreated" );

        // prepareBackgroundManager();

        // getVideosFromCloud();

        renderData();

        setupUIElements();

        loadRows();

        setupEventListeners();

    }

    private void getVideosFromCloud(){
        // Call the Webservice for the data
        /*AsyncYouTubeData asyncYouTubeData = new AsyncYouTubeData();
        asyncYouTubeData.execute();*/
    }

    private void renderData() {
        File tempFile = new File( ( context.getFilesDir() + File.separator + "json.data" ) );

        try {
            JSONArray responsesArray = null;
            responsesArray = new JSONArray( UtilFile.readData( tempFile ) );

            videoCategories = new VideoCategory[ responsesArray.length() ];

            for ( int i = 0; i < responsesArray.length(); i++ ) {
                JSONObject responseObject = responsesArray.getJSONObject( i );
                videoCategories[ i ] = new VideoCategory();
                videoCategories[ i ].setCategoryName( responseObject.getString("category") );
                Log.d( TAG, responseObject.getString("category"));
                JSONArray responseArray = responseObject.getJSONArray("information" );

                videoInfo = new VideoInfo[ responseArray.length() ];

                for ( int j = 0; j < responseArray.length(); j++ ) {
                    JSONObject videoInfoJsonObject = responseArray.getJSONObject( j );
                    String use_thumbnail = videoInfoJsonObject.getString("use_thumbnail" );
                    videoInfo[ j ] = new VideoInfo( videoInfoJsonObject.getString("videoID"),
                            videoInfoJsonObject.getString("title" ),
                            videoInfoJsonObject.getLong("publishedAt" ),
                            videoInfoJsonObject.getString("description" ),
                            use_thumbnail,
                            use_thumbnail.equals( "1" )?videoInfoJsonObject.getString("thumbnail_path_lg" ):use_thumbnail );
                    // Log.d( TAG, "videoInfo[ j ] : " + videoInfo[ j ].getVideoID() );
                    videoCategories[ i ].setVideoInfo( videoInfo );

                }
            }

            ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );

            for( int i = 0 ; i < videoCategories.length ; i++ ){
                HeaderItem gridHeader = null;
                GridItemPresenter mGridPresenter = new GridItemPresenter();
                //CustomListRowPresenter mGridPresenter = new CustomListRowPresenter( FocusHighlight.ZOOM_FACTOR_NONE );
                //ListRowPresenter listRowPresenter = new ListRowPresenter( FocusHighlight.ZOOM_FACTOR_NONE );
                ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );


                gridHeader = new HeaderItem( i, videoCategories[ i ].getCategoryName() );
                videoInfo = videoCategories [ i ].getVideoInfo();
                for( int j = 0 ; j < videoInfo.length ; j++ ){
                    gridRowAdapter.add( videoInfo[ j ] );
                }

                rowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );
            }

            setAdapter( rowsAdapter );
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if ( null != mBackgroundTimer ) {
            Log.d( TAG, "onDestroy: " + mBackgroundTimer.toString() );
            mBackgroundTimer.cancel();
        }*/
    }

    private void loadRows() {
        List<Movie> list = MovieList.setupMovies();

        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
        CardPresenter cardPresenter = new CardPresenter();

        /*int i;
        for ( i = 0; i < NUM_ROWS; i++ ) {
            if ( i != 0 ) {
                Collections.shuffle( list );
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( cardPresenter );
            for ( int j = 0; j < NUM_COLS; j++ ) {
                listRowAdapter.add( list.get( j % 5 ) );
            }
            HeaderItem header = new HeaderItem( i, MovieList.MOVIE_CATEGORY[ i ] );
            rowsAdapter.add( new ListRow( header, listRowAdapter ) );
            // rowsAdapter.add( new ListRow( listRowAdapter ));
        }*/

        /*for( int i = 0 ; i < 2 ; i++ ){
            HeaderItem gridHeader = null;
            GridItemPresenter mGridPresenter = new GridItemPresenter();
            ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );

            if( i == 0 ){
                gridHeader = new HeaderItem( i, "Chinese 1" );
            }
            else if( i == 1 ){
                gridHeader = new HeaderItem( i, "Chinese 2" );
            }

            gridRowAdapter.add(  );

            gridRowAdapter.add( getResources().getString( R.string.grid_view ) );
            gridRowAdapter.add( getString( R.string.error_fragment ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
            gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );

            rowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );
        }









        setAdapter( rowsAdapter );*/
    }

    private void prepareBackgroundManager() {

        /*mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());

        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);*/
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        /*setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));*/

        //layoutInflater = (LayoutInflater) .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    private void setupEventListeners() {
        /*setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });*/

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());

    }

    private void updateBackground(String uri) {
        /*int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();*/
    }

    private void startBackgroundTimer() {
        /*if ( null != mBackgroundTimer ) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule( new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY );*/
    }


    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        activityFragmentInterface = (ActivityFragmentInterface) activity;
        context = activity.getApplicationContext();

    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            VideoInfo videoInfo = (VideoInfo) item;

            Intent in = new Intent( context, PlayVideoActivity.class );
            in.putExtra( "videoID", videoInfo.getVideoID() );
            in.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( in );



        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {
            /*if ( item instanceof Movie ) {
                mBackgroundUri = ((Movie) item).getBackgroundImageUrl();
                startBackgroundTimer();
            }*/
            if ( item instanceof VideoInfo ) {
                VideoInfo videoInfo = (VideoInfo) item;
                activityFragmentInterface.updatePoster( videoInfo );
                activityFragmentInterface.updateMetaData( videoInfo );
            }

        }

    }





    private class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent) {
            View rcv_item = View.inflate( parent.getContext(), R.layout.rcv_item, null );

            rcv_item.setOnFocusChangeListener( new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange( View v, boolean hasFocus ) {

                    if( hasFocus ){
                        //activityFragmentInterface.updatePoster( videoInfo );
                        //v.setBackground( context.getDrawable( R.drawable.item_focus_bg ));
                        v.setBackground( context.getResources().getDrawable( R.drawable.item_focus_bg ));
                        /*int width = v.getWidth();
                        int height = v.getHeight();
                        Log.d( TAG, "hasFocus, width : " + width + ", height : " + height );
                        v.setScaleX( -0.5f );
                        v.setScaleY( -0.5f );*/
                    }
                    else{
                        /*int width = v.getWidth();
                        int height = v.getHeight();
                        Log.d( TAG, "lost Focus, width : " + width + ", height : " + height );*/
                        v.setBackground( null );
                    }

                }

            });


            //TextView view = new TextView(parent.getContext());
            //view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            rcv_item.setFocusable(true);
            rcv_item.setFocusableInTouchMode(true);
            // view.setBackgroundColor( ContextCompat.getColor(getActivity(), R.color.default_background));
            //view.setTextColor(Color.WHITE);
            //view.setGravity(Gravity.CENTER);
            return new ViewHolder( rcv_item );
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, Object item ) {

            View rcv_item = viewHolder.view;
            ImageView iv = (ImageView) rcv_item.findViewById( R.id.iv );
            final VideoInfo videoInfo = (VideoInfo) item;
            /*Picasso.get()
                    .load( "http://img.youtube.com/vi/" + videoInfo.getVideoID() + "/0.jpg" )
                    //.load( "https://img.youtube.com/vi/" + videoInfo[ position ].getVideoID() + "/maxresdefault.jpg" )
                    .resize( 215, 119 )
                    .centerCrop( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL )
                    .into( iv );*/

            loadImageIntoView( iv, videoInfo, false );

            /*rcv_item.setOnFocusChangeListener( new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange( View v, boolean hasFocus ) {

                    if( hasFocus ){
                        //activityFragmentInterface.updatePoster( videoInfo );
                        v.setBackground( context.getDrawable( R.drawable.item_focus_bg ));
                        *//*int width = v.getWidth();
                        int height = v.getHeight();
                        Log.d( TAG, "hasFocus, width : " + width + ", height : " + height );
                        v.setScaleX( -0.5f );
                        v.setScaleY( -0.5f );*//*
                    }
                    else{
                        *//*int width = v.getWidth();
                        int height = v.getHeight();
                        Log.d( TAG, "lost Focus, width : " + width + ", height : " + height );*//*
                        v.setBackground( null );
                    }

                }

            });*/

        }

        @Override
        public void onUnbindViewHolder( ViewHolder viewHolder ) {
        }

        public void loadImageIntoView( final ImageView iv, final VideoInfo videoInfo, boolean isFailed ){

            if( ! isFailed ) {
                Picasso.get()
                        //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                        .load("https://img.youtube.com/vi/" + videoInfo.getVideoID() + "/maxresdefault.jpg")
                        .resize( 215, 119 )
                        .centerCrop( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL )
                        .into( iv, new Callback() {

                            @Override
                            public void onSuccess() {
                                // Log.i( TAG, "Image Load Successful for index : " + position );
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Image Load Failed ");
                                loadImageIntoView( iv, videoInfo, true );
                            }

                        });
            }
            else{
                Picasso.get()
                        //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                        // .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/maxresdefault.jpg" )
                        .load("https://img.youtube.com/vi/" + videoInfo.getVideoID() + "/0.jpg" )
                        .resize( 215, 119 )
                        .centerCrop( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL )
                        .into( iv, new Callback() {

                            @Override
                            public void onSuccess() {
                                // Log.i( TAG, "Image Load Successful for index : " + position );
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Image Load Failed for 0 thumbnail  " );

                            }

                        });
            }
        }
    }




    /*class AsyncYouTubeData extends AsyncTask< String, Integer, String > {

        @Override
        protected String doInBackground( String... params ) {
            String url = WEBSERVICE_URL;
            Log.d( TAG, "Webservice path : " + url );
            String response = UtilNetwork.makeRequestForData( url, "POST",
                    UtilURL.getURLParamsFromPairs( new String[][]{ { "what_do_you_want", "get_static_video_info" } } ) );

            return response;
        }

        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );

            Log.i( TAG,  "inside onPostExecute()" );

            if( result != null ){
                videoInfo = new VideoInfo[ 6 ];
                videoInfo1 = new VideoInfo[ 7 ];

                Log.i( TAG,  result );
                try {
                    JSONArray jsonArray = new JSONArray( result );
                    JSONObject jsonObject = jsonArray.getJSONObject( 0 );

                    String type = jsonObject.getString( "type" );
                    if( type.equals( "success" )){

                        JSONArray responsesArray = jsonObject.getJSONArray( "info" );
                        for( int i = 0 ; i < responsesArray.length() ; i++ ){
                            JSONArray responseArray = responsesArray.getJSONArray( i );
                            for( int j = 0 ; j < responseArray.length() ; j++ ){
                                JSONObject videoInfoJsonObject = responseArray.getJSONObject( j );
                                if( i == 0 ){
                                    videoInfo[ j ] = new VideoInfo( videoInfoJsonObject.getString( "videoID" ),
                                            videoInfoJsonObject.getString( "title" ),
                                            videoInfoJsonObject.getLong( "publishedAt" ),
                                            videoInfoJsonObject.getString( "description" ) );
                                    //Log.d( TAG, "videoInfo[ j ] : " + videoInfo[ j ].getVideoID() );
                                }
                                else if( i == 1 ){
                                    videoInfo1[ j ] = new VideoInfo( videoInfoJsonObject.getString( "videoID" ),
                                            videoInfoJsonObject.getString( "title" ),
                                            videoInfoJsonObject.getLong( "publishedAt" ),
                                            videoInfoJsonObject.getString( "description" ) );
                                    //Log.i( TAG, "videoInfo1[ j ] : " + videoInfo1[ j ].getVideoID() );
                                }

                            }
                        }
                    }

                    ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );

                    for( int i = 0 ; i < 2 ; i++ ){
                        HeaderItem gridHeader = null;
                        GridItemPresenter mGridPresenter = new GridItemPresenter();
                        //CustomListRowPresenter mGridPresenter = new CustomListRowPresenter( FocusHighlight.ZOOM_FACTOR_NONE );
                        //ListRowPresenter listRowPresenter = new ListRowPresenter( FocusHighlight.ZOOM_FACTOR_NONE );
                        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );

                        if( i == 0 ){
                            gridHeader = new HeaderItem( i, "Chinese 1" );
                            for( int j = 0 ; j < videoInfo.length ; j++ ){
                                gridRowAdapter.add( videoInfo[ j ] );
                            }
                        }
                        else if( i == 1 ){
                            gridHeader = new HeaderItem( i, "Chinese 2" );
                            for( int j = 0 ; j < videoInfo1.length ; j++ ){
                                gridRowAdapter.add( videoInfo1[ j ] );
                            }
                        }

                        rowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );
                    }

                    setAdapter( rowsAdapter );

                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
            else{
                Log.e( TAG, "Null was returned " );
            }

        }
    }*/

    public interface ActivityFragmentInterface{
        public void updatePoster( VideoInfo videoInfo );

        public void updateMetaData( VideoInfo videoInfo );
    }


}
