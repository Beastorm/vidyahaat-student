package com.vidyahaat.model.testper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @Expose
    @SerializedName("testDetails")
    private List<TestDetails> testDetails;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("marks")
    private String marks;
    @Expose
    @SerializedName("student")
    private String student;
    @Expose
    @SerializedName("test")
    private String test;
    @Expose
    @SerializedName("id")
    private String id;

    public List<TestDetails> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(List<TestDetails> testDetails) {
        this.testDetails = testDetails;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
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
