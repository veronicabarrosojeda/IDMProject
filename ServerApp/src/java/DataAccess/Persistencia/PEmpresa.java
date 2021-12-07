package DataAccess.Persistencia;

import Business.Entities.Empresa;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PEmpresa {

    private static PEmpresa instancePEmpresa;

    public static PEmpresa newInstancePEmpresa() {
        if (instancePEmpresa == null) {
            instancePEmpresa = new PEmpresa();
        }
        return instancePEmpresa;
    }

    public dtoMensaje altaEmpresa(Empresa empresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.save(empresa);
            tx.commit();
            s.close();
            msg.tipo = Status.success.toString();
            msg.msg = "La empresa fue ingresada correctamente!";
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje modificarEmpresa(Empresa empresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(empresa);
            tx.commit();
            s.close();
            TimeUnit.SECONDS.sleep(10);
            msg.tipo = Status.success.toString();
            msg.msg = "La empresa fue mofidicada correctamente!";
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje bajaEmpresa(Integer empresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE empresa SET estado= 0 WHERE idEmpresa = :idEmpresa").addEntity(Empresa.class);
            q.setParameter("idEmpresa", empresa);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public boolean findEmpresaByName(String name) {
        Empresa empresa = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from empresa where nombre = :name").addEntity(Empresa.class);
            q.setParameter("name", name);
            empresa = (Empresa) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return empresa != null;
    }

    public Empresa getEmpresa(Integer idEmpresa) {
        Empresa empresa = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from empresa where idEmpresa = :idEmpresa").addEntity(Empresa.class);
            q.setParameter("idEmpresa", idEmpresa);
            empresa = (Empresa) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw null;
        }
        return empresa;
    }

    public boolean getEmpresaByRut(Integer rut) {
        Empresa empresa = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from empresa where rut = :rut").addEntity(Empresa.class);
            q.setParameter("rut", rut);
            empresa = (Empresa) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return empresa != null;
    }

    public List<Empresa> getEmpresas() {
        List<Empresa> colEmpresas = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from empresa where estado = 1").addEntity(Empresa.class);
            colEmpresas = (List<Empresa>) q.list();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return colEmpresas;
    }

    public void deleteEmpresasUsuario(Integer idUsuario) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Delete from usuarioempresa where idUsuario = :idUsuario").addEntity(Empresa.class);
            q.setParameter("idUsuario", idUsuario);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteEmpresasToService(long idServicio) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Delete from servicioempresa where idServicio = :idServ");
            q.setParameter("idServ", idServicio);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Empresa> getEmpresasByUser(Integer idUser) throws Exception {
        List<Empresa> colEmpresas = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select em.idEmpresa, em.nombre, em.estado, em.rut, em.descripcion from empresa as em inner join usuarioempresa as ue on em.idEmpresa = ue.idEmpresa where ue.idUsuario = :idUser").addEntity(Empresa.class);
            q.setParameter("idUser", idUser);
            colEmpresas = (List<Empresa>) q.list();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD +" G3P");
        }
        return colEmpresas;
    }

    public List<Empresa> getEmpresasByService(Long idServicio) {
        List<Empresa> colEmpresas = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("SELECT em.idEmpresa, em.nombre, em.rut, em.descripcion, em.estado FROM empresa as em inner join servicioempresa as see on em.idEmpresa = see.idEmpresa where see.idServicio = :idServ").addEntity(Empresa.class);
            q.setParameter("idServ", idServicio);
            colEmpresas = (List<Empresa>) q.list();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
        return colEmpresas;
    }

    public Empresa getEmpresaById(Integer idEmpresa) {
        Empresa empresa = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from empresa where idEmpresa = :idEmpresa and estado = 1").addEntity(Empresa.class);
            q.setParameter("idEmpresa", idEmpresa);
            empresa = (Empresa) q.uniqueResult();
        } catch (Exception ex) {
            throw ex;
        }
        return empresa;
    }

}
