package ServiceLayer.WS;

import Business.Facade.FPermiso;
import Business.Interfaces.IPermiso;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import Common.DTO.dtoRolPermiso;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsPermiso")
public class wsPermiso {

    @Context
    private UriInfo context;
    private Gson gson;

    public wsPermiso() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizarRolPermiso")
    public String actualizarRolPermiso(String json) {
        IPermiso ip = new FPermiso() {
        };
        dtoRolPermiso rolpermiso = gson.fromJson(json, dtoRolPermiso.class);
        dtoMensaje msg = ip.actualizarPermisosRol(rolpermiso);
        return gson.toJson(msg);
    }

     @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPermisos")
    public String getPermisos(String json) {
        IPermiso ip = new FPermiso() {
        };
        dtoRol rol = gson.fromJson(json, dtoRol.class);
        return gson.toJson(ip.getPermisos(rol));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPermisosByRol")
    public String getPermisosByRol(String json) {
        IPermiso ip = new FPermiso() {
        };
        dtoRol rol = gson.fromJson(json, dtoRol.class);
        return gson.toJson(ip.getPermisosByRol(rol));
    }
}
