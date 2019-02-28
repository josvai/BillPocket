package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.ResponseAuthorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;
import com.addcel.bill.pocket.BillPocket.bean.TransactionRequest;

public interface PaymentService {

    ResponseEnrollmentCard cardTokenization(Integer idApp, Integer idPais, String idioma,long idUsuario, Integer idTarjeta);

    String cardTokenValidation(Integer idTarjeta);

    ResponseAuthorization authorization(Integer idApp, Integer idPais, String idioma, TransactionRequest transaction);

    ResponseAuthorization refund(Integer idApp, Integer idPais, String idioma, long idTransaccion);

    ResponseAuthorization checkTransaction(Integer idApp, Integer idPais, String idioma, Integer idTransaccion);

}
