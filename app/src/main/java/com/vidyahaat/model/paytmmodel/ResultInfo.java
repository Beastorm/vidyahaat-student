package com.vidyahaat.model.paytmmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultInfo {
    @Expose
    @SerializedName("resultMsg")
    private String resultMsg;
    @Expose
    @SerializedName("resultCode")
    private String resultCode;
    @Expose
    @SerializedName("resultStatus")
    private String resultStatus;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
