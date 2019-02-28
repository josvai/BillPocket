package com.addcel.bill.pocket.BillPocket.model.domain;

import javax.persistence.*;

@Entity
/*@NamedQueries({
        @NamedQuery(
                name = "insertaTransaccion",
                query = "SELECT insertatransaccion_billpocket(:idUsuario, :idApp,"+
                        ":idProveedor, :idProducto, :concepto, :referencia, :cargo, :comision, :idTarjeta,"+
                        ":tipoTarjeta, :imei, :software, :modelo, :lat, :lon, :cardToken, :maskedPan)"
        )
})*/
public class Transaction {

    @Id
    @Column(name = "idUsuario")
    private long idUsuario;

    @Column(name = "idApp")
    private int idApp;

    @Column(name = "idProveedor")
    private int idProveedor;

    @Column(name = "idProducto")
    private int idProducto;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "referencia")
    private String referencia;

    @Column(name = "cargo")
    private double cargo;

    @Column(name = "comision")
    private double comision;

    @Column(name = "idTarjeta")
    private int idTarjeta;

    @Column(name = "tipoTarjeta")
    private String tipoTarjeta;

    @Column(name = "imei")
    private String imei;

    @Column(name = "software")
    private String software;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @Column(name = "cardToken")
    private String cardToken;

    @Column(name = "maskedPan")
    private String maskedPan;

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }
}
