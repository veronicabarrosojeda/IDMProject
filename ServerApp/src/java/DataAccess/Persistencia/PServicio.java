/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Persistencia;

import Business.Entities.Empresa;
import Business.Entities.Servicio;
import Business.Entities.Serviciosolicitante;
import Business.Entities.Tiposervicio;
import Business.Entities.Ubicacionservicio;
import Business.Entities.Supervisor;
import Business.Logic.Controllers.CGeneric;
import Common.Constant.CError;
import Common.Constant.CRutaImgPinMap;
import Common.DTO.dtoGetDataSolicitud;
import Common.DTO.dtoHistorialServicio;
import Common.DTO.dtoListServicioEmpresa;
import Common.DTO.dtoListServicioSupervisor;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import Common.DTO.dtoServicioEntidadesExternas;
import Common.DTO.dtoServicioSolicitante;
import Common.DTO.dtoServicioVista;
import Common.DTO.dtoServiciosEntidadesExternas;
import Common.DTO.dtoSolicitantes;
import Common.DTO.dtoViewServicio;
import Common.DTO.dtoWhereGetServicios;
import Common.Enums.Status;
import DataAccess.HibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Facundo
 */
public class PServicio {

    private static PServicio newInstancePServicio;

    public static PServicio newInstancePServicio() {
        if (newInstancePServicio == null) {
            newInstancePServicio = new PServicio();
        }
        return newInstancePServicio;
    }

    public List<dtoViewServicio> getServicios(dtoGetDataSolicitud dataWhere) throws IllegalArgumentException, IllegalAccessException, Exception {
        List<dtoViewServicio> colViewServicio = new ArrayList<dtoViewServicio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String whereEstados = generarWhereEstados("vie.estado", dataWhere.colEstados);
        String whereTipoServicio = generarWhereTipoServicio("vie.idTipoServicio", dataWhere.tipoDeServicios);
        try {
            String sql = "SELECT vie.*, count(ses.idServicioSolicitante) as qtSolicitantes, DATEDIFF(vie.fechaIngreso, NOW()) as DiasIngreso FROM `viewservicio` as vie left join serviciosolicitante as ses on vie.idServicio = ses.idServicio ";

            if (dataWhere.colEmpresas.size() > 0) {
                String empIn = generarIN(dataWhere.colEmpresas);
                sql += " inner join servicioempresa as em on vie.idServicio = em.idServicio where em.idEmpresa in " + empIn;
                sql += " and (" + whereEstados + ")";
            } else {
                sql += " where (" + whereEstados + ")";
            }

            if (whereTipoServicio != null && whereTipoServicio.length() > 0) {
                sql += " and (" + whereTipoServicio + ")";
            }

            if (dataWhere.fechaDesde != null && dataWhere.fechaHasta != null) {
                sql += " and vie.fechaIngreso >= '" + dataWhere.fechaDesde + "' and vie.fechaIngreso <= '" + dataWhere.fechaHasta + "'";
            }

            if (dataWhere.origen != null && !dataWhere.origen.equals("") && !dataWhere.origen.equals("-1")) {
                sql += " and vie.tipoOrigen = " + dataWhere.origen;
            }

            if (dataWhere.tipoUbicacion != null && !dataWhere.tipoUbicacion.equals("") && !dataWhere.tipoUbicacion.equals("-1")) {
                if (dataWhere.tipoUbicacion.equals("2")) {
                    sql += " and vie.latitud is null and vie.longitud is null";
                } else if (dataWhere.tipoUbicacion.equals("1")) {
                    sql += " and vie.latitud is not null and vie.longitud is not null";
                }
            }

            sql += " group by vie.idServicio";

            if (dataWhere.qtSolicitantes > 0) {
                sql += " having count(ses.idServicioSolicitante) >= " + dataWhere.qtSolicitantes;
            }

            if (dataWhere.diasPendiente > 0) {
                if (dataWhere.qtSolicitantes > 0) {
                    sql += " and DATEDIFF(vie.fechaIngreso, NOW()) <= " + (dataWhere.diasPendiente * -1);
                } else {
                    sql += " having DATEDIFF(vie.fechaIngreso, NOW()) <= " + (dataWhere.diasPendiente * -1);
                }
            }

            tx = s.beginTransaction();
            Query q = s.createSQLQuery(sql);

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoViewServicio dtoServ = new dtoViewServicio();
                dtoServ = (dtoViewServicio) CGeneric.hasMapToObject(dtoServ, result);
                dtoServ.imgEstado = setImgEstado(dtoServ.estado, dtoServ.DiasIngreso);
                colViewServicio.add(dtoServ);
            }

            return colViewServicio;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<dtoServiciosEntidadesExternas> getServiciosEntidadesExternas(dtoWhereGetServicios dataWhere) throws IllegalArgumentException, IllegalAccessException, Exception {
        List<dtoServiciosEntidadesExternas> colViewServicio = new ArrayList<dtoServiciosEntidadesExternas>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String whereEstados = generarWhereEstados("vie.estado", dataWhere.colEstados);
        try {
            String sql = "SELECT vie.*, count(ses.idServicioSolicitante) as qtSolicitantes, DATEDIFF(vie.fechaIngreso, NOW()) as DiasIngreso FROM `viewservicio` as vie left join serviciosolicitante as ses on vie.idServicio = ses.idServicio ";

            if (dataWhere.colEmpresas.size() > 0) {
                String empIn = generarIN(dataWhere.colEmpresas);
                sql += " inner join servicioempresa as em on vie.idServicio = em.idServicio where em.idEmpresa in " + empIn;
                sql += " and (" + whereEstados + ")";
            } else {
                sql += " where (" + whereEstados + ")";
            }

            if (dataWhere.fechaDesde != null && dataWhere.fechaHasta != null) {
                sql += " and vie.fechaIngreso >= '" + dataWhere.fechaDesde + "' and vie.fechaIngreso <= '" + dataWhere.fechaHasta + "'";
            }

            sql += " group by vie.idServicio";

            tx = s.beginTransaction();
            Query q = s.createSQLQuery(sql);

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoServiciosEntidadesExternas dtoServ = new dtoServiciosEntidadesExternas();
                dtoServ = (dtoServiciosEntidadesExternas) CGeneric.hasMapToObject(dtoServ, result);
                colViewServicio.add(dtoServ);
            }

            return colViewServicio;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String setImgEstado(short estado, int retrazo) {
        String imagen = "";
        switch (estado) {
            case 1:
                if (retrazo < -2) {
                    imagen = CRutaImgPinMap.IMG_PIN_ROJO_CON_RETRAZO;
                } else {
                    imagen = CRutaImgPinMap.IMG_PIN_ROJO;
                }
                break;
            case 2:
                imagen = CRutaImgPinMap.IMG_PIN_VERDE;
                break;
            case 3:
                imagen = CRutaImgPinMap.IMG_PIN_CELESTE;
                break;
            case 4:
                imagen = CRutaImgPinMap.IMG_PIN_AMARILLO;
                break;
            case 5:
                imagen = CRutaImgPinMap.IMG_PIN_PURPURA;
                break;
            case 6:
                imagen = CRutaImgPinMap.IMG_PIN_ROSADO;
                break;
            case 7:
                imagen = CRutaImgPinMap.IMG_PIN_BLANCO;
                break;
            case 8:
                imagen = CRutaImgPinMap.IMG_PIN_NARANJA;
                break;
        }
        return imagen;
    }

    public String generarWhereEstados(String propiedad, List<String> estados) {
        String whereEstados = "";

        for (String estado : estados) {
            if (estados.get(0) == estado) {
                whereEstados = propiedad + "=" + estado;
            } else {
                whereEstados += " or " + propiedad + " = " + estado;
            }
        }

        return whereEstados;
    }

    public String generarWhere(String propiedad, List<String> itemWhere) {
        String colWhere = "";

        for (String item : itemWhere) {
            if (itemWhere.get(0) == item) {
                colWhere = propiedad + "=" + item;
            } else {
                colWhere += " or " + propiedad + " = " + item;
            }
        }

        return colWhere;
    }

    public String generarIN(List<String> itemWhere) {
        String colWhere = "";

        for (String item : itemWhere) {
            if (itemWhere.get(0) == item) {
                colWhere = "(" + item;
            } else {
                colWhere += "," + item;
            }
        }

        return colWhere + ")";
    }

    public String generarWhereTipoServicio(String propiedad, List<String> tipoServicio) {
        String whereTipoServicio = "";

        for (String tipoServ : tipoServicio) {
            if (tipoServicio.get(0) == tipoServ) {
                whereTipoServicio = propiedad + "=" + tipoServ;
            } else {
                whereTipoServicio += " or " + propiedad + " = " + tipoServ;
            }
        }

        return whereTipoServicio;
    }

    public List<dtoViewServicio> getServiciosUser(Integer idUsu) throws Exception {
        List<dtoViewServicio> colViewServUser = new ArrayList<dtoViewServicio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select vserv.* from viewservicio as vserv inner join serviciosolicitante as servsol  on vserv.idServicio = servsol.idServicio where servsol.idUsuario = :idUs");
            q.setParameter("idUs", idUsu);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            for (Object result : q.list()) {
                dtoViewServicio dtoServ = new dtoViewServicio();
                dtoServ = (dtoViewServicio) CGeneric.hasMapToObject(dtoServ, result);
                colViewServUser.add(dtoServ);
            }

        } catch (Exception ex) {
            throw ex;
        }
        return colViewServUser;
    }

    public List<dtoListServicioEmpresa> getSupervisorEmpresa(Integer idEmpresa) throws Exception {
        List<dtoListServicioEmpresa> colViewServicio = new ArrayList<dtoListServicioEmpresa>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            String sql = "Select emp.idEmpresa, emp.nombre as nombreEmpresa, emp.descripcion as descripcionEmpresa, vie.* from empresa as emp inner join servicioempresa as emps on emp.idEmpresa = emps.idEmpresa inner join viewservicio as vie on emps.idServicio = vie.idServicio";

            if (idEmpresa != -1) {
                sql += " where emp.idEmpresa = " + idEmpresa;
            }

            tx = s.beginTransaction();
            Query q = s.createSQLQuery(sql);

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoListServicioEmpresa dtoSupEmp = new dtoListServicioEmpresa();
                dtoSupEmp = (dtoListServicioEmpresa) CGeneric.hasMapToObject(dtoSupEmp, result);
                colViewServicio.add(dtoSupEmp);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colViewServicio;
    }

    public List<dtoSolicitantes> getSolicitantesServicio(long idServicio) throws Exception {
        List<dtoSolicitantes> colSolServ = new ArrayList<dtoSolicitantes>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("SELECT ses.idServicioSolicitante,ses.correoSolicitante, ses.`nroIdentidad`, ses.`tipoIdentidad`,ses.`telefono`,ses.idServicio,ses.`nombreSolicitante`, ses.`idUsuario`, us.nombre as nombreUsuario, us.apellido as apellidoUsuario, us.correo as correoUsuario, us.nroIdentidad as nroIdentidadUsuario, us.tipoIdentidad as tipoIdentidadUsuario, us.telefono as telefonoUsuario, us.notificar FROM `serviciosolicitante` as ses left join usuario as us on ses.idUsuario = us.idUsuario where ses.idServicio = :idServ");
            q.setParameter("idServ", idServicio);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoServicioSolicitante dtoServ = new dtoServicioSolicitante();
                dtoSolicitantes dtoSol = new dtoSolicitantes();
                dtoServ = (dtoServicioSolicitante) CGeneric.hasMapToObject(dtoServ, result);
                if (dtoServ != null) {
                    dtoSol.idServicioSolicitante = dtoServ.idServicioSolicitante;
                    dtoSol.idServicio = dtoServ.idServicio;

                    if (dtoServ.nombreUsuario == null || dtoServ.equals("")) {
                        dtoSol.nombreSolicitante = dtoServ.nombreSolicitante;
                        dtoSol.correoSolicitante = dtoServ.correoSolicitante;
                        dtoSol.telefono = dtoServ.telefono;
                        dtoSol.nroIdentidad = dtoServ.nroIdentidad;
                        dtoSol.tipoIdentidad = dtoServ.tipoIdentidad;
                        dtoSol.notificar = dtoServ.notificar;
                    } else {
                        dtoSol.nombreSolicitante = dtoServ.nombreUsuario;
                        dtoSol.correoSolicitante = dtoServ.correoUsuario;
                        dtoSol.telefono = dtoServ.telefonoUsuario;
                        dtoSol.nroIdentidad = dtoServ.nroIdentidadUsuario;
                        dtoSol.tipoIdentidad = dtoServ.tipoIdentidadUsuario;
                    }
                    colSolServ.add(dtoSol);
                }
            }

        } catch (Exception ex) {
            throw ex;
        }
        return colSolServ;
    }

    public List<dtoViewServicio> getServiciosByEmpresa(dtoGetDataSolicitud dataWhere) {
        List<dtoViewServicio> colViewServicio = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String whereEstados = generarWhereEstados("estado", dataWhere.colEstados);
        String whereTipoServicio = generarWhereTipoServicio("idTipoServicio", dataWhere.tipoDeServicios);

        String sql = "SELECT ser.idServicio, ser.descripcion as descripcionServicio, ser.estado, tid.nombre, ser.idTipoServicio, tis.nombre as nombreTipoServicio, tis.descripcion as descripcionTipoServicio, tis.idArea, ar.nombre as nombreArea, ser.idUbicacionServicio, ubs.calle, ubs.nroPuerta, ubs.apto, ubs.entreCalles, ubs.nroManzana, ubs.nroSolar, ubs.nroPadron, ubs.latitud, ubs.longitud, ubs.idBarrio, ba.nombre as nombreBarrio, mu.idMunicipio, mu.nombre as nombreMunicipiom, ser.fechaIngreso, ser.fechaCambioEstado "
                + "FROM servicio as ser "
                + "inner join ubicacionservicio as ubs on ser.idUbicacionServicio = ubs.idUbicacion "
                + "inner join tiposervicio as tis on ser.idTipoServicio = tis.idTipoServicio "
                + "inner join area as ar on tis.idArea = ar.idArea "
                + "inner join tipodetalle as tid on ser.estado = tid.idTipoDetalle "
                + "inner join barrio as ba on ubs.idBarrio = ba.idBarrio "
                + "inner join municipio as mu on ba.idMunicipio = mu.idMunicipio "
                + "WHERE tid.idTipo = 2 and (" + whereEstados + ")";

        if (whereTipoServicio != null && whereTipoServicio.length() > 0) {
            sql += " and (" + whereTipoServicio + ")";
        }

        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery(sql).addEntity(Servicio.class);
            colViewServicio = (List<dtoViewServicio>) q.list();
            return colViewServicio;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Ubicacionservicio getUbicacionServicio(long idServicio) {
        Ubicacionservicio us = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from ubicacionservicio as us inner join servicio as s where idServicio = :idServicio and s.idUbicacionServicio = us.idUbicacion").addEntity(Ubicacionservicio.class);
            q.setParameter("idServicio", idServicio);
            us = (Ubicacionservicio) q.uniqueResult();
            return us;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public dtoServicioVista getServicioVista(long idServicio) throws Exception {
        dtoServicioVista dtoServ = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from viewservicio where idServicio = :idServ");
            q.setParameter("idServ", idServicio);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            Object result = q.uniqueResult();
            dtoServ = new dtoServicioVista();
            dtoServ = (dtoServicioVista) CGeneric.hasMapToObject(dtoServ, result);
            return dtoServ;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public dtoServicioVista getServicioVistaMovil(long idServicio) throws Exception {
        dtoServicioVista dtoServ = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select vie.* from viewservicio as vie inner join serviciosolicitante as sse on vie.idServicio = sse.idServicio where vie.idServicio = :idServ and sse.idUsuario is null");
            q.setParameter("idServ", idServicio);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            Object result = q.uniqueResult();
            dtoServ = new dtoServicioVista();
            dtoServ = (dtoServicioVista) CGeneric.hasMapToObject(dtoServ, result);
            return dtoServ;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public List<dtoHistorialServicio> getHistorialServicio(Long idServicio) {
        List<dtoHistorialServicio> colHistorialServicio = new ArrayList<dtoHistorialServicio>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from viewhistorialservicio where idServicio = :idServ ORDER BY idHistorialServicio DESC");
            q.setParameter("idServ", idServicio);

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoHistorialServicio historial = new dtoHistorialServicio();
                historial = (dtoHistorialServicio) CGeneric.hasMapToObject(historial, result);
                colHistorialServicio.add(historial);
            }
            return colHistorialServicio;
        } catch (Exception ex) {
            return null;
        }
    }

    public void updateEmpresasServicio(Servicio serv, List<Empresa> colEmp) {

        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {

            tx = s.beginTransaction();
            for (Empresa emp : colEmp) {
                Query q = s.createSQLQuery("INSERT INTO servicioempresa values (:emp,:serv)");
                q.setParameter("emp", emp.getIdEmpresa());
                q.setParameter("serv", serv.getIdServicio());
                q.executeUpdate();
            }
            tx.commit();
            s.close();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updateEmpresaServicio(long idServicio, int idEmpresa) {

        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {

            tx = s.beginTransaction();

            Query q = s.createSQLQuery("INSERT INTO servicioempresa values (:emp,:serv)");
            q.setParameter("emp", idEmpresa);
            q.setParameter("serv", idServicio);
            q.executeUpdate();

            tx.commit();
            s.close();

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updateSupervisoresServicio(Servicio serv, List<Supervisor> colSupervisores) throws Exception {
        Transaction tx = null;
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            tx = s.beginTransaction();
            for (Supervisor sup : colSupervisores) {
                Query q = s.createSQLQuery("INSERT INTO supervisorservicio values (:idSup,:serv)");
                q.setParameter("idSup", sup.getIdSupervisor());
                q.setParameter("serv", serv.getIdServicio());
                q.executeUpdate();
            }
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD +" 20P3");
        }
    }

    public Servicio getServicioById(long idServicio) throws Exception {
        try {
            Servicio serv = null;
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from servicio where idServicio = :idServ").addEntity(Servicio.class);
            q.setParameter("idServ", idServicio);
            serv = (Servicio) q.uniqueResult();
            return serv;
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " G8P");
        }
    }

    public Serviciosolicitante getServiciosolicitante(long idServicio, int idUsuario) {
        Serviciosolicitante servS = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("SELECT * FROM serviciosolicitante WHERE idServicio = :idServ AND idUsuario = :idUsu").addEntity(Serviciosolicitante.class);
            q.setParameter("idServ", idServicio);
            q.setParameter("idUsu", idUsuario);
            servS = (Serviciosolicitante) q.uniqueResult();
            return servS;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Servicio> getServiciosBySupervisor(Integer idSupervisor) throws Exception {
        List<Servicio> colServicios = new ArrayList<Servicio>();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select serv.* from servicio as serv  natural join supervisorservicio as sus  where sus.idSupervisor = :idSup").addEntity(Servicio.class);
            q.setParameter("idSup", idSupervisor);
            colServicios = (List<Servicio>) q.list();
            return colServicios;
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + " 17P1");
        }
    }

    public Servicio altaServicio(Servicio serv) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.save(serv);
            tx.commit();
            s.close();
            return serv;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void cargarRutaImg(Servicio serv) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.merge(serv);
            tx.commit();
            s.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<Tiposervicio> getTiposServicios() {
        List<Tiposervicio> colTiposServicios = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("select * from tiposervicio where estado = 1").addEntity(Tiposervicio.class);
            colTiposServicios = (List<Tiposervicio>) q.list();
            s.close();
            return colTiposServicios;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<dtoListServicioSupervisor> getSupervisorServicios(int idSupervisor) throws Exception {

        List<dtoListServicioSupervisor> colViewServicio = new ArrayList<dtoListServicioSupervisor>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            String sql = "Select sup.idSupervisor, sup.nombre as nombreSupervisor, sup.apellido as apellidoSupervisor, vie.* from supervisor as sup inner join supervisorservicio as sups on sup.idSupervisor = sups.idSupervisor inner join viewservicio as vie on sups.idServicio = vie.idServicio";

            if (idSupervisor != -1) {
                sql += " where sup.idSupervisor = " + idSupervisor;
            }
            tx = s.beginTransaction();
            Query q = s.createSQLQuery(sql);

            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            for (Object result : q.list()) {
                dtoListServicioSupervisor dtoSupServ = new dtoListServicioSupervisor();
                dtoSupServ = (dtoListServicioSupervisor) CGeneric.hasMapToObject(dtoSupServ, result);
                colViewServicio.add(dtoSupServ);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colViewServicio;
    }

    public Tiposervicio getTipoServicioById(int idTipoServicio) {
        Tiposervicio tipoSer = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("Select * from tiposervicio where idTipoServicio = :idTipo and estado = 1").addEntity(Tiposervicio.class);
            q.setParameter("idTipo", idTipoServicio);
            tipoSer = (Tiposervicio) q.uniqueResult();
            return tipoSer;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Ubicacionservicio altaUbicacionServicio(Ubicacionservicio ubicacion) {
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            if (ubicacion != null) {
                s.save(ubicacion);
            }
            tx.commit();
            s.close();
            return ubicacion;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public dtoMensaje altaServicioSolicitante(Serviciosolicitante servSol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            s.save(servSol);
            tx.commit();
            s.close();
            msg.tipo = Status.success.toString();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje modificarServicio(dtoServicio serv) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE servicio set descripcion= :descrip , idTipoServicio= :idTipoServ , estado= :estado , fechaModificacion= :fechaCambioEstado , observaciones= :observaciones where idServicio = :idServicio");
            q.setParameter("idServicio", serv.idServicio);
            q.setParameter("idTipoServ", serv.idCategoria);
            q.setParameter("estado", serv.estado);
            q.setParameter("descrip", serv.descripcion);
            q.setParameter("fechaCambioEstado", serv.fechaCambioEstado);
            q.setParameter("observaciones", serv.observaciones);
            q.executeUpdate();
            tx.commit();
            s.close();
            msg.tipo = Status.success.toString();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

    public dtoMensaje modificarServicioEntidadesExternas(Date fechaCambioEstado, dtoServicioEntidadesExternas serv) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Session s = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            tx = s.beginTransaction();
            Query q = s.createSQLQuery("UPDATE servicio set estado= :estado , fechaModificacion= :fechaModificacion , observaciones= :observaciones where idServicio = :idServicio");
            q.setParameter("idServicio", serv.idServicio);
            q.setParameter("estado", serv.estado);
            q.setParameter("observaciones", serv.observaciones);
            q.setParameter("fechaModificacion", fechaCambioEstado);

            q.executeUpdate();
            tx.commit();
            s.close();

            msg.msg = "OK";
            msg.tipo = Status.success.toString();
        } catch (Exception ex) {
            msg.tipo = Status.danger.toString();
            msg.msg = ex.getMessage();
        }
        return msg;
    }

}
