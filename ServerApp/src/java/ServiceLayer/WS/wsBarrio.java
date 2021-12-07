package ServiceLayer.WS;

import Business.Facade.FBarrio;
import Business.Interfaces.IBarrio;
import Common.DTO.dtoBarrio;
import Common.DTO.dtoMensaje;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsBarrio")
public class wsBarrio {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsBarrio
     */
    public wsBarrio() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaBarrio")
    public String altaBarrio(String json) {
        IBarrio ib = new FBarrio() {
        };
        dtoBarrio barrio = gson.fromJson(json, dtoBarrio.class);
        dtoMensaje msg = ib.altaBarrio(barrio);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bajaBarrio")
    public String bajaBarrio(String idBarrio) {
        IBarrio ib = new FBarrio() {
        };
        Integer idBar = Integer.parseInt(idBarrio);
        dtoMensaje msg = ib.bajaBarrio(idBar);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarBarrio")
    public String modificarBarrio(String json) {
        IBarrio ib = new FBarrio() {
        };
        dtoBarrio barrio = gson.fromJson(json, dtoBarrio.class);
        dtoMensaje msg = ib.modificarBarrio(barrio);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listarBarrios")
    public String listarBarrios() {
        IBarrio ib = new FBarrio() {
        };
        return gson.toJson(ib.listarBarrios());
    }
}