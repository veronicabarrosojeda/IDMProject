/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Logic.Controllers;

import Business.Entities.Notificaciones;
import DataAccess.Persistencia.PNotificaciones;

public class CNotificaciones {

    private static CNotificaciones instanceCNotificaciones;

    public static CNotificaciones getInstanceCNotificaciones() {
        if (instanceCNotificaciones == null) {
            return new CNotificaciones();
        }
        return instanceCNotificaciones;
    }

    public void notificarCambioEstado(long idServicio, String estado, String correo) {
        Notificaciones nuevaNotificacion = new Notificaciones();
        String msg = "<style type='text/css'>body {margin: 0; padding: 0; min-width: 100%!important;}.content {width: 100%; max-width: 600px;}"
                + "</style>" 
                + "<body yahoo bgcolor='#f6f8f1'>"
                + "<table width='100%' bgcolor='#f6f8f1' border='0' cellpadding='0' cellspacing='0'>"                
                + "<tr>"
                + "<td>"
                + "<center><h1>ATuServicio IDM</h1></center>"
                + "</td>"
                + "<tr>"
                + "<td>"
                + "<center><img style='width:150px;' src='https://pbs.twimg.com/profile_images/639187735891329024/zT4n4ld5.jpg'/></center>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>"
                + "<table class='content' align='center' cellpadding='0' cellspacing='0' border='0'>"
                + "<tr>"
                + "<td>"
                + "<h2>Notificación de solicitud de servicio</h2>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td><p style='font-size:18px;'>La solicitud de servicio número: <b>" + idServicio + "</b> se encuentra en estado <b>" + estado + "</b>."
                + "Se continuará informando sobre los cambios de estado de su solicitud.</p>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>";
        nuevaNotificacion.setEnviado("N");
        nuevaNotificacion.setCorreo(correo);
        nuevaNotificacion.setMensaje(msg);
        altaNotificacion(nuevaNotificacion);
    }
    //AGREGAR TRY Y CATCH X NO NOTIFICAR Y EL ERROR AL LOG.

    public Boolean altaNotificacion(Notificaciones notificacion) {

        try {
            PNotificaciones.getInstancePNotificaciones().altaNotificacion(notificacion);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
