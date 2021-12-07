package Business.Logic.Controllers;

import Business.Entities.Permiso;
import Business.Entities.Rol;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoGridPermiso;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoPermiso;
import Common.DTO.dtoRol;
import Common.DTO.dtoRolPermiso;
import java.util.ArrayList;
import java.util.List;
import Common.Enums.Status;
import DataAccess.Persistencia.PPermiso;
import DataAccess.Persistencia.PRol;
import java.lang.reflect.Field;

public class CPermiso {
    
    private static CPermiso ICPermiso;
    
    public static CPermiso getNewInstanceCPermiso() {
        if (ICPermiso == null) {
            return new CPermiso();
        }
        return ICPermiso;
    }
    
    public dtoMensaje actualizarPermisosRol(dtoRolPermiso rolpermiso) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rolpermiso != null) {
                if (rolpermiso.token == null || rolpermiso.token == "" || rolpermiso.token.equals("")) {
                    msg.addMsgError(CError.ErrorAut + " 15C1");
                } else {
                    CUsuario cUsr = new CUsuario();
                    Usuario usr = cUsr.getUserByToken(rolpermiso.token);
                    if (usr != null) {
                        msg = validarPermisoRol(rolpermiso);
                        if (!msg.isError()) {
                            if (rolpermiso.permisos != null) {
                                msg = validarPermisos(rolpermiso.permisos);
                                if (!msg.isError()) {
                                    DataAccess.Persistencia.PPermiso.newInstancePPermiso().deleteRolPermisos(rolpermiso.idRol);
                                    Rol rol = DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(rolpermiso.idRol);
                                    List<Permiso> colPermiso = dtoPermisoToPermiso(rolpermiso.permisos);
                                    DataAccess.Persistencia.PPermiso.newInstancePPermiso().updatePermisosRol(rol, colPermiso);
                                    msg.addMsgOk("Permisos " + CError.modificados);
                                }
                            } else {
                                msg.addMsgError("Permisos " + CError.inexs);
                            }
                        }
                    } else {
                        msg.addMsgError(CError.ErrorAut + " 15C1");
                    }
                }
            } else {
                msg.addMsgError("Datos " + CError.inexs);
            }
            
        } catch (Exception ex) {
            msg.addMsgOk(CError.ErrorIn + " 15P2");
        }
        return msg;
    }
    
    public dtoMensaje getPermisos(dtoRol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol.token == null || rol.token == "" || rol.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 13C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(rol.token);
                if (usr != null) {
                    List<Permiso> colPermisos = DataAccess.Persistencia.PPermiso.newInstancePPermiso().getPermisos();
                    List<dtoGridPermiso> colDtoPermisos = permisoToDtoPermiso(colPermisos);
                    msg.colGridPer = colDtoPermisos;
                } else {
                    msg.addMsgError(CError.ErrorAut + " 13C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 13C2");
        }
        return msg;
    }
    
    public dtoMensaje getPermisosByRol(dtoRol rol) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (rol.token == null || rol.token == "" || rol.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 14C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(rol.token);
                if (usr != null) {
                    if (rol.idRol != null && PRol.newInstancePRol().getRolByRolId(rol.idRol) != null) {
                        List<Permiso> colPermisos = PPermiso.newInstancePPermiso().getPermisosByRol(rol.idRol);
                        List<dtoGridPermiso> colDtoPermisos = permisoToDtoPermiso(colPermisos);
                        msg.colGridPer = colDtoPermisos;
                    } else {
                        msg.addMsgError("Rol " + CError.inex);
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 14C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 14C2");
        }
        return msg;
    }
    
    public List<dtoGridPermiso> permisoToDtoPermiso(List<Permiso> colPermisos) {
        List<dtoGridPermiso> colDtoPermiso = new ArrayList<>();
        try {
            for (Permiso per : colPermisos) {
                dtoGridPermiso dtoPer = new dtoGridPermiso();
                dtoPer.descripcion = per.getDescripcion();
                dtoPer.idPermiso = per.getIdPermiso();
                dtoPer.nombre = per.getNombre();
                dtoPer.estado = per.isEstado();
                dtoPer.nombreUnico = per.getNombreUnico();
                colDtoPermiso.add(dtoPer);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colDtoPermiso;
    }
    
    public List<Permiso> dtoPermisoToPermiso(List<dtoPermiso> colDtoPermso) {
        List<Permiso> colPermiso = new ArrayList<>();
        try {
            for (dtoPermiso dtoPer : colDtoPermso) {
                Permiso per = new Permiso();
                per.setIdPermiso(dtoPer.idPermiso);
                per.setNombre(dtoPer.nombre);
                per.setDescripcion(dtoPer.descripcion);
                per.setNombreUnico(dtoPer.nombreUnico);
                per.setEstado(dtoPer.estado);
                colPermiso.add(per);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colPermiso;
    }
    
    private dtoMensaje validarPermisoRol(dtoRolPermiso rolpermiso) {
        dtoMensaje msg = new dtoMensaje();
        if (rolpermiso != null) {
            for (Field propiedad : rolpermiso.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idRol":
                            if (rolpermiso.idRol == null) {
                                throw new Exception("Identificador del rol requerido");
                            } else if (DataAccess.Persistencia.PRol.newInstancePRol().getRolByRolId(rolpermiso.idRol) == null) {
                                throw new Exception("Rol " + CError.inex);
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
    
    private dtoMensaje validarPermisos(List<dtoPermiso> permisos) {
        dtoMensaje msg = new dtoMensaje();
        if (permisos != null) {
            for (dtoPermiso permiso : permisos) {
                for (Field propiedad : permiso.getClass().getFields()) {
                    try {
                        switch (propiedad.getName()) {
                            case "idPermiso":
                                if (permiso.idPermiso == null) {
                                    throw new Exception("Identificador del permiso requerido");
                                } else if (DataAccess.Persistencia.PPermiso.newInstancePPermiso().getPermisoById(permiso.idPermiso) == null) {
                                    throw new Exception("Permiso " + CError.inex);
                                }
                                break;
                            case "nombre":
                                if (permiso.nombre == null || permiso.nombre.equals("")) {
                                    throw new Exception("Nombre requerido");
                                }
                                if (permiso.nombre.length() > 25) {
                                    throw new Exception("El nombre supera los 25 caracteres");
                                }
                                break;
                            case "estado":
                                if ((Boolean) permiso.estado == null) {
                                    throw new Exception("Estado requerido");
                                }
                                break;
                            case "nombreUnico":
                                if (permiso.nombreUnico == null || permiso.nombreUnico.equals("")) {
                                    throw new Exception("Nombre unico requerido");
                                }
                                if (permiso.nombreUnico.length() > 100) {
                                    throw new Exception("El nombre unico supera los 100 caracteres");
                                }
                                break;
                        }
                        
                    } catch (Exception ex) {
                        msg.addMsgError(ex.getMessage());
                    }
                }
                
            }
        }
        return msg;
    }
}
