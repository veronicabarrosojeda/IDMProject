package Business.Logic.Controllers;

import Business.Entities.Subarea;
import Business.Entities.Area;
import Business.Entities.SubareaId;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoSubArea;
import Common.Enums.Status;
import DataAccess.Persistencia.PArea;
import DataAccess.Persistencia.PSubArea;
import java.util.ArrayList;
import java.util.List;

public class CSubArea {

    private static CSubArea intanceCsubArea;

    public static CSubArea getInstanceCSubArea() {
        if (intanceCsubArea == null) {
            return new CSubArea();
        }

        return intanceCsubArea;
    }

    public dtoMensaje altaSubArea(dtoSubArea dtoSubA) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (dtoSubA == null) {
                throw new Exception("Datos vacios");
            }
            if (dtoSubA.nombre == null) {
                throw new Exception("Nombre requerido");
            }
            Area areaCurrent = PArea.newInstancePArea().getArea(dtoSubA.idArea);
            if (areaCurrent != null) {
                Subarea subAcurrent = PSubArea.newInstancePSubArea().getSubArea(dtoSubA);
                if (subAcurrent == null) {
                    Subarea subA = new Subarea();
                    SubareaId subI = new SubareaId();
                    subI.setIdArea(areaCurrent.getIdArea());
                    subA.setId(subI);
                    subA.setArea(areaCurrent);
                    subA.setNombre(dtoSubA.nombre);
                    subA.setDescripcion(dtoSubA.descripcion);
                    subA.setEstado(true);
                    msg = PSubArea.newInstancePSubArea().altaSubArea(subA);
                    if (!msg.isError()) {
                        msg.addMsgOk("Subarea ingresada exitosamente ");
                    }
                } else {
                    throw new Exception("Subarea ya existente");
                }
            } else {
                throw new Exception("Area no identificada");
            }
        } catch (Exception ex) {
            msg.msg = ex.getMessage();
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public List<dtoSubArea> SubareaToDtoSubArea(List<Subarea> colDataSubAreas) {
        List<dtoSubArea> colSubAreas = new ArrayList<>();
        try {
            for (Subarea s : colDataSubAreas) {
                dtoSubArea dtosubArea = new dtoSubArea();
                dtosubArea.idSubArea = s.getId().getIdSubArea();
                dtosubArea.idArea = s.getArea().getIdArea();
                dtosubArea.nombre = s.getNombre();
                dtosubArea.descripcion = s.getDescripcion();
                dtosubArea.estado = s.isEstado();
                colSubAreas.add(dtosubArea);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colSubAreas;
    }

    public List<dtoSubArea> getSubAreas() {
        List<dtoSubArea> colSubAreas = new ArrayList<>();
        try {
            List<Subarea> colDataSubAreas = PSubArea.newInstancePSubArea().getSubAreas();
            if (colDataSubAreas != null) {
                colSubAreas = SubareaToDtoSubArea(colDataSubAreas);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colSubAreas;
    }

    public List<dtoSubArea> getSubAreasDeArea(Integer idArea) {
        List<dtoSubArea> colSubAreasDArea = new ArrayList<>();
        try {
            List<Subarea> colDataSubAreasDArea = PSubArea.newInstancePSubArea().getSubAreasDeArea(idArea);
            if (colDataSubAreasDArea != null) {
                colSubAreasDArea = SubareaToDtoSubArea(colDataSubAreasDArea);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colSubAreasDArea;
    }

    public dtoMensaje borrarSubArea(Integer idSubArea) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (idSubArea == null || idSubArea == 0) {
                throw new Exception("Subarea a eliminar no identificada");
            }
            Subarea subAcurr = null;
            subAcurr = PSubArea.newInstancePSubArea().getSubAreaPorID(idSubArea);
            if (subAcurr == null) {
                throw new Exception("Subarea a eliminar inexistente");
            }
            msg = PSubArea.newInstancePSubArea().deleteSubArea(idSubArea);
            if (!msg.isError()) {
                msg.addMsgOk("Subarea eliminada exitosamente");
            }
        } catch (Exception ex) {
            msg.msg = ex.getMessage();
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje modificarSubArea(dtoSubArea dtoSubA) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (dtoSubA == null) {
                throw new Exception("Datos vacios");
            }
            if (dtoSubA.nombre == null || dtoSubA.nombre == "") {
                throw new Exception("Nombre vacio");
            }
            if (dtoSubA.descripcion == null || dtoSubA.descripcion == "") {
                throw new Exception(" Descripción vacia");
            }
            Area areaCurrent = PArea.newInstancePArea().getArea(dtoSubA.idArea);
            if (areaCurrent != null) {
                Subarea subAcurrent = PSubArea.newInstancePSubArea().getSubAreaPorID(dtoSubA.idSubArea);
                if (subAcurrent != null) {
                    Subarea subA = new Subarea();
                    SubareaId subAid = new SubareaId();
                    subAid.setIdArea(areaCurrent.getIdArea());
                    subAid.setIdSubArea(dtoSubA.idSubArea);
                    subA.setId(subAid);
                    subA.setArea(areaCurrent);
                    subA.setNombre(dtoSubA.nombre);
                    subA.setDescripcion(dtoSubA.descripcion);
                    subA.setEstado(true);
                    msg = PSubArea.newInstancePSubArea().modificarSubArea(subA);
                    if (!msg.isError()) {
                        msg.addMsgOk("Subarea modificada exitosamente ");
                    }
                } else {
                    throw new Exception("Subarea a modificar inexistente");
                }
            } else {
                throw new Exception("Area de Subarea no identificada");
            }
        } catch (Exception ex) {
            msg.msg = ex.getMessage();
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }
}
