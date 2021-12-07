package DataAccess.Persistencia;

import Business.Entities.Subarea;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoSubArea;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PSubArea {

    private static PSubArea instancePsubArea;

    public static PSubArea newInstancePSubArea() {
        if (instancePsubArea == null) {
            instancePsubArea = new PSubArea();
        }
        return instancePsubArea;
    }

    public dtoMensaje altaSubArea(Subarea subA) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.save(subA);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return msg;
    }

    public Subarea getSubArea(dtoSubArea dtoSubA) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from subarea where nombre = :name and descripcion = :descripcion and estado = 1").addEntity(Subarea.class);
            q.setParameter("name", dtoSubA.nombre);
            q.setParameter("descripcion", dtoSubA.descripcion);
            Subarea subrea = (Subarea) q.uniqueResult();
            s.close();
            return subrea;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Subarea> getSubAreas() {
        List<Subarea> colSubAs = null;    
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from subarea where estado = 1").addEntity(Subarea.class);
            colSubAs = (List<Subarea>) q.list();
            return colSubAs;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Subarea getSubAreaPorID(int idSubArea) {
        try {
            Subarea subA = null;
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from subarea where estado = 1 and idSubArea = :idSubArea ").addEntity(Subarea.class);
            q.setParameter("idSubArea", idSubArea);
            subA = (Subarea) q.uniqueResult();
            return subA;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Subarea> getSubAreasDeArea(Integer idArea) {
        try {
            List<Subarea> colSubAsDArea = null;
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from subarea where estado = 1 and idArea = :idArea").addEntity(Subarea.class);
            q.setParameter("idArea", idArea);
            colSubAsDArea = (List<Subarea>) q.list();
            s.close();
            return colSubAsDArea;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public dtoMensaje deleteSubArea(int idSubArea) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE subarea SET estado= 0 WHERE idSubArea = :idSubArea").addEntity(Subarea.class);
            q.setParameter("idSubArea", idSubArea);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError("Error de conexion al intentar eliminar Subarea");
        }
        return msg;
    }
    
    public dtoMensaje modificarSubArea(Subarea subA) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Update subarea SET idSubArea = :idSubArea,nombre = :nombre, descripcion = :descripcion, idArea = :idArea WHERE idSubArea = :idSubArea").addEntity(Subarea.class);
            q.setParameter("idSubArea", subA.getId().getIdSubArea());
            q.setParameter("nombre", subA.getNombre());
            q.setParameter("descripcion", subA.getDescripcion());
            q.setParameter("idArea",subA.getArea().getIdArea());
            q.executeUpdate();
            tx.commit();
            s.close(); 
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }
}
