package Business.Interfaces;

import Common.DTO.dtoCambioClave;
import Common.DTO.dtoLogin;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoResultLogin;
import Common.DTO.dtoUserPass;
import Common.DTO.dtoUsuario;
import Common.DTO.dtoUsuarioEmpresa;
import Common.DTO.dtoUsuarioGrid;
import java.util.List;

public interface IUsuario {

    dtoUsuario logIn(dtoLogin dataLogin);

    dtoResultLogin logInExternalSistem(dtoLogin dataLogin);

    void logOut(String token);

    Boolean isLogged(String userToken);

    dtoMensaje getUsuarios(dtoUsuario usr);

    dtoMensaje altaUsuario(dtoUsuario usr);
    
    dtoMensaje altaUsuarioMovil(dtoUsuario usr);

    dtoMensaje borrarUsuario(dtoUsuario usr);

    dtoMensaje modificarUsuario(dtoUsuario usr);

    dtoMensaje modificarUsuarioMovil(dtoUsuario usr);

    dtoMensaje cambiarClave(dtoCambioClave cambio);

    dtoMensaje actualizarEmpreasaUsuario(dtoUsuarioEmpresa datos);

    dtoMensaje recuperarPass(dtoUserPass user);
}
