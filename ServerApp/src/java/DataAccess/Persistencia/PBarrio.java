package DataAccess.Persistencia;

import Business.Entities.Barrio;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PBarrio {

    private static PBarrio newInstancePBarrio;

    public static PBarrio newInstancePBarrio() {
        if (newInstancePBarrio == null) {
            newInstancePBarrio = new PBarrio();
        }
        return newInstancePBarrio;
    }

    public dtoMensaje altaBarrio(Barrio barrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (barrio != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    s.save(barrio);
                    tx.commit();
                    s.close();
                    msg.tipo = Status.success.toString();
                    msg.msg = "Barrio ingresado correctamente";
            }
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje bajaBarrio(Integer idBarrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE barrio SET estado= 0 WHERE idBarrio = :idBarrio").addEntity(Barrio.class);
            q.setParameter("idBarrio", idBarrio);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError("Error al intentar conectar con la base de datos");
        }
        return msg;
    }

    public dtoMensaje modificarBarrio(Barrio barrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (barrio != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    Query q = s.createSQLQuery("Update barrio SET nombre = :nombre, descripcion = :descripcion, idMunicipio = :idMunicipio WHERE idBarrio = :idBarrio").addEntity(Barrio.class);
                    q.setParameter("idBarrio", barrio.getIdBarrio());
                    q.setParameter("nombre", barrio.getNombre());
                    q.setParameter("descripcion", barrio.getDescripcion());
                    q.setParameter("idMunicipio", barrio.getMunicipio().getIdMunicipio());
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

    public List<Barrio> listarBarrios() {
        List<Barrio> colBarrios = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from barrio where estado = 1").addEntity(Barrio.class);
            colBarrios = (List<Barrio>) q.list();
        } catch (Exception ex) {
            throw ex;
        }
        return colBarrios;
    }

    public Barrio getBarrioById(Integer idBarrio) {
        Barrio barrio = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from barrio where idBarrio = :idBarrio and estado = 1").addEntity(Barrio.class);
            q.setParameter("idBarrio", idBarrio);
            barrio = (Barrio) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return barrio;
    }

    public boolean existeBarrio(String nombre) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from barrio where nombre = :name").addEntity(Barrio.class);
            q.setParameter("name", nombre);
            Barrio barrio = (Barrio) q.uniqueResult();
            if (barrio != null) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }
    
}