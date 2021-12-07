package DataAccess.Persistencia;

import Business.Entities.Rol;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PRol {

    private static PRol newInstancePRol;

    public static PRol newInstancePRol() {
        if (newInstancePRol == null) {
            newInstancePRol = new PRol();
        }
        return newInstancePRol;
    }

    public Rol getRolByUserId(Integer idUsuario) throws Exception {
        Rol rol = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select r.idRol,r.nombre,r.descripcion,r.estado from rol as r inner join usuario as u on r.idRol = u.idRol where u.idUsuario = :usr").addEntity(Rol.class);
            q.setParameter("usr", idUsuario);
            rol = (Rol) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " GP1");
        }
        return rol;
    }

    public List<Rol> getRoles() throws Exception {
        List<Rol> colRol = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from rol where estado = 1").addEntity(Rol.class);
            colRol = (List<Rol>) q.list();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD +" 12P1");
        }
        return colRol;
    }

    public Rol getRolByRolId(Integer idRol) throws Exception {
        Rol rol = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from rol where idRol = :idRol").addEntity(Rol.class);
            q.setParameter("idRol", idRol);
            rol = (Rol) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 6GP");
        }
        return rol;
    }

    public dtoMensaje deleteRol(Rol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(rol);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 10P2");
        }
        return msg;
    }

    public dtoMensaje altaRol(Rol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.save(rol);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 9P1");
        }
        return msg;
    }

    public dtoMensaje modificarRol(Rol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol != null) {
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = s.beginTransaction();
                s.merge(rol);
                tx.commit();
                s.close();
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD+" 11P1");
        }
        return msg;
    }

    public boolean existeRol(String nombre) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from rol where nombre = :name").addEntity(Rol.class);
            q.setParameter("name", nombre);
            Rol existeRol = (Rol) q.uniqueResult();
            if (existeRol != null) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }
}
