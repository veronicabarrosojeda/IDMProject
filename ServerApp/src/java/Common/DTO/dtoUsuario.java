/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common.DTO;

import Business.Entities.Rol;
import java.util.ArrayList;
import java.util.List;

public class dtoUsuario {
    
     public Integer idUsuario;
     public String nombre;
     public String apellido;
     public String nickname;
     public String password;
     public String ritpassword;
     public String token;
     public String correo;
     public boolean estado;
     public String nroIdentidad;
     public Integer tipoIdentidad;
     public boolean isLogged;
     public Integer idRol;
     public dtoRol dtoRol;
     public String status;
     public String msg;
     public List<dtoPermiso> colPermiso;
     public List<dtoEmpresa> colEmpresas;
     public String telefono;
     public String mode;
     public boolean notificar;
}
