/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Interfaces;

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
import Common.DTO.dtoSupervisorServicio;
import Common.DTO.dtoViewServicio;
import java.util.List;

/**
 *
 * @author Facundo
 */
public interface IServicio {
    
    List<dtoViewServicio> getSolicitudesInEstados(dtoGetDataSolicitud estados);
    List<dtoViewServicio> getSolicitudesInEstadosMovil(dtoGetDataSolicitud estados);
    dtoMensaje altaServicio(dtoServicio ser);
    dtoMensaje altaServicioMovil(dtoServicio ser);
    dtoMensaje unirseServicioMovil(dtoServicioSolicitante dtoServSol);
    List<dtoSolicitantes> getSolicitantes(long idServicio);
    dtoMensaje actulizarEmpresasServicio(dtoEmpresaServicio empresaServicio);
    dtoMensaje actulizarSupervisorServicio(dtoSupervisorServicio supServ);
    dtoServicioVista getServicio(dtoDataGetServicio data);
    dtoServicioVista getServicioMovil(Long idServicio);
    List<dtoListServicioSupervisor> getSupervisorServicios(int idSupervisor);
    dtoMensaje modificarServicio(dtoServicio servicio);
    List<dtoViewServicio> getServiciosUser(dtoGetServiciosUser data);    
    List<dtoListServicioEmpresa> getSupervisorEmpresa(Integer idEmpresa);
    dtoMensaje modificarServicioEntidadesExternas(dtoServicioEntidadesExternas servicio);
    dtoResultServicios getServiciosByToken(dtoGetServicios response);
}
