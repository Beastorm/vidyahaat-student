package com.vidyahaat.model.loginuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDetails {
//    @Expose
//    @SerializedName("subject")
//    private List<Null> subject;
//    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("school")
    private String school;
    @Expose
    @SerializedName("id")
    private String id;

//    public List<Null> getSubject() {
//        return subject;
//    }
//
//    public void setSubject(List<Null> subject) {
//        this.subject = subject;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
