package Business.Logic.Controllers;

import Business.Entities.Area;
import Common.DTO.dtoArea;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CArea {

    private static CArea ICArea;

    public static CArea getNewInstanceCArea() {
        if (ICArea == null) {
            return new CArea();
        }
        return ICArea;
    }

    public List<dtoArea> listarAreaFiltro(String filtro) {
        List<dtoArea> areas = null;
        try {
            areas = new ArrayList<>();
            List<Area> colDataAreas = DataAccess.Persistencia.PArea.newInstancePArea().listarAreaFiltro(filtro);
            if (colDataAreas != null) {
                areas = areaToDtoArea(colDataAreas);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return areas;
    }

    public List<dtoArea> listarAreas() {
        List<dtoArea> colAreas = null;
        try {
            colAreas = new ArrayList<>();
            List<Area> colDataAreas = DataAccess.Persistencia.PArea.newInstancePArea().listarAreas();
            if (colDataAreas != null) {
                colAreas = areaToDtoArea(colDataAreas);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colAreas;
    }

    public List<dtoArea> areaToDtoArea(List<Area> colDataAreas) {
        List<dtoArea> colAreas = null;
        try {
            colAreas = new ArrayList<>();
            for (Area a : colDataAreas) {
                dtoArea dtoA = new dtoArea();
                dtoA.idArea = a.getIdArea();
                dtoA.nombre = a.getNombre();
                dtoA.descripcion = a.getDescripcion();
                dtoA.estado = a.isEstado();
                colAreas.add(dtoA);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colAreas;
    }

    public dtoMensaje altaArea(dtoArea dtoAr) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarArea(dtoAr, true);
            if (!msg.isError()) {
                Area area = new Area();
                area.setNombre(dtoAr.nombre);
                area.setDescripcion(dtoAr.descripcion);
                area.setEstado(true);
                msg = DataAccess.Persistencia.PArea.newInstancePArea().altaArea(area);
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje modificarArea(dtoArea dtoAr) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarArea(dtoAr, false);
            if (!msg.isError()) {
                Area area = new Area();
                area.setIdArea(dtoAr.idArea);
                area.setNombre(dtoAr.nombre);
                area.setDescripcion(dtoAr.descripcion);
                area.setEstado(dtoAr.estado);
                msg = DataAccess.Persistencia.PArea.newInstancePArea().modificarArea(area);
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje bajaArea(Integer idArea) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (idArea != null) {
                Area area = DataAccess.Persistencia.PArea.newInstancePArea().getArea(idArea);
                if (area != null) {
                    if (area.getSubareas().isEmpty()) {
                        msg = DataAccess.Persistencia.PArea.newInstancePArea().bajaArea(idArea);
                        if (!msg.isError()) {
                            msg.addMsgOk("El area cambio su estado a inactivo");
                        }
                    } else {
                        msg.addMsgError("Existen subareas asociadas al area. La operacion no fue realizada");
                    }
                } else {
                    msg.addMsgError("El area no existe");
                }
            } else {
                msg.addMsgError("Identificador del area requerido");
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoArea getArea(int idArea) {
        dtoArea dtoarea = null;
        try {
            Area area = DataAccess.Persistencia.PArea.newInstancePArea().getArea(idArea);
            if (area != null) {
                dtoarea = new dtoArea();
                dtoarea.nombre = area.getNombre();
                dtoarea.descripcion = area.getDescripcion();
                dtoarea.estado = area.isEstado();
                dtoarea.idArea = area.getIdArea();
            }
        } catch (Exception ex) {
            throw ex;
        }
        return dtoarea;
    }

    public dtoMensaje validarArea(dtoArea area, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (area != null) {
            for (Field propiedad : area.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idArea":
                            if (!isInsert) {
                                if (area.idArea == null) {
                                    throw new Exception("Identificador del area requerido");
                                } else if (DataAccess.Persistencia.PArea.newInstancePArea().getArea(area.idArea) == null) {
                                    throw new Exception("El area no existe");
                                }
                            }
                            break;
                        case "nombre":
                            if (area.nombre == null || area.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }

                            if (area.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (area.idArea != null) {
                                if (!isInsert) {
                                    Area comprobarArea = DataAccess.Persistencia.PArea.newInstancePArea().getArea(area.idArea);
                                    if (comprobarArea != null) {
                                        if (!comprobarArea.getNombre().equals(area.nombre)) {
                                            if (DataAccess.Persistencia.PArea.newInstancePArea().findAreaByName(area.nombre)) {
                                                throw new Exception("El area ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PArea.newInstancePArea().findAreaByName(area.nombre)) {
                                    throw new Exception("El area ya existe");
                                }
                            }
                            break;
                        case "descripcion":
                            if (area.descripcion != null && !area.descripcion.equals("")) {
                                if (area.descripcion.length() > 256) {
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
