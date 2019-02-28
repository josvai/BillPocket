package com.addcel.bill.pocket.BillPocket.bean;

public class Refund extends BaseResponse {

    private int opId;

    private String apiKey;

    public int getOpId() {
        return opId;
    }

    public void setOpId(int opId) {
        this.opId = opId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
