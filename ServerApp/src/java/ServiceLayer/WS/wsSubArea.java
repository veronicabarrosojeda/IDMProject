package ServiceLayer.WS;

import Business.Facade.FSubArea;
import Business.Interfaces.ISubArea;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoSubArea;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsSubArea")
public class wsSubArea {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsSubArea
     */
    public wsSubArea() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSubAreas")
    public String getSubAreas() {

        ISubArea iSuba = new FSubArea() {
        };
        return gson.toJson(iSuba.getSubAreas());

    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSubAreasDeArea")
    public String getSubAreasDeArea(String idArea) {
        ISubArea iSuba = new FSubArea() {};
        Integer idA = Integer.parseInt(idArea);
        return gson.toJson(iSuba.getSubAreasDeArea(idA));

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaSubArea")
    public String altaSubArea(String json) {
        ISubArea iSub = new FSubArea() {};
        dtoSubArea subArea = gson.fromJson(json, dtoSubArea.class);
        dtoMensaje msg = iSub.altaSubArea(subArea);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrarSubArea")
    public String borrarSubArea(String idSubArea) {
        ISubArea iSub = new FSubArea() {};
        Integer idSub = Integer.parseInt(idSubArea);
        dtoMensaje msg = iSub.borrarSubArea(idSub);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarSubArea")
    public String modificarSubArea(String json) {
        ISubArea iSub = new FSubArea() {};
        dtoSubArea subArea = gson.fromJson(json, dtoSubArea.class);
        dtoMensaje msg = iSub.modificarSubArea(subArea);
        return gson.toJson(msg);
    }
}