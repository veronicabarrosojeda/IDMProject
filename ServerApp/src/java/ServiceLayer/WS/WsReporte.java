/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer.WS;

import Business.Facade.FReporte;
import Business.Interfaces.IReporte;
import Common.DTO.dtoGenerarReporte;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("wsReporte")
public class WsReporte {

    @Context
    private UriInfo context;
    private Gson gson;


    public WsReporte() {
         gson = new Gson();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getReporteByTipo")
    public String getReporteByTipo(String json) {
        IReporte ir = new FReporte(){};      
        
        dtoGenerarReporte dtoDataRep = gson.fromJson(json, dtoGenerarReporte.class);
        return gson.toJson( ir.getReporteById(dtoDataRep));
    }

    /**
     * Retrieves representation of an instance of ServiceLayer.WS.WsReporte
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of WsReporte
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
