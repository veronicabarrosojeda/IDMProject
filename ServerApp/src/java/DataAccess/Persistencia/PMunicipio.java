package DataAccess.Persistencia;

import Business.Entities.Barrio;
import Business.Entities.Municipio;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PMunicipio {

    private static PMunicipio instancePmunicipio;

    public static PMunicipio newInstancePMunicipio() {
        if (instancePmunicipio == null) {
            instancePmunicipio = new PMunicipio();
        }
        return instancePmunicipio;
    }

    public dtoMensaje altaMunicipio(Municipio mun) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (mun != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    s.save(mun);
                    tx.commit();
                    s.close();
                    msg.msg = "Municipio agregado exitosamente";
                    msg.tipo = Status.success.toString();
            }
        } catch (Exception ex) {
            throw ex;
        }
        return msg;
    }

    public dtoMensaje borrarMunicipio(Integer idMunicipio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Update municipio SET estado = 0 WHERE idMunicipio = :idMunicipio").addEntity(Municipio.class);
            q.setParameter("idMunicipio", idMunicipio);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje modificarMunicipio(Municipio mun) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (mun != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    Query q = s.createSQLQuery("Update municipio SET nombre = :nombre, descripcion = :descripcion WHERE idMunicipio = :idMunicipio").addEntity(Municipio.class);
                    q.setParameter("idMunicipio", mun.getIdMunicipio());
                    q.setParameter("nombre", mun.getNombre());
                    q.setParameter("descripcion", mun.getDescripcion());
                    q.executeUpdate();
                    tx.commit();
                    s.close();
                    msg.tipo = Status.success.toString();
                    msg.msg = "Municipio modificado exitosamente";
            }
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public List<Municipio> getMunicipios() {
        List<Municipio> colMunicipios = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from municipio where estado = 1").addEntity(Municipio.class);
            colMunicipios = (List<Municipio>) q.list();
        } catch (Exception ex) {
            throw ex;
        }
        return colMunicipios;
    }

    public Municipio getMunicipioByMunicipioId(Integer idMunicipio) {
        Municipio municipio = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from municipio where idMunicipio = :idMunicipio").addEntity(Municipio.class);
            q.setParameter("idMunicipio", idMunicipio);
            municipio = (Municipio) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return municipio;
    }

    public boolean existeMunicipio(String nombre) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from municipio where nombre = :name").addEntity(Municipio.class);
            q.setParameter("name", nombre);
            Municipio municipio = (Municipio) q.uniqueResult();
            if (municipio != null) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }

    public List<Barrio> getBarriosByMunicipio(Integer idMunicipio) {
        List<Barrio> colBarrios = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from barrio where idMunicipio = :idMun and estado = 1").addEntity(Municipio.class);
            colBarrios = (List<Barrio>) q.list();
        } catch (Exception ex) {
            throw ex;
        }
        return colBarrios;
    }
}