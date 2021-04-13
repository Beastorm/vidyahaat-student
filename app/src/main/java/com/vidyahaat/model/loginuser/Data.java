package com.vidyahaat.model.loginuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @Expose
    @SerializedName("classDetails")
    private List<ClassDetails> classDetails;
    @Expose
    @SerializedName("schoolDetails")
    private List<SchoolDetails> schoolDetails;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("gender")
    private String gender;
    @Expose
    @SerializedName("age")
    private String age;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("mobile")
    private String mobile;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("school")
    private String school;
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("pancard_type")
    private String panCardType;
    @Expose
    @SerializedName("pancard_number")
    private String panCardNumber;

    public String getPanCardType() {
        return panCardType;
    }

    public void setPanCardType(String panCardType) {
        this.panCardType = panCardType;
    }

    public String getPanCardNumber() {
        return panCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {
        this.panCardNumber = panCardNumber;
    }

    public List<ClassDetails> getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(List<ClassDetails> classDetails) {
        this.classDetails = classDetails;
    }

    public List<SchoolDetails> getSchoolDetails() {
        return schoolDetails;
    }

    public void setSchoolDetails(List<SchoolDetails> schoolDetails) {
        this.schoolDetails = schoolDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
