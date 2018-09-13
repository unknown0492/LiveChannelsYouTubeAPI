package com.excel.livechannels;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.excel.livechannels.data.VideoInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity1 extends Activity {

    final static String TAG = "HomeActivity";
    ImageView iv_poster;
    Context context = this;
    private List<String> videoID;
    VideoInfo videoInfo[];

    RelativeLayout rl_content;
    LinearLayout ll_category_holder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home1 );

        init();
    }

    private void init(){
        iv_poster = (ImageView) findViewById( R.id.iv_poster );
        rl_content = (RelativeLayout) findViewById( R.id.rl_content );
        ll_category_holder = (LinearLayout) findViewById( R.id.ll_category_holder );

        Picasso.get()
                .load( R.drawable.bahubali1 )
                .resize( 1344, 594 )
                .centerCrop( Gravity.LEFT | Gravity.TOP )
                .into( iv_poster );

        // Add items to the RecyclerView
        addVideoIDsIntoList();
    }

    public void addVideoIDsIntoList(){
        videoID = new ArrayList<String>();
        videoInfo = new VideoInfo[ 11 ];
        //videoID.add( "null" );

        videoID.add( "Ga3maNZ0x0w" );
        videoID.add( "sSTH5sBWcVQ" );
        videoID.add( "J78SdCzzumA" );
        videoID.add( "m5Nst2zMZVY" );
        videoID.add( "1MeK6RUO28Y" );

        videoID.add( "wUPPkSANpyo" );
        videoID.add( "TL8mmew3jb8" );

        videoID.add( "UhNX5NdJCs4" );
        videoID.add( "G8I5D6-Bhes" );
        videoID.add( "sYXEikrqaus" );
        videoID.add( "wuc9nK4grmY" );

        for( int i = 0 ; i < 11 ; i++ ){
            if( i == 0 )
                videoInfo[ i ] = new VideoInfo( videoID.get( i ), true );
            else
                videoInfo[ i ] = new VideoInfo( videoID.get( i ), false );
        }
    }
}
