package DataAccess.Persistencia;

import Business.Entities.Tiposervicio;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PTipoServicio {

    private static PTipoServicio newInstancePTipoServicio;

    public static PTipoServicio newInstancePTipoServicio() {
        if (newInstancePTipoServicio == null) {
            newInstancePTipoServicio = new PTipoServicio();
        }
        return newInstancePTipoServicio;
    }

    public dtoMensaje altaTipoServicio(Tiposervicio tipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (tipoServicio != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    s.save(tipoServicio);
                    tx.commit();
                    s.close();
                    msg.tipo = Status.success.toString();
                    msg.msg = "Tipo de Servicio ingresado correctamente";
            }
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje bajaTipoServicio(Integer idTipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE tiposervicio SET estado= 0 WHERE idTipoServicio = :idTipoServicio").addEntity(Tiposervicio.class);
            q.setParameter("idTipoServicio", idTipoServicio);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError("Error al intentar conectar con la base de datos");
        }
        return msg;
    }

    public dtoMensaje modificarTipoServicio(Tiposervicio tipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (tipoServicio != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    Query q = s.createSQLQuery("Update tiposervicio SET nombre = :nombre, descripcion = :descripcion, idArea = :idArea WHERE idTipoServicio = :idTipoServicio").addEntity(Tiposervicio.class);
                    q.setParameter("idTipoServicio", tipoServicio.getIdTipoServicio());
                    q.setParameter("nombre", tipoServicio.getNombre());
                    q.setParameter("descripcion", tipoServicio.getDescripcion());
                    q.setParameter("idArea", tipoServicio.getArea().getIdArea());
                    q.executeUpdate();
                    tx.commit();
                    s.close();
            }
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public List<Tiposervicio> listarTipoServicios() {
        List<Tiposervicio> colTipoServicios = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from tiposervicio where estado = 1").addEntity(Tiposervicio.class);
            colTipoServicios = (List<Tiposervicio>) q.list();
        } catch (Exception ex) {
            throw ex;
        }
        return colTipoServicios;
    }

    public Tiposervicio getTipoServicioById(Integer idTipoServicio) {
        Tiposervicio tipoServicio = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from tiposervicio where idTipoServicio = :idTipoServicio and estado = 1").addEntity(Tiposervicio.class);
            q.setParameter("idTipoServicio", idTipoServicio);
            tipoServicio = (Tiposervicio) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return tipoServicio;
    }

    public boolean existeTipoServicio(String nombre) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from tiposervicio where nombre = :name").addEntity(Tiposervicio.class);
            q.setParameter("name", nombre);
            Tiposervicio tipoServicio = (Tiposervicio) q.uniqueResult();
            if (tipoServicio != null) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }

}
