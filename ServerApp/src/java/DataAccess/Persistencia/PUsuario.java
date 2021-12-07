package DataAccess.Persistencia;

import Business.Entities.Empresa;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoCambioClave;
import Common.DTO.dtoLogin;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoUserPass;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PUsuario {

    private static PUsuario newInstancePUsuario;

    public static PUsuario newInstancePUsuario() {
        if (newInstancePUsuario == null) {
            newInstancePUsuario = new PUsuario();
        }
        return newInstancePUsuario;
    }

    public Usuario logIn(dtoLogin login) throws Exception {
        Usuario user = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where nickname = :name and password = :pass and estado = 1").addEntity(Usuario.class);
            q.setParameter("name", login.nickname);
            q.setParameter("pass", login.password);
            user = (Usuario) q.uniqueResult();
            if (user != null) {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                Transaction tx = s.beginTransaction();
                s.merge(user);
                tx.commit();
                s.close();
            }
            return user;
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 5P1");
        }
    }

    public void logOut(String token){
        Usuario currUser = findUserByToken(token);
        if (currUser != null) {
            currUser.setToken("");
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(currUser);
            tx.commit();
            s.close();
        }
    }

    public Usuario findUserByNickName(String nickname) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where nickname = :name and estado = 1").addEntity(Usuario.class);
            q.setParameter("name", nickname);
            Usuario user = (Usuario) q.uniqueResult();
            return user;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Usuario findUserByToken(String token) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where token = :token and estado = 1").addEntity(Usuario.class);
            q.setParameter("token", token);
            Usuario user = (Usuario) q.uniqueResult();
            return user;
        } catch (Exception ex) {
            //NO MODIFICAR LA EXEPCIÓN,FUNCION EN CUsuario TIENE EL MENSAJE.
            throw ex;
        }
    }

    public Usuario getUserById(Integer idUser) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where idUsuario = :idUser and estado = 1").addEntity(Usuario.class);
            q.setParameter("idUser", idUser);
            Usuario user = (Usuario) q.uniqueResult();
            return user;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public dtoMensaje deleteUsuario(Usuario usuDelet) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(usuDelet);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 2P1");
        }
        return msg;
    }

    public List<Usuario> getUsuarios(Integer idUser) throws Exception {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where estado = 1 and idRol != 3 and idUsuario != :idUser").addEntity(Usuario.class);
            q.setParameter("idUser", idUser);
            List<Usuario> colUsers = (List<Usuario>) q.list();
            return colUsers;
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + "4P1");
        }
    }

    public List<Usuario> getUsuariosByIdRol(Integer idRol) throws Exception {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where estado = 1 and idRol = :idRol").addEntity(Usuario.class);
            q.setParameter("idRol", idRol);
            List<Usuario> colUsers = (List<Usuario>) q.list();
            return colUsers;
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 10P1");
        }
    }

    public dtoMensaje altaUsuario(Usuario us) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.save(us);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 1P1");
        }
        return msg;
    }

    public void updateEmpresasUsuario(Usuario usr, List<Empresa> colEmp) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Set<Empresa> empresas = new HashSet<Empresa>(colEmp);
            usr.setEmpresas(empresas);
            s.merge(usr);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public dtoMensaje modificarUsuario(Usuario user) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            s.merge(user);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorBD + " 3P1");
        }
        return msg;
    }

    public dtoMensaje modificarUsuarioMovil(Usuario user) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = s.beginTransaction();
            Query q = s.createSQLQuery("Update usuario SET nombre = :nombre, apellido = :apellido, nickname = :nickname, correo = :correo, nroIdentidad = :nroIdentidad, tipoIdentidad = :tipoIdentidad,idRol = :idRol, telefono = :telefono, notificar= :notificar WHERE idUsuario = :idUsuario").addEntity(Usuario.class);
            q.setParameter("idUsuario", user.getIdUsuario());
            q.setParameter("nombre", user.getNombre());
            q.setParameter("apellido", user.getApellido());
            q.setParameter("nickname", user.getNickname());
            q.setParameter("correo", user.getCorreo());
            q.setParameter("nroIdentidad", user.getNroIdentidad());
            q.setParameter("tipoIdentidad", user.getTipoIdentidad());
            q.setParameter("idRol", user.getRol().getIdRol());
            q.setParameter("telefono", user.getTelefono());
            q.setParameter("notificar", user.getNotificar());
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public void cambiarClave(dtoCambioClave datos) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Update usuario SET password = :pass WHERE idUsuario = :idUsuario").addEntity(Usuario.class);
            q.setParameter("idUsuario", datos.idUsuario);
            q.setParameter("pass", datos.passwordNueva);
            q.executeUpdate();
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }

    }

    public Usuario findUserByNicknameAndCorreo(dtoUserPass us) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createSQLQuery("Select * from usuario where nickname = :nickname and correo = :correo and estado = 1").addEntity(Usuario.class);
            q.setParameter("nickname", us.nickname);
            q.setParameter("correo", us.correo);
            Usuario user = (Usuario) q.uniqueResult();
            return user;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
