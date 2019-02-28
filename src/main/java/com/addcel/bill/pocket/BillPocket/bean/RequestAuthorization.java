package com.addcel.bill.pocket.BillPocket.bean;

public class RequestAuthorization {

    private String apiKey;

    private String cardToken;

    private double amount;

    private String txnType;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
}
