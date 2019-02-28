package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.*;
import com.addcel.utils.AddcelCrypto;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BillPocketClientServiceImpl implements BillPocketClientService{

    /**
     * Logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BillPocketClientServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private Gson GSON = new Gson();

    @Override
    public ResponseEnrollmentCard enrollCard(String pan, String expDate, String ct, String apiKey) {
        ResponseEnrollmentCard response = new ResponseEnrollmentCard();
        RequestEnrollmenCard requestEnrollmenCard = null;
        String exp = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            exp = AddcelCrypto.decryptTarjeta(expDate);
            exp = exp.replace("/", "");
            exp = exp.substring(2, exp.length())+exp.substring(0, 2);
            LOGGER.info("FECHA VIGENCIA FORMATO BILL POCKET: "+exp);
            requestEnrollmenCard = new RequestEnrollmenCard();
            requestEnrollmenCard.setApiKey("y6cU_oNjQ0ZUlpNQNaYNmgAAEsyH-ivxXeARF0MZP7qQesYSAAANJw");
            requestEnrollmenCard.setPan(AddcelCrypto.decryptTarjeta(pan));
            requestEnrollmenCard.setExpDate(exp);
            requestEnrollmenCard.setCvv2(AddcelCrypto.decryptTarjeta(ct));
            HttpEntity<RequestEnrollmenCard> request = new HttpEntity<>(requestEnrollmenCard, headers);

            ResponseEntity<ResponseEnrollmentCard> respEnrollCard = restTemplate.exchange("https://test.bpckt.com/scops/card",
                    HttpMethod.POST, request, ResponseEnrollmentCard.class);
            response = respEnrollCard.getBody();
            response.setMaskedPan(requestEnrollmenCard.getPan().substring(0, 6)+"********"
                    +requestEnrollmenCard.getPan().substring(requestEnrollmenCard.getPan().length()-4,
                    requestEnrollmenCard.getPan().length()));
            LOGGER.info("Response bill pocket - {}", GSON.toJson(response));
        } catch (HttpClientErrorException e) {
            LOGGER.error("La solicitud contiene sintaxis incorrecta o no puede procesarse.", e);
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            response.setCode(-1);
            response.setMessage(e.getLocalizedMessage());

        } catch (HttpServerErrorException ex) {
            LOGGER.error("El servidor falló al completar una solicitud aparentemente válida.", ex);
            LOGGER.info("HTTP Status Code: " + ex.getStatusCode());
            response.setCode(-1);
            response.setMessage(ex.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ResponseAuthorization authorizationRequest(Authorization authorizationRequest){
        ResponseAuthorization response = null;
        RequestAuthorization requestVM = null;
        RequestAmexAuthorization requestAmex = null;
        try {
            LOGGER.info("SOLICITANDO AUTORIZACION BANCARIA BILL POCKET - {}", GSON.toJson(authorizationRequest));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if(!authorizationRequest.isAmex()){
                requestVM = new RequestAuthorization();
                requestVM.setCardToken(authorizationRequest.getCardToken());
                requestVM.setApiKey("y6cU_oNjQ0ZUlpNQNaYNmgAAEsyH-ivxXeARF0MZP7qQesYSAAANJw");
                requestVM.setAmount(authorizationRequest.getAmount());
                //requestVM.setTxnType("checkIn");
                HttpEntity<RequestAuthorization> request = new HttpEntity<>(requestVM, headers);
                LOGGER.info("SOLICITANDO AUTORIZACION PARA VISA O MASTER CARD - {}", GSON.toJson(requestVM));
                ResponseEntity<String> respEnrollCard = restTemplate.exchange("https://test.bpckt.com/scops/txn",
                        HttpMethod.POST, request, String.class);
                LOGGER.info("RESPUESTA DE BILL POCKET - {}", respEnrollCard.getBody());
                response = GSON.fromJson(respEnrollCard.getBody(), ResponseAuthorization.class);
            } else {
                requestAmex = new RequestAmexAuthorization();
                HttpEntity<Authorization> request = new HttpEntity<>(authorizationRequest, headers);
                ResponseEntity<String> respEnrollCard = restTemplate.exchange("https://test.bpckt.com/scops/txn",
                        HttpMethod.POST, request, String.class);
                LOGGER.info("RESPUESTA DE BILL POCKET - {}", respEnrollCard.getBody());
                response = GSON.fromJson(respEnrollCard.getBody(), ResponseAuthorization.class);
            }
        } catch (Exception e){
            e.printStackTrace();
            response.setCode(-1);
            response.setMessage(e.getLocalizedMessage());
        }
        return response;
    }

    @Override
    public ResponseAuthorization refundRequest(Integer opId){
        ResponseAuthorization response = null;
        RequestRefund refund = new RequestRefund();
        try {
            LOGGER.info("SOLICITANDO REVERSO BILL POCKET - {}", opId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            refund.setApiKey("y6cU_oNjQ0ZUlpNQNaYNmgAAEsyH-ivxXeARF0MZP7qQesYSAAANJw");
            refund.setOpId(opId);
            HttpEntity<RequestRefund> request = new HttpEntity<>(refund, headers);
            ResponseEntity<String> respReverse = restTemplate.exchange("https://test.bpckt.com/scops/txn/refund",
                    HttpMethod.POST, request, String.class);
            LOGGER.info("RESPUESTA DE BILL POCKET - {}", respReverse.getBody());
            response = GSON.fromJson(respReverse.getBody(), ResponseAuthorization.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ResponseAuthorization checkTransaction(Integer opId){
        ResponseAuthorization response = null;
        RequestRefund refund = new RequestRefund();
        try{
            LOGGER.info("CHECK TRANSACTION BILL POCKET - {}", opId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            refund.setApiKey("y6cU_oNjQ0ZUlpNQNaYNmgAAEsyH-ivxXeARF0MZP7qQesYSAAANJw");
            refund.setOpId(opId);
            HttpEntity<RequestRefund> request = new HttpEntity<>(refund, headers);
            ResponseEntity<String> respReverse = restTemplate.exchange("https://test.bpckt.com/scops/txn/"+opId+
                            "?api_key=y6cU_oNjQ0ZUlpNQNaYNmgAAEsyH-ivxXeARF0MZP7qQesYSAAANJw",
                    HttpMethod.GET, request, String.class);
            LOGGER.info("RESPUESTA CHECK TRANSACTION BILL POCKET - {}", respReverse.getBody());
            response = GSON.fromJson(respReverse.getBody(), ResponseAuthorization.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

}
