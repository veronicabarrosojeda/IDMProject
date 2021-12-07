package Business.Logic.Controllers;

import Business.Entities.Empresa;
import Business.Entities.Tiposervicio;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoTipoServicio;
import Common.DTO.dtoTipoServicioGrid;
import Common.Enums.Status;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CTipoServicio {

    private static CTipoServicio ICTipoServicio;

    public static CTipoServicio getNewInstanceCTipoServicio() {
        if (ICTipoServicio == null) {
            return new CTipoServicio();
        }
        return ICTipoServicio;
    }

    public dtoMensaje altaTipoServicio(dtoTipoServicio tipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarTipoServicio(tipoServicio, true);
            if (!msg.isError()) {
                Tiposervicio nuevoTipoServicio = new Tiposervicio();
                nuevoTipoServicio.setNombre(tipoServicio.nombre);
                nuevoTipoServicio.setDescripcion(tipoServicio.descripcion);
                nuevoTipoServicio.setEstado(true);
                nuevoTipoServicio.setIdEmpresa(tipoServicio.idEmpresa);
                nuevoTipoServicio.setArea(DataAccess.Persistencia.PArea.newInstancePArea().getArea(tipoServicio.idArea));
                msg = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().altaTipoServicio(nuevoTipoServicio);
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje bajaTipoServicio(Integer idTipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Tiposervicio tipoServicio = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().getTipoServicioById(idTipoServicio);
            if (tipoServicio != null) {
                msg = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().bajaTipoServicio(idTipoServicio);
                if (!msg.isError()) {
                    msg.addMsgOk("El tipo de servicio cambio su estado a inactivo");
                }
            } else {
                msg.addMsgError("El tipo de servicio no existe");
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje modificarTipoServicio(dtoTipoServicio tipoServicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarTipoServicio(tipoServicio, false);
            if (!msg.isError()) {
                Tiposervicio tipoServicioModificado = new Tiposervicio();
                tipoServicioModificado.setIdTipoServicio(tipoServicio.idTipoServicio);
                tipoServicioModificado.setNombre(tipoServicio.nombre);
                tipoServicioModificado.setDescripcion(tipoServicio.descripcion);
                tipoServicioModificado.setArea(DataAccess.Persistencia.PArea.newInstancePArea().getArea(tipoServicio.idArea));
                msg = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().modificarTipoServicio(tipoServicioModificado);
                if (!msg.isError()) {
                    msg.addMsgOk("Tipo de servicio modificado exitosamente");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public List<dtoTipoServicioGrid> listarTipoServicio() {
        List<dtoTipoServicioGrid> colTipoServicios = null;
        try {
            colTipoServicios = new ArrayList<>();
            List<Tiposervicio> colDataTipoServicios = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().listarTipoServicios();
            if (colDataTipoServicios != null) {
                colTipoServicios = tipoServicioToDtoTipoServicio(colDataTipoServicios);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colTipoServicios;
    }

    private dtoMensaje validarTipoServicio(dtoTipoServicio tipoServicio, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (tipoServicio != null) {
            for (Field propiedad : tipoServicio.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idTipoServicio":
                            if (!isInsert) {
                                if (tipoServicio.idTipoServicio == null) {
                                    throw new Exception("Identificador del tipo de servicio requerido");
                                } else if (DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().getTipoServicioById(tipoServicio.idTipoServicio) == null) {
                                    throw new Exception("El tipo de servicio no existe");
                                }
                            }
                            break;
                        case "nombre":
                            if (tipoServicio.nombre == null || tipoServicio.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }
                            if (tipoServicio.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (tipoServicio.idTipoServicio != null) {
                                if (!isInsert) {
                                    Tiposervicio comprobarTipoServicio = DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().getTipoServicioById(tipoServicio.idTipoServicio);
                                    if (comprobarTipoServicio != null) {
                                        if (!comprobarTipoServicio.getNombre().equals(tipoServicio.nombre)) {
                                            if (DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().existeTipoServicio(tipoServicio.nombre)) {
                                                throw new Exception("El tipo de servicio ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PTipoServicio.newInstancePTipoServicio().existeTipoServicio(tipoServicio.nombre)) {
                                    throw new Exception("El tipo de servicio ya existe");
                                }
                            }
                            break;
                        case "descripcion":
                            if (tipoServicio.descripcion != null && !tipoServicio.descripcion.equals("")) {
                                if (tipoServicio.descripcion.length() > 256) {
                                    throw new Exception("La descripcion supera los 256 caracteres");
                                }
                            }
                            break;
                        case "idArea":
                            if (tipoServicio.idArea == null) {
                                throw new Exception("Area requerida");
                            }
                            if (DataAccess.Persistencia.PArea.newInstancePArea().getArea(tipoServicio.idArea) == null) {
                                throw new Exception("El area no existe");
                            }
                            break;
                        case "idEmpresa":
                            if (tipoServicio.idEmpresa == null) {
                                throw new Exception("Empresa requerida");
                            }
                            if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresa(tipoServicio.idEmpresa) == null) {
                                throw new Exception("La empresa no existe");
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

    public List<dtoTipoServicioGrid> tipoServicioToDtoTipoServicio(List<Tiposervicio> colDataTipoServicios) {
        List<dtoTipoServicioGrid> colTipoServicios = new ArrayList<>();
        try {
            for (Tiposervicio tipoServicio : colDataTipoServicios) {
                dtoTipoServicioGrid dtoTipoServicio = new dtoTipoServicioGrid();
                dtoTipoServicio.idTipoServicio = tipoServicio.getIdTipoServicio();
                dtoTipoServicio.nombre = tipoServicio.getNombre();
                dtoTipoServicio.descripcion = tipoServicio.getDescripcion();
                dtoTipoServicio.estado = (tipoServicio.isEstado() == true) ? "Activo" : "Inactivo";
                dtoTipoServicio.idArea = tipoServicio.getArea().getIdArea();
                dtoTipoServicio.nombreArea = tipoServicio.getArea().getNombre();

                Empresa emp = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresa(tipoServicio.getIdEmpresa());
                if (emp != null) {
                    dtoTipoServicio.idEmpresa = emp.getIdEmpresa();
                    dtoTipoServicio.nombreEmpresa = emp.getNombre();
                }

                colTipoServicios.add(dtoTipoServicio);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colTipoServicios;
    }

}
