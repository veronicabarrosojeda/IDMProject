/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Persistencia;

import Business.Entities.Notificaciones;
import DataAccess.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Facundo
 */
public class PNotificaciones {

    private static PNotificaciones instancePNotificaciones;

    public static PNotificaciones getInstancePNotificaciones() {
        if (instancePNotificaciones == null) {
            return new PNotificaciones();
        }
        return instancePNotificaciones;
    }

    public Boolean altaNotificacion(Notificaciones notificacion) {

        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.save(notificacion);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
