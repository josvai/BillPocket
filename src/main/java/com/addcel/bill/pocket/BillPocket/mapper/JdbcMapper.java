/*package com.addcel.bill.pocket.BillPocket.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface JdbcMapper {

    @Select("SELECT insertatransaccion_app(#{idUsuario}, #{idioma},  "+
            " #{idProveedor}, #{idProducto}, #{concepto}, #{referencia}, #{cargo}, #{comision}, #{idTarjeta}, " +
            " #{tipoTarjeta}, #{imei}, #{software}, #{modelo}, #{lat}, #{lon}, #{cardToken}, #{maskedPan}")
    String insertaTransaccionBillPocket(@Param("idUsuario") long idUsuario,
                                        @Param("idioma") String idioma, @Param("idProveedor") int idProveedor,
                                        @Param("idProducto") int idProducto, @Param("concepto") String concepto,
                                        @Param("referencia") String referencia, @Param("cargo") double cargo,
                                        @Param("comision") double comision, @Param("idTarjeta") int idTarjeta,
                                        @Param("tipoTarjeta") String tipoTarjeta, @Param("imei") String imei,
                                        @Param("software") String software, @Param("lat") double lat,
                                        @Param("lon") double lon, @Param("cardToken") String cardToken,
                                        @Param("maskedPan") String maskedPan);

}*/
