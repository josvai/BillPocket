package com.addcel.bill.pocket.BillPocket.model.repository;

import com.addcel.bill.pocket.BillPocket.model.domain.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorizationRepository  extends CrudRepository<Transaction, Long> {

    @Query(value = "SELECT insertatransaccion_billpocket(:nidusuario, :nidaplicacion, :nidioma, "+
            ":nid_proveedor, :nid_integrador, :nid_producto, :nbit_concepto, :nreferencia, :nbit_cargo, :ncomision, " +
            ":nbit_card_id,"+
            ":ncardtype, :nimei, :nsoftware, :nmodelo, :nlat, :nlon, :ncardToken, :nmaskedPan)", nativeQuery = true)
    String insertatransaccion_billpocket(@Param("nidusuario") String idUsuario,
                                  @Param("nidaplicacion") int idApp, @Param("nidioma") String idioma,
                                  @Param("nid_proveedor") int idProveedor, @Param("nid_integrador") int idIntegrador,
                                  @Param("nid_producto") int idProducto, @Param("nbit_concepto") String concepto,
                                  @Param("nreferencia") String referencia, @Param("nbit_cargo") double cargo,
                                  @Param("ncomision") double comision, @Param("nbit_card_id") int idTarjeta,
                                  @Param("ncardtype") String tipoTarjeta, @Param("nimei") String imei,
                                  @Param("nsoftware") String software, @Param("nmodelo") String modelo,
                                         @Param("nlat") double lat,
                                  @Param("nlon") double lon, @Param("ncardToken") String cardToken,
                                  @Param("nmaskedPan") String maskedPan);



    @Query(value = "SELECT actualizatransaccion_billpocket(:idTransaccion, :status, :message, :opId, " +
            " :txnISOCode, :authNumber, :ticketUrl, :maskedPAN)", nativeQuery = true)
    String actualizatransaccion_billpocket(@Param("idTransaccion") long idTransaccion, @Param("status") int status,
                                           @Param("message")String message, @Param("opId") long opId,
                                           @Param("txnISOCode") String txnISOCode, @Param("authNumber") String authNumber,
                                           @Param("ticketUrl") String ticketUrl, @Param("maskedPAN") String maskedPAN);

    @Query(value = "SELECT reversatransaccion_billpocket(:idApp, :idIntegrador, :idTransaccion)", nativeQuery = true)
    String reversatransaccion_billpocket(@Param("idApp") long idApp,
                                         @Param("idIntegrador") long idIntegrador,
                                         @Param("idTransaccion") long idTransaccion);

    @Query(value = "SELECT actualizareverso_billpocket(:idTransaccion, :nstatus, :message, :opId, :txnISOCode , " +
            " :authNumber, :maskedPan)", nativeQuery = true)
    String actualizareverso_billpocket(@Param("idTransaccion") long idTransaccion,
                                       @Param("nstatus") int status,
                                       @Param("message") String message,
                                       @Param("opId") int opId,
                                       @Param("txnISOCode") String txnISOCode,
                                       @Param("authNumber") String authNumber,
                                       @Param("maskedPan") String maskedPan);
}
