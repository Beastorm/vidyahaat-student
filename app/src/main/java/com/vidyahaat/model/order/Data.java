package com.vidyahaat.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @Expose
    @SerializedName("created")
    private String created;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("check_sum_hash")
    private String check_sum_hash;
    @Expose
    @SerializedName("bank_name")
    private String bank_name;
    @Expose
    @SerializedName("bank_txn_id")
    private String bank_txn_id;
    @Expose
    @SerializedName("gateway_name")
    private String gateway_name;
    @Expose
    @SerializedName("txn_date")
    private String txn_date;
    @Expose
    @SerializedName("currency")
    private String currency;
    @Expose
    @SerializedName("payment_mode")
    private String payment_mode;
    @Expose
    @SerializedName("mid")
    private String mid;
    @Expose
    @SerializedName("paid_amt")
    private String paid_amt;
    @Expose
    @SerializedName("txn_id")
    private String txn_id;
    @Expose
    @SerializedName("quiz_id")
    private String quiz_id;
    @Expose
    @SerializedName("student_id")
    private String student_id;
    @Expose
    @SerializedName("order_id")
    private String order_id;
    @Expose
    @SerializedName("id")
    private String id;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheck_sum_hash() {
        return check_sum_hash;
    }

    public void setCheck_sum_hash(String check_sum_hash) {
        this.check_sum_hash = check_sum_hash;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_txn_id() {
        return bank_txn_id;
    }

    public void setBank_txn_id(String bank_txn_id) {
        this.bank_txn_id = bank_txn_id;
    }

    public String getGateway_name() {
        return gateway_name;
    }

    public void setGateway_name(String gateway_name) {
        this.gateway_name = gateway_name;
    }

    public String getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(String txn_date) {
        this.txn_date = txn_date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPaid_amt() {
        return paid_amt;
    }

    public void setPaid_amt(String paid_amt) {
        this.paid_amt = paid_amt;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
