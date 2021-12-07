package ServiceLayer.WS;

import Business.Facade.FUsuario;
import Business.Interfaces.IUsuario;
import Business.Logic.Controllers.CUsuario;
import Common.DTO.dtoCambioClave;
import Common.DTO.dtoLogin;
import Common.DTO.dtoUserPass;
import Common.DTO.dtoUsuario;
import Common.DTO.dtoUsuarioEmpresa;
import com.google.gson.Gson;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("wsUsuario")
public class wsUsuario {

    @Context
    private UriInfo context;
    private Gson gson;

    public wsUsuario() {
        gson = new Gson();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logIn")
    public String logIn(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoLogin usuario = gson.fromJson(json, dtoLogin.class);
        return gson.toJson(iu.logIn(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsuarios")
    public String getUsuarios(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.getUsuarios(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/borrarUsuario")
    public String borrarUsuario(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.borrarUsuario(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/logOut")
    public void logOut(String userToken) {
        IUsuario iu = new FUsuario() {
        };
        iu.logOut(userToken);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaUsuario")
    public String altaUsuario(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.altaUsuario(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/altaUsuarioMovil")
    public String altaUsuarioMovil(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.altaUsuarioMovil(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarUsuario")
    public String modificarUsuario(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.modificarUsuario(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modificarUsuarioMovil")
    public String modificarUsuarioMovil(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUsuario usuario = gson.fromJson(json, dtoUsuario.class);
        return gson.toJson(iu.modificarUsuarioMovil(usuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/isLogged")
    public String isLogged(String userToken) {
        CUsuario iu = new CUsuario() {
        };
        return gson.toJson(iu.isLogged(userToken));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cambiarClave")
    public String cambiarClave(String json) {
        CUsuario iu = new CUsuario() {
        };
        dtoCambioClave cambioClave = gson.fromJson(json, dtoCambioClave.class);
        return gson.toJson(iu.cambiarClave(cambioClave));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizarEmpresaUsuario")
    public String actualizarEmpresaUsuario(String json) {
        CUsuario iu = new CUsuario() {
        };
        dtoUsuarioEmpresa empresaUsuario = gson.fromJson(json, dtoUsuarioEmpresa.class);
        return gson.toJson(iu.actualizarEmpreasaUsuario(empresaUsuario));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recuperarPass")
    public String recuperarPass(String json) {
        IUsuario iu = new FUsuario() {
        };
        dtoUserPass usuario = gson.fromJson(json, dtoUserPass.class);
        return gson.toJson(iu.recuperarPass(usuario));
    }
}
