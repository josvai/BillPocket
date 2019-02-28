package com.addcel.bill.pocket.BillPocket.model.repository;

public interface TransaccionDAO {

    String insertaTransaccionBillPocket(long idUsuario, String idioma, int idProveedor, int idProducto, String concepto,
                                        String referencia, double cargo, double comision, int idTarjeta,
                                        String tipoTarjeta, String imei, String software, double lat, double lon,
                                        String cardToken, String maskedPan);

}
