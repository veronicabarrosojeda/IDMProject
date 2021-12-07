/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Persistencia;

import Business.Logic.Controllers.CGeneric;
import Common.DTO.dtoGenerarReporte;
import Common.DTO.dtoReporteCobertura;
import Common.DTO.dtoReportePorFecha;
import Common.DTO.dtoReporteTipoOrigen;
import Common.DTO.dtoReporteTipoServicio;
import DataAccess.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PReporte {

    private static PReporte newInstancePReporte;

    public static PReporte newInstancePReporte() {
        if (newInstancePReporte == null) {
            newInstancePReporte = new PReporte();
        }
        return newInstancePReporte;
    }

    public List<Object> reporteByTipo(dtoGenerarReporte data) throws Exception {
        List<Object> colDataReporte = new ArrayList<Object>();
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            if (data.tipo.equals("ServiciosPorFecha")) {
                List<dtoReportePorFecha> colReporte = new ArrayList<dtoReportePorFecha>();
                Query q = s.createSQLQuery("Select serv.fechaIngreso, count(serv.idServicio) as qtSolicitudes from servicio as serv where serv.fechaIngreso >= :desde and serv.fechaIngreso <= :hasta group by fechaIngreso");
                q.setParameter("desde", data.fechaDesde);
                q.setParameter("hasta", data.fechaHasta);
                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                for (Object result : q.list()) {
                    dtoReportePorFecha dtoRep = new dtoReportePorFecha();
                    dtoRep = (dtoReportePorFecha) CGeneric.hasMapToObject(dtoRep, result);
                    colReporte.add(dtoRep);
                }
                colDataReporte = (List<Object>) (List<?>) colReporte;
            } else if (data.tipo.equals("ServiciosPorFechaEstado")) {
                List<dtoReportePorFecha> colReporte = new ArrayList<dtoReportePorFecha>();
                Query q = s.createSQLQuery("SELECT ser.estado, td.nombre, count(ser.idServicio) as qtSolicitudes FROM servicio as ser inner join tipodetalle as td on ser.estado = td.idTipoDetalle where td.idTipo = 2 and ser.fechaIngreso >= :desde and ser.fechaIngreso <= :hasta group by estado");
                q.setParameter("desde", data.fechaDesde);
                q.setParameter("hasta", data.fechaHasta);
                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                for (Object result : q.list()) {
                    dtoReportePorFecha dtoRep = new dtoReportePorFecha();
                    dtoRep = (dtoReportePorFecha) CGeneric.hasMapToObject(dtoRep, result);
                    colReporte.add(dtoRep);
                }
                colDataReporte = (List<Object>) (List<?>) colReporte;

            } else if (data.tipo.equals("Cobertura")) {
                List<dtoReporteCobertura> colReporte = new ArrayList<dtoReporteCobertura>();
                Query q = s.createSQLQuery("Select serv.fechaIngreso, count(serv.idServicio) as total, IFNULL((Select count(serv2.idServicio) as qtFinalizados from servicio as serv2 where (serv2.estado = 2 or serv2.estado = 4) and serv2.fechaIngreso = serv.fechaIngreso group by fechaIngreso),0) as finalizados from servicio as serv where serv.fechaIngreso >= :desde and serv.fechaIngreso <= :hasta group by fechaIngreso");
                q.setParameter("desde", data.fechaDesde);
                q.setParameter("hasta", data.fechaHasta);
                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                for (Object result : q.list()) {
                    dtoReporteCobertura dtoRep = new dtoReporteCobertura();
                    dtoRep = (dtoReporteCobertura) CGeneric.hasMapToObject(dtoRep, result);
                    colReporte.add(dtoRep);
                }
                colDataReporte = (List<Object>) (List<?>) colReporte;

            } else if (data.tipo.equals("TipoServicio")) {
                List<dtoReporteTipoServicio> colReporte = new ArrayList<dtoReporteTipoServicio>();
                Query q = s.createSQLQuery("SELECT ser.idTipoServicio, ts.nombre, count(ser.idServicio) as qtSolicitudes FROM servicio as ser inner join tiposervicio as ts on ser.idTipoServicio = ts.idTipoServicio where ser.fechaIngreso >= :desde and ser.fechaIngreso <= :hasta group by idTipoServicio");
                q.setParameter("desde", data.fechaDesde);
                q.setParameter("hasta", data.fechaHasta);

                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                for (Object result : q.list()) {
                    dtoReporteTipoServicio dtoRep = new dtoReporteTipoServicio();
                    dtoRep = (dtoReporteTipoServicio) CGeneric.hasMapToObject(dtoRep, result);
                    colReporte.add(dtoRep);
                }
                colDataReporte = (List<Object>) (List<?>) colReporte;
            }
            else if (data.tipo.equals("TipoOrigen")) {
                List<dtoReporteTipoOrigen> colReporte = new ArrayList<dtoReporteTipoOrigen>();
                Query q = s.createSQLQuery("Select count(serv.idServicio) as qtCallCenter, (Select count(serv1.idServicio) from servicio as serv1 where serv1.tipoOrigen = 2 and serv1.fechaIngreso >= :desde and serv1.fechaIngreso <= :hasta) as qtApp from servicio as serv where serv.tipoOrigen = 1 and serv.fechaIngreso >= :desde and serv.fechaIngreso <= :hasta");
                q.setParameter("desde", data.fechaDesde);
                q.setParameter("hasta", data.fechaHasta);

                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                for (Object result : q.list()) {
                    dtoReporteTipoOrigen dtoRep = new dtoReporteTipoOrigen();
                    dtoRep = (dtoReporteTipoOrigen) CGeneric.hasMapToObject(dtoRep, result);
                    colReporte.add(dtoRep);
                }
                colDataReporte = (List<Object>) (List<?>) colReporte;
            }
            
            
            s.close();
            return colDataReporte;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
