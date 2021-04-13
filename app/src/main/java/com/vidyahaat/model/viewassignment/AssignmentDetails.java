package com.vidyahaat.model.viewassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentDetails {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("doc")
    private String doc;
    @Expose
    @SerializedName("lastdate")
    private String lastdate;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("course")
    private String course;
    @Expose
    @SerializedName("id")
    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
