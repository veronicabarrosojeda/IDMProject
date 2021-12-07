package Business.Logic.Controllers;

import Business.Entities.Barrio;
import Business.Entities.Municipio;
import Common.DTO.dtoBarrio;
import Common.DTO.dtoBarrioGrid;
import Common.DTO.dtoMensaje;
import Common.Enums.Status;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CBarrio {

    private static CBarrio ICBarrio;

    public static CBarrio getNewInstanceCBarrio() {
        if (ICBarrio == null) {
            return new CBarrio();
        }
        return ICBarrio;
    }

    public dtoMensaje altaBarrio(dtoBarrio barrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarBarrio(barrio, true);
            if (!msg.isError()) {
                Barrio nuevoBarrio = new Barrio();
                nuevoBarrio.setNombre(barrio.nombre);
                nuevoBarrio.setDescripcion(barrio.descripcion);
                nuevoBarrio.setEstado(true);
                nuevoBarrio.setMunicipio(DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(barrio.idMunicipio));
                msg = DataAccess.Persistencia.PBarrio.newInstancePBarrio().altaBarrio(nuevoBarrio);
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje bajaBarrio(Integer idBarrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            Barrio barrio = DataAccess.Persistencia.PBarrio.newInstancePBarrio().getBarrioById(idBarrio);
            if (barrio != null) {
                msg = DataAccess.Persistencia.PBarrio.newInstancePBarrio().bajaBarrio(idBarrio);
                if (!msg.isError()) {
                    msg.addMsgOk("El barrio cambio su estado a inactivo");
                }
            } else {
                msg.addMsgError("El barrio no existe");
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje modificarBarrio(dtoBarrio barrio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarBarrio(barrio, false);
            if (!msg.isError()) {
                Barrio bar = new Barrio();
                bar.setIdBarrio(barrio.idBarrio);
                bar.setNombre(barrio.nombre);
                bar.setDescripcion(barrio.descripcion);
                Municipio municipio = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(barrio.idMunicipio);
                bar.setMunicipio(municipio);
                msg = DataAccess.Persistencia.PBarrio.newInstancePBarrio().modificarBarrio(bar);
                if (!msg.isError()) {
                    msg.addMsgOk("Barrio modificado exitosamente");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public List<dtoBarrioGrid> listarBarrios() {
        List<dtoBarrioGrid> colBarrios = null;
        try {
            colBarrios = new ArrayList<>();
            List<Barrio> colDataBarrios = DataAccess.Persistencia.PBarrio.newInstancePBarrio().listarBarrios();
            if (colDataBarrios != null) {
                colBarrios = barrioToDtoBarrio(colDataBarrios);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colBarrios;
    }

    private dtoMensaje validarBarrio(dtoBarrio barrio, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (barrio != null) {
            for (Field propiedad : barrio.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idBarrio":
                            if (!isInsert) {
                                if (barrio.idBarrio == null) {
                                    throw new Exception("Identificador del barrio requerido");
                                } else if (DataAccess.Persistencia.PBarrio.newInstancePBarrio().getBarrioById(barrio.idBarrio) == null) {
                                    throw new Exception("El barrio no existe");
                                }
                            }
                            break;
                        case "nombre":
                            if (barrio.nombre == null || barrio.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }
                            if (barrio.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (barrio.idBarrio != null) {
                                if (!isInsert) {
                                    Barrio comprobarBarrio = DataAccess.Persistencia.PBarrio.newInstancePBarrio().getBarrioById(barrio.idBarrio);
                                    if (comprobarBarrio != null) {
                                        if (!comprobarBarrio.getNombre().equals(barrio.nombre)) {
                                            if (DataAccess.Persistencia.PBarrio.newInstancePBarrio().existeBarrio(barrio.nombre)) {
                                                throw new Exception("El barrio ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PBarrio.newInstancePBarrio().existeBarrio(barrio.nombre)) {
                                    throw new Exception("El barrio ya existe");
                                }
                            }
                            break;
                        case "descripcion":
                            if(barrio.descripcion != null && !barrio.descripcion.equals(""))
                              {
                            if (barrio.descripcion.length() > 256) {
                                throw new Exception("La descripcion supera los 256 caracteres");
                            }
                              }
                            break;
                        case "idMunicipio":
                            if (barrio.idMunicipio == null) {
                                throw new Exception("Municipio requerido");
                            }
                            if (DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(barrio.idMunicipio) == null) {
                                throw new Exception("El municipio no existe");
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

    public List<dtoBarrioGrid> barrioToDtoBarrio(List<Barrio> colDataBarrios) {
        List<dtoBarrioGrid> colBarrios = new ArrayList<>();
        try {
            for (Barrio b : colDataBarrios) {
                dtoBarrioGrid dtoBarrio = new dtoBarrioGrid();
                dtoBarrio.idBarrio = b.getIdBarrio();
                dtoBarrio.nombre = b.getNombre();
                dtoBarrio.descripcion = b.getDescripcion();
                dtoBarrio.estado = (b.isEstado() == true) ? "Activo" : "Inactivo";
                dtoBarrio.idMunicipio = b.getMunicipio().getIdMunicipio();
                dtoBarrio.nombreMunicipio = b.getMunicipio().getNombre();
                colBarrios.add(dtoBarrio);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colBarrios;
    }
}
