package ServiceLayer.WS;

import Business.Facade.FArea;
import Business.Interfaces.IArea;
import Common.DTO.dtoArea;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("wsArea")
public class wsArea {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsArea
     */
    public wsArea() {
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.wsArea
     * @return an instance of java.lang.String
     */
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listarAreas")
    public String listarAreas() {
        IArea ia = new FArea(){};
        return gson.toJson(ia.listarAreas());
    }
    
    @POST
    @Path("/listarAreaFiltro")
    public String listarAreaFiltro(String filtro) { 
        IArea ia = new FArea(){};
        return gson.toJson(ia.listarAreaFiltro(filtro));
        
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaArea")
    public String altaArea(String json) {
        IArea ia = new FArea(){};
        dtoArea area = gson.fromJson(json, dtoArea.class);
        return gson.toJson(ia.altaArea(area));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getArea")
    public String getArea(String json) {      
        int idArea = Integer.parseInt(json); 
        IArea ia = new FArea(){};
        dtoArea d = ia.getArea(idArea);
        String a = gson.toJson(d);
        return  a;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarArea")
    public String modificarArea(String json) {
        IArea ia = new FArea(){};
        dtoArea area = gson.fromJson(json, dtoArea.class);
        return gson.toJson(ia.modificarArea(area));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bajaArea")
    public String bajaArea(String json) {
        int idArea = Integer.parseInt(json);
        IArea ia = new FArea(){};
        return gson.toJson(ia.bajaArea(idArea));
    }
}