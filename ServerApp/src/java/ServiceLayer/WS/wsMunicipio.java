package ServiceLayer.WS;

import Business.Facade.FMunicipio;
import Business.Interfaces.IMunicipio;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoMunicipio;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("wsMunicipio")
public class wsMunicipio {

    @Context
    private UriInfo context;
    private Gson gson;
    /**
     * Creates a new instance of WsMunicipio
     */
    public wsMunicipio() {
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.WsMunicipio
     * @param json
     * @return an instance of java.lang.String
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaMunicipio")
    public String altaMuncipio(String json)
    {
        IMunicipio im = new FMunicipio(){};
        dtoMunicipio municipio = gson.fromJson(json, dtoMunicipio.class);
        return gson.toJson(im.altaMunicipio(municipio));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarMunicipio")
    public String modificarMunicipio(String json) {   
        IMunicipio im = new FMunicipio(){};
        dtoMunicipio municipio = gson.fromJson(json, dtoMunicipio.class);
        dtoMensaje msg = im.modificarMunicipio(municipio);
        return gson.toJson(msg);              
    }
            
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrarMunicipio")
    public String borrarMunicipio(String idMunicipio) {   
        IMunicipio im = new FMunicipio(){};
        Integer idMun = Integer.parseInt(idMunicipio);
        dtoMensaje msg = im.borrarMunicipio(idMun);
        return gson.toJson(msg);              
    }
  
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getMunicipios")
    public String getMunicipios()
    {
        IMunicipio im = new FMunicipio(){};
       return gson.toJson(im.getMunicipios());
    }
}