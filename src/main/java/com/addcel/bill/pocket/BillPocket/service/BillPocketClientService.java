package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;

public interface BillPocketClientService {

    ResponseEnrollmentCard enrollCard(String pan, String expDate, String ct, String apiKey);

}
