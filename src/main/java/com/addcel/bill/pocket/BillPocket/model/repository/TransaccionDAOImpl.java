package com.addcel.bill.pocket.BillPocket.model.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TransaccionDAOImpl implements TransaccionDAO{

    final static Logger logger = LoggerFactory.getLogger(TransaccionDAOImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public TransaccionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String insertaTransaccionBillPocket(long idUsuario, String idioma, int idProveedor, int idProducto,
                                               String concepto, String referencia, double cargo, double comision,
                                               int idTarjeta, String tipoTarjeta, String imei, String software,
                                               double lat, double lon, String cardToken, String maskedPan) {
        String result = null;
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query query = session.getNamedQuery("insertaTransaccion");
            query.setParameter("idUsuario", String.valueOf(idUsuario));
            query.setParameter("idioma", idioma);
            query.setParameter("idProveedor", String.valueOf(idProveedor));
            query.setParameter("idProducto", String.valueOf(idProducto));
            query.setParameter("concepto", concepto);
            query.setParameter("referencia", referencia);
            query.setParameter("cargo", String.valueOf(cargo));
            query.setParameter("comision", String.valueOf(comision));
            query.setParameter("idTarjeta", String.valueOf(idTarjeta));
            query.setParameter("tipoTarjeta", tipoTarjeta);
            query.setParameter("imei", imei);
            query.setParameter("software", software);
            query.setParameter("lat", String.valueOf(lat));
            query.setParameter("lon", String.valueOf(lon));
            query.setParameter("cardToken", cardToken);
            query.setParameter("maskedPan", maskedPan);
            result = (String) query.uniqueResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
