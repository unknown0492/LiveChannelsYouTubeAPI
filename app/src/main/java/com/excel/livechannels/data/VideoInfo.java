package com.excel.livechannels.data;

public class VideoInfo {
    private String videoID;
    private boolean isFirst = false;

    public VideoInfo( String videoID, boolean isFirst ){
        this.videoID = videoID;
        this.isFirst = isFirst;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
