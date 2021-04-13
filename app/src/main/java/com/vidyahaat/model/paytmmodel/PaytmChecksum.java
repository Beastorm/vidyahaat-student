package com.vidyahaat.model.paytmmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaytmChecksum {


    @Expose
    @SerializedName("body")
    private Body body;
    @Expose
    @SerializedName("head")
    private Head head;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}
