package ServiceLayer.WS;

import Business.Facade.FEmpresa;
import Business.Interfaces.IEmpresa;
import Common.DTO.dtoEmpresa;
import Common.DTO.dtoMensaje;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsEmpresa")
public class wsEmpresa {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of wsEmpresa
     */
    public wsEmpresa() {
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.wsEmpresa
     *
     * @return an instance of java.lang.String
     */

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getEmpresas")
    public String getEmpresas() {
        IEmpresa ie = new FEmpresa() {
        };
        return gson.toJson(ie.getEmpresas());

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getEmpresasByUser")
    public String getEmpresasByUser(String json) {
        IEmpresa ie = new FEmpresa() {
        };
        Integer idUsuario = Integer.parseInt(json);
        return gson.toJson(ie.getEmpresasByUser(idUsuario));

    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getEmpresasByService")
    public String getEmpresasByService(String json) {
        IEmpresa ie = new FEmpresa() {
        };
        Long idServicio = Long.parseLong(json);
        return gson.toJson(ie.getEmpresasByService(idServicio));

    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaEmpresa")
    public String altaEmpresa(String json) {
        IEmpresa ie = new FEmpresa() {};
        dtoEmpresa empresa = gson.fromJson(json, dtoEmpresa.class);
        dtoMensaje msg = ie.altaEmpresa(empresa);
        return gson.toJson(msg);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarEmpresa")
    public String modificarEmpresa(String json) {
        IEmpresa ie = new FEmpresa() {};
        dtoEmpresa empresa = gson.fromJson(json, dtoEmpresa.class);
        dtoMensaje msg = ie.modificarEmpresa(empresa);
        return gson.toJson(msg);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/bajaEmpresa")
    public String bajaEmpresa(String json) {
        int idEmpresa = Integer.parseInt(json);
        IEmpresa ie = new FEmpresa(){};
        dtoMensaje msg = ie.bajaEmpresa(idEmpresa);
        return gson.toJson(msg);
    }
}
