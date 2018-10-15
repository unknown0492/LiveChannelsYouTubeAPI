package com.excel.livechannels.data;

public class VideoCategory {

    private String categoryName;
    private VideoInfo[] videoInfo;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public VideoInfo[] getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo[] videoInfo) {
        this.videoInfo = videoInfo;
    }
}
