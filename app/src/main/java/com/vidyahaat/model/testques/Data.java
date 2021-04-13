package com.vidyahaat.model.testques;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("marks")
    private String marks;
    @Expose
    @SerializedName("ans")
    private String ans;
    @Expose
    @SerializedName("ansoption")
    private String ansoption;
    @Expose
    @SerializedName("question")
    private String question;
    @Expose
    @SerializedName("test")
    private String test;
    @Expose
    @SerializedName("id")
    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getAnsoption() {
        return ansoption;
    }

    public void setAnsoption(String ansoption) {
        this.ansoption = ansoption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
