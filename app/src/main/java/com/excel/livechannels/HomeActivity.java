package com.excel.livechannels;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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

import com.excel.livechannels.adapters.RecyclerViewAdapter;
import com.excel.livechannels.data.VideoInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {

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


    private void init(){

        iv_poster = (ImageView) findViewById( R.id.iv_poster );
        ll_content = (LinearLayout) findViewById( R.id.ll_content );
        //rcv = (RecyclerView) findViewById( R.id.rcv1 );

        // 1st Line
        addVideoIDsIntoList();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE );
        LinearLayout ll_category_holder = (LinearLayout) layoutInflater.inflate( R.layout.ll_category_holder, null );
        rcv1 = (RecyclerView) ll_category_holder.findViewById( R.id.rcv );
        rcv1.setNextFocusDownId( rcv1.getId() );
        tv_category_name1 = (TextView) ll_category_holder.findViewById( R.id.tv_category_name );
        tv_category_name1.setText( "台湾ニュース" );

        rcvAdapter = new RecyclerViewAdapter( context, videoInfo, iv_poster, rcv1 );
        rcvLayoutManager = new LinearLayoutManager( context, RecyclerView.HORIZONTAL, false );
        //rcvLayoutManager.scrollHorizontallyBy(  );
        rcv1.setLayoutManager( rcvLayoutManager );
        rcv1.setAdapter( rcvAdapter );

        ll_content.addView( ll_category_holder );

        // 2nd Line
        LayoutInflater layoutInflater1 = (LayoutInflater) getSystemService( LAYOUT_INFLATER_SERVICE );
        LinearLayout ll_category_holder1 = (LinearLayout) layoutInflater1.inflate( R.layout.ll_category_holder, null );
        rcv2 = (RecyclerView) ll_category_holder1.findViewById( R.id.rcv );
        rcv2.setNextFocusDownId( rcv2.getId() );
        tv_category_name2 = (TextView) ll_category_holder1.findViewById( R.id.tv_category_name );
        tv_category_name2.setText( "台北台北の観光名所" );

        rcvAdapter1 = new RecyclerViewAdapter( context, videoInfo1, iv_poster, rcv2 );
        rcvLayoutManager1 = new LinearLayoutManager( context, RecyclerView.HORIZONTAL, false );
        rcv2.setLayoutManager( rcvLayoutManager1 );
        rcv2.setAdapter( rcvAdapter1 );

        ll_content.addView( ll_category_holder1 );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        init();

        /*rcv1 = (RecyclerView) findViewById( R.id.rcv1 );
        rcv2 = (RecyclerView) findViewById( R.id.rcv2 );
        rcv1.setNestedScrollingEnabled(false);
        rcv2.setNestedScrollingEnabled(false);
        //recyclerViewLayoutManager = new LinearLayoutManager( context );

        recyclerViewLayoutManager = new LinearLayoutManagerWithSmoothScroller( context, RecyclerView.HORIZONTAL, false );
        recyclerViewLayoutManager1 = new LinearLayoutManagerWithSmoothScroller( context, RecyclerView.HORIZONTAL, false );

        // Add items to the RecyclerView
        addVideoIDsIntoList();

        recyclerViewHorizontalAdapter = new RecyclerViewAdapter( context, videoInfo, iv_poster, rcv1 );
        recyclerViewHorizontalAdapter1 = new RecyclerViewAdapter( context, videoInfo, iv_poster, rcv2 );

        horizontalLayout = new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false );
        horizontalLayout1 = new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false );
        rcv1.setLayoutManager( horizontalLayout );
        rcv2.setLayoutManager( horizontalLayout1 );
        rcv1.setAdapter( recyclerViewHorizontalAdapter );
        rcv2.setAdapter( recyclerViewHorizontalAdapter1 );*/




        /*rcv1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d( TAG, "keyCode rc : " +keyCode );

                *//*LinearLayout ll = (LinearLayout) v;

                if( ( keyCode == 22 ) && ( index < total_items ) ) {
                    //scrollCenter( ll, rcv1 );
                    rcv1.scrollBy( 219, 0 );
                    rcv1.scrollToPosition(index++);
                }
                else if( ( keyCode == 22 ) && ( index == total_items ) ){

                }
                else if( ( keyCode == 21 ) && ( index > 0 ) ) {
                    //scrollCenter( ll, rcv1 );
                    rcv1.scrollBy( -219, 0 );
                    rcv1.scrollToPosition(index--);
                }
                else if( ( keyCode == 21 ) && ( index == 0 ) ){

                }*//*
                return false;
            }
        });*/






        /*rcv1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                //Log.i("ONFOCUSCHANGE- reclist", "focus has changed I repeat the focus has changed! current focus = " + currFocus);

                if( currFocus != RECVIEW1 ){
                    currFocus = RECVIEW1;
                    rcv1.getChildAt(0 ).requestFocus();

                    //scrollCenter( v, rcv1 );
                }

                rcv1.scrollBy( 219, 0 );
            }
        });*/



        //Log.d( TAG, "oncreate Height : " + iv_poster.getHeight() + ", Width : " + iv_poster.getWidth() );

        Picasso.get()
                .load( R.drawable.bahubali1 )
                .resize( 1344, 594 )
                .centerCrop( Gravity.LEFT | Gravity.TOP )
                .into( iv_poster );


        //iv_poster.setMaxWidth( 1056 );
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        Log.d( TAG, "Height : " + iv_poster.getHeight() + ", Width : " + iv_poster.getWidth() );
    }*/

    public void addVideoIDsIntoList(){
        videoID = new ArrayList<String>();
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
        }
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

}

