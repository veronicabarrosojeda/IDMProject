package DataAccess.Persistencia;

import Business.Entities.Permiso;
import Business.Entities.Rol;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import DataAccess.HibernateUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PPermiso {

    private static PPermiso newInstancePPermiso;

    public static PPermiso newInstancePPermiso() {
        if (newInstancePPermiso == null) {
            newInstancePPermiso = new PPermiso();
        }
        return newInstancePPermiso;
    }

    public List<Permiso> getPermisosByUserId(Integer idUsuario) throws Exception {
        List<Permiso> colPermisos = new ArrayList<>();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT per.idPermiso, per.nombre, per.estado, per.url, per.descripcion, per.nombreUnico FROM usuario as us inner join rol as ro on us.idRol = ro.idRol inner join rolpermiso as rp on ro.idRol = rp.idRol inner join permiso as per on rp.idPermiso = per.idPermiso where us.idUsuario = :usr").addEntity(Permiso.class);
            q.setParameter("usr", idUsuario);
            colPermisos = (List<Permiso>) q.list();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " G2P");
        }
        return colPermisos;
    }

    public List<Permiso> getPermisosByRol(Integer idRol) throws Exception {
        List<Permiso> colPermisos = new ArrayList<>();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT per.idPermiso, per.nombre, per.estado, per.url, per.descripcion, per.nombreUnico FROM permiso as per inner join rolpermiso as rp on per.idPermiso = rp.idPermiso where rp.idRol = :idRol").addEntity(Permiso.class);
            q.setParameter("idRol", idRol);
            colPermisos = (List<Permiso>) q.list();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 14P1");
        }
        return colPermisos;
    }

    public void deleteRolPermisos(Integer idRol) throws Exception {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Delete from rolpermiso where idRol = :idRol").addEntity(Rol.class);
            q.setParameter("idRol", idRol);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " G5P");
        }
    }

    public dtoMensaje updatePermisosRol(Rol rol, List<Permiso> permisos) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Set<Permiso> colPermisos = new HashSet<>(permisos);
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            rol.setPermisos(colPermisos);
            s.merge(rol);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 15C1");
        }
        return msg;
    }

    public List<Permiso> getPermisos() throws Exception {
        List<Permiso> colPermisos = new ArrayList<>();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT * from permiso where estado = 1").addEntity(Permiso.class);
            colPermisos = (List<Permiso>) q.list();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 13P1");
        }
        return colPermisos;
    }

    public Permiso getPermisoById(Integer idPermiso) {
        Permiso permiso = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT * from permiso where idPermiso = :idPermiso").addEntity(Permiso.class);
            q.setParameter("idPermiso", idPermiso);
            permiso = (Permiso) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return permiso;
    }
}
