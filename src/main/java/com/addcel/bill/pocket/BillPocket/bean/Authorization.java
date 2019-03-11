package com.addcel.bill.pocket.BillPocket.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Authorization extends BaseResponse{

    private String apiKey;

    private String cardToken;

    private double amount;

    private String txnType;

    private String contractNumber;

    private int paymentPlan;

    private String amexCustPostalCode;

    private String amexCustAddress;

    private String amexCustFirstName;

    private String amexCustLastName;

    private String amexCustEmailAddress;

    private String amexCustHostServerNm;

    private String amexCustBrowserTypDescTxt;

    private String amexShipToCtryCd;

    private String amexShipMthdCd;

    private String amexMerSKUNbr;

    private String amexCustIPAddr;

    private String amexCustIdPhoneNbr;

    private String amexCallTypId;

    private long idBitacora;

    private String pan;

    private String cvv2;

    private String expDate;

    private boolean amex;

    private long idTransaccion;

    public long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(int paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getAmexCustPostalCode() {
        return amexCustPostalCode;
    }

    public void setAmexCustPostalCode(String amexCustPostalCode) {
        this.amexCustPostalCode = amexCustPostalCode;
    }

    public String getAmexCustAddress() {
        return amexCustAddress;
    }

    public void setAmexCustAddress(String amexCustAddress) {
        this.amexCustAddress = amexCustAddress;
    }

    public String getAmexCustFirstName() {
        return amexCustFirstName;
    }

    public void setAmexCustFirstName(String amexCustFirstName) {
        this.amexCustFirstName = amexCustFirstName;
    }

    public String getAmexCustLastName() {
        return amexCustLastName;
    }

    public void setAmexCustLastName(String amexCustLastName) {
        this.amexCustLastName = amexCustLastName;
    }

    public String getAmexCustEmailAddress() {
        return amexCustEmailAddress;
    }

    public void setAmexCustEmailAddress(String amexCustEmailAddress) {
        this.amexCustEmailAddress = amexCustEmailAddress;
    }

    public String getAmexCustHostServerNm() {
        return amexCustHostServerNm;
    }

    public void setAmexCustHostServerNm(String amexCustHostServerNm) {
        this.amexCustHostServerNm = amexCustHostServerNm;
    }

    public String getAmexCustBrowserTypDescTxt() {
        return amexCustBrowserTypDescTxt;
    }

    public void setAmexCustBrowserTypDescTxt(String amexCustBrowserTypDescTxt) {
        this.amexCustBrowserTypDescTxt = amexCustBrowserTypDescTxt;
    }

    public String getAmexShipToCtryCd() {
        return amexShipToCtryCd;
    }

    public void setAmexShipToCtryCd(String amexShipToCtryCd) {
        this.amexShipToCtryCd = amexShipToCtryCd;
    }

    public String getAmexShipMthdCd() {
        return amexShipMthdCd;
    }

    public void setAmexShipMthdCd(String amexShipMthdCd) {
        this.amexShipMthdCd = amexShipMthdCd;
    }

    public String getAmexMerSKUNbr() {
        return amexMerSKUNbr;
    }

    public void setAmexMerSKUNbr(String amexMerSKUNbr) {
        this.amexMerSKUNbr = amexMerSKUNbr;
    }

    public String getAmexCustIPAddr() {
        return amexCustIPAddr;
    }

    public void setAmexCustIPAddr(String amexCustIPAddr) {
        this.amexCustIPAddr = amexCustIPAddr;
    }

    public String getAmexCustIdPhoneNbr() {
        return amexCustIdPhoneNbr;
    }

    public void setAmexCustIdPhoneNbr(String amexCustIdPhoneNbr) {
        this.amexCustIdPhoneNbr = amexCustIdPhoneNbr;
    }

    public String getAmexCallTypId() {
        return amexCallTypId;
    }

    public void setAmexCallTypId(String amexCallTypId) {
        this.amexCallTypId = amexCallTypId;
    }

    public long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public boolean isAmex() {
        return amex;
    }

    public void setAmex(boolean amex) {
        this.amex = amex;
    }
}
