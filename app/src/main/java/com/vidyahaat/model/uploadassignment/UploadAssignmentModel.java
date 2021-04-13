package com.vidyahaat.model.uploadassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadAssignmentModel {


    @Expose
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
