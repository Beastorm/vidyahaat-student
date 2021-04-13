package com.vidyahaat.model.viewassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @Expose
    @SerializedName("assignmentDetails")
    private List<AssignmentDetails> assignmentDetails;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("grades")
    private String grades;
    @Expose
    @SerializedName("teacher")
    private String teacher;
    @Expose
    @SerializedName("doc")
    private String doc;
    @Expose
    @SerializedName("student")
    private String student;
    @Expose
    @SerializedName("assignment")
    private String assignment;
    @Expose
    @SerializedName("id")
    private String id;

    public List<AssignmentDetails> getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(List<AssignmentDetails> assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
