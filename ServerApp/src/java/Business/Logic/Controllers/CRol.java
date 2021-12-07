package Business.Logic.Controllers;

import Business.Entities.Rol;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoRol;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CRol {

    private static CRol ICRol;

    public static CRol getNewInstanceIRol() {
        if (ICRol == null) {
            return new CRol();
        }
        return ICRol;
    }

    public dtoMensaje getRoles(String token) {
        dtoMensaje msg = new dtoMensaje();
        List<dtoRol> colDtoRol = null;
        try {
            if (token == null || token == "" || token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 12C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(token);
                if (usr != null) {
                    List<Rol> colRoles = DataAccess.Persistencia.PRol.newInstancePRol().getRoles();
                    if (colRoles != null) {
                        colDtoRol = rolToDtoRol(colRoles);
                        msg.colRol = colDtoRol;
                    } else {
                        msg.addMsgError("No existen roles por el momento");
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 12C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 12C2");
        }
        return msg;
    }

    public List<dtoRol> rolToDtoRol(List<Rol> colRoles) {
        List<dtoRol> colDtoRol = new ArrayList<>();
        try {
            for (Rol r : colRoles) {
                dtoRol dtoRol = new dtoRol();
                dtoRol.nombre = r.getNombre();
                dtoRol.idRol = r.getIdRol();
                dtoRol.descripcion = r.getDescripcion();
                colDtoRol.add(dtoRol);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colDtoRol;
    }

    public Rol dtoRolToRol(dtoRol dtoRol) {
        Rol rol = new Rol();
        try {
            if (dtoRol != null) {
                rol.setIdRol(dtoRol.idRol);
                rol.setNombre(dtoRol.nombre);
                rol.setDescripcion(dtoRol.descripcion);
                rol.setEstado(dtoRol.estado);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return rol;
    }

    public dtoMensaje altaRol(dtoRol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol.token == null || rol.token == "" || rol.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 9C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(rol.token);
                if (usr != null) {
                    msg = validarRol(rol, true);
                    if (!msg.isError()) {
                        Rol nuevoRol = dtoRolToRol(rol);
                        nuevoRol.setEstado(true);
                        msg = DataAccess.Persistencia.PRol.newInstancePRol().altaRol(nuevoRol);
                        if (!msg.isError()) {
                            msg.addMsgOk("Rol " + CError.registrado);
                        }
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 9C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 9C2");
        }
        return msg;
    }

    public dtoMensaje modificarRol(dtoRol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol.token == null || rol.token == "" || rol.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 11C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(rol.token);
                if (usr != null) {
                    msg = validarRol(rol, false);
                    if (!msg.isError()) {
                        Rol updateRol = dtoRolToRol(rol);
                        updateRol.setEstado(true);
                        msg = DataAccess.Persistencia.PRol.newInstancePRol().modificarRol(updateRol);
                        if (!msg.isError()) {
                            msg.addMsgOk("Rol " + CError.modificado);
                        }
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 11C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 11C2");
        }
        return msg;
    }

    public dtoMensaje borrarRol(dtoRol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol.token == null || rol.token == "" || rol.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 10C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(rol.token);
                if (usr != null) {
                    if (rol.idRol != null) {
                        List<Usuario> colUserRol = DataAccess.Persistencia.PUsuario.newInstancePUsuario().getUsuariosByIdRol(rol.idRol);
                        if (colUserRol != null && colUserRol.size() > 0) {
                            msg.addMsgError("Rol asociado a usuario, no se puede eliminar");
                        } else {
                            Rol rolCurr = DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(rol.idRol);
                            if (rolCurr != null) {
                                DataAccess.Persistencia.PPermiso.newInstancePPermiso().deleteRolPermisos(rolCurr.getIdRol());
                                rolCurr.setEstado(false);
                                msg = DataAccess.Persistencia.PRol.newInstancePRol().deleteRol(rolCurr);
                                if (!msg.isError()) {
                                    msg.addMsgOk("Rol " + CError.eliminado);
                                }
                            } else {
                                msg.addMsgError("Rol " + CError.inex);
                            }
                        }
                    } else {
                        msg.addMsgError("Rol " + CError.inex);
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 10C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 10C2");
        }
        return msg;
    }

    public dtoMensaje validarRol(dtoRol rol, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (rol != null) {
            for (Field propiedad : rol.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idRol":
                            if (!isInsert) {
                                if (rol.idRol == null) {
                                    throw new Exception("Identificador del rol requerido");
                                } else if (DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(rol.idRol) == null) {
                                    throw new Exception("Rol " + CError.inex);
                                }
                            }
                            break;
                        case "nombre":
                            if (rol.nombre == null || "".equals(rol.nombre)) {
                                throw new Exception("Nombre requerido");
                            }
                            if (rol.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (rol.idRol != null) {
                                if (!isInsert) {
                                    Rol comprobarRol = DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(rol.idRol);
                                    if (comprobarRol != null) {
                                        if (!comprobarRol.getNombre().equals(rol.nombre)) {
                                            if (DataAccess.Persistencia.PRol.newInstancePRol().existeRol(rol.nombre)) {
                                                throw new Exception("Rol " + CError.existente);
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PRol.newInstancePRol().existeRol(rol.nombre)) {
                                    throw new Exception("Rol " + CError.existente);
                                }
                            }
                            break;
                        case "descripcion":
                            if (rol.descripcion != null && !rol.descripcion.equals("")) {
                                if (rol.descripcion.length() > 256) {
                                    throw new Exception("La descripcion supera los 256 caracteres");
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
}
