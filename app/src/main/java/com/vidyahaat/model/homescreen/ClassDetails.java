package com.vidyahaat.model.homescreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDetails {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("subject")
    private String subject;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("school")
    private String school;
    @Expose
    @SerializedName("id")
    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
