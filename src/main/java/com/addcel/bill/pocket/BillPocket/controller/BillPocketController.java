package com.addcel.bill.pocket.BillPocket.controller;

import com.addcel.bill.pocket.BillPocket.bean.BaseResponse;
import com.addcel.bill.pocket.BillPocket.bean.BillPocketTransaction;
import com.addcel.bill.pocket.BillPocket.bean.TransactionRequest;
import com.addcel.bill.pocket.BillPocket.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BillPocketController {

    @Autowired
    private PaymentServiceImpl paymentService;

    private final static String PATH_ENROLL_CARD = "/{idApp}/{idPais}/{idioma}/enroll/card";

    private final static String PATH_AUTHORIZATION = "/{idApp}/{idPais}/{idioma}/authorization";


    @RequestMapping(value = PATH_ENROLL_CARD, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> verifyEnrollmentCardholder(@PathVariable Integer idApp,
                                                             @PathVariable Integer idPais,
                                                             @PathVariable String idioma,
                                                             @RequestParam int idTarjeta,
                                                             @RequestParam long idUsuario,
                                                             @RequestBody BillPocketTransaction transaction) {
        ResponseEntity<Object> response = null;
        try {
            response = new ResponseEntity<>(paymentService.cardTokenization(idApp, idPais, idioma, idUsuario, idTarjeta),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponse error = new BaseResponse();
            error.setCode(-10);
            error.setMessage("ERROR: "+e.getLocalizedMessage());
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(value = PATH_AUTHORIZATION, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Object> authorizationRequest(@PathVariable Integer idApp,
                                                       @PathVariable Integer idPais,
                                                       @PathVariable String idioma,
                                                       @RequestParam int idTarjeta,
                                                       @RequestParam long idUsuario,
                                                       @RequestBody TransactionRequest transaction) {
        ResponseEntity<Object> response = null;
        try {
            response = new ResponseEntity<>(paymentService.authorization(idApp, idPais, idioma, idUsuario, idTarjeta, transaction),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponse error = new BaseResponse();
            error.setCode(-10);
            error.setMessage("ERROR: "+e.getLocalizedMessage());
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
