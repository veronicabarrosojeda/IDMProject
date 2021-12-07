
package Business.Logic.Controllers;

import Business.Entities.Empresa;
import Common.Constant.CError;
import Common.DTO.dtoEmpresa;
import Common.DTO.dtoMensaje;
import DataAccess.Persistencia.PEmpresa;
import java.util.ArrayList;
import java.util.List;
import Common.Enums.Status;
import java.lang.reflect.Field;

public class CEmpresa {

    private static CEmpresa ICEmpresa;

    public static CEmpresa getNewInstanceCEmpresa() {
        if (ICEmpresa == null) {
            return new CEmpresa();
        }
        return ICEmpresa;
    }

    public dtoMensaje altaEmpresa(dtoEmpresa empresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarEmpresa(empresa, true);
            if (!msg.isError()) {
                Empresa nuevaEmpresa = new Empresa();
                nuevaEmpresa.setNombre(empresa.nombre);
                nuevaEmpresa.setRut(empresa.rut);
                nuevaEmpresa.setDescripcion(empresa.descripcion);
                nuevaEmpresa.setEstado(true);
                msg = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().altaEmpresa(nuevaEmpresa);
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public dtoMensaje modificarEmpresa(dtoEmpresa dtoEmpresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarEmpresa(dtoEmpresa, false);
            if (!msg.isError()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(dtoEmpresa.idEmpresa);
                empresa.setNombre(dtoEmpresa.nombre);
                empresa.setRut(dtoEmpresa.rut);
                empresa.setDescripcion(dtoEmpresa.descripcion);
                empresa.setEstado(dtoEmpresa.estado);
                msg = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().modificarEmpresa(empresa);
                if (!msg.isError()) {
                    msg.addMsgOk("Empresa modificada correctamente");
                }
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }

        return msg;
    }

    public dtoMensaje bajaEmpresa(Integer idEmpresa) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (idEmpresa != null) {
                Empresa empresa = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaById(idEmpresa);
                if (empresa != null) {
                    msg = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().bajaEmpresa(idEmpresa);
                    if (!msg.isError()) {
                        msg.addMsgOk("La empresa fue dada de baja correctamente!");
                    }
                } else {
                    msg.addMsgError("La empresa no existe");
                }
            } else {
                msg.addMsgError("Identificador de la empresa requerido");
            }
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

    public List<dtoEmpresa> getEmpresas() {
        List<dtoEmpresa> colDtoEmp = null;
        try {
            colDtoEmp = new ArrayList<>();
            List<Empresa> colEmp = PEmpresa.newInstancePEmpresa().getEmpresas();
            if (colEmp != null) {
                colDtoEmp = EmpresaToDto(colEmp);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colDtoEmp;
    }

    public dtoMensaje getEmpresasByUser(Integer idUsuario){
       dtoMensaje msg = new dtoMensaje();
        try {
            if (idUsuario != null) {
               List<dtoEmpresa> colDtoEmp = new ArrayList<>();
                List<Empresa> colEmp = PEmpresa.newInstancePEmpresa().getEmpresasByUser(idUsuario);
                if (colEmp != null) {
                    colDtoEmp = EmpresaToDto(colEmp);
                    msg.colEmp =colDtoEmp;
                }else{
                    msg.addMsgError(CError.ErrorIn);
                }
            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }

    public List<dtoEmpresa> getEmpresasByService(Long idServicio) {
        List<dtoEmpresa> colDtoEmp = null;
        try {
            if (idServicio != null) {
                colDtoEmp = new ArrayList<>();
                List<Empresa> colEmp = PEmpresa.newInstancePEmpresa().getEmpresasByService(idServicio);
                if (colEmp != null) {
                    colDtoEmp = EmpresaToDto(colEmp);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colDtoEmp;
    }

    public List<dtoEmpresa> EmpresaToDto(List<Empresa> colEmp) {
        List<dtoEmpresa> colDtoEmp = new ArrayList<>();
        try {
            for (Empresa e : colEmp) {
                dtoEmpresa dtoE = new dtoEmpresa();
                dtoE.idEmpresa = e.getIdEmpresa();
                dtoE.nombre = e.getNombre();
                dtoE.descripcion = e.getDescripcion();
                dtoE.rut = e.getRut();
                dtoE.estado = e.isEstado();
                colDtoEmp.add(dtoE);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colDtoEmp;
    }

    private dtoMensaje validarEmpresa(dtoEmpresa empresa, boolean isInsert) {
        dtoMensaje msg = new dtoMensaje();
        if (empresa != null) {
            for (Field propiedad : empresa.getClass().getFields()) {
                try {
                    switch (propiedad.getName()) {
                        case "idEmpresa":
                            if (!isInsert) {
                                if (empresa.idEmpresa == null) {
                                    throw new Exception("Identificador de la empresa requerido");
                                } else if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaById(empresa.idEmpresa) == null) {
                                    throw new Exception("La empresa no existe");
                                }
                            }
                            break;
                        case "nombre":
                            if (empresa.nombre == null || empresa.nombre.equals("")) {
                                throw new Exception("Nombre requerido");
                            }
                            if (empresa.nombre.length() > 25) {
                                throw new Exception("El nombre supera los 25 caracteres");
                            }
                            if (empresa.idEmpresa != null) {
                                if (!isInsert) {
                                    Empresa comprobarEmpresa = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaById(empresa.idEmpresa);
                                    if (comprobarEmpresa != null) {
                                        if (!comprobarEmpresa.getNombre().equals(empresa.nombre)) {
                                            if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().findEmpresaByName(empresa.nombre)) {
                                                throw new Exception("La empresa ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().findEmpresaByName(empresa.nombre)) {
                                    throw new Exception("La empresa ya existe");
                                }
                            }
                            break;
                        case "descripcion":
                            if (empresa.descripcion != null && !empresa.descripcion.equals("")) {
                                if (empresa.descripcion.length() > 256) {
                                    throw new Exception("La descripcion supera los 256 caracteres");
                                }
                            }
                            break;
                        case "rut":
                            if (empresa.rut == null || empresa.rut.equals("")) {
                                throw new Exception("Rut requerido");
                            }
                            if (empresa.idEmpresa != null) {
                                if (!isInsert) {
                                    Empresa comprobarEmpresa = DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaById(empresa.idEmpresa);
                                    if (comprobarEmpresa != null) {
                                        if (comprobarEmpresa.getRut() != empresa.rut) {
                                            if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaByRut(empresa.rut)) {
                                                throw new Exception("La empresa ya existe");
                                            }
                                        }
                                    }
                                }
                            }
                            if (isInsert) {
                                if (DataAccess.Persistencia.PEmpresa.newInstancePEmpresa().getEmpresaByRut(empresa.rut)) {
                                    throw new Exception("La empresa ya existe");
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
