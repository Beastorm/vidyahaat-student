package com.vidyahaat.model.mocktest;

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
    @SerializedName("anyoption")
    private String anyoption;
    @Expose
    @SerializedName("question")
    private String question;
    @Expose
    @SerializedName("mocktest")
    private String mocktest;
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

    public String getAnyoption() {
        return anyoption;
    }

    public void setAnyoption(String anyoption) {
        this.anyoption = anyoption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMocktest() {
        return mocktest;
    }

    public void setMocktest(String mocktest) {
        this.mocktest = mocktest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
