/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Persistencia;

import Business.Entities.Area;
import Business.Entities.Barrio;
import Business.Entities.Empresa;
import Business.Entities.Municipio;
import Business.Entities.Rol;
import Business.Entities.Tipodetalle;
import Business.Entities.Tiposervicio;
import DataAccess.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Facundo
 */
public class PListItem {

    private static PListItem newInstancePListItem;

    public static PListItem getNewInstance() {
        if (newInstancePListItem == null) {
            newInstancePListItem = new PListItem();
        }
        return newInstancePListItem;
    }

    public List<Rol> getRoles() {
        List<Rol> colRol = new ArrayList<Rol>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from rol where estado = 1").addEntity(Rol.class);
            colRol = (List<Rol>) q.list();

            s.close();
            return colRol;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Municipio> getMunicipios() {
        List<Municipio> colMunicipio = new ArrayList<Municipio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from municipio where estado = 1").addEntity(Municipio.class);
            colMunicipio = (List<Municipio>) q.list();

            s.close();
            return colMunicipio;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Barrio> getBarriosPorMunicipio(int idMunicipio) {
        List<Barrio> colBarrio = new ArrayList<Barrio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from barrio where estado = 1 and idMunicipio= :idMun").addEntity(Barrio.class);
            q.setParameter("idMun", idMunicipio);
            colBarrio = (List<Barrio>) q.list();
            s.close();
            return colBarrio;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<Tipodetalle> getDetTipoById(Integer idTipo)
    {
        List<Tipodetalle> colDetalleTipo = new ArrayList<Tipodetalle>();
        Session s = HibernateUtil.getSessionFactory().openSession();               
        Transaction tx = null;
        try {
            tx = s.beginTransaction();             
            Query q = s.createSQLQuery("Select * from tipodetalle where idTipo = :id").addEntity(Tipodetalle.class);                      
            q.setParameter("id", idTipo);
            colDetalleTipo = (List<Tipodetalle>) q.list();            
            s.close();
            return colDetalleTipo;
        }
        catch(Exception ex)
        {
            throw ex;
        }       
    }
    
    public Tipodetalle getDetTipoByIdDet(Integer idTipo, short idTipoDetalle)
    {
        Tipodetalle tipo = new Tipodetalle();
        Session s = HibernateUtil.getSessionFactory().openSession();               
        Transaction tx = null;
        try {
            tx = s.beginTransaction();             
            Query q = s.createSQLQuery("Select * from tipodetalle where idTipo = :id and idTipoDetalle = :idTipoDet").addEntity(Tipodetalle.class);                      
            q.setParameter("id", idTipo);
            q.setParameter("idTipoDet", idTipoDetalle);
            tipo = (Tipodetalle) q.uniqueResult();            
            s.close();
            return tipo;
        }
        catch(Exception ex)
        {
            throw ex;
        }       
    }
    
    
    public List<Tiposervicio> getCategorias() {
        List<Tiposervicio> colCategorias = new ArrayList<Tiposervicio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from tiposervicio where estado = 1").addEntity(Tiposervicio.class);
            colCategorias = (List<Tiposervicio>) q.list();
            s.close();
            return colCategorias;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    
    public List<Tiposervicio> getTipoServicio()
    {
        List<Tiposervicio> colTipoServicio = new ArrayList<Tiposervicio>();
        Session s = HibernateUtil.getSessionFactory().openSession();               
        Transaction tx = null;
        try {
            tx = s.beginTransaction();             
            Query q = s.createSQLQuery("Select * from tiposervicio where estado = 1").addEntity(Tiposervicio.class);                      
            colTipoServicio = (List<Tiposervicio>) q.list();            
            s.close();
            return colTipoServicio;
        }
        catch(Exception ex)
        {
            throw ex;
        }       
    }
    
    public List<Empresa> getEmpresas()
    {
        List<Empresa> colEmpresas = new ArrayList<Empresa>();
        Session s = HibernateUtil.getSessionFactory().openSession();               
        Transaction tx = null;
        try {
            tx = s.beginTransaction();             
            Query q = s.createSQLQuery("Select * from empresa where estado = 1").addEntity(Empresa.class);                      
            colEmpresas = (List<Empresa>) q.list();            
            s.close();
            return colEmpresas;
        }
        catch(Exception ex)
        {
            throw ex;
        }       
    }

    public List<Area> getAreas() {
        List<Area> colArea = new ArrayList<Area>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from area where estado = 1").addEntity(Area.class);
            colArea = (List<Area>) q.list();

            s.close();
            return colArea;
        } catch (Exception ex) {
            throw ex;
        }
    }

    
}
