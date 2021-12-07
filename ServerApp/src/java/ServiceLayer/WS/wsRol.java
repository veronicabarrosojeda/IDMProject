package ServiceLayer.WS;

import Business.Facade.FRol;
import Business.Interfaces.IRol;
import Common.DTO.dtoRol;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsRol")
public class wsRol {

    @Context
    private UriInfo context;
    private Gson gson;
    /**
     * Creates a new instance of wsRol
     */
    public wsRol() {
        gson = new Gson();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getRoles")
    public String getRoles(String token) {
        IRol ir = new FRol() {
        };
        return gson.toJson(ir.getRoles(token));        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaRol")
    public String altaRol(String json) {
        IRol ir = new FRol() {
        };
        dtoRol rol = gson.fromJson(json, dtoRol.class);             
        return gson.toJson(ir.altaRol(rol));        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarRol")
    public String modificarRol(String json) {
        IRol ir = new FRol() {
        };
        dtoRol rol = gson.fromJson(json, dtoRol.class);    
        return gson.toJson(ir.modificarRol(rol));        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrarRol")
    public String borrarRol(String json) {
        IRol ir = new FRol() {
        };
        dtoRol rol = gson.fromJson(json, dtoRol.class);
        return gson.toJson(ir.borrarRol(rol));        
    }
}
