package ServiceLayer.WS;

import Business.Facade.FListItem;
import Business.Interfaces.IListItem;
import Common.DTO.dtoListItem;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsListItem")
public class WsListItem {

    @Context
    private UriInfo context;
    private Gson gson;

    /**
     * Creates a new instance of WsListItem
     */
    public WsListItem() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getRoles")
    public String getRoles() {
        IListItem il = new FListItem() {
        };
        return gson.toJson(il.getRoles());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getMunicipios")
    public String getMunicipios() {
        IListItem il = new FListItem() {
        };
        return gson.toJson(il.getMunicipios());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getBarriosPorMunicipio")
    public String getBarriosPorMunicipio(String id) {
        IListItem il = new FListItem() {
        };
        Integer idMun = Integer.parseInt(id);
        return gson.toJson(il.getBarriosPorMunicipio(idMun));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDetTipoById")
    public String getDetTipoById(String whereData) {
        IListItem il = new FListItem() {
        };
        Integer idTipo = Integer.parseInt(whereData);
        List<dtoListItem> item = il.getDetTipoById(idTipo);
        return gson.toJson(item);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getCategorias")
    public String getCategorias() {
        IListItem il = new FListItem() {
        };        
        return gson.toJson(il.getCategorias());

    }

    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTipoServicio")
    public String getTipoServicio() {
        IListItem il = new FListItem(){};
        List<dtoListItem> item = il.getTipoServicio();
        return gson.toJson(item);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getEmpresas")
    public String getEmpresas() {
        IListItem il = new FListItem(){};
        List<dtoListItem> item = il.getEmpresas();
        return gson.toJson(item);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAreas")
    public String getAreas() {
        IListItem il = new FListItem(){
        };
        return gson.toJson(il.getAreas());
    }
    
}
