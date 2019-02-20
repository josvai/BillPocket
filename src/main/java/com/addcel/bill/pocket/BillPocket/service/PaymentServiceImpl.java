package com.addcel.bill.pocket.BillPocket.service;

import com.addcel.bill.pocket.BillPocket.bean.Authorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseAuthorization;
import com.addcel.bill.pocket.BillPocket.bean.ResponseEnrollmentCard;
import com.addcel.bill.pocket.BillPocket.bean.TransactionRequest;
import com.addcel.bill.pocket.BillPocket.mapper.JdbcMapper;
import com.addcel.bill.pocket.BillPocket.model.domain.BillPocketConfig;
import com.addcel.bill.pocket.BillPocket.model.domain.Tarjeta;
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

    private static final Integer CODE_SUCESS_BP = 1;

    /*@Autowired
    private TarjetasRepository tarjetasRepository;

    @Autowired
    private BillPocketConfigRepository billPocketConfigRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;*/


    private JdbcMapper mapper;

    @Autowired
    private BillPocketClientServiceImpl billPocketClient;

    private Gson GSON = new Gson();

    @Override
    public ResponseEnrollmentCard cardTokenization(Integer idApp, Integer idPais, String idioma,long idUsuario, int idTarjeta) {
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
    public ResponseAuthorization authorization(Integer idApp, Integer idPais, String idioma, long idUsuario,
                                               int idTarjeta, TransactionRequest transaction) {
        ResponseAuthorization response = null;
        ResponseEnrollmentCard enrollmentCard = null;
        Tarjeta tarjeta;
        Authorization authorization = null;
        String callTransaccion = null;
        try {
            callTransaccion = mapper.insertaTransaccionBillPocket(transaction.getIdUsuario(),
                    transaction.getIdioma(), transaction.getIdProveedor(), transaction.getIdProducto(),
                    transaction.getConcepto(), transaction.getReferencia(), transaction.getCargo(),
                    transaction.getComision(), transaction.getIdTarjeta(),transaction.getTipoTarjeta(),
                    transaction.getImei(), transaction.getSoftware(), transaction.getLat(), transaction.getLon(),
                    null, null);

            authorization = GSON.fromJson(callTransaccion, Authorization.class);

            if(authorization.getCardToken() != null) response = billPocketClient.authorizationRequest(
                    authorization, false);
            else {
                enrollmentCard = cardTokenization(idApp, idPais, idioma, idUsuario, idTarjeta);
                if(CODE_SUCESS_BP.equals(enrollmentCard.getCode())){
                    callTransaccion = mapper.insertaTransaccionBillPocket(transaction.getIdUsuario(),
                            transaction.getIdioma(), transaction.getIdProveedor(), transaction.getIdProducto(),
                            transaction.getConcepto(), transaction.getReferencia(), transaction.getCargo(),
                            transaction.getComision(), transaction.getIdTarjeta(),transaction.getTipoTarjeta(),
                            transaction.getImei(), transaction.getSoftware(), transaction.getLat(), transaction.getLon(),
                            enrollmentCard.getCardToken(), enrollmentCard.getMaskedPan());

                    authorization = GSON.fromJson(callTransaccion, Authorization.class);

                    response = billPocketClient.authorizationRequest(authorization, false);
                }
            }
            LOGGER.info("RESPUESTA DE BILL POCKET . {}", GSON.toJson(response));
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String cardTokenValidation(int idTarjeta) {
        return null;
    }

    @Override
    public String refund(int idTransaccion) {
        return null;
    }

    @Override
    public String checkTransaction(int idTransaccion) {
        return null;
    }
}
