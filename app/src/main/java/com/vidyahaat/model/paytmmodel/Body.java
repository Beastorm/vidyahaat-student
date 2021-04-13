package com.vidyahaat.model.paytmmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {
    @Expose
    @SerializedName("authenticated")
    private boolean authenticated;
    @Expose
    @SerializedName("isPromoCodeValid")
    private boolean isPromoCodeValid;
    @Expose
    @SerializedName("txnToken")
    private String txnToken;
    @Expose
    @SerializedName("resultInfo")
    private ResultInfo resultInfo;

    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean getIsPromoCodeValid() {
        return isPromoCodeValid;
    }

    public void setIsPromoCodeValid(boolean isPromoCodeValid) {
        this.isPromoCodeValid = isPromoCodeValid;
    }

    public String getTxnToken() {
        return txnToken;
    }

    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
