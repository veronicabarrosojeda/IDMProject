package Business.Logic.Controllers;

import Business.Entities.Barrio;
import Business.Entities.Municipio;
import Common.DTO.dtoBarrioGrid;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoMunicipio;
import DataAccess.Persistencia.PMunicipio;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CMunicipio {

    private static CMunicipio instanceCmunicipio;

    public static CMunicipio getInstanceCMunicipio() {
        if (instanceCmunicipio == null) {
            return new CMunicipio();
        }
        return instanceCmunicipio;
    }

    public dtoMensaje altaMunicipio(dtoMunicipio dtoMun) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarMunicipio(dtoMun, true);
            if (!msg.isError()) {
                Municipio mun = new Municipio();
                mun.setDescripcion(dtoMun.descripcion);
                mun.setNombre(dtoMun.nombre);
                mun.setEstado(true);
                msg = PMunicipio.newInstancePMunicipio().altaMunicipio(mun);
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public dtoMensaje borrarMunicipio(Integer idMunicipio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (idMunicipio != null) {
                Municipio mun = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(idMunicipio);
                if (mun != null) {
                    if (mun.getBarrios().isEmpty()) {
                        msg = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().borrarMunicipio(idMunicipio);
                        if (!msg.isError()) {
                            msg.addMsgOk("Municipio borrado exitosamente");
                        }
                    } else {
                        msg.addMsgError("Existen barrios asociados al municipio. La operacion no fue realizada");
                    }
                } else {
                    msg.addMsgError("El municipio no existe");
                }
            } else {
                msg.addMsgError("Identificador del municipio requerido");
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public dtoMensaje modificarMunicipio(dtoMunicipio dtoMun) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarMunicipio(dtoMun, false);
            if (!msg.isError()) {
                Municipio municipio = new Municipio();
                municipio.setIdMunicipio(dtoMun.idMunicipio);
                municipio.setNombre(dtoMun.nombre);
                municipio.setDescripcion(dtoMun.descripcion);
                municipio.setEstado(true);
                msg = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().modificarMunicipio(municipio);
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public List<dtoMunicipio> getMunicipios() {
        List<dtoMunicipio> colMunicipios = null;
        try {
            colMunicipios = new ArrayList<>();
            List<Municipio> colDataMunicipios = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipios();
            if (colDataMunicipios != null) {
                colMunicipios = municipiosToDtoMunicipios(colDataMunicipios);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colMunicipios;
    }

    public List<dtoBarrioGrid> getBarriosByMunicipio(Integer idMunicipio) {
        List<dtoBarrioGrid> colBarrios = null;
        try {
            colBarrios = new ArrayList<>();
            List<Barrio> colDataBarrios = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getBarriosByMunicipio(idMunicipio);
            if (colDataBarrios != null) {
                colBarrios = Business.Logic.Controllers.CBarrio.getNewInstanceCBarrio().barrioToDtoBarrio(colDataBarrios);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colBarrios;
    }

    public List<dtoMunicipio> municipiosToDtoMunicipios(List<Municipio> colDataMunicipios) {
        List<dtoMunicipio> colMunicipios = null;
        try {
            colMunicipios = new ArrayList<>();
            for (Municipio m : colDataMunicipios) {
                dtoMunicipio dtoMun = new dtoMunicipio();
                dtoMun.idMunicipio = m.getIdMunicipio();
                dtoMun.nombre = m.getNombre();
                dtoMun.descripcion = m.getDescripcion();
                dtoMun.estado = m.isEstado();
                colMunicipios.add(dtoMun);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colMunicipios;
    }

    public dtoMensaje validarMunicipio(dtoMunicipio municipio, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (municipio != null) {
            for (Field propiedad : municipio.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idMunicipio":
                            if (!isInsert) {
                                if (municipio.idMunicipio == null) {
                                    throw new Exception("Identificador del municipio requerido");
                                } else if (DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(municipio.idMunicipio) == null) {
                                    throw new Exception("El municipio no existe");
                                }
                            }
                            break;
                        case "nombre":
                            if (municipio.nombre == null || municipio.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }

                            if (municipio.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (municipio.idMunicipio != null) {
                                if (!isInsert) {
                                    Municipio comprobarMunicipio = DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().getMunicipioByMunicipioId(municipio.idMunicipio);
                                    if (comprobarMunicipio != null) {
                                        if (!comprobarMunicipio.getNombre().equals(municipio.nombre)) {
                                            if (DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().existeMunicipio(municipio.nombre)) {
                                                throw new Exception("El municipio ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PMunicipio.newInstancePMunicipio().existeMunicipio(municipio.nombre)) {
                                    throw new Exception("El municipio ya existe");
                                }
                            }
                            break;
                        case "descripcion":
                            if (municipio.descripcion != null && !municipio.descripcion.equals("")) {
                                if (municipio.descripcion.length() > 256) {
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
