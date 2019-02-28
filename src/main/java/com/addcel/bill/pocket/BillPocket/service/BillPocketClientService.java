package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.Authorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseAuthorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;

public interface BillPocketClientService {

    ResponseEnrollmentCard enrollCard(String pan, String expDate, String ct, String apiKey);

    ResponseAuthorization authorizationRequest(Authorization authorizationRequest);

    ResponseAuthorization refundRequest(Integer opId);

    ResponseAuthorization checkTransaction(Integer opId);

}
