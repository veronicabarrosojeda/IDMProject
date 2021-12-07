/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Facade;

import Business.Interfaces.IServicio;
import Business.Logic.Controllers.CServicio;
import Common.DTO.dtoDataGetServicio;
import Common.DTO.dtoEmpresaServicio;
import Common.DTO.dtoGetDataSolicitud;
import Common.DTO.dtoGetServicios;
import Common.DTO.dtoGetServiciosUser;
import Common.DTO.dtoListServicioEmpresa;
import Common.DTO.dtoListServicioSupervisor;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoResultServicios;
import Common.DTO.dtoServicio;
import Common.DTO.dtoServicioEntidadesExternas;
import Common.DTO.dtoServicioSolicitante;
import Common.DTO.dtoServicioVista;
import Common.DTO.dtoSolicitantes;
import Common.DTO.dtoSupervisor;
import Common.DTO.dtoSupervisorServicio;
import Common.DTO.dtoViewServicio;
import java.util.List;

/**
 *
 * @author Facundo
 */
public abstract class FServicio implements IServicio {

    @Override
    public List<dtoViewServicio> getSolicitudesInEstados(dtoGetDataSolicitud estados) {
        return CServicio.getInstanceCServicio().getSolicitudesInEstados(estados);

    }
    
    @Override
    public List<dtoViewServicio> getSolicitudesInEstadosMovil(dtoGetDataSolicitud estados) {
        return CServicio.getInstanceCServicio().getSolicitudesInEstadosMovil(estados);

    }

    @Override
    public dtoMensaje altaServicio(dtoServicio ser) {
        return Business.Logic.Controllers.CServicio.getInstanceCServicio().altaServicio(ser);
    }

    @Override
    public dtoMensaje altaServicioMovil(dtoServicio ser) {
        return Business.Logic.Controllers.CServicio.getInstanceCServicio().altaServicioMovil(ser);
    }
    
    @Override
    public dtoMensaje unirseServicioMovil(dtoServicioSolicitante serSol) {
        return Business.Logic.Controllers.CServicio.getInstanceCServicio().unirseServicioMovil(serSol);
    }

    @Override
    public List<dtoSolicitantes> getSolicitantes(long idServicio) {
        return CServicio.getInstanceCServicio().getSolicitantes(idServicio);
    }

    @Override
    public dtoMensaje actulizarEmpresasServicio(dtoEmpresaServicio empresaServicio) {
        return CServicio.getInstanceCServicio().actualizarEmpresasServicio(empresaServicio);
    }

    @Override
    public dtoMensaje actulizarSupervisorServicio(dtoSupervisorServicio supServ) {
        return CServicio.getInstanceCServicio().actualizarSupervisorServicio(supServ);
    }

    @Override
    public dtoServicioVista getServicio(dtoDataGetServicio data) {
        return CServicio.getInstanceCServicio().getServicio(data);
    }

    @Override
    public List<dtoListServicioSupervisor> getSupervisorServicios(int idSupervisor) {
        return CServicio.getInstanceCServicio().getSupervisorServicios(idSupervisor);
    }

    @Override
    public dtoMensaje modificarServicio(dtoServicio servicio) {
        return CServicio.getInstanceCServicio().modificarServicio(servicio);
    }

    @Override
    public List<dtoViewServicio> getServiciosUser(dtoGetServiciosUser data) {
        return CServicio.getInstanceCServicio().getServiciosUser(data);
    }
    @Override
    public dtoServicioVista getServicioMovil(Long idServicio)
    {
        return CServicio.getInstanceCServicio().getServicioMovil(idServicio);
    }
    
    @Override
    public dtoMensaje modificarServicioEntidadesExternas(dtoServicioEntidadesExternas servicio)
    {
        return CServicio.getInstanceCServicio().modificarServicioEntidadesExternas(servicio);
    }
    
    @Override
    public dtoResultServicios getServiciosByToken(dtoGetServicios response)
    {
        return CServicio.getInstanceCServicio().getServiciosByTocken(response);
    }
    
    @Override
    public List<dtoListServicioEmpresa> getSupervisorEmpresa(Integer idEmpresa)
    {
        return CServicio.getInstanceCServicio().getSupervisorEmpresa(idEmpresa);
    }
}
