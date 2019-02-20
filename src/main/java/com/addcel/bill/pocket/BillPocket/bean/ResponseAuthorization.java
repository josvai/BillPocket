package com.addcel.bill.pocket.BillPocket.bean;

public class ResponseAuthorization {

    private Integer status;

    private String message;

    private Integer opId;

    private String txnISOCode;

    private String authNumber;

    private String ticketURL;

    private double amount;

    private String maskedPAN;

    private long dateTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getTxnISOCode() {
        return txnISOCode;
    }

    public void setTxnISOCode(String txnISOCode) {
        this.txnISOCode = txnISOCode;
    }

    public String getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    public String getTicketURL() {
        return ticketURL;
    }

    public void setTicketURL(String ticketURL) {
        this.ticketURL = ticketURL;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public void setMaskedPAN(String maskedPAN) {
        this.maskedPAN = maskedPAN;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
