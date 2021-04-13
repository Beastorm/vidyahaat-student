package com.vidyahaat.model.viewprofilepackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("ifsc_code")
    private String ifsc_code;
    @Expose
    @SerializedName("branch")
    private String branch;
    @Expose
    @SerializedName("bank_name")
    private String bank_name;
    @Expose
    @SerializedName("bank_account")
    private String bank_account;
    @Expose
    @SerializedName("pancard_number")
    private String pancard_number;
    @Expose
    @SerializedName("pancard_type")
    private String pancard_type;
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
    @SerializedName("class")
    private String classes;
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

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getPancard_number() {
        return pancard_number;
    }

    public void setPancard_number(String pancard_number) {
        this.pancard_number = pancard_number;
    }

    public String getPancard_type() {
        return pancard_type;
    }

    public void setPancard_type(String pancard_type) {
        this.pancard_type = pancard_type;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
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
