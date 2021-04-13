package com.vidyahaat.model.homescreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @Expose
    @SerializedName("about")
    private String about;
    @Expose
    @SerializedName("mockTestDetails")
    private List<MockTestDetails> mockTestDetails;
    @Expose
    @SerializedName("subjectDetails")
    private List<SubjectDetails> subjectDetails;
    @Expose
    @SerializedName("classDetails")
    private ClassDetails classDetails;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<MockTestDetails> getMockTestDetails() {
        return mockTestDetails;
    }

    public void setMockTestDetails(List<MockTestDetails> mockTestDetails) {
        this.mockTestDetails = mockTestDetails;
    }

    public List<SubjectDetails> getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(List<SubjectDetails> subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    public ClassDetails getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassDetails classDetails) {
        this.classDetails = classDetails;
    }
}
