package DataAccess.Persistencia;

import Business.Entities.Supervisor;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoSupervisor;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PSupervisor {

    private static PSupervisor newInstancePSupervisor;

    public static PSupervisor newInstancePSupervisor() {
        if (newInstancePSupervisor == null) {
            newInstancePSupervisor = new PSupervisor();
        }
        return newInstancePSupervisor;
    }

    public List<Supervisor> getSupervisores() throws Exception {
        List<Supervisor> colSup = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from supervisor where estado = 1").addEntity(Supervisor.class);
            colSup = (List<Supervisor>) q.list();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 19P1");
        }
        return colSup;
    }

    public List<Supervisor> getSupervisoresByServicio(Long idServicio) throws Exception {
        List<Supervisor> colSup = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select sup.* from supervisor as sup left join supervisorservicio as sups on sup.idSupervisor = sups.idSupervisor where estado = 1 and sups.idServicio = :idServ").addEntity(Supervisor.class);
            q.setParameter("idServ", idServicio);
            colSup = (List<Supervisor>) q.list();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 20P1");
        }
        return colSup;
    }

    public Supervisor getSupervisorById(Integer idSupervisor) throws Exception {
        Supervisor supervisor = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("SELECT * from supervisor where idSupervisor = :idSupervisor").addEntity(Supervisor.class);
            q.setParameter("idSupervisor", idSupervisor);
            supervisor = (Supervisor) q.uniqueResult();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " G7P");
        }
        return supervisor;
    }

    public dtoMensaje altaSupervisor(Supervisor supervisor) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.save(supervisor);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 16P1");
        }
        return msg;
    }

    public dtoMensaje modificarSupervisor(Supervisor supervisor) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (supervisor != null) {
                Session s = HibernateUtil.getSessionFactory().openSession();
                Transaction tx = s.beginTransaction();
                s.merge(supervisor);
                tx.commit();
                s.close();
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 18P1");
        }
        return msg;
    }

    public void deleteSupervisoresToService(long idServicio) throws Exception {  
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Delete from supervisorservicio where idServicio = :idServ");
            q.setParameter("idServ", idServicio);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 20P2");
        }
    }

    public dtoMensaje deleteSupervisor(Supervisor supervisor) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(supervisor);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + "17P2");
        }
        return msg;
    }

    public boolean existeSupervisor(dtoSupervisor supervisor) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from supervisor where nombre = :name and apellido = :apellido and estado = true").addEntity(Supervisor.class);
            q.setParameter("name", supervisor.nombre);
            q.setParameter("apellido", supervisor.apellido);
            Supervisor existeSupervisor = (Supervisor) q.uniqueResult();
            if (existeSupervisor != null) {
                return true;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }

}
