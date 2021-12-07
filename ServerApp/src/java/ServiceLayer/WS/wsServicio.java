/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer.WS;

import Business.Facade.FServicio;
import Business.Interfaces.IServicio;
import Common.DTO.dtoDataGetServicio;
import Common.DTO.dtoEmpresaServicio;
import Common.DTO.dtoGetDataSolicitud;
import Common.DTO.dtoGetServiciosUser;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import Common.DTO.dtoServicioSolicitante;
import Common.DTO.dtoSupervisorServicio;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("wsServicio")
public class wsServicio {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsServicio
     */
    public wsServicio() {
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.wsServicio
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSolicitudesForStatus")
    public String getSolicitudesForStatus(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoGetDataSolicitud estados = new dtoGetDataSolicitud();
        estados = gson.fromJson(json, dtoGetDataSolicitud.class);
        return gson.toJson(iServ.getSolicitudesInEstados(estados));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSolicitudesForStatusMovil")
    public String getSolicitudesForStatusMovil(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoGetDataSolicitud estados = new dtoGetDataSolicitud();
        estados = gson.fromJson(json, dtoGetDataSolicitud.class);
        return gson.toJson(iServ.getSolicitudesInEstadosMovil(estados));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaServicio")
    public String altaServicio(String json) {
        IServicio is = new FServicio() {
        };
        dtoServicio servicio = gson.fromJson(json, dtoServicio.class);
        dtoMensaje msg = is.altaServicio(servicio);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaServicioMovil")
    public String altaServicioMovil(String json) {
        IServicio is = new FServicio() {
        };
        dtoServicio servicio = gson.fromJson(json, dtoServicio.class);
        dtoMensaje msg = is.altaServicioMovil(servicio);
        return gson.toJson(msg);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/unirseServicioMovil")
    public String unirseServicioMovil(String json) {
        IServicio is = new FServicio() {
        };
        dtoServicioSolicitante servicioSol = gson.fromJson(json, dtoServicioSolicitante.class);
        dtoMensaje msg = is.unirseServicioMovil(servicioSol);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSolicitantes")
    public String getSolicitantes(String idServicio) {
        IServicio iServ = new FServicio() {
        };
        long idServ = Long.parseLong(idServicio);
        return gson.toJson(iServ.getSolicitantes(idServ));
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizarEmpresaServicio")
    public String actualizarEmpresaServicio(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoEmpresaServicio servicioEmpresa = gson.fromJson(json, dtoEmpresaServicio.class);
        return gson.toJson(iServ.actulizarEmpresasServicio(servicioEmpresa));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizarSupervisoresServicio")
    public String actualizarSupervisoresServicio(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoSupervisorServicio servicioSupervisor = gson.fromJson(json, dtoSupervisorServicio.class);
        return gson.toJson(iServ.actulizarSupervisorServicio(servicioSupervisor));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServicio")
    public String getServicio(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoDataGetServicio data = gson.fromJson(json, dtoDataGetServicio.class); 
        return gson.toJson(iServ.getServicio(data));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServicioMovil")
    public String getServicioMovil(String idServicio) {
        IServicio iServ = new FServicio() {
        };
        Long idServ = Long.parseLong(idServicio);
        return gson.toJson(iServ.getServicioMovil(idServ));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServiciosUser")
    public String getServiciosUser(String json) {
        IServicio iServ = new FServicio() {
        };
//        Integer idUsu = Integer.parseInt(idU);
        dtoGetServiciosUser data = gson.fromJson(json, dtoGetServiciosUser.class);
        return gson.toJson(iServ.getServiciosUser(data));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupervisorServicios")
    public String getSupervisorServicios(String idSupervisor) {
        IServicio iServ = new FServicio() {
        };
        Integer idSup = Integer.parseInt(idSupervisor);
        return gson.toJson(iServ.getSupervisorServicios(idSup));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupervisorEmpresa")
    public String getSupervisorEmpresa(String idEmpresa) {
        IServicio iServ = new FServicio() {
        };
        Integer idEmp = Integer.parseInt(idEmpresa);
        return gson.toJson(iServ.getSupervisorEmpresa(idEmp));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarServicio")
    public String modificarServicio(String json) {
        IServicio iServ = new FServicio() {
        };
        dtoServicio servicio = gson.fromJson(json, dtoServicio.class);
        return gson.toJson(iServ.modificarServicio(servicio));
    }

    /**
     * PUT method for updating or creating an instance of wsServicio
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
