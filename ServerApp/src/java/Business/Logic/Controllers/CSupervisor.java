package Business.Logic.Controllers;

import Business.Entities.Servicio;
import Business.Entities.Supervisor;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoServicio;
import Common.DTO.dtoSupervisor;
import Common.Enums.Status;
import DataAccess.Persistencia.PServicio;
import DataAccess.Persistencia.PSupervisor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CSupervisor {

    private static CSupervisor ICSupervisor;

    public static CSupervisor getNewInstanceCSupervisor() {
        if (ICSupervisor == null) {
            return new CSupervisor();
        }

        return ICSupervisor;
    }

    public List<dtoSupervisor> supervisorToDtoSupervisor(List<Supervisor> colDataSupervisores) throws Exception {
        List<dtoSupervisor> colSupervisores = new ArrayList<>();
        try {
            for (Supervisor sup : colDataSupervisores) {
                dtoSupervisor dtoSup = new dtoSupervisor();
                dtoSup.idSupervisor = sup.getIdSupervisor();
                dtoSup.nombre = sup.getNombre();
                dtoSup.apellido = sup.getApellido();
                dtoSup.estado = (sup.isEstado() == true) ? "Activo" : "Inactivo";
                colSupervisores.add(dtoSup);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colSupervisores;

    }

    public dtoMensaje getSupervisores(String token) {
        dtoMensaje msg = new dtoMensaje();
        List<dtoSupervisor> colSupervisores = new ArrayList<>();
        try {
            if (token == null || token == "" || token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 19C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(token);
                if (usr != null) {
                    List<Supervisor> colDataSupervisores = PSupervisor.newInstancePSupervisor().getSupervisores();
                    if (colDataSupervisores != null) {
                        colSupervisores = supervisorToDtoSupervisor(colDataSupervisores);
                        msg.colSup = colSupervisores;
                    } else {
                        msg.addMsgOk("Supervisores " + CError.inexs);
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 19C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 19C2");
        }
        return msg;
    }

    public dtoMensaje getSupervisoresByServicio(dtoServicio serv) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (serv != null) {
                if (serv.token == null || serv.token == "" || serv.token.equals("")) {
                    msg.addMsgError(CError.ErrorAut + " 20C1");
                } else {
                    CUsuario cUsr = new CUsuario();
                    Usuario usr = cUsr.getUserByToken(serv.token);
                    if (usr != null) {
                        if (serv.idServicio != 0) {
                            List<dtoSupervisor> colSupervisores = new ArrayList<>();
                            List<Supervisor> colDataSupervisores = PSupervisor.newInstancePSupervisor().getSupervisoresByServicio(serv.idServicio);
                            if (colDataSupervisores != null) {
                                colSupervisores = supervisorToDtoSupervisor(colDataSupervisores);
                                msg.colSup = colSupervisores;
                            }
                        }
                    } else {
                        msg.addMsgError(CError.ErrorAut + " 20C1");
                    }
                }
            } else {
                msg.addMsgError("Datos " + CError.inexs);
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn +" 20C2");
        }
        return msg;
    }

    public dtoMensaje altaSupervisor(dtoSupervisor supervisor) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (supervisor != null) {
                if (supervisor.token == null || supervisor.token == "" || supervisor.token.equals("")) {
                    msg.addMsgError(CError.ErrorAut + " 16C1");
                } else {
                    CUsuario cUsr = new CUsuario();
                    Usuario usr = cUsr.getUserByToken(supervisor.token);
                    if (usr != null) {
                        msg = validarSupervisor(supervisor, true);
                        if (!msg.isError()) {
                            Supervisor nuevoSupervisor = new Supervisor();
                            nuevoSupervisor.setNombre(supervisor.nombre);
                            nuevoSupervisor.setApellido(supervisor.apellido);
                            nuevoSupervisor.setEstado(true);
                            msg = PSupervisor.newInstancePSupervisor().altaSupervisor(nuevoSupervisor);
                            if (!msg.isError()) {
                                msg.addMsgOk("Supervisor " + CError.registrado);
                            }
                        }
                    } else {
                        msg.addMsgError(CError.ErrorAut + " 16C1");
                    }
                }
            } else {
                msg.addMsgError("Datos " + CError.inexs);
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 16C2");
        }
        return msg;
    }

    public dtoMensaje borrarSupervisor(dtoSupervisor dtoSup) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (dtoSup.token == null || dtoSup.token == "" || dtoSup.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 17C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(dtoSup.token);
                if (usr != null) {
                    if (dtoSup.idSupervisor != null) {
                        Supervisor supervisor = PSupervisor.newInstancePSupervisor().getSupervisorById(dtoSup.idSupervisor);
                        if (supervisor != null) {
                            List<Servicio> servicios = PServicio.newInstancePServicio().getServiciosBySupervisor(dtoSup.idSupervisor);
                            if (servicios != null || !servicios.isEmpty() || servicios.size() > 0) {
                                for (Servicio servicio : servicios) {
                                    if (servicio.getEstado() == 1 || servicio.getEstado() == 3 || servicio.getEstado() == 5) {
                                        msg.addMsgError("Existen servicios activos asignados al supervisor");
                                        return msg;
                                    }
                                }

                            }
                            supervisor.setEstado(false);
                            msg = DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().deleteSupervisor(supervisor);
                            if (!msg.isError()) {
                                msg.addMsgOk("Supervisor " + CError.eliminado);
                            }
                        } else {
                            msg.addMsgError("Supervisor " + CError.inex);
                        }
                    } else {
                        msg.addMsgError("Identificador del supervisor requerido");
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 17C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 17C2");
        }
        return msg;
    }

    public dtoMensaje modificarSupervisor(dtoSupervisor sup) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (sup.token == null || sup.token == "" || sup.token.equals("")) {
                msg.addMsgError(CError.ErrorAut + " 18C1");
            } else {
                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(sup.token);
                if (usr != null) {
                    msg = validarSupervisor(sup, false);
                    if (!msg.isError()) {
                        Supervisor superCurrent = PSupervisor.newInstancePSupervisor().getSupervisorById(sup.idSupervisor);
                        superCurrent.setIdSupervisor(sup.idSupervisor);
                        superCurrent.setNombre(sup.nombre);
                        superCurrent.setApellido(sup.apellido);
                        msg = DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().modificarSupervisor(superCurrent);
                        if (!msg.isError()) {
                            msg.addMsgOk("Supervisor " + CError.modificado);
                        }
                    }
                } else {
                    msg.addMsgError(CError.ErrorAut + " 18C1");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 18C2");
        }
        return msg;
    }

    public dtoMensaje validarSupervisor(dtoSupervisor supervisor, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (supervisor != null) {
            for (Field propiedad : supervisor.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idSupervisor":
                            if (!isInsert) {
                                if (supervisor.idSupervisor == null) {
                                    throw new Exception("Identificador del supervisor requerido");
                                } else if (DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().getSupervisorById(supervisor.idSupervisor) == null) {
                                    throw new Exception("Supervisor " + CError.inex);
                                }
                            }
                            break;
                        case "nombre":
                            if (supervisor.nombre == null || supervisor.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }
                            if (supervisor.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            break;
                        case "apellido":
                            if (supervisor.apellido == null || supervisor.apellido.equals("")) {
                                throw new Exception("Apellido requerido");
                            }
                            if (supervisor.apellido.length() > 25) {
                                throw new Exception("El apellido supera los 25 caracteres");
                            }
                            if (supervisor.idSupervisor != null) {
                                if (!isInsert) {
                                    Supervisor comprobarSupervisor = DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().getSupervisorById(supervisor.idSupervisor);
                                    if (comprobarSupervisor != null) {
                                        if ((!comprobarSupervisor.getNombre().equals(supervisor.nombre)) && (!comprobarSupervisor.getApellido().equals(supervisor.apellido))) {
                                            if (DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().existeSupervisor(supervisor)) {
                                                throw new Exception("Supervisor " + CError.existente);
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PSupervisor.newInstancePSupervisor().existeSupervisor(supervisor)) {
                                    throw new Exception("Supervisor " + CError.existente);
                                }
                            }
                            break;
                    }
                } catch (Exception ex) {
                    msg.addMsgError(ex.getMessage());
                }
            }
        } else {
            msg.addMsgError("Datos " + CError.inexs);
        }
        return msg;
    }

}
