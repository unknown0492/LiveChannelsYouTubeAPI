package com.excel.livechannels;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_DATA;
import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_KEY_INFO;
import static com.excel.livechannels.data.Constants.WEBSERVICE_URL;
import static com.excel.livechannels.logic.DateLogic.getReadableDateFromMilis;

public class HomeActivity extends Activity implements MainFragment.ActivityFragmentInterface {

    final static String TAG = "HomeActivity";
    ImageView iv_poster;
    RecyclerView rcv1, rcv;
    RecyclerView rcv2;
    TextView tv_category_name1, tv_category_name2;

    RecyclerView.LayoutManager rcvLayoutManager, rcvLayoutManager1, recyclerViewLayoutManager1;
    RecyclerViewAdapter rcvAdapter, rcvAdapter1, recyclerViewHorizontalAdapter1;
    LinearLayoutManager llm, horizontalLayout1 ;

    View ChildView ;
    int RecyclerViewItemPosition ;
    Context context = this;
    private List<String> videoID, videoID1;
    VideoInfo videoInfo[], videoInfo1[];

    //RelativeLayout rl_content;
    LinearLayout ll_content;

    TextView tv_video_title, tv_published_date, tv_video_description;
    VideoCategory[] videoCategories;


    private void init(){

        iv_poster = (ImageView) findViewById( R.id.iv_poster );
        tv_video_title = (TextView) findViewById( R.id.tv_video_title );
        tv_published_date = (TextView) findViewById( R.id.tv_published_date );
        tv_video_description = (TextView) findViewById( R.id.tv_video_description );

        //renderData();

    }

    /*private void renderData() {
        SharedPreferences spfs = UtilSharedPreferences.createSharedPreference(context, SHARED_PREFERENCES_DATA);

        try {
            JSONArray responsesArray = null;
            responsesArray = new JSONArray( UtilSharedPreferences.getSharedPreference( spfs, SHARED_PREFERENCES_KEY_INFO, "" ) );

            videoCategories = new VideoCategory[responsesArray.length()];

            for (int i = 0; i < responsesArray.length(); i++) {
                JSONObject responseObject = responsesArray.getJSONObject(i);
                videoCategories[i].setCategoryName(responseObject.getString("category"));
                JSONArray responseArray = responseObject.getJSONArray("information");

                videoInfo = new VideoInfo[responseArray.length()];

                for (int j = 0; j < responseArray.length(); j++) {
                    JSONObject videoInfoJsonObject = responseArray.getJSONObject(j);

                    videoInfo[j] = new VideoInfo(videoInfoJsonObject.getString("videoID"),
                            videoInfoJsonObject.getString("title"),
                            videoInfoJsonObject.getLong("publishedAt"),
                            videoInfoJsonObject.getString("description"));
                    // Log.d( TAG, "videoInfo[ j ] : " + videoInfo[ j ].getVideoID() );
                    videoCategories[i].setVideoInfo(videoInfo);

                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        init();

        /*Picasso.get()
                .load( R.drawable.bahubali1 )
                .resize( 1344, 594 )
                .centerCrop( Gravity.LEFT | Gravity.TOP )
                .into( iv_poster );*/

    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        Log.d( TAG, "Height : " + iv_poster.getHeight() + ", Width : " + iv_poster.getWidth() );
    }*/

    public void addVideoIDsIntoList(){

        // Call the Webservice for the data
        /*AsyncYouTubeData asyncYouTubeData = new AsyncYouTubeData();
        asyncYouTubeData.execute();*/

        /*videoID = new ArrayList<String>();
        videoID1 = new ArrayList<String>();
        videoInfo = new VideoInfo[ 7 ];
        videoInfo1 = new VideoInfo[ 7 ];
        //videoID.add( "null" );

        videoID.add( "wUPPkSANpyo" );
        videoID.add( "u5X_hiHtKkM" );
        videoID.add( "TL8mmew3jb8" );
        videoID.add( "XxJKnDLYZz4" );
        videoID.add( "TtvxRcZbPM0" );
        videoID.add( "yk2CUjbyyQY" );
        videoID.add( "rgVBe6VGWm4" );

        videoID1.add( "9XzlkgI-j50" );
        videoID1.add( "r_nCj1X_Hhw" );
        videoID1.add( "YjAXz1hFJug" );
        videoID1.add( "Sb-c_1z_UM0" );
        videoID1.add( "uz8VZITv97g" );
        videoID1.add( "_b4t0pB0Viw" );
        videoID1.add( "tMCg3T8NVRs" );


        for( int i = 0 ; i < 7 ; i++ ){
            if( i == 0 ) {
                videoInfo[i] = new VideoInfo(videoID.get(i), true);
                videoInfo1[i] = new VideoInfo(videoID1.get(i), true);
            }
            else {
                videoInfo[i] = new VideoInfo(videoID.get(i), false);
                videoInfo1[i] = new VideoInfo(videoID1.get(i), false);
            }
        }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d( TAG, "keyCode : " +keyCode );
        return super.onKeyDown(keyCode, event);
    }

    public void scrollCenter(View ll, final View viewToScroll ) {
        // Source : http://stackoverflow.com/questions/8642677/reduce-speed-of-smooth-scroll-in-scroll-view
        int endPos    = (int) ll.getX();
        int halfWidth = (int) ll.getWidth() / 2;
        // ObjectAnimator.ofFloat( viewToScroll, "scrollX",  endPos + halfWidth - viewToScroll.getWidth() / 2 ).setDuration( 500 ).start();
        ObjectAnimator.ofInt( viewToScroll, "scrollX",  endPos + halfWidth - viewToScroll.getWidth() / 2 ).setDuration( 500 ).start();

    }

    public void scrollRecyclerViewToMiddle( RecyclerView recyclerView, int position ){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int left = recyclerView.getChildAt(position - layoutManager.findFirstVisibleItemPosition()).getLeft();
        int right = recyclerView.getChildAt(layoutManager.findLastVisibleItemPosition() - position).getLeft();
        recyclerView.scrollBy((left - right)/2,0);
    }

    @Override
    public void updatePoster( VideoInfo videoInfo ) {
        loadImageIntoView( videoInfo, false );
    }

    @Override
    public void updateMetaData( VideoInfo videoInfo ) {
        loadImageIntoView( videoInfo, false );
        tv_video_title.setText( videoInfo.getVideoTitle() );
        tv_published_date.setText( getReadableDateFromMilis( videoInfo.getPublishedAt() ) );
        tv_video_description.setText( videoInfo.getVideoDescription() );
    }

    public void loadImageIntoView( final VideoInfo videoInfo, boolean isFailed ){

        if( ! isFailed ) {
            Picasso.get()
                    //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                    .load("https://img.youtube.com/vi/" + videoInfo.getVideoID() + "/maxresdefault.jpg" )
                    .resize( 1344, 594 )
                    .centerCrop( Gravity.LEFT | Gravity.TOP )
                    .into( iv_poster, new Callback() {

                        @Override
                        public void onSuccess() {
                            // Log.i( TAG, "Image Load Successful for index : " + position );
                        }

                        @Override
                        public void onError( Exception e ) {
                            Log.e( TAG, "Image Load Failed : " );
                            loadImageIntoView( videoInfo,true );
                        }

                    });
        }
        else{
            Picasso.get()
                    //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                    // .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/maxresdefault.jpg" )
                    .load("https://img.youtube.com/vi/" + videoInfo.getVideoID() + "/0.jpg" )
                    .resize( 1344, 594 )
                    .centerCrop( Gravity.LEFT | Gravity.TOP )
                    .into( iv_poster, new Callback() {

                        @Override
                        public void onSuccess() {
                            // Log.i( TAG, "Image Load Successful for index : " + position );
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Image Load Failed for 0 thumbnail ");

                        }

                    });
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

                    rcvAdapter = new RecyclerViewAdapter( context, videoInfo, iv_poster, tv_video_title, tv_published_date, tv_video_description, rcv1 );
                    rcv1.setAdapter( rcvAdapter );
                    rcvAdapter1 = new RecyclerViewAdapter( context, videoInfo1, iv_poster, tv_video_title, tv_published_date, tv_video_description, rcv2 );
                    rcv2.setAdapter( rcvAdapter1 );

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



}



