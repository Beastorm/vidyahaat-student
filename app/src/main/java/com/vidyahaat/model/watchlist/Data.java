package com.vidyahaat.model.watchlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @Expose
    @SerializedName("videoDetails")
    private List<VideoDetails> videoDetails;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("student")
    private String student;
    @Expose
    @SerializedName("video")
    private String video;
    @Expose
    @SerializedName("id")
    private String id;

    public List<VideoDetails> getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(List<VideoDetails> videoDetails) {
        this.videoDetails = videoDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
