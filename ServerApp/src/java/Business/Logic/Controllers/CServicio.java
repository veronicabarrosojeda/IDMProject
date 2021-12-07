/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Logic.Controllers;

import Business.Entities.Barrio;
import Business.Entities.Serviciosolicitante;
import Business.Entities.Ubicacionservicio;
import Business.Entities.Empresa;
import Business.Entities.Rol;
import Business.Entities.Servicio;
import Business.Entities.Supervisor;
import Business.Entities.Tiposervicio;
import Business.Entities.Usuario;
import Common.Constant.CError;
import Common.DTO.dtoDataGetServicio;
import Common.DTO.dtoEmpresa;
import Common.DTO.dtoEmpresaServicio;
import Common.DTO.dtoGetDataSolicitud;
import Common.DTO.dtoGetServicios;
import Common.DTO.dtoGetServiciosUser;
import Common.DTO.dtoHistorialServicio;
import Common.DTO.dtoListServicioEmpresa;
import Common.DTO.dtoListServicioSupervisor;
import Common.DTO.dtoMensaje;
import Common.DTO.dtoResultServicios;
import Common.DTO.dtoServicio;
import Common.DTO.dtoServicioEntidadesExternas;
import Common.DTO.dtoServicioSolicitante;
import Common.DTO.dtoServicioVista;
import Common.DTO.dtoServiciosEntidadesExternas;
import Common.DTO.dtoSolicitantes;
import Common.DTO.dtoSupervisor;
import Common.DTO.dtoSupervisorServicio;
import Common.DTO.dtoViewServicio;
import Common.DTO.dtoWhereGetServicios;
import DataAccess.Persistencia.PBarrio;
import DataAccess.Persistencia.PServicio;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Common.Enums.Status;
import DataAccess.Persistencia.PEmpresa;
import DataAccess.Persistencia.PListItem;
import DataAccess.Persistencia.PSupervisor;
import DataAccess.Persistencia.PUsuario;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

public class CServicio {

    private static CServicio intanceCServicio;

    public static CServicio getInstanceCServicio() {
        if (intanceCServicio == null) {
            return new CServicio();
        }

        return intanceCServicio;
    }

    public dtoServicioVista getServicioMovil(Long idServicio) {
        List<dtoHistorialServicio> dtoHistorialServicio = new ArrayList<dtoHistorialServicio>();
        dtoServicioVista dtoServ = null;
        try {

            dtoServ = PServicio.newInstancePServicio().getServicioVistaMovil(idServicio);
            if (dtoServ.latitud == 0 || dtoServ.longitud == 0) {
                dtoServ.coordenadas = false;
                dtoServ.ubicacionManual = true;
            } else {
                dtoServ.ubicacionManual = false;
                dtoServ.coordenadas = true;
            }

            dtoHistorialServicio = PServicio.newInstancePServicio().getHistorialServicio(idServicio);
            if (dtoHistorialServicio != null) {
                dtoServ.historial = dtoHistorialServicio;
            }
            return dtoServ;
        } catch (Exception ex) {
            return null;
        }
    }

    public dtoServicioVista getServicio(dtoDataGetServicio data) {
        List<dtoHistorialServicio> dtoHistorialServicio = new ArrayList<dtoHistorialServicio>();
        dtoServicioVista dtoServ = null;
        try {

            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(data.token);

            if (usr != null) {

                dtoServ = PServicio.newInstancePServicio().getServicioVista(data.idServicio);
                if (dtoServ.latitud == 0 || dtoServ.longitud == 0) {
                    dtoServ.coordenadas = false;
                    dtoServ.ubicacionManual = true;
                } else {
                    dtoServ.ubicacionManual = false;
                    dtoServ.coordenadas = true;
                }

                dtoHistorialServicio = PServicio.newInstancePServicio().getHistorialServicio(data.idServicio);
                if (dtoHistorialServicio != null) {
                    dtoServ.historial = dtoHistorialServicio;
                }
            }
            return dtoServ;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<dtoViewServicio> getServiciosUser(dtoGetServiciosUser data) {
        List<dtoViewServicio> colVServUser = new ArrayList<dtoViewServicio>();
        try {
            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(data.token);

            if (usr != null) {
                colVServUser = DataAccess.Persistencia.PServicio.newInstancePServicio().getServiciosUser(data.idUsuario);
            }
        } catch (Exception ex) {
            return null;
        }
        return colVServUser;
    }

    public List<dtoViewServicio> getSolicitudesInEstados(dtoGetDataSolicitud estados) {
        List<dtoViewServicio> colSolicitudes = new ArrayList<dtoViewServicio>();
        try {
            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(estados.token);
            if (usr != null) {
                colSolicitudes = DataAccess.Persistencia.PServicio.newInstancePServicio().getServicios(estados);
            }
        } catch (Exception ex) {
            Logger.getLogger(CServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        return colSolicitudes;
    }

    public List<dtoViewServicio> getSolicitudesInEstadosMovil(dtoGetDataSolicitud estados) {
        List<dtoViewServicio> colSolicitudes = new ArrayList<dtoViewServicio>();
        try {
            colSolicitudes = DataAccess.Persistencia.PServicio.newInstancePServicio().getServicios(estados);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CServicio.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(CServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colSolicitudes;
    }

    public dtoResultServicios getServiciosByTocken(dtoGetServicios data) {
        dtoWhereGetServicios where = new dtoWhereGetServicios();

        dtoResultServicios rest = new dtoResultServicios();
        rest.colServicios = new ArrayList<dtoServiciosEntidadesExternas>();
        try {
            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(data.token);

            if (usr != null) {
                Rol rol = usr.getRol();
                if (rol.getIdRol() == 4) {
                    where.colEmpresas = new ArrayList<String>();
                    List<Empresa> colEmpresas = new ArrayList<Empresa>();
                    colEmpresas.addAll(usr.getEmpresas());
                    where.colEstados = data.estados;
                    where.fechaDesde = data.fechaDesde;
                    where.fechaHasta = data.fechaHasta;

                    for (Empresa emp : colEmpresas) {
                        where.colEmpresas.add(emp.getIdEmpresa().toString());
                    }

                    rest.colServicios = PServicio.newInstancePServicio().getServiciosEntidadesExternas(where);
                    rest.status = Status.success.toString();
                    rest.msg = "OK";
                } else {
                    throw new Exception("Rol del usuario inválido");
                }
            } else {
                throw new Exception("Tocken vencido");
            }
        } catch (Exception ex) {
            rest.status = Status.danger.toString();
            rest.msg = ex.getMessage();
        }
        return rest;
    }

    public List<dtoSolicitantes> getSolicitantes(long idServicio) {
        List<dtoSolicitantes> colSolicitantes = new ArrayList<dtoSolicitantes>();
        try {
            colSolicitantes = DataAccess.Persistencia.PServicio.newInstancePServicio().getSolicitantesServicio(idServicio);
        } catch (Exception ex) {
            return null;
        }
        return colSolicitantes;
    }

    public Date getDiaActual() throws ParseException {
        Date date;
        date = Calendar.getInstance().getTime();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formato.parse(modifiedDate);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return date;
    }

    public List<dtoListServicioEmpresa> getSupervisorEmpresa(Integer idEmpresa) {
        List<dtoListServicioEmpresa> colServicios = new ArrayList<dtoListServicioEmpresa>();
        try {
            colServicios = DataAccess.Persistencia.PServicio.newInstancePServicio().getSupervisorEmpresa(idEmpresa);
        } catch (Exception ex) {
            return null;
        }
        return colServicios;

    }

    public List<dtoListServicioSupervisor> getSupervisorServicios(int idSupervisor) {

        List<dtoListServicioSupervisor> colSupervisorServicio = new ArrayList<dtoListServicioSupervisor>();
        try {
            colSupervisorServicio = DataAccess.Persistencia.PServicio.newInstancePServicio().getSupervisorServicios(idSupervisor);
        } catch (Exception ex) {
            return null;
        }
        return colSupervisorServicio;

    }

    public dtoMensaje actualizarEmpresasServicio(dtoEmpresaServicio empServ) {
        dtoMensaje msg = new dtoMensaje();
        try {
            PEmpresa.newInstancePEmpresa().deleteEmpresasToService(empServ.idServicio);
            Servicio serv = PServicio.newInstancePServicio().getServicioById(empServ.idServicio);
            List<Empresa> colEmp = EmpresaToDto(empServ.empresas);
            DataAccess.Persistencia.PServicio.newInstancePServicio().updateEmpresasServicio(serv, colEmp);
            msg.msg = "Operación relizada con éxito";
            msg.tipo = Status.success.toString();
        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }

        return msg;
    }

    public dtoMensaje actualizarSupervisorServicio(dtoSupervisorServicio supServ) {
        dtoMensaje msg = new dtoMensaje();
        try {
            if (supServ != null || supServ.idServicio != null || supServ.supervisores != null) {
                if (supServ.token == null || supServ.token == "" || supServ.token.equals("")) {
                    msg.addMsgError(CError.ErrorAut + " 20C3");
                } else {
                    CUsuario cUsr = new CUsuario();
                    Usuario usr = cUsr.getUserByToken(supServ.token);
                    if (usr != null) {
                        PSupervisor.newInstancePSupervisor().deleteSupervisoresToService(supServ.idServicio);
                        Servicio serv = PServicio.newInstancePServicio().getServicioById(supServ.idServicio);
                        List<Supervisor> colSup = dtoSupervisorToSupervisor(supServ.supervisores);
                        PServicio.newInstancePServicio().updateSupervisoresServicio(serv, colSup);
                        msg.addMsgOk("Supervisores" + CError.modificados);
                    } else {
                        msg.addMsgError(CError.ErrorAut + " 20C3");
                    }
                }
            } else {
                msg.addMsgError("Datos " + CError.inexs);
            }
        } catch (Exception ex) {
            msg.addMsgError(CError.ErrorIn + " 20C4");
        }
        return msg;
    }

    public List<Empresa> EmpresaToDto(List<dtoEmpresa> colDtoEmp) {
        List<Empresa> colEmp = new ArrayList<Empresa>();
        for (dtoEmpresa e : colDtoEmp) {
            Empresa dtoE = new Empresa();
            dtoE.setIdEmpresa(e.idEmpresa);
            dtoE.setNombre(e.nombre);
            dtoE.setDescripcion(e.descripcion);
            dtoE.setRut(e.rut);
            dtoE.setEstado(e.estado);

            colEmp.add(dtoE);
        }

        return colEmp;
    }

    public List<Supervisor> dtoSupervisorToSupervisor(List<dtoSupervisor> colSupervisores) {

        List<Supervisor> colSup = new ArrayList<Supervisor>();
        try {
            for (dtoSupervisor sup : colSupervisores) {
                Supervisor s = new Supervisor();
                s.setApellido(sup.apellido);
                s.setNombre(sup.nombre);
                s.setIdSupervisor(sup.idSupervisor);
                colSup.add(s);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return colSup;
    }

    public Servicio dtoServiceToService(dtoServicio dtoSer) {
        Servicio ser = new Servicio();

        return ser;
    }

    public dtoMensaje altaServicio(dtoServicio dtoSer) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarServicio(dtoSer);

            if (!msg.isError()) {
                Ubicacionservicio ubicacion = new Ubicacionservicio();
                if (dtoSer.ubicacionservicio != null) {
                    if (dtoSer.ubicacionservicio.coordenadas == true) {
                        ubicacion.setLatitud(dtoSer.ubicacionservicio.latitud);
                        ubicacion.setLongitud(dtoSer.ubicacionservicio.longitud);
                        Barrio bar = PBarrio.newInstancePBarrio().getBarrioById(dtoSer.idBarrio);
                        ubicacion.setBarrio(bar);
                    } else {
                        Barrio bar = PBarrio.newInstancePBarrio().getBarrioById(dtoSer.idBarrio);
                        ubicacion.setBarrio(bar);
                        ubicacion.setCalle(dtoSer.ubicacionservicio.calle);
                        ubicacion.setEntreCalles(dtoSer.ubicacionservicio.entreCalles);
                        ubicacion.setApto(dtoSer.ubicacionservicio.apto);
                        ubicacion.setNroManzana(dtoSer.ubicacionservicio.nroManzana);
                        ubicacion.setNroPadron(dtoSer.ubicacionservicio.nroPadron);
                        ubicacion.setNroPuerta(dtoSer.ubicacionservicio.nroPuerta);
                        ubicacion.setNroSolar(dtoSer.ubicacionservicio.nroSolar);
                    }

                    Integer i = 1;
                    short s = i.shortValue();
                    Servicio serv = new Servicio();
                    serv.setUbicacionservicio(PServicio.newInstancePServicio().altaUbicacionServicio(ubicacion));
                    serv.setEstado(s);
                    serv.setDescripcion(dtoSer.descripcion);
                    serv.setFechaIngreso(getDiaActual());
                    serv.setFechaModificacion(getDiaActual());
                    serv.setTiposervicio(PServicio.newInstancePServicio().getTipoServicioById(dtoSer.idCategoria));

                    if (dtoSer.tipoOrigen == 1) {
                        serv.setIdUsuarioFuncionario(dtoSer.idUsuario);
                        serv.setTipoOrigen(1);
                    }

                    Serviciosolicitante servSol = new Serviciosolicitante();
                    servSol.setNombreSolicitante(dtoSer.nombre);
                    servSol.setCorreoSolicitante(dtoSer.correo);
                    servSol.setNroIdentidad(dtoSer.nroIdentidad);
                    servSol.setTipoIdentidad(dtoSer.tipoIdentidad);
                    servSol.setTelefono(dtoSer.telefono);
                    servSol.setServicio(PServicio.newInstancePServicio().altaServicio(serv));
                    PServicio.newInstancePServicio().altaServicioSolicitante(servSol);
                    msg.addMsgOk("Solicitud de servicio ingresada correctamente.");
                } else {
                    msg.addMsgError("Debe ingresar una ubicación");
                }
            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public dtoMensaje altaServicioMovil(dtoServicio dtoSer) {
        dtoMensaje msg = new dtoMensaje();
        try {
            msg = validarServicioMovil(dtoSer);

            if (!msg.isError()) {
                Ubicacionservicio ubicacion = new Ubicacionservicio();
                if (dtoSer.ubicacionservicio.coordenadas == true) {
                    ubicacion.setLatitud(dtoSer.ubicacionservicio.latitud);
                    ubicacion.setLongitud(dtoSer.ubicacionservicio.longitud);
                    //Barrio bar = null;
                    ubicacion.setBarrio(null);
                } else {
                    Barrio bar = PBarrio.newInstancePBarrio().getBarrioById(dtoSer.idBarrio);
                    ubicacion.setBarrio(bar);
                    ubicacion.setCalle(dtoSer.ubicacionservicio.calle);
                    ubicacion.setEntreCalles(dtoSer.ubicacionservicio.entreCalles);
                    ubicacion.setApto(dtoSer.ubicacionservicio.apto);
                    ubicacion.setNroManzana(dtoSer.ubicacionservicio.nroManzana);
                    ubicacion.setNroPadron(dtoSer.ubicacionservicio.nroPadron);
                    ubicacion.setNroPuerta(dtoSer.ubicacionservicio.nroPuerta);
                    ubicacion.setNroSolar(dtoSer.ubicacionservicio.nroSolar);
                }

                Integer i = 1;
                short s = i.shortValue();
                Servicio serv = new Servicio();
                serv.setUbicacionservicio(PServicio.newInstancePServicio().altaUbicacionServicio(ubicacion));
                serv.setEstado(s);
                serv.setDescripcion(dtoSer.descripcion);
                serv.setFechaIngreso(getDiaActual());
                serv.setFechaModificacion(getDiaActual());
                serv.setTiposervicio(PServicio.newInstancePServicio().getTipoServicioById(dtoSer.idCategoria));

                if (dtoSer.tipoOrigen == 2) {
                    serv.setIdUsuarioFuncionario(dtoSer.idUsuario);
                    serv.setTipoOrigen(2);
                }

                Serviciosolicitante servSol = new Serviciosolicitante();
                servSol.setCorreoSolicitante(dtoSer.correo);
                servSol.setNroIdentidad(dtoSer.nroIdentidad);
                servSol.setTipoIdentidad(dtoSer.tipoIdentidad);
                servSol.setTelefono(dtoSer.telefono);
                servSol.setNombreSolicitante(dtoSer.nombre);
                servSol.setUsuario(PUsuario.newInstancePUsuario().getUserById(dtoSer.idUsuario));

                Servicio servN = PServicio.newInstancePServicio().altaServicio(serv);
                servSol.setServicio(servN);

                PServicio.newInstancePServicio().altaServicioSolicitante(servSol);
                if (dtoSer.imagen != null) {
                    if (dtoSer.imagen != "") {
                        String rutaImagen = "/home/alvaro/proyectoidm/ServerApp/web/STORAGE/" + servN.getIdServicio() + ".jpeg";
                        String rutaBase = "/ServerApp/STORAGE/" + servN.getIdServicio() + ".jpeg";
                        byte[] data = Base64.decodeBase64(dtoSer.imagen);
                        try (OutputStream stream = new FileOutputStream(rutaImagen)) {
                            stream.write(data);
                            servN.setRutaImagen(rutaBase);
                            PServicio.newInstancePServicio().cargarRutaImg(serv);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (dtoSer.idUsuario != 0) {
                    msg.addMsgOk("Solicitud de servicio ingresada correctamente, puede seguir el estado de la misma desde la opción MIS SOLICITUDES en el menú.<br><br> Número de solicitud: " + servN.getIdServicio());
                } else {
                    msg.addMsgOk("Solicitud de servicio ingresada correctamente, puede seguir el estado de la misma desde la opción SEGUIMIENTO en el menú.<br><br> Número de solicitud: " + servN.getIdServicio());
                }

            }
        } catch (Exception ex) {
            msg.addMsgError("Error no controlado");
        }
        return msg;
    }

    public dtoMensaje unirseServicioMovil(dtoServicioSolicitante dtoServSol) {
        dtoMensaje msg = new dtoMensaje();

        //CONTROLA QUE EL USUARIO YA NO SE HAYA UNIDO AL RECLAMO
        Serviciosolicitante existeUnion = PServicio.newInstancePServicio().getServiciosolicitante(dtoServSol.idServicio, dtoServSol.idUsuario);
        if (existeUnion != null) {
            msg.addMsgOk("EXISTE");

        } else {

            try {

                CUsuario cUsr = new CUsuario();
                Usuario usr = cUsr.getUserByToken(dtoServSol.token);
                if (usr != null) {
                    Serviciosolicitante servSol = new Serviciosolicitante();
                    servSol.setCorreoSolicitante(dtoServSol.correoSolicitante);
                    servSol.setNroIdentidad(dtoServSol.nroIdentidad);
                    servSol.setTipoIdentidad(dtoServSol.tipoIdentidad);
                    servSol.setTelefono(dtoServSol.telefono);
                    servSol.setNombreSolicitante(dtoServSol.nombreSolicitante);
                    servSol.setUsuario(PUsuario.newInstancePUsuario().getUserById(dtoServSol.idUsuario));
                    servSol.setServicio(PServicio.newInstancePServicio().getServicioById(dtoServSol.idServicio));
                    PServicio.newInstancePServicio().altaServicioSolicitante(servSol);

                    msg.addMsgOk("OK");
                }

            } catch (Exception ex) {
                msg.addMsgError("Error no controlado");
            }

        }
        return msg;
    }

    public dtoMensaje validarServicioMovil(dtoServicio servicio) {

        dtoMensaje msg = new dtoMensaje();
        Boolean n = false;
        int t = 0;

        Field[] propServicio = servicio.getClass().getFields();
        int cantidadCampos = propServicio.length;
        List<String> campos = new ArrayList<>();
        for (int i = 0; i < cantidadCampos; i++) {
            campos.add(i, propServicio[i].getName());
        }
        t = campos.size();
        if (servicio.ubicacionservicio != null) {
            Field[] propUbicacionServicio = servicio.ubicacionservicio.getClass().getFields();

            int cantidadCamposUbicacion = propUbicacionServicio.length;
            for (int i = 0; i < cantidadCamposUbicacion; i++) {
                campos.add(t, propUbicacionServicio[i].getName());
                t++;
            }
            t = campos.size();
        }
        for (int i = 0; i < t; i++) {
            try {

                switch (campos.get(i)) {

                    case "nroIdentidad":

                        if (servicio.nroIdentidad == null) {
                            throw new Exception("Número de Identidad requerida");
                        }
                        if (servicio.nroIdentidad.length() > 29) {
                            throw new Exception("Número de Identidad supera los 30 caracteres");
                        }
                        break;

                    case "observaciones":
                        if (servicio.observaciones != null && !"".equals(servicio.observaciones)) {
                            if (servicio.observaciones.length() > 512) {
                                throw new Exception("Descripción supera los 512 caracteres");
                            }
                        }
                        break;

                    case "idCategoria":

                        if (servicio.idCategoria == 0) {
                            throw new Exception("Categoría requerida");
                        }
                        break;

                    case "tipoIdentidad":

                        if (servicio.tipoIdentidad == 0) {
                            throw new Exception("Tipo de Identidad requerida");
                        }
                        break;

                    case "telefono":
                        if (servicio.telefono != null && !"".equals(servicio.telefono)) {
                            if (servicio.telefono.length() > 19) {
                                throw new Exception("Teléfono supera los 20 caracteres");
                            }
                        }
                        break;

                    case "descripcion":
                        if (servicio.descripcion != null && !"".equals(servicio.descripcion)) {
                            if (servicio.descripcion.length() > 256) {
                                throw new Exception("Descripción supera los 256 caracteres");
                            }
                        }
                        break;

                    case "correo":
                        if (servicio.correo != null && !"".equals(servicio.correo)) {
                            if (servicio.correo.length() > 99) {
                                throw new Exception("Correo electrónico supera los 100 caracteres");
                            }
                        }
                        break;

                    case "calle":
                        if (servicio.ubicacionservicio != null) {
                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.calle == null || "".equals(servicio.ubicacionservicio.calle)) {
                                    throw new Exception("Calle requeridas");
                                } else if (servicio.ubicacionservicio.calle.length() > 256) {
                                    throw new Exception("Calle supera los 256 caracteres");
                                }

                            }
                        }

                        break;

                    case "entreCalles":
                        if (servicio.ubicacionservicio != null) {
                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.entreCalles == null || "".equals(servicio.ubicacionservicio.entreCalles)) {
                                    throw new Exception("Entre calles requeridas");
                                } else if (servicio.ubicacionservicio.entreCalles.length() > 256) {
                                    throw new Exception("Entre calles supera los 256 caracteres");
                                }
                            }
                        }

                        break;

                    case "idMunicipio":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.idMunicipio == 0) {
                                    throw new Exception("Municipio requerido");
                                }
                            }
                        }
                        break;

                    case "idBarrio":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.idBarrio == 0) {
                                    throw new Exception("Barrio requerido");
                                }
                            }
                        }
                        break;

                    case "apto":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.apto != null && !"".equals(servicio.ubicacionservicio.apto)) {
                                    if (servicio.ubicacionservicio.apto.length() > 4) {
                                        throw new Exception("Número de apartamento supera los 5 caracteres");
                                    }
                                }
                            }
                        }
                        break;

                    case "nroManzana":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.nroManzana != null && !"".equals(servicio.ubicacionservicio.nroManzana)) {
                                    if (servicio.ubicacionservicio.nroManzana.length() > 14) {
                                        throw new Exception("Número de manzana supera los 15 caracteres");
                                    }
                                }
                            }
                        }
                        break;

                    case "nroPadron":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.nroPadron != null && !"".equals(servicio.ubicacionservicio.nroPadron)) {
                                    if (servicio.ubicacionservicio.nroPadron.length() > 14) {
                                        throw new Exception("Número de padrón supera los 15 caracteres");
                                    }
                                }
                            }
                        }
                        break;

                    case "nroPuerta":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.nroPuerta != null && !"".equals(servicio.ubicacionservicio.nroPuerta)) {
                                    if (servicio.ubicacionservicio.nroPuerta.length() > 14) {
                                        throw new Exception("Número de puerta supera los 15 caracteres");
                                    }
                                }
                            }
                        }
                        break;

                    case "nroSolar":
                        if (servicio.ubicacionservicio != null) {

                            if (servicio.ubicacionservicio.ubicacionManual) {
                                if (servicio.ubicacionservicio.nroSolar != null && !"".equals(servicio.ubicacionservicio.nroSolar)) {
                                    if (servicio.ubicacionservicio.nroSolar.length() > 14) {
                                        throw new Exception("Número de solar supera los 15 caracteres");
                                    }
                                }
                            }
                        }
                        break;

                    case "latitud":
                        if (servicio.ubicacionservicio != null) {
                            if (servicio.ubicacionservicio.coordenadas) {
                                if (servicio.ubicacionservicio.latitud == null || servicio.ubicacionservicio.latitud == 0
                                        || servicio.ubicacionservicio.longitud == null || servicio.ubicacionservicio.longitud == 0) {
                                    throw new Exception("Punto en el mapa requerido");
                                }
                            }
                        }
                        break;
                }
            } catch (Exception ex) {
                msg.addMsgError(ex.getMessage());
            }
        }
        return msg;

    }

    public dtoMensaje validarServicio(dtoServicio servicio) {
        dtoMensaje msg = new dtoMensaje();
        Boolean n = false;
        int t = 0;

        try {
            Field[] propServicio = servicio.getClass().getFields();
            int cantidadCampos = propServicio.length;
            List<String> campos = new ArrayList<>();
            for (int i = 0; i < cantidadCampos; i++) {
                campos.add(i, propServicio[i].getName());
            }
            t = campos.size();
            if (servicio.ubicacionservicio != null) {
                Field[] propUbicacionServicio = servicio.ubicacionservicio.getClass().getFields();

                int cantidadCamposUbicacion = propUbicacionServicio.length;
                for (int i = 0; i < cantidadCamposUbicacion; i++) {
                    campos.add(t, propUbicacionServicio[i].getName());
                    t++;
                }
                t = campos.size();
            }

            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(servicio.token);

            if (usr == null) {
                throw new Exception("Token Inválido");
            }

            for (int i = 0; i < t; i++) {
                try {

                    switch (campos.get(i)) {

                        case "nombre":

                            if (servicio.nombre != null && !"".equals(servicio.nombre)) {
                                if (servicio.nombre.length() > 100) {
                                    throw new Exception("Nombre supera los 100 caracteres");
                                }
                            }
                            break;

                        case "nroIdentidad":

                            if (servicio.nroIdentidad == null) {
                                throw new Exception("Número de Identidad requerida");
                            }
                            if (servicio.nroIdentidad.length() > 29) {
                                throw new Exception("Número de Identidad supera los 30 caracteres");
                            }
                            break;

                        case "observaciones":
                            if (servicio.observaciones != null && !"".equals(servicio.observaciones)) {
                                if (servicio.observaciones.length() > 512) {
                                    throw new Exception("Descripción supera los 512 caracteres");
                                }
                            }
                            break;

                        case "idCategoria":

                            if (servicio.idCategoria == 0) {
                                throw new Exception("Categoría requerida");
                            }
                            break;

                        case "tipoIdentidad":

                            if (servicio.tipoIdentidad == 0) {
                                throw new Exception("Tipo de Identidad requerida");
                            }
                            break;

                        case "telefono":
                            if (servicio.telefono != null && !"".equals(servicio.telefono)) {
                                if (servicio.telefono.length() > 19) {
                                    throw new Exception("Teléfono supera los 20 caracteres");
                                }
                            }
                            break;

                        case "descripcion":
                            if (servicio.descripcion != null && !"".equals(servicio.descripcion)) {
                                if (servicio.descripcion.length() > 256) {
                                    throw new Exception("Descripción supera los 256 caracteres");
                                }
                            }
                            break;

                        case "correo":
                            if (servicio.correo != null && !"".equals(servicio.correo)) {
                                if (servicio.correo.length() > 99) {
                                    throw new Exception("Correo electrónico supera los 100 caracteres");
                                }
                            }
                            break;

                        case "calle":
                            if (servicio.ubicacionservicio != null) {
                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.calle == null || "".equals(servicio.ubicacionservicio.calle)) {
                                        throw new Exception("Calle requeridas");
                                    } else if (servicio.ubicacionservicio.calle.length() > 256) {
                                        throw new Exception("Calle supera los 256 caracteres");
                                    }

                                }
                            }

                            break;

                        case "entreCalles":
                            if (servicio.ubicacionservicio != null) {
                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.entreCalles == null || "".equals(servicio.ubicacionservicio.entreCalles)) {
                                        throw new Exception("Entre calles requeridas");
                                    } else if (servicio.ubicacionservicio.entreCalles.length() > 256) {
                                        throw new Exception("Entre calles supera los 256 caracteres");
                                    }
                                }
                            }

                            break;

                        case "idMunicipio":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.idMunicipio == 0) {
                                        throw new Exception("Municipio requerido");
                                    }
                                }
                            }
                            break;

                        case "idBarrio":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.idBarrio == 0) {
                                        throw new Exception("Barrio requerido");
                                    }
                                }
                            }
                            break;

                        case "apto":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.apto != null && !"".equals(servicio.ubicacionservicio.apto)) {
                                        if (servicio.ubicacionservicio.apto.length() > 4) {
                                            throw new Exception("Número de apartamento supera los 5 caracteres");
                                        }
                                    }
                                }
                            }
                            break;

                        case "nroManzana":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.nroManzana != null && !"".equals(servicio.ubicacionservicio.nroManzana)) {
                                        if (servicio.ubicacionservicio.nroManzana.length() > 14) {
                                            throw new Exception("Número de manzana supera los 15 caracteres");
                                        }
                                    }
                                }
                            }
                            break;

                        case "nroPadron":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.nroPadron != null && !"".equals(servicio.ubicacionservicio.nroPadron)) {
                                        if (servicio.ubicacionservicio.nroPadron.length() > 14) {
                                            throw new Exception("Número de padrón supera los 15 caracteres");
                                        }
                                    }
                                }
                            }
                            break;

                        case "nroPuerta":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.nroPuerta != null && !"".equals(servicio.ubicacionservicio.nroPuerta)) {
                                        if (servicio.ubicacionservicio.nroPuerta.length() > 14) {
                                            throw new Exception("Número de puerta supera los 15 caracteres");
                                        }
                                    }
                                }
                            }
                            break;

                        case "nroSolar":
                            if (servicio.ubicacionservicio != null) {

                                if (servicio.ubicacionservicio.ubicacionManual) {
                                    if (servicio.ubicacionservicio.nroSolar != null && !"".equals(servicio.ubicacionservicio.nroSolar)) {
                                        if (servicio.ubicacionservicio.nroSolar.length() > 14) {
                                            throw new Exception("Número de solar supera los 15 caracteres");
                                        }
                                    }
                                }
                            }
                            break;

                        case "latitud":
                            if (servicio.ubicacionservicio != null) {
                                if (servicio.ubicacionservicio.coordenadas) {
                                    if (servicio.ubicacionservicio.latitud == null || servicio.ubicacionservicio.latitud == 0
                                            || servicio.ubicacionservicio.longitud == null || servicio.ubicacionservicio.longitud == 0) {
                                        throw new Exception("Punto en el mapa requerido");
                                    }
                                }
                            }
                            break;
                    }

                } catch (Exception ex) {
                    msg.addMsgError(ex.getMessage());
                }

            }
            if (servicio.ubicacionservicio == null) {
                throw new Exception("Ubicación requerida");
            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;

    }

    public dtoMensaje modificarServicio(dtoServicio servicio) {
        dtoMensaje msg = new dtoMensaje();
        try {

            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(servicio.token);

            if (usr != null) {
                Servicio se = PServicio.newInstancePServicio().getServicioById(servicio.idServicio);
                if (se.getEstado() != 2 && se.getEstado() != 4) {

                    dtoServicioVista ser = PServicio.newInstancePServicio().getServicioVista(servicio.idServicio);

                    servicio.fechaCambioEstado = getDiaActual();
                    msg = PServicio.newInstancePServicio().modificarServicio(servicio);

                    if (ser.idTipoServicio != servicio.idCategoria) {
                        //BORRA EMPRESAS ASIGNADAS
                        PEmpresa.newInstancePEmpresa().deleteEmpresasToService(servicio.idServicio);
                        Tiposervicio tipo = PServicio.newInstancePServicio().getTipoServicioById(servicio.idCategoria);
                        //ASIGNA EMPRESA
                        if (tipo.getIdEmpresa() != null) {
                            PServicio.newInstancePServicio().updateEmpresaServicio(servicio.idServicio, tipo.getIdEmpresa());
                        }
                    }
                    //NOTIFICACIONES
                    if (ser.estado != servicio.estado) {
                        List<dtoSolicitantes> solicitantes = PServicio.newInstancePServicio().getSolicitantesServicio(servicio.idServicio);

                        for (dtoSolicitantes s : solicitantes) {
                            if (s.correoSolicitante != null && !"".equals(s.correoSolicitante) && (s.notificar == null || s.notificar == true)) {
                                String dsEstado = PListItem.getNewInstance().getDetTipoByIdDet(2, servicio.estado).getNombre();
                                CNotificaciones.getInstanceCNotificaciones().notificarCambioEstado(servicio.idServicio, dsEstado, s.correoSolicitante);
                            }
                        }
                    }
                    msg.msg = "Solicitud de servicio modificada con éxito";
                    msg.tipo = Status.success.toString();
                } else {
                    throw new Exception("No se puede modificar una solicitud de servicio finalizada o cancelada");
                }

            }
        } catch (Exception ex) {
            msg.addMsgError(ex.getMessage());
        }
        return msg;
    }

    public dtoMensaje modificarServicioEntidadesExternas(dtoServicioEntidadesExternas servicio) {
        dtoMensaje msg = new dtoMensaje();
        try {
            CUsuario cUsr = new CUsuario();
            Usuario usr = cUsr.getUserByToken(servicio.token);

            if (usr != null) {

                dtoServicioVista ser = PServicio.newInstancePServicio().getServicioVista(servicio.idServicio);
                if (ser.estado != 2 && ser.estado != 4) {
                    Date fecha = getDiaActual();
                    msg = PServicio.newInstancePServicio().modificarServicioEntidadesExternas(fecha, servicio);

                    if (ser.estado != servicio.estado) {
                        List<dtoSolicitantes> solicitantes = PServicio.newInstancePServicio().getSolicitantesServicio(servicio.idServicio);

                        for (dtoSolicitantes s : solicitantes) {
                            if (s.correoSolicitante != null && !"".equals(s.correoSolicitante) && ((s.notificar == null || s.notificar == true))) {
                                CNotificaciones.getInstanceCNotificaciones().notificarCambioEstado(servicio.idServicio, ser.nombre, s.correoSolicitante);
                            }
                        }
                    }
                } else {
                    msg.msg = "No se puede modificar una solicitud de servicio finalizada o cancelada";
                    msg.tipo = Status.danger.toString();
                }
            }

        } catch (Exception ex) {
            msg.msg = "Error no controlado";
            msg.tipo = Status.danger.toString();
        }
        return msg;
    }

}
