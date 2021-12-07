package Business.Facade;

import Common.DTO.dtoLogin;
import Common.DTO.dtoUsuario;
import Business.Interfaces.IUsuario;
import Common.DTO.dtoCambioClave;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoResultLogin;
import Common.DTO.dtoUserPass;
import Common.DTO.dtoUsuarioEmpresa;
import Common.DTO.dtoUsuarioGrid;
import java.util.List;

public abstract class FUsuario implements IUsuario {

    public dtoUsuario logIn(dtoLogin dataLogin) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().logIn(dataLogin);
    }
    
    public dtoResultLogin logInExternalSistem(dtoLogin dataLogin)
    {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().logInExternalSistem(dataLogin);
    }

    public void logOut(String token) {
        Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().logOut(token);
    }

    public Boolean isLogged(String userToken) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().isLogged(userToken);
    }

    public dtoMensaje getUsuarios(dtoUsuario usr) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().getUsuarios(usr);
    }
    
    public dtoMensaje altaUsuario(dtoUsuario usr) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().altaUsuario(usr);
    }
    
    public dtoMensaje altaUsuarioMovil(dtoUsuario usr) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().altaUsuarioMovil(usr);
    }

    public dtoMensaje borrarUsuario(dtoUsuario usr) {
       return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().borrarUsuario(usr);
    }

    public dtoMensaje modificarUsuario(dtoUsuario usr) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().modificarUsuario(usr);
    }
    public dtoMensaje modificarUsuarioMovil(dtoUsuario usr) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().modificarUsuarioMovil(usr);
    }

    public dtoMensaje cambiarClave(dtoCambioClave cambio) {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().cambiarClave(cambio);
    }
    
    public dtoMensaje actualizarEmpreasaUsuario(dtoUsuarioEmpresa datos)
    {
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().actualizarEmpreasaUsuario(datos);
    }
    public dtoMensaje recuperarPass(dtoUserPass user){
        return Business.Logic.Controllers.CUsuario.getNewInstanceCUsuario().recuperarPass(user);
    }
}