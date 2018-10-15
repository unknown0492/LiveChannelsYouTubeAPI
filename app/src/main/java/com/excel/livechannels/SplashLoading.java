package com.excel.livechannels;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.excel.configuration.ConfigurationReader;
import com.excel.customitems.CustomItems;
import com.excel.excelclasslibrary.UtilFile;
import com.excel.excelclasslibrary.UtilNetwork;
import com.excel.excelclasslibrary.UtilSharedPreferences;
import com.excel.excelclasslibrary.UtilURL;
import com.excel.livechannels.adapters.RecyclerViewAdapter;
import com.excel.livechannels.data.VideoCategory;
import com.excel.livechannels.data.VideoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_DATA;
import static com.excel.livechannels.data.Constants.SHARED_PREFERENCES_KEY_INFO;
import static com.excel.livechannels.data.Constants.WEBSERVICE_URL;

public class SplashLoading extends Activity {

    Context context = this;
    AnimatedGifImageView iv_loading;
    final static String TAG = "SplashLoading";
    ConfigurationReader configurationReader;
    String appstv_live_channels_key;

    VideoCategory[] videoCategories;
    VideoInfo[] videoInfo;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_loading );

        configurationReader = ConfigurationReader.getInstance();

        iv_loading = (AnimatedGifImageView) findViewById( R.id.iv_loading );
        iv_loading.setMaxHeight( 20 );
        iv_loading.setMaxWidth( 20 );
        iv_loading.setAnimatedGif( context.getResources().getIdentifier( "drawable/loading1" , null, context.getPackageName() ), AnimatedGifImageView.TYPE.AS_IS );

        getAccessKey();
    }

    private void getAccessKey(){
        AsyncAccessKey asyncAccessKey = new AsyncAccessKey();
        asyncAccessKey.execute();
    }


    class AsyncAccessKey extends AsyncTask< String, Integer, String > {

        @Override
        protected String doInBackground( String... params ) {
            String url = UtilURL.getWebserviceURL(); //configurationReader.getWebservicePath();
            Log.d( TAG, "Webservice path : " + url );
            String response = UtilNetwork.makeRequestForData( url, "POST",
                    UtilURL.getURLParamsFromPairs( new String[][]{ { "what_do_you_want", "get_lch_access_key" } } ) );

            return response;
        }

        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );

            Log.i( TAG,  "inside onPostExecute()" );

            if( result != null ){

                Log.i( TAG,  result );
                try {
                    JSONArray jsonArray = new JSONArray( result );
                    JSONObject jsonObject = jsonArray.getJSONObject( 0 );

                    String type = jsonObject.getString( "type" );
                    if( type.equals( "success" )){

                        JSONObject info = jsonObject.getJSONObject( "info" );
                        appstv_live_channels_key = info.getString( "appstv_live_channels_key" );
                        Log.d( TAG, appstv_live_channels_key );

                        AsyncYouTubeData asyncYouTubeData = new AsyncYouTubeData();
                        asyncYouTubeData.execute();
                    }
                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
            else{
                Log.e( TAG, "Null was returned " );
            }

        }
    }



    class AsyncYouTubeData extends AsyncTask< String, Integer, String > {

        @Override
        protected String doInBackground( String... params ) {
            String url = WEBSERVICE_URL;
            Log.d( TAG, "Webservice path : " + url );
            String response = UtilNetwork.makeRequestForData( url, "POST",
                    UtilURL.getURLParamsFromPairs( new String[][]{ { "what_do_you_want", "get_static_video_info" },
                            { "access_key", appstv_live_channels_key } } ) );

            return response;
        }

        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );

            Log.i( TAG,  "inside onPostExecute()" );

            if( result != null ){

                Log.i( TAG,  result );
                try {
                    JSONArray jsonArray = new JSONArray( result );
                    JSONObject jsonObject = jsonArray.getJSONObject( 0 );

                    String type = jsonObject.getString( "type" );
                    if( type.equals( "success" ) ){

                        JSONArray responsesArray = jsonObject.getJSONArray( "info" );

                        File tempFile = new File( ( getFilesDir() + File.separator + "json.data" ) );
                        UtilFile.saveDataToFile( tempFile, responsesArray.toString() );

                        //UtilSharedPreferences.editSharedPreference( spfs, SHARED_PREFERENCES_KEY_INFO, responsesArray.toString() );

                        Intent in = new Intent( context, HomeActivity.class );
                        startActivity( in );
                        finish();
                    }
                    else if( type.equals( "error" ) ){
                        String info = jsonObject.getString( "info" );
                        Log.e( TAG, info );
                        CustomItems.showCustomToast( context, type, info, Toast.LENGTH_LONG );
                        new Handler().postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3000 );
                    }

                }
                catch ( JSONException e ) {
                    e.printStackTrace();
                }
            }
            else{
                Log.e( TAG, "Null was returned " );
            }

        }
    }
}
