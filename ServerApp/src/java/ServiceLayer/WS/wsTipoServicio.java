package ServiceLayer.WS;

import Business.Facade.FTipoServicio;
import Business.Interfaces.ITipoServicio;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoTipoServicio;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsTipoServicio")
public class wsTipoServicio {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsTipoServicio
     */
    public wsTipoServicio() {
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.wsTipoServicio
     * @param json
     * @return an instance of java.lang.String
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaTipoServicio")
    public String altaTipoServicio(String json) {
        ITipoServicio iTS = new FTipoServicio() {
        };
        dtoTipoServicio tipoServicio = gson.fromJson(json, dtoTipoServicio.class);
        dtoMensaje msg = iTS.altaTipoServicio(tipoServicio);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bajaTipoServicio")
    public String bajaTipoServicio(String idTipoServicio) {
        ITipoServicio iTS = new FTipoServicio() {
        };
        Integer idTS = Integer.parseInt(idTipoServicio);
        dtoMensaje msg = iTS.bajaTipoServicio(idTS);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarTipoServicio")
    public String modificarTipoServicio(String json) {
        ITipoServicio iTS = new FTipoServicio() {
        };
        dtoTipoServicio tipoServicio = gson.fromJson(json, dtoTipoServicio.class);
        dtoMensaje msg = iTS.modificarTipoServicio(tipoServicio);
        return gson.toJson(msg);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listarTipoServicio")
    public String listarTipoServicio() {
        ITipoServicio iTS = new FTipoServicio() {
        };
        return gson.toJson(iTS.listarTipoServicio());
    }
    
}
