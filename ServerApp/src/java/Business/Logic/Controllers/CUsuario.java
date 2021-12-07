package Business.Logic.Controllers;

import Business.Entities.Empresa;
import Business.Entities.Notificaciones;
import Business.Entities.Permiso;
import Business.Entities.Rol;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoCambioClave;
import Common.DTO.dtoEmpresa;
import Common.DTO.dtoLogin;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoPermiso;
import Common.DTO.dtoResultLogin;
import Common.DTO.dtoRol;
import Common.DTO.dtoUserPass;
import Common.DTO.dtoUsuario;
import Common.DTO.dtoUsuarioEmpresa;
import Common.DTO.dtoUsuarioGrid;
import Common.Enums.Status;
import DataAccess.Persistencia.PEmpresa;
import DataAccess.Persistencia.PRol;
import DataAccess.Persistencia.PUsuario;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CUsuario {
    
    private static CUsuario ICUsario;
    
    public static CUsuario getNewInstanceCUsuario() {
        if (ICUsario == null) {
            return new CUsuario();
        }
        return ICUsario;
    }

    //----SEGURIDAD----
    public dtoUsuario logIn(dtoLogin dataLogin) {
        dtoUsuario dtoUser = new dtoUsuario();
        try {
            dtoUser = validarLogIn(dataLogin);
            if (dtoUser.msg == null || dtoUser.msg.equals("")) {
                Usuario user = DataAccess.Persistencia.PUsuario.newInstancePUsuario().logIn(dataLogin);
                if (user != null) {
                    Rol rol = DataAccess.Persistencia.PRol.newInstancePRol().getRolByUserId(user.getIdUsuario());
                    dtoRol dtoRol = new dtoRol();
                    dtoRol.nombre = rol.getNombre();
                    dtoRol.descripcion = rol.getDescripcion();
                    dtoRol.idRol = rol.getIdRol();
                    dtoRol.estado = rol.isEstado();
                    
                    dtoUser.nickname = user.getNickname();
                    dtoUser.nombre = user.getNombre();
                    dtoUser.apellido = user.getApellido();
                    dtoUser.correo = user.getCorreo();
                    dtoUser.telefono = user.getTelefono();
                    dtoUser.nroIdentidad = user.getNroIdentidad();
                    dtoUser.tipoIdentidad = user.getTipoIdentidad();
                    dtoUser.token = user.getToken();
                    dtoUser.idUsuario = user.getIdUsuario();
                    dtoUser.dtoRol = dtoRol;
                    dtoUser.isLogged = true;
                    List<Permiso> colPermisos = DataAccess.Persistencia.PPermiso.newInstancePPermiso().getPermisosByUserId(dtoUser.idUsuario);
                    List<Empresa> colEmpresa = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresasByUser(dtoUser.idUsuario);
                    CEmpresa ce = new CEmpresa();
                    dtoUser.colEmpresas = ce.EmpresaToDto(colEmpresa);
                    dtoUser.colPermiso = new ArrayList<>();
                    dtoUser.colPermiso = permisoToDtoPermiso(colPermisos);
                } else {
                    throw new Exception("Nombre de usuario o contraseña incorrecto");
                }
            }
        } catch (Exception ex) {
            dtoUser.status = Status.danger.toString();
            dtoUser.msg = ex.getMessage();
            dtoUser.isLogged = false;
        }
        return dtoUser;
    }
    
//    public dtoMensaje VerificarToken(String token) throws Exception {
//        dtoMensaje msg = new dtoMensaje();
//        try {
//            if (token == null || token == "" || token.equals("")) {
//                msg.addMsgError(CError.ErrorAut);
//            } else {
//                CUsuario cUsr = new CUsuario();
//                Usuario usr = cUsr.getUserByToken(token);
//                if (usr == null) {
//                msg.addMsgError(CError.ErrorAut);
//                }
//            }
//        } catch (Exception ex) {
//            msg.addMsgError(CError.ErrorIn);
//        }
//        return msg;
//    }
    
    public void logOut(String token) {
        DataAccess.Persistencia.PUsuario.newInstancePUsuario().logOut(token);
    }
    
    public Boolean isLogged(String token) {
        try {
            return PUsuario.newInstancePUsuario().findUserByToken(token) != null;
        } catch (Exception ex) {
            throw ex;
        }
    }
    //--------------------------

    public Usuario getUserByToken(String token) throws Exception {
        try {
            return PUsuario.newInstancePUsuario().findUserByToken(token);
        } catch (Exception ex) {
            throw new Exception(CError.ErrorBD + "G4P");
        }
    }
    
    public dtoMensaje altaUsuario(dtoUsuario dtoUser) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (dtoUser.token == null || dtoUser.token == "" || dtoUser.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 1C2");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(dtoUser.token);
                if (usr != null) {
                    
                    msg = validarUsuario(dtoUser, true);
                    if (!msg.isError()) {
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setApellido(dtoUser.apellido);
                        nuevoUsuario.setCorreo(dtoUser.correo);
                        nuevoUsuario.setNombre(dtoUser.nombre);
                        nuevoUsuario.setNickname(dtoUser.nickname);
                        nuevoUsuario.setPassword(dtoUser.password);
                        nuevoUsuario.setRol(PRol.newInstancePRol().getRolByRolId(dtoUser.idRol));
                        nuevoUsuario.setEstado(true);
                        msg = PUsuario.newInstancePUsuario().altaUsuario(nuevoUsuario);
                        if (!msg.isError()) {
                            msg.addMsgOk("Usuario " + CError.registrado);
                        }
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 1C2");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 1C1");
        }
        
        return msg;
    }
    
    public dtoMensaje altaUsuarioMovil(dtoUsuario dtoUser) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarUsuario(dtoUser, true);
            if (!msg.isError()) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setApellido(dtoUser.apellido);
                nuevoUsuario.setCorreo(dtoUser.correo);
                nuevoUsuario.setTelefono(dtoUser.telefono);
                nuevoUsuario.setNombre(dtoUser.nombre);
                nuevoUsuario.setNickname(dtoUser.nickname);
                
                MessageDigest mdEnc = MessageDigest.getInstance("MD5");
                mdEnc.update(dtoUser.password.getBytes(), 0, dtoUser.password.length());
                String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
                
                nuevoUsuario.setPassword(md5);
                nuevoUsuario.setRol(PRol.newInstancePRol().getRolByRolId(dtoUser.idRol));
                nuevoUsuario.setEstado(true);
                msg = PUsuario.newInstancePUsuario().altaUsuario(nuevoUsuario);
                if (!msg.isError()) {
                    msg.addMsgOk("¡Usuario registrado correctamente!");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }
    
    public dtoResultLogin logInExternalSistem(dtoLogin dataLogin) {
        dtoResultLogin result = new dtoResultLogin();
        try {
            if (dataLogin.nickname == null || dataLogin.nickname.equals("")) {
                throw new Exception("Nickname requerido");
            }
            
            if (dataLogin.password == null || dataLogin.password.equals("")) {
                throw new Exception("Password requerida");
            }
            
            Usuario user = PUsuario.newInstancePUsuario().logIn(dataLogin);
            result.status = Status.success.toString();
            result.msg = "OK";
            result.token = user.getToken();
            
        } catch (Exception ex) {
            result.status = Status.danger.toString();
            result.msg = ex.getMessage();
            result.token = "";
        }
        return result;
    }
    
    public List<dtoPermiso> permisoToDtoPermiso(List<Permiso> colPermisos) {
        try {
            List<dtoPermiso> colDtoPermiso = new ArrayList<>();
            for (Permiso per : colPermisos) {
                dtoPermiso dtoPer = new dtoPermiso();
                dtoPer.descripcion = per.getDescripcion();
                dtoPer.estado = per.isEstado();
                dtoPer.idPermiso = per.getIdPermiso();
                dtoPer.nombre = per.getNombre();
                dtoPer.nombreUnico = per.getNombreUnico();
                dtoPer.url = per.getUrl();
                colDtoPermiso.add(dtoPer);
            }
            return colDtoPermiso;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<dtoUsuarioGrid> usuarioToDtoUser(List<Usuario> colDataUsuarios) {
        try {
            List<dtoUsuarioGrid> colUsuarios = new ArrayList<>();
            for (Usuario u : colDataUsuarios) {
                dtoUsuarioGrid dtoUser = new dtoUsuarioGrid();
                dtoUser.apellido = u.getApellido();
                dtoUser.nickname = u.getNickname();
                dtoUser.correo = u.getCorreo();
                dtoUser.nombre = u.getNombre();
                dtoUser.tipoIdentidad = u.getTipoIdentidad();
                dtoUser.nroIdentidad = u.getNroIdentidad();
                dtoUser.nombre = u.getNombre();
                dtoUser.idUsuario = u.getIdUsuario();
                dtoUser.estado = (u.isEstado() == true) ? "Activo" : "Inactivo";
                dtoUser.idRol = u.getRol().getIdRol();
                dtoUser.nombreRol = u.getRol().getNombre();
                colUsuarios.add(dtoUser);
            }
            return colUsuarios;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public dtoMensaje getUsuarios(dtoUsuario usr) {
        
        dtoMensaje msg = new dtoMensaje();
        try {
            if (usr.token == null || usr.token == "" || usr.idUsuario == null) {
                msg.addMsgError(CError.ErrorAut + " 4C2");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usuarioConcurrente = cUsr.getUserByToken(usr.token);
                if (usuarioConcurrente != null) {
                    List<dtoUsuarioGrid> colUsuarios = new ArrayList<>();
                    List<Usuario> colDataUsuarios = PUsuario.newInstancePUsuario().getUsuarios(usr.idUsuario);
                    if (colDataUsuarios != null) {
                        colUsuarios = usuarioToDtoUser(colDataUsuarios);
                        msg.colUsu = colUsuarios;
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 4C2");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 4C1");
        }
        return msg;
    }
    
    public dtoMensaje borrarUsuario(dtoUsuario usr) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (usr.token != null) {
                if (!usr.token.equals("")) {
                    CUsuario cUsr = new CUsuario();
                    Usuario usuarioConcurrente = cUsr.getUserByToken(usr.token);
                    
                    if (usuarioConcurrente != null) {
                        Usuario us = PUsuario.newInstancePUsuario().getUserById(usr.idUsuario);
                        if (us != null) {
                            us.setEstado(false);
                            msg = PUsuario.newInstancePUsuario().deleteUsuario(us);
                            if (!msg.isError()) {
                                msg.addMsgOk("Usuario " + CError.eliminado);
                            }
                        } else {
                            msg.addMsgError("Usuario " + CError.inex + " 2C2");
                        }
                    } else {
                        msg.addMsgError(CError.ErrorAut + " 2C3");
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 2C3");
                }
            } else {
                msg.addMsgError(CError.ErrorAut + " 2C3");
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 2C1");
        }
        return msg;
    }
    
    public dtoMensaje modificarUsuario(dtoUsuario usr) {
        
        dtoMensaje msg = validarUsuario(usr, false);
        try {
            if (usr.token == null || usr.token == "" || usr.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 3C3");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usuarioConcurrente = cUsr.getUserByToken(usr.token);
                
                if (usuarioConcurrente != null) {
                    if (!msg.isError()) {
                        Usuario usuario = PUsuario.newInstancePUsuario().getUserById(usr.idUsuario);
                        if (usuario != null) {
                            usuario.setApellido(usr.apellido);
                            usuario.setCorreo(usr.correo);
                            usuario.setNombre(usr.nombre);
                            usuario.setNickname(usr.nickname);
                            Rol rol = PRol.newInstancePRol().getRolByRolId(usr.idRol);
                            usuario.setRol(rol);
                            msg = PUsuario.newInstancePUsuario().modificarUsuario(usuario);
                            if (!msg.isError()) {
                                msg.addMsgOk("Usuario " + CError.modificado);
                            }
                        } else {
                            msg.addMsgError("Usuario " + CError.inex + " 3C2");
                        }
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 3C3");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 3C1");
        }
        return msg;
    }
    
    public dtoMensaje modificarUsuarioMovil(dtoUsuario usr) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Rol rol1 = PRol.newInstancePRol().getRolByUserId(usr.idUsuario);
            Usuario usu = PUsuario.newInstancePUsuario().getUserById(usr.idUsuario);
            usr.idRol = rol1.getIdRol();
            usr.password = usu.getPassword();
            usr.ritpassword = usu.getPassword();
            msg = validarUsuario(usr, false);
            if (!msg.isError()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(usr.idUsuario);
                usuario.setNombre(usr.nombre);
                usuario.setApellido(usr.apellido);
                usuario.setNickname(usr.nickname);
                usuario.setCorreo(usr.correo);
                usuario.setNroIdentidad(usr.nroIdentidad);
                usuario.setTipoIdentidad(usr.tipoIdentidad);
                usuario.setTelefono(usr.telefono);
                usuario.setNotificar(usr.notificar);
                Rol rol = PRol.newInstancePRol().getRolByRolId(usr.idRol);
                usuario.setRol(rol);
                msg = PUsuario.newInstancePUsuario().modificarUsuarioMovil(usuario);
                if (!msg.isError()) {
                    msg.addMsgOk("Usuario modificado");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }
    
    public dtoMensaje validarRequeridosCambioClave(dtoMensaje msg, dtoCambioClave datos) {
        try {
            if (datos.passwordActual == null || datos.passwordActual.equals("")) {
                msg.addMsgError("La contraseña actual es requerida");
            }
            if (datos.passwordNueva == null || datos.passwordNueva.equals("")) {
                msg.addMsgError("La nueva contraseña es requerida");
            }
            if (datos.passwordNuevaReiterada == null || datos.passwordNuevaReiterada.equals("")) {
                msg.addMsgError("La reiteracion de la nueva contraseña es requerida");
            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }
    
    public dtoMensaje validarCambioClave(dtoMensaje msg, dtoCambioClave datos) {
        try {
            msg = validarRequeridosCambioClave(msg, datos);
            if (msg.msg == null || msg.msg.equals("")) {
                Usuario usr = PUsuario.newInstancePUsuario().getUserById(datos.idUsuario);
                if (usr == null || !usr.getPassword().equals(datos.passwordActual)) {
                    msg.addMsgError("Contraseña actual inválida");
                }
                if (!datos.passwordNueva.equals(datos.passwordNuevaReiterada)) {
                    msg.addMsgError("Las contraseñas no coinciden");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }
    
    public dtoMensaje cambiarClave(dtoCambioClave datos) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarCambioClave(msg, datos);
            if (msg.msg == null || msg.msg.equals("")) {
                PUsuario.newInstancePUsuario().cambiarClave(datos);
                msg.addMsgOk("La contraseña fue modificada");
            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }
    
    public List<Empresa> EmpresaToDto(List<dtoEmpresa> colDtoEmp) {
        List<Empresa> colEmp = new ArrayList<>();
        try {
            for (dtoEmpresa e : colDtoEmp) {
                Empresa dtoE = new Empresa();
                dtoE.setIdEmpresa(e.idEmpresa);
                dtoE.setNombre(e.nombre);
                dtoE.setDescripcion(e.descripcion);
                dtoE.setRut(e.rut);
                dtoE.setEstado(e.estado);
                colEmp.add(dtoE);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colEmp;
    }
    
    public dtoMensaje actualizarEmpreasaUsuario(dtoUsuarioEmpresa data) {
        dtoMensaje msg = new dtoMensaje();
        try {
            PEmpresa.newInstancePEmpresa().deleteEmpresasUsuario(data.idUsuario);
            Usuario user = PUsuario.newInstancePUsuario().getUserById(data.idUsuario);
            List<Empresa> colEmp = EmpresaToDto(data.empresas);
            PUsuario.newInstancePUsuario().updateEmpresasUsuario(user, colEmp);
            msg.msg = "Operación relizada con éxito";
            msg.tipo = Status.success.toString();
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }
    
    public dtoMensaje validarUsuario(dtoUsuario usr, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (usr != null) {
            for (Field propiedad : usr.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idUsuario":
                            if (!isInsert) {
                                if ((usr.idUsuario == null) || (DataAccess.Persistencia.PUsuario.newInstancePUsuario().getUserById(usr.idUsuario) == null)) {
                                    throw new Exception("Usuario" + CError.inex);
                                }
                            }
                            break;
                        case "nombre":
                            if (usr.nombre == null || usr.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }
                            if (usr.nombre.length() > 25) {
                                throw new Exception("Nombre supera los 25 caracteres");
                            }
                            break;
                        case "apellido":
                            
                            if (usr.apellido == null || usr.apellido.equals("")) {
                                throw new Exception("Apellido requerido");
                            }
                            if (usr.apellido.length() > 25) {
                                throw new Exception("Apellido supera los 25 caracteres");
                            }
                            break;
                        case "nickname":
                            if (usr.nickname == null || usr.nickname.equals("")) {
                                throw new Exception("Nombre de usuario requerido");
                            }
                            if (usr.nickname.length() > 15) {
                                throw new Exception("Nombre de usuario supera los 15 caracteres");
                            }
                            Usuario us = DataAccess.Persistencia.PUsuario.newInstancePUsuario().findUserByNickName(usr.nickname);
                            if (us != null) {
                                if (!isInsert) {
                                    if (!Objects.equals(us.getIdUsuario(), usr.idUsuario)) {
                                        throw new Exception("El nombre de usuario ya fue asignado a otro usuario");
                                    }
                                } else {
                                    throw new Exception("El nombre de usuario ya fue asignado a otro usuario");
                                }
                            }
                            break;
                        case "correo":
                            if (usr.correo != null && !usr.correo.equals("")) {
                                if (usr.correo.length() > 100) {
                                    throw new Exception("El correo no puede superar los 100 caracteres");
                                }
                            }
                            break;
                        case "idRol":
                            if (usr.idRol == null) {
                                throw new Exception("Rol requerido");
                            }
                            if (DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(usr.idRol) == null) {
                                throw new Exception("Rol" + CError.inex);
                            }
                            break;
                        case "password":
                            
                            if (usr.mode == null || usr.mode.equals("A")) {
                                if (usr.password == null || usr.password.equals("")) {
                                    throw new Exception("Contraseña requerida");
                                }
                                if (usr.ritpassword == null || usr.ritpassword.equals("")) {
                                    throw new Exception("Reiteración de contraseña requerida");
                                }
                                if (usr.password != null) {
                                    if (usr.password.length() > 500) {
                                        throw new Exception("La contraseña no puede superar los 500 caracteres");
                                    }
                                }
                                if (!usr.password.equals(usr.ritpassword)) {
                                    throw new Exception("Las contraseñas no coinciden");
                                }
                            }
                            break;
                    }
                } catch (Exception ex) {
                    msg.addMsgError(ex.getMessage());
                }
            }
        }
        return msg;
    }
    
    public dtoUsuario validarLogIn(dtoLogin dataLogin) {
        dtoUsuario dtoUser = new dtoUsuario();
        try {
            if (dataLogin.nickname == null || dataLogin.nickname.equals("")) {
                throw new Exception("Por favor ingrese su nombre de usuario");
            }
            if (dataLogin.password == null || dataLogin.password.equals("") || dataLogin.password.equals("d41d8cd98f00b204e9800998ecf8427e")) {
                throw new Exception("Por favor ingrese su contraseña");
            }
        } catch (Exception ex) {
            dtoUser.status = Status.danger.toString();
            dtoUser.msg = ex.getMessage();
            dtoUser.isLogged = false;
        }
        return dtoUser;
    }
    
    String getStringAleatorio(int longitud) {
        String strRandom = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                strRandom += c;
                i++;
            }
        }
        return strRandom;
    }
    
    public dtoMensaje recuperarPass(dtoUserPass user) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (user.nickname == null || user.nickname.equals("")) {
                throw new Exception("Por favor ingrese su nickname");
            } else if (user.correo == null || user.correo.equals("")) {
                throw new Exception("Por favor ingrese su correo");
            } else {
                Usuario uscurr = PUsuario.newInstancePUsuario().findUserByNicknameAndCorreo(user);
                if (uscurr != null) {
                    Notificaciones nMail = new Notificaciones();
                    String claveNueva = getStringAleatorio(10);
                    
                    String mail = "<style type='text/css'>body {margin: 0; padding: 0; min-width: 100%!important;}.content {width: 100%; max-width: 600px;}.img {width: 50%; height: 20%;}"
                            + "</style>"
                            + "<body yahoo bgcolor='#f6f8f1'>"
                            + "<table width='100%' bgcolor='#f6f8f1' border='0' cellpadding='0' cellspacing='0'>"
                            + "<tr>"
                            + "<td>"
                            + "<center><h1 style='color: blue;'>ATuServicio IDM</h1></center>"
                            + "</td>"
                            + "<tr>"
                            + "<td>"
                            + "<center><img class='img' src='https://pbs.twimg.com/profile_images/639187735891329024/zT4n4ld5.jpg'/></center>"
                            + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td>"
                            + "<table class='content' align='center' cellpadding='0' cellspacing='0' border='0'>"
                            + "<tr>"
                            + "<td>"
                            + "<h2>Recumeración de Contraseña</h2>"
                            + "</td>"
                            + "</tr>"
                            + "<tr>"
                            + "<td><h2>" + uscurr.getNickname() + ":</h2><p style='font-size:20px;'> Has solcitado recuperar tu contraseña, te brindamos la siguiente contraseña: <b style='color: #C00;'>" + claveNueva + "</b>   la cual puedes cambiar en ATuServicio>Perfil</p>"
                            + "<p style='font-size:20px;'>Saludos equipo ATuServicio!.</p>"
                            + "</td>"
                            + "</tr>"
                            + "</table>"
                            + "</td>"
                            + "</tr>"
                            + "</table>"
                            + "</body>";
                    nMail.setEnviado("N");
                    nMail.setCorreo(uscurr.getCorreo());
                    nMail.setMensaje(mail);
                    boolean sendMail = CNotificaciones.getInstanceCNotificaciones().altaNotificacion(nMail);
                    if (sendMail) {
                        dtoCambioClave camclave = new dtoCambioClave();
                        camclave.idUsuario = uscurr.getIdUsuario();
                        MessageDigest mdEnc = MessageDigest.getInstance("MD5");
                        mdEnc.update(claveNueva.getBytes(), 0, claveNueva.length());
                        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
                        
                        camclave.passwordNueva = md5;
                        PUsuario.newInstancePUsuario().cambiarClave(camclave);
                        msg.addMsgOk("Verifique su casilla de correos le enviamos un mail con su nueva clave");
                    } else {
                        throw new Exception("Lo sentimos intentelo mas tarde");
                    }
                } else {
                    throw new Exception("Los datos no son validos");
                }
            }
            
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }
    
}
