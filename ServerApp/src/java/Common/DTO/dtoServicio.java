/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.DTO;

import Common.Enums.TipoOrigen;
import java.util.Date;


public class dtoServicio {
    
     public long idServicio;
     public dtoTipoServicio tiposervicio;
     public dtoUbicacionServicio ubicacionservicio;
     public String descripcion;
     public short estado;
     public Date fechaIngreso;
     public Date fechaCambioEstado;
     public int idCategoria;
     public int idMunicipio;
     public int idBarrio;
     public int idUsuario;
     public int tipoOrigen;
     public String nombre;
     public String correo;
     public String telefono;
     public String nroIdentidad;
     public int tipoIdentidad;
     public String observaciones;
     public String token;
     public String imagen;
}
