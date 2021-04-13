package com.vidyahaat.model.homescreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockTestDetails {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("examdate")
    private String examdate;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("duration")
    private String duration;

    @Expose
    @SerializedName("examtime")
    private String examTime;

    @Expose
    @SerializedName("number_of_candidate")
    private String noOfCandidate;

    @Expose
    @SerializedName("prize_one")
    private String prizeOne;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Expose
    @SerializedName("prize_two")
    private String prizeTwo;
    @Expose
    @SerializedName("prize_three")
    private String prizeThree;
    @Expose
    @SerializedName("price")
    private String price;
    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getNoOfCandidate() {
        return noOfCandidate;
    }

    public void setNoOfCandidate(String noOfCandidate) {
        this.noOfCandidate = noOfCandidate;
    }

    public String getPrizeOne() {
        return prizeOne;
    }

    public void setPrizeOne(String prizeOne) {
        this.prizeOne = prizeOne;
    }

    public String getPrizeTwo() {
        return prizeTwo;
    }

    public void setPrizeTwo(String prizeTwo) {
        this.prizeTwo = prizeTwo;
    }

    public String getPrizeThree() {
        return prizeThree;
    }

    public void setPrizeThree(String prizeThree) {
        this.prizeThree = prizeThree;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExamdate() {
        return examdate;
    }

    public void setExamdate(String examdate) {
        this.examdate = examdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
