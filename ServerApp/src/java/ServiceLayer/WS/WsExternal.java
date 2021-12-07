/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceLayer.WS;

import Business.Facade.FServicio;
import Business.Facade.FUsuario;
import Business.Interfaces.IServicio;
import Business.Interfaces.IUsuario;
import Business.Logic.Controllers.CUsuario;
import Common.DTO.dtoGetServicios;
import Common.DTO.dtoLogin;
import Common.DTO.dtoServicioEntidadesExternas;
import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;


@Path("wsExternal")
public class WsExternal {

    @Context
    private UriInfo context;
    private Gson gson;
    /**
     * Creates a new instance of WsExternal
     */
    public WsExternal() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logIn")
    public String logIn(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoLogin usuario = gson.fromJson(json, dtoLogin.class);
        return gson.toJson(iu.logInExternalSistem(usuario));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServicios")
    public String getServicios(String json) {
        IServicio is = new FServicio() {
        };
        dtoGetServicios response = gson.fromJson(json, dtoGetServicios.class);
        return gson.toJson(is.getServiciosByToken(response));
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarEstado")
    public String modificarEstado(String json) {
        IServicio is = new FServicio() {
        };
        dtoServicioEntidadesExternas response = gson.fromJson(json, dtoServicioEntidadesExternas.class);
        return gson.toJson(is.modificarServicioEntidadesExternas(response));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logOut")
    public void logOut(String userToken) {
        CUsuario iu = new CUsuario() {
        };
        iu.logOut(userToken);
    }
}
