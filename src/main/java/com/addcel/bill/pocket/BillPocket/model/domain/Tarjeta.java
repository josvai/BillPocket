package com.addcel.bill.pocket.BillPocket.model.domain;

/*@Entity
@Table(name = "tarjetas_usuario")*/
public class Tarjeta {

//    @Id
//    @Column(name = "idtarjetasusuario")
    private int idTarjeta;

//    @Column(name = "idusuario")
    private long idUsuario;

//    @Column(name = "tarjetas_usuario")
    private String pan;

//    @Column(name = "vigencia")
    private String expDate;

//    @Column(name = "token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    //@Column(name = "ct")
    private String ct;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
