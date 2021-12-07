package DataAccess.Persistencia;

import Business.Entities.Area;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PArea {

    private static PArea newInstancePArea;

    public static PArea newInstancePArea() {
        if (newInstancePArea == null) {
            newInstancePArea = new PArea();
        }
        return newInstancePArea;
    }

    public List<Area> listarAreas() {
        List<Area> colAreas = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from area where estado = 1").addEntity(Area.class);
            colAreas = (List<Area>) q.list();
        } catch (Exception ex) {
            throw ex;
        }
        return colAreas;
    }

    public Area getArea(int idArea) {
        Area area = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from area where estado = 1 and idArea = :idArea").addEntity(Area.class);
            q.setParameter("idArea", idArea);
            area = (Area) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return area;
    }

    public List<Area> listarAreaFiltro(String filtro) {
        List<Area> colAreasF = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT * FROM area WHERE (estado = 1) and (nombre LIKE :filtros OR descripcion LIKE :filtros)").addEntity(Area.class);
            q.setParameter("filtros", "%" + filtro + "%");
            colAreasF = (List<Area>) q.list();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return colAreasF;
    }

    public dtoMensaje altaArea(Area area) {
        dtoMensaje msg = new dtoMensaje();
        try {
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = s.beginTransaction();
                s.save(area);
                tx.commit();
                s.close();
                msg.tipo = Status.success.toString();
                msg.msg = "El área fue ingresada correctamente";
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje modificarArea(Area area) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (area != null) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    Transaction tx = s.beginTransaction();
                    s.merge(area);
                    tx.commit();
                    s.close();
                    msg.tipo = Status.success.toString();
                    msg.msg = "El área fue modificada correctamente";
            }
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje bajaArea(int idArea) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE area SET estado= 0 WHERE idArea = :idArea").addEntity(Area.class);
            q.setParameter("idArea", idArea);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public boolean findAreaByName(String name) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from area where nombre = :name").addEntity(Area.class);
            q.setParameter("name", name);
            Area area = (Area) q.uniqueResult();
            return area != null;
        } catch (Exception ex) {
            throw ex;
        }
    }
}