package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.Authorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseAuthorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class BillPocketClientServiceImpl implements BillPocketClientService{

    /**
     * Logging
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BillPocketClientServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEnrollmentCard enrollCard(String pan, String expDate, String ct, String apiKey) {
        ResponseEnrollmentCard response = new ResponseEnrollmentCard();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("pan", pan);
            map.add("cvv2", ct);
            map.add("expDate",  expDate);
            map.add("apiKey",  apiKey);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<ResponseEnrollmentCard> respEnrollCard = restTemplate.postForEntity("https://test.bpckt.com/scops/card",
                    request, ResponseEnrollmentCard.class);
            response = respEnrollCard.getBody();
            LOGGER.info("Response bill pocket: " + response);
        } catch (HttpClientErrorException e) {
            LOGGER.error("La solicitud contiene sintaxis incorrecta o no puede procesarse.", e);
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            response.setCode(-1);
            response.setMessage(e.getLocalizedMessage());

        } catch (HttpServerErrorException e) {
            LOGGER.error("El servidor falló al completar una solicitud aparentemente válida.", e);
            LOGGER.info("HTTP Status Code: " + e.getStatusCode());
            response.setCode(-1);
            response.setMessage(e.getLocalizedMessage());
        }
        return response;
    }

    public ResponseAuthorization authorizationRequest(Authorization authorizationRequest, boolean isAmex){
        ResponseAuthorization response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("apiKey", authorizationRequest.getApiKey());
            map.add("cardToken", authorizationRequest.getCardToken());
            map.add("amount", String.valueOf(authorizationRequest.getAmount()));
            map.add("txnType", authorizationRequest.getTxnType());
            map.add("contractNumber", authorizationRequest.getContractNumber());
            map.add("paymentPlan", String.valueOf(authorizationRequest.getPaymentPlan()));
            if(isAmex){
                map.add("amexCustPostalCode", authorizationRequest.getAmexCustPostalCode());
                map.add("amexCustAddress", authorizationRequest.getAmexCustAddress());
                map.add("amexCustFirstName", authorizationRequest.getAmexCustFirstName());
                map.add("amexCustLastName", authorizationRequest.getAmexCustLastName());
                map.add("amexCustEmailAddress", authorizationRequest.getAmexCustEmailAddress());
                map.add("amexCustHostServerNm", authorizationRequest.getAmexCustEmailAddress());
                map.add("amexCustBrowserTypDescTxt", authorizationRequest.getAmexCustBrowserTypDescTxt());
                map.add("amexShipToCtryCd", authorizationRequest.getAmexShipToCtryCd());
                map.add("amexMerSKUNbr", authorizationRequest.getAmexMerSKUNbr());
                map.add("amexCustIPAddr", authorizationRequest.getAmexCustIPAddr());
                map.add("amexCustIdPhoneNbr", authorizationRequest.getAmexCustIdPhoneNbr());
                map.add("amexCallTypId", authorizationRequest.getAmexCallTypId());
            }
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<ResponseAuthorization> respEnrollCard = restTemplate.postForEntity("https://test.bpckt.com/scops/card",
                    request, ResponseAuthorization.class);
            response = respEnrollCard.getBody();

        } catch (Exception e){

        }
        return response;
    }
}
