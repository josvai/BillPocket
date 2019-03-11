package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.*;
import com.addcel.bill.pocket.BillPocket.model.domain.BillPocketConfig;
import com.addcel.bill.pocket.BillPocket.model.domain.Tarjeta;
import com.addcel.bill.pocket.BillPocket.model.repository.AuthorizationRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class PaymentServiceImpl  implements PaymentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final Integer CODE_SUCESS_BP = 0;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private BillPocketClientServiceImpl billPocketClient;

    private Gson GSON = new Gson();

    @Override
    public ResponseEnrollmentCard cardTokenization(Integer idApp, Integer idPais, String idioma, long idUsuario,
                                                   Integer idTarjeta) {
        Tarjeta tarjeta;
        BillPocketConfig config;
        ResponseEnrollmentCard response = new ResponseEnrollmentCard();
        try {
            //tarjeta = tarjetasRepository.findByIdAndIdUsuario(idTarjeta, idUsuario);
            //config = billPocketConfigRepository.findById(1);
            config = new BillPocketConfig();
            tarjeta = new Tarjeta();
            response = billPocketClient.enrollCard(tarjeta.getPan(), tarjeta.getExpDate(), tarjeta.getCt(), config.getApiKey());
        } catch (HttpClientErrorException e) {
            LOGGER.error("La solicitud contiene sintaxis incorrecta o no puede procesarse.", e);
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            response.setCode(-1);
            response.setMessage("Error 500 - Al invocar Procesar el pago");

        } catch (HttpServerErrorException e) {
            LOGGER.error("El servidor falló al completar una solicitud aparentemente válida.", e);
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            response.setCode(-1);
            response.setMessage("Error 500 - Al invocar Procesar el pago");
        }

        return response;
    }

    @Override
    public ResponseAuthorization authorization(Integer idApp, Integer idPais, String idioma,
                                               TransactionRequest transaction) {
        ResponseAuthorization response = new ResponseAuthorization();
        ResponseEnrollmentCard enrollmentCard = null;
        Tarjeta tarjeta;
        Authorization authorization = null;
        String callTransaccion = null;
        BaseResponse actualizaTran = null;
        LOGGER.info("SOLICITANDO LA AUTORIZACION BANCARIA CON BILL POCKET - {}", GSON.toJson(transaction));
        try {
            callTransaccion = authorizationRepository.insertatransaccion_billpocket(String.valueOf(transaction.getIdUsuario()),
                    idApp, transaction.getIdioma(), transaction.getIdProveedor(), 9, transaction.getIdProducto(),
                    transaction.getConcepto(), transaction.getReferencia(), transaction.getCargo(),
                    transaction.getComision(), transaction.getIdTarjeta(),transaction.getTipoTarjeta(),
                    transaction.getImei(), transaction.getSoftware(), transaction.getModelo(), transaction.getLat(),
                    transaction.getLon(),
                    null, null);
            LOGGER.info("RESPUESTA DE DB: {}", callTransaccion);
            if(callTransaccion != null){
                authorization = GSON.fromJson(callTransaccion, Authorization.class);
                if(authorization.getCardToken() != null){
                    LOGGER.info("TARJETA TOKENIZADA. SE ENVIA A PAGO - {}", GSON.toJson(authorization));
                    response = billPocketClient.authorizationRequest(authorization);
                    LOGGER.info("RESPUESTA DE AUTORIZACION - {}", GSON.toJson(response));
                    LOGGER.info("ACTUALIZA TRANSACION PARAMS - " +
                            " ID TRANSACCION: "+authorization.getIdTransaccion()+
                            " MESSAGE: "+response.getMessage()+
                            " OPID: "+response.getOpId()+
                            " TXNISOCODE: "+response.getTxnISOCode()+
                            " AUTH NUMBER: "+response.getAuthNumber()+
                            " TICKET: "+response.getTicketUrl()+
                            " MASKED PAN: "+response.getMaskedPAN());
                    callTransaccion = authorizationRepository.actualizatransaccion_billpocket(authorization.getIdTransaccion(),
                            response.getStatus(), response.getMessage(), response.getOpId(), response.getTxnISOCode(),
                            response.getAuthNumber(), response.getTicketUrl(), response.getMaskedPAN());
                    LOGGER.info("RESPUESTA DE ACTUALIZACION DE BD - {}", callTransaccion);
                    actualizaTran = GSON.fromJson(callTransaccion, BaseResponse.class);
                } else {
                    if(authorization.getCode() == 0){
                        LOGGER.info("TARJETA NO TOKENIZADA - SOLICITANDO TOKENIZACION - {}", GSON.toJson(authorization));
                        enrollmentCard = billPocketClient.enrollCard(authorization.getPan(), authorization.getExpDate(),
                                authorization.getCvv2(), "");
                        LOGGER.info("RESPUESTA DE TOKENIZACION DE TARJETA - {}", GSON.toJson(enrollmentCard));
                        if(CODE_SUCESS_BP == enrollmentCard.getCode()){
                            LOGGER.info("INSERTANDO TRANSACCION - ID USUARIO - {}", transaction.getIdUsuario());
                            callTransaccion = authorizationRepository.insertatransaccion_billpocket(String.valueOf(transaction.getIdUsuario()),
                                    idApp, transaction.getIdioma(), transaction.getIdProveedor(), 9, transaction.getIdProducto(),
                                    transaction.getConcepto(), transaction.getReferencia(), transaction.getCargo(),
                                    transaction.getComision(), transaction.getIdTarjeta(),transaction.getTipoTarjeta(),
                                    transaction.getImei(), transaction.getSoftware(),transaction.getModelo(),
                                    transaction.getLat(), transaction.getLon(),
                                    enrollmentCard.getCardToken(), enrollmentCard.getMaskedPan());
                            LOGGER.info("RESPUESTA DE DB: {}", callTransaccion);
                            if(callTransaccion != null){
                                authorization.setCardToken(enrollmentCard.getCardToken());
                                LOGGER.info("DATOS DE TRANSACCION - {} - CARDTOKEN - {}", GSON.toJson(transaction),
                                        enrollmentCard.getCardToken()+" MASKED CARD -"+ enrollmentCard.getMaskedPan());
                                authorization = GSON.fromJson(callTransaccion, Authorization.class);
                                response = billPocketClient.authorizationRequest(authorization);
                                LOGGER.info("ACTUALIZA TRANSACION PARAMS - " +
                                        " ID TRANSACCION: "+authorization.getIdTransaccion()+
                                        " MESSAGE: "+response.getMessage()+
                                        " OPID: "+response.getOpId()+
                                        " TXNISOCODE: "+response.getTxnISOCode()+
                                        " AUTH NUMBER: "+response.getAuthNumber()+
                                        " TICKET: "+response.getTicketUrl()+
                                        " MASKED PAN: "+response.getMaskedPAN());
                                authorizationRepository.actualizatransaccion_billpocket(authorization.getIdTransaccion(),
                                        response.getStatus(), response.getMessage(), response.getOpId(),
                                        response.getAuthNumber(), response.getTxnISOCode(),
                                        response.getTicketUrl(), response.getMaskedPAN());
                                LOGGER.info("RESPUESTA DE ACTUALIZACION DE BD - {}", callTransaccion);
                                actualizaTran = GSON.fromJson(callTransaccion, BaseResponse.class);
                            } else {
                                response.setCode(-50);
                                response.setMessage("ERROR AL GUARDAR LA TRANSACION EN BD");
                            }
                        } else {
                            response.setCode(enrollmentCard.getCode());
                            response.setMessage(enrollmentCard.getMessage());
                        }
                    } else {
                        response.setCode(authorization.getCode());
                        response.setMessage(authorization.getMessage());
                    }
                }
                LOGGER.info("RESPUESTA DE BILL POCKET . {}", GSON.toJson(response));
            } else {
                response = new ResponseAuthorization();
                response.setCode(-50);
                response.setMessage("ERROR AL GUARDAR LA TRANSACION EN BD");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String cardTokenValidation(Integer idTarjeta) {
        return null;
    }

    @Override
    public ResponseAuthorization refund(Integer idApp, Integer idPais, String idioma, long idTransaccion) {
        ResponseAuthorization response = new ResponseAuthorization();
        String respBD = null;
        Refund validate = null;
        try{
            LOGGER.info("SELECT reversatransaccion_billpocket("+idApp+", 9, "+idTransaccion+")");
            respBD = authorizationRepository.reversatransaccion_billpocket(idApp, 9, idTransaccion);
            LOGGER.info("RESPUESTA DE REVERSA TRANSACCION DB - {}", respBD);
            if(respBD != null){
                validate = GSON.fromJson(respBD, Refund.class);
                if(validate.getCode() == 0){
                    response = billPocketClient.refundRequest(validate.getOpId());
                    LOGGER.info("RESPUESTA DE BILL POCKET - {}", GSON.toJson(response));
                    respBD = authorizationRepository.actualizareverso_billpocket(idTransaccion, response.getStatus(),
                            response.getMessage(),
                            response.getOpId() != null ? response.getOpId() : validate.getOpId(),
                            response.getTxnISOCode(),
                            response.getAuthNumber() != null ? response.getAuthNumber() : "",
                            response.getMaskedPAN() != null ? response.getMaskedPAN() : "");
                    LOGGER.info("RESPUESTA DE ACTUALIZA REVERSA DB - {}", respBD);
                } else {
                    response = new ResponseAuthorization();
                    response.setCode(validate.getCode());
                    response.setMessage(validate.getMessage());
                }
            } else {
                response = new ResponseAuthorization();
                response.setCode(-5000);
                response.setMessage("ERROR EN BD");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ResponseAuthorization checkTransaction(Integer idApp, Integer idPais, String idioma, Integer opId) {
        ResponseAuthorization response = new ResponseAuthorization();
        try {
            response = billPocketClient.checkTransaction(opId);
        } catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
