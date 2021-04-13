package com.vidyahaat.model.loginuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolDetails {
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("image")
    private String image;
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
    @SerializedName("id")
    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
