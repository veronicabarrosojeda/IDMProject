package enivarmail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objectos.notificaciones;

public class Controller {

    public List<notificaciones> getNotificaciones() {
        List<notificaciones> colNotificaciones = new ArrayList<notificaciones>();

        ConexionSql conect = new ConexionSql();
        Connection conn = conect.Conectar();
        ResultSet result = conect.consultar(conn,"select * from notificaciones where enviado = 'N'");
        try {
            if (result != null) {
                while (result.next()) {
                    notificaciones noti = new notificaciones();
                    noti.idNotificaciones = result.getLong("idNotificaciones");
                    noti.correo = result.getString("correo");
                    noti.asunto = result.getString("asunto");
                    noti.mensaje = result.getString("mensaje");
                    noti.enviado = result.getString("enviado");
                    colNotificaciones.add(noti);
                }
            }
        } catch (SQLException ex) {
           return null;
        }
        
        try {
            conn.close();
        } catch (SQLException ex) {
            //Fallo Close
        }
        return colNotificaciones;
    }

    public void updateNotificaciones(List<Long> colNoti, String estado) {
        ConexionSql conect = new ConexionSql();
        Connection conn = conect.Conectar();
        for (int i = 0; i < colNoti.size(); i++) {
            conect.ejecutar(conn,"update notificaciones set enviado = '" + estado + "' where idNotificaciones = " + colNoti.get(i));
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            //Fallo close
        }
    }

}
