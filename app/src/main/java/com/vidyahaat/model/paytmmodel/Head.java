package com.vidyahaat.model.paytmmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Head {
    @Expose
    @SerializedName("signature")
    private String signature;
    @Expose
    @SerializedName("version")
    private String version;
    @Expose
    @SerializedName("responseTimestamp")
    private String responseTimestamp;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }
}
