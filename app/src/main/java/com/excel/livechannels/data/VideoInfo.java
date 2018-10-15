package com.excel.livechannels.data;

public class VideoInfo {
    private String videoID;
    private String videoTitle;
    private String useThumbnail;
    private String thumbnailPath;
    private long publishedAt;
    private String videoDescription;
    private boolean isFirst = false;

    public VideoInfo( String videoID, boolean isFirst ){
        this.videoID = videoID;
        this.isFirst = isFirst;
    }

    public VideoInfo( String videoID, String videoTitle, long publishedAt, String videoDescription, String useThumbnail, String thumbnailPath ){
        this.videoID = videoID;
        this.videoTitle = videoTitle;
        this.publishedAt = publishedAt;
        this.videoDescription = videoDescription;
        this.useThumbnail = useThumbnail;
        this.thumbnailPath = thumbnailPath;
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

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getUseThumbnail() {
        return useThumbnail;
    }

    public void setUseThumbnail(String useThumbnail) {
        this.useThumbnail = useThumbnail;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
