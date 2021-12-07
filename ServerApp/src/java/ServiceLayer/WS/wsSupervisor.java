package ServiceLayer.WS;

import Business.Facade.FSupervisor;
import Business.Interfaces.ISupervisor;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import Common.DTO.dtoSupervisor;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsSupervisor")
public class wsSupervisor {

    @Context
    private UriInfo context;
    private Gson gson;

    public wsSupervisor() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupervisores")
    public String getSupervisores(String token) {
        ISupervisor iu = new FSupervisor() {
        };
        return gson.toJson(iu.getSupervisores(token));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSupervisoresByServicio")
    public String getSupervisoresByServicio(String json) {
        ISupervisor iu = new FSupervisor() {
        };
        dtoServicio serv = gson.fromJson(json, dtoServicio.class);
        return gson.toJson(iu.getSupervisoresByServicio(serv));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaSupervisor")
    public String altaSupervisor(String json) {
        ISupervisor is = new FSupervisor() {
        };
        dtoSupervisor supervisor = gson.fromJson(json, dtoSupervisor.class);
        return gson.toJson(is.altaSupervisor(supervisor));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarSupervisor")
    public String modificarSupervisor(String json) {
        ISupervisor s = new FSupervisor() {
        };
        dtoSupervisor supervisor = gson.fromJson(json, dtoSupervisor.class);
        dtoMensaje msg = s.modificarSupervisor(supervisor);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrarSupervisor")
    public String borrarSupervisor(String json) {
        ISupervisor s = new FSupervisor() {
        };
        dtoSupervisor dtoSupervisor = gson.fromJson(json, dtoSupervisor.class);
        return gson.toJson(s.borrarSupervisor(dtoSupervisor));
    }

}
