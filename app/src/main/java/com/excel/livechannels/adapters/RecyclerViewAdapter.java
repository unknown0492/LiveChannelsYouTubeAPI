package com.excel.livechannels.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.excel.livechannels.PlayVideoActivity;
import com.excel.livechannels.R;
import com.excel.livechannels.data.VideoInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {

    private List<String> videoID;
    private VideoInfo[] videoInfo;
    private List<String> title;
    private List<String> published;
    private List<String> description;
    private Context context;
    int positions = 0;
    ImageView iv_poster;
    RecyclerView rcv;
    TextView tv_video_title;
    TextView tv_published_date;
    TextView tv_video_description;

    final static String TAG = "RecyclerViewAdapter";

    public class MyView extends RecyclerView.ViewHolder {

        public ImageView iv;
        public TextView tv_spacing;

        public MyView( View view ) {
            super( view );

            iv = (ImageView) view.findViewById( R.id.iv );
            //tv_spacing = (TextView) view.findViewById( R.id.tv_spacing );

        }
    }


    public RecyclerViewAdapter( Context context, List<String> videoID ) {
        this.videoID = videoID;
        this.context = context;
    }

    public RecyclerViewAdapter( Context context, VideoInfo[] videoInfo, ImageView iv_poster, TextView tv_video_title, TextView tv_published_date, TextView tv_video_description, RecyclerView rcv ) {
        this.videoInfo = videoInfo;
        this.context = context;
        this.iv_poster = iv_poster;
        this.tv_video_title = tv_video_title;
        this.tv_published_date = tv_published_date;
        this.tv_video_description = tv_video_description;
        this.rcv = rcv;
    }

    @Override
    public MyView onCreateViewHolder( ViewGroup parent, int viewType ) {

        final View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.rcv_item, parent, false );

        /*if( positions == 0 ){
            TextView tv_spacing = (TextView) itemView.findViewById( R.id.tv_spacing );
            tv_spacing.setVisibility( View.VISIBLE );
        }
        positions++;*/

        /*if( videoID.get( positions ) == null ){
            TextView tv_spacing = (TextView) itemView.findViewById( R.id.tv_spacing );
            tv_spacing.setVisibility( View.VISIBLE );
        }*/

        /*itemView.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                //Log.d( TAG, "Focus changed 1 !" );
                if( hasFocus ) {
                    *//*ObjectAnimator.ofFloat( itemView, "scaleX", 1.0f, 0.9f ).setDuration( 250 ).start();
                    ObjectAnimator.ofFloat( itemView, "scaleY", 1.0f, 0.9f ).setDuration( 250 ).start();*//*
                    //itemView.setBackground( context.getResources().getDrawable( R.drawable.bt_focus ) );
                    itemView.setBackgroundColor( context.getResources().getColor( R.color.white ) );
                }
                else{
                    *//*ObjectAnimator.ofFloat( itemView, "scaleX", 0.9f, 1.0f ).setDuration( 250 ).start();
                    ObjectAnimator.ofFloat( itemView, "scaleY", 0.9f, 1.0f ).setDuration( 250 ).start();*//*
                    //itemView.setBackground( null );
                    itemView.setBackgroundColor( 0x00000000 );
                }
            }

        });*/


        return new MyView( itemView );
    }

    public void loadImageIntoView(final MyView holder, final int position, boolean isFailed ){

        if( ! isFailed ) {
            Picasso.get()
                    //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                    .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/maxresdefault.jpg")
                    .into(holder.iv, new Callback() {

                        @Override
                        public void onSuccess() {
                            // Log.i( TAG, "Image Load Successful for index : " + position );
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Image Load Failed for index : " + position);
                            loadImageIntoView( holder, position, true );
                        }

                    });
        }
        else{
            Picasso.get()
                    //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                    // .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/maxresdefault.jpg" )
                    .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/0.jpg" )
                    .resize( 215, 119 )
                    .centerCrop( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL )
                    .into(holder.iv, new Callback() {

                        @Override
                        public void onSuccess() {
                            // Log.i( TAG, "Image Load Successful for index : " + position );
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Image Load Failed for 0 thumbnail  : " + position);

                        }

                    });
        }
    }

    @Override
    public void onBindViewHolder( final MyView holder, final int position ) {

        loadImageIntoView( holder, position, false );
        final View rootView = holder.iv.getRootView();


        rootView.setOnFocusChangeListener( new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                //Log.d( TAG, "Focus changed 1 !" );
                if( hasFocus ) {
                    /*ObjectAnimator.ofFloat( itemView, "scaleX", 1.0f, 0.9f ).setDuration( 250 ).start();
                    ObjectAnimator.ofFloat( itemView, "scaleY", 1.0f, 0.9f ).setDuration( 250 ).start();*/
                    //holder.iv.getRootView().setBackgroundColor( context.getResources().getColor( R.color.white ) );
                    v.setBackground( context.getResources().getDrawable( R.drawable.bt_focus ) );
                    tv_video_title.setText( videoInfo[ position ].getVideoTitle() );
                    tv_video_description.setText( videoInfo[ position ].getVideoDescription() );
                    tv_published_date.setText( videoInfo[ position ].getPublishedAt() + "" );

                    Picasso.get()
                            //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                            .load( "https://img.youtube.com/vi/" + videoInfo[ position ].getVideoID() + "/maxresdefault.jpg" )
                            .resize( 1344, 594 )
                            .centerCrop( Gravity.LEFT | Gravity.TOP )
                            .into( iv_poster, new Callback() {

                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError( Exception e ) {
                                    Picasso.get()
                                            //.load( "http://img.youtube.com/vi/" + videoID.get( position ) + "/0.jpg" )
                                            .load("https://img.youtube.com/vi/" + videoInfo[position].getVideoID() + "/0.jpg" )
                                            .resize( 1344, 594 )
                                            .centerCrop( Gravity.LEFT | Gravity.TOP )
                                            .into( iv_poster );

                                }

                            });

                }
                else{
                    /*ObjectAnimator.ofFloat( itemView, "scaleX", 0.9f, 1.0f ).setDuration( 250 ).start();
                    ObjectAnimator.ofFloat( itemView, "scaleY", 0.9f, 1.0f ).setDuration( 250 ).start();*/
                    v.setBackground( null );
                    //holder.iv.getRootView().setBackgroundColor( 0x00000000 );
                }
            }

        });


        rootView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Log.d( TAG, "Clicked position : " + position );
                Intent in = new Intent( context, PlayVideoActivity.class );
                in.putExtra( "videoID", videoInfo[ position ].getVideoID() );
                context.startActivity( in );
            }

        });



    }

    @Override
    public int getItemCount() {
        //return videoID.size();
        return videoInfo.length;
    }

    public void scrollCenter( View ll, final View viewToScroll ) {
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
        //recyclerView.scrollBy((left - right)/2,0);
        recyclerView.smoothScrollToPosition( position );
    }
}
