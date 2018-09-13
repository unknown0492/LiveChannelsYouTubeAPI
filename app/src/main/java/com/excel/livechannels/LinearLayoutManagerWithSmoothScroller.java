package com.excel.livechannels;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class LinearLayoutManagerWithSmoothScroller extends LinearLayoutManager {

    public LinearLayoutManagerWithSmoothScroller( Context context ) {
        super( context, VERTICAL, false );
    }

    public LinearLayoutManagerWithSmoothScroller( Context context, int orientation, boolean reverseLayout ) {
        super( context, orientation, reverseLayout );
    }

    @Override
    public void smoothScrollToPosition( RecyclerView recyclerView, RecyclerView.State state, int position ) {
        RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller( recyclerView.getContext() );
        smoothScroller.setTargetPosition( position );
        startSmoothScroll( smoothScroller );
    }

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {
        public TopSnappedSmoothScroller(Context context) {
            super(context);

        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return LinearLayoutManagerWithSmoothScroller.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_ANY;
        }
    }

    /*private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller( Context context ) {
            super( context );
        }

        @Override
        public int calculateDtToFit( int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference ) {
            return ( boxStart + ( boxEnd - boxStart ) / 2 ) - ( viewStart + ( viewEnd - viewStart ) / 2 );
        }

        @Override
        protected float calculateSpeedPerPixel( DisplayMetrics displayMetrics ) {
            return 0.5f; //pass as per your requirement
        }
    }*/
}
