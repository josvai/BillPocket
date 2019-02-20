package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.ResponseAuthorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;
import com.addcel.bill.pocket.BillPocket.bean.TransactionRequest;

public interface PaymentService {

    ResponseEnrollmentCard cardTokenization(Integer idApp, Integer idPais, String idioma,long idUsuario, int idTarjeta);

    String cardTokenValidation(int idTarjeta);

    ResponseAuthorization authorization(Integer idApp, Integer idPais, String idioma, long idUsuario, int idTarjeta, TransactionRequest transaction);

    String refund(int idTransaccion);

    String checkTransaction(int idTransaccion);

}
